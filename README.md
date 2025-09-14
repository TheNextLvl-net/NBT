# NBT

A small library for reading, writing, and (de)serializing Minecraft-like NBT (Named Binary Tag) data.

This project provides:

- Low-level streaming APIs to read/write NBT with GZIP compression: NBTInputStream and NBTOutputStream.
- A convenient file wrapper NBTFile for loading/saving a CompoundTag from/to disk.
- A flexible, pluggable serialization system (NBT facade) to convert between Java objects and Tag trees using
  serializers/deserializers (aka adapters).

## Installation

Gradle (Kotlin DSL):

- Repository is published to https://repo.thenextlvl.net/#/releases/net/thenextlvl/nbt
- Group/artifact inferred from `build.gradle.kts`

```kts
repositories {
    mavenCentral()
    maven("https://repo.thenextlvl.net/releases/")
}

dependencies {
    implementation("net.thenextlvl:nbt:3.0.0")
}
```

## Core concepts

- Tag: Base type for all NBT values (ByteTag, ShortTag, IntTag, LongTag, FloatTag, DoubleTag, StringTag, ByteArrayTag,
  IntArrayTag, LongArrayTag, ListTag, CompoundTag). All tags know how to read/write themselves from/to streams.
- CompoundTag: A map of name → Tag. Commonly used as the root tag in files.
- NBTInputStream / NBTOutputStream: Low-level, GZIP-compressed streams for reading/writing tags. Strings are encoded
  using the configured Charset (UTF-8 by default).
- NBTFile: Small utility to load/save a CompoundTag from a file path with charset handling.
- Serialization API: The NBT interface that converts between Tag and arbitrary Java
  objects using TagSerializer and TagDeserializer (or a combined TagAdapter).

## Reading NBT files

You can read any NBT file using NBTInputStream. The stream transparently handles GZIP compression.

```java
import net.thenextlvl.nbt.NBTInputStream;
import net.thenextlvl.nbt.tag.CompoundTag;
import net.thenextlvl.nbt.tag.Tag;

import javax.swing.text.html.Option;
import java.io.FileInputStream;
import java.util.Map;
import java.util.Optional;

public class NBTExample {
    public static void readData() throws Exception {
        try (NBTInputStream input = new NBTInputStream(new FileInputStream("data.nbt"))) {
            // Read the root entry (tag and optional name)
            Map.Entry<Tag, Optional<String>> entry = input.readNamedTag();
            Tag root = entry.getKey();
            String rootName = entry.getValue().orElse(null);

            if (root instanceof CompoundTag compound) {
                // Access values by name
                var level = compound.get("Level");
                var data = compound.getAsCompound("Data");
                var list = compound.getAsList("Items");
            }
        }
    }
}
```

> [!TIP]
> - If you only need the tag, use readTag().
> - Unknown tag IDs cause an IllegalArgumentException. You may register custom mappings on NBTInputStream via
    registerMapping(typeId, function).

## Writing NBT files

Use NBTOutputStream to write a named root tag. The stream writes GZIP-compressed output.

```java
import net.thenextlvl.nbt.NBTOutputStream;
import net.thenextlvl.nbt.tag.CompoundTag;

import java.io.FileOutputStream;

public class NBTExample {

    public static void writeData() throws Exception {
        try (var out = new NBTOutputStream(new FileOutputStream("data.nbt"))) {
            CompoundTag root = CompoundTag.builder()
                    .put("Name", "Example")
                    .put("Health", 20)
                    .put("Position", CompoundTag.builder()
                            .put("x", 1)
                            .put("y", 64)
                            .put("z", 1)
                            .build());
            out.writeTag("Root", root); // name can be null
        }
    }
}
```

CompoundTag has a fluent Builder for convenience.

## Using NBTFile helper

If you prefer a small wrapper for file IO, NBTFile<R extends CompoundTag> can load/save and retain the root name.

```java
import core.io.PathIO; // from net.thenextlvl.core:files
import net.thenextlvl.nbt.file.NBTFile;
import net.thenextlvl.nbt.tag.CompoundTag;

public static class NBTExample {

    public static void writeData() throws Exception {
        NBTFile<CompoundTag> file = new NBTFile<>(new PathIO(Path.of("data.nbt")), CompoundTag.empty());
        CompoundTag root = file.get(); // loads if file exists, otherwise returns default root
        String rootName = file.getRootName().orElse(null);

        // modify root ...
        root.add("Updated", true);
        file.setRootName("Root");
        file.save();
    }
}
```

## Serialization: NBT facade

The serialization API turns Java objects into Tags and back. The NBT interface is the entry point. You configure an
instance via `NBT.builder()` and register (de)serializers or combined adapters.

Key types:

- `TagSerializer<T>`: object -> Tag
- `TagDeserializer<T>`: Tag -> object
- `TagAdapter<T>`: both serializer and deserializer in one
- `TagSerializationContext` / `TagDeserializationContext`: provided to your (de)serializers for recursive (de)
  serialization

An `NBT` instance comes with built-in adapters for common types:

- Primitives and boxed: boolean/Boolean, byte/Byte, short/Short, int/Integer, long/Long, float/Float, double/Double
- String, java.io.File, java.nio.file.Path, java.time.Duration, java.net.InetSocketAddress, java.util.UUID

### Quick start

```java
import net.thenextlvl.nbt.serialization.NBT;
import net.thenextlvl.nbt.tag.CompoundTag;
import net.thenextlvl.nbt.tag.Tag;

public record Player(String name, int level) {

    public static void adapt() {
        var nbt = NBT.builder().registerTypeAdapter(Player.class, new TagAdapter<Player>() {
            @Override
            public Tag serialize(Player player, TagSerializationContext ctx) {
                var tag = CompoundTag.empty();
                tag.add("name", player.name());
                tag.add("level", player.level());
                return tag;
            }

            @Override
            public Player deserialize(Tag tag, TagDeserializationContext ctx) {
                var root = tag.getAsCompound();
                var name = root.get("name").getAsString();
                var level = root.get("level").getAsInt();
                return new Player(name, level);
            }
        }).build();

        Tag asTag = nbt.serialize(new Player("Alex", 42));
        Player back = nbt.deserialize(asTag, Player.class);
    }
}
```

You can also register serializer and deserializer separately:

```java
NBT nbt = NBT.builder()
        .registerTypeAdapter(Player.class, (TagSerializer<Player>) (player, context) -> {
            var tag = net.thenextlvl.nbt.tag.CompoundTag.empty();
            tag.add("name", player.name());
            tag.add("level", player.level());
            return tag;
        })
        .registerTypeAdapter(Player.class, (TagDeserializer<Player>) (tag, context) -> {
            var root = tag.getAsCompound();
            return new Player(
                    root.get("name").getAsString(),
                    root.get("level").getAsInt()
            );
        })
        .build();
```

If you need polymorphic handling, register a hierarchy adapter so it also applies to subtypes:

```java
NBT nbt = NBT.builder().registerTypeHierarchyAdapter(Animal.class, new AnimalAdapter()).build(); // applies to all subclasses
```

During (de)serialization, you can call `context.serialize(object)` and `context.deserialize(tag, type)` from within your
custom adapters to handle nested fields using already registered adapters.

## Creating a custom serializer with the NBT class

This example shows how to write a dedicated adapter for a complex type containing nested objects and collections.

```java
import net.thenextlvl.nbt.serialization.*;
import net.thenextlvl.nbt.tag.*;

record Position(int x, int y, int z) {
}

record InventoryItem(String id, int count) {
}

record PlayerData(String name, Position pos, java.util.List<InventoryItem> items) {
}

class PositionAdapter implements TagAdapter<Position> {
    @Override
    public Tag serialize(Position position, TagSerializationContext context) {
        return CompoundTag.builder()
                .put("x", position.x())
                .put("y", position.y())
                .put("z", position.z())
                .build();
    }

    @Override
    public Position deserialize(Tag tag, TagDeserializationContext context) {
        var root = tag.getAsCompound();
        return new Position(
                root.get("x").getAsInt(),
                root.get("y").getAsInt(),
                root.get("z").getAsInt()
        );
    }
}

class InventoryItemAdapter implements TagAdapter<InventoryItem> {
    @Override
    public Tag serialize(InventoryItem item, TagSerializationContext context) {
        return CompoundTag.builder()
                .put("id", item.id())
                .put("count", item.count())
                .build();
    }

    @Override
    public InventoryItem deserialize(Tag tag, TagDeserializationContext context) {
        var root = tag.getAsCompound();
        return new InventoryItem(
                root.get("id").getAsString(),
                root.get("count").getAsInt()
        );
    }
}

class PlayerDataAdapter implements TagAdapter<PlayerData> {
    @Override
    public Tag serialize(PlayerData data, TagSerializationContext context) throws ParserException {
        var list = ListTag.of(CompoundTag.ID);
        for (var it : data.items()) {
            // Let context use InventoryItemAdapter
            list.add(context.serialize(it));
        }
        return CompoundTag.builder()
                .put("name", data.name())
                .put("pos", context.serialize(data.pos())) // delegate to PositionAdapter
                .put("items", list)
                .build();
    }

    @Override
    public PlayerData deserialize(Tag tag, TagDeserializationContext context) throws ParserException {
        var c = tag.getAsCompound();
        var name = c.get("name").getAsString();
        var pos = context.deserialize(c.get("pos"), Position.class);
        var listTag = c.getAsList("items");
        var items = new java.util.ArrayList<InventoryItem>(listTag.size());
        for (var t : listTag) {
            items.add(context.deserialize(t, InventoryItem.class));
        }
        return new PlayerData(name, pos, java.util.List.copyOf(items));
    }
}

var nbt = NBT.builder()
        .registerTypeAdapter(Position.class, new PositionAdapter())
        .registerTypeAdapter(InventoryItem.class, new InventoryItemAdapter())
        .registerTypeAdapter(PlayerData.class, new PlayerDataAdapter())
        .build();

var data = new PlayerData("Alex", new Position(1, 64, 1), java.util.List.of(new InventoryItem("minecraft:stone", 32)));
Tag tag = nbt.serialize(data);
PlayerData back = nbt.deserialize(tag, PlayerData.class);
```

> [!TIP]
> - Use `CompoundTag.Builder` to construct compound values fluently.
> - `ListTag<E extends Tag>` stores tags only; use the context to convert elements.
> - Throw `ParserException` in your (de)serializers to signal invalid data.

## Registering custom tag type mappings for reading

If you introduce your own `Tag` implementation with a custom type ID, you can teach NBTInputStream how to read it:

```java
public static void createCustomTag() throws Exception {
    NBTInputStream input = new NBTInputStream(new FileInputStream("data.nbt"));
    input.registerMapping(MyCustomTag.ID, MyCustomTag::read);
}
```

`NBTOutputStream` will call `Tag#write` on whatever Tag you pass to `writeTag`.
