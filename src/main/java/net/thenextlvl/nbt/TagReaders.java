package net.thenextlvl.nbt;

import net.thenextlvl.nbt.tag.ByteArrayTag;
import net.thenextlvl.nbt.tag.ByteTag;
import net.thenextlvl.nbt.tag.CompoundTag;
import net.thenextlvl.nbt.tag.DoubleTag;
import net.thenextlvl.nbt.tag.FloatTag;
import net.thenextlvl.nbt.tag.IntArrayTag;
import net.thenextlvl.nbt.tag.IntTag;
import net.thenextlvl.nbt.tag.ListTag;
import net.thenextlvl.nbt.tag.LongArrayTag;
import net.thenextlvl.nbt.tag.LongTag;
import net.thenextlvl.nbt.tag.ShortTag;
import net.thenextlvl.nbt.tag.StringTag;
import net.thenextlvl.nbt.tag.Tag;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;

class TagReaders {
    public static StringTag readString(NBTInputStream input) throws IOException {
        var length = input.readShort();
        var bytes = new byte[length];
        input.readFully(bytes);
        var value = new String(bytes, input.getCharset());
        return StringTag.of(value);
    }

    public static ShortTag readShort(NBTInputStream input) throws IOException {
        return ShortTag.of(input.readShort());
    }

    public static LongTag readLong(NBTInputStream input) throws IOException {
        return LongTag.of(input.readLong());
    }

    public static LongArrayTag readLongArray(NBTInputStream input) throws IOException {
        var length = input.readInt();
        var array = new long[length];
        for (var i = 0; i < length; i++)
            array[i] = input.readLong();
        return LongArrayTag.of(array);
    }

    @SuppressWarnings("unchecked")
    public static <V extends Tag> ListTag<V> readList(NBTInputStreamImpl input) throws IOException {
        var type = input.readByte();
        var length = input.readInt();
        var list = new LinkedList<V>();
        for (var i = 0; i < length; i++) list.add((V) input.readTag(type));
        return ListTag.of(type, list);
    }

    public static IntTag readInt(NBTInputStream input) throws IOException {
        return IntTag.of(input.readInt());
    }

    public static IntArrayTag readIntArray(NBTInputStream input) throws IOException {
        var length = input.readInt();
        var array = new int[length];
        for (var i = 0; i < length; i++) array[i] = input.readInt();
        return IntArrayTag.of(array);
    }

    public static FloatTag readFloat(NBTInputStream input) throws IOException {
        return FloatTag.of(input.readFloat());
    }

    public static DoubleTag readDouble(NBTInputStream input) throws IOException {
        return DoubleTag.of(input.readDouble());
    }

    public static CompoundTag readCompound(NBTInputStreamImpl input) throws IOException {
        var value = new LinkedHashMap<String, Tag>();
        while (true) {
            var entry = input.readNamedTagInternal();
            if (entry == null) break;
            value.put(entry.getKey(), entry.getValue());
        }
        return CompoundTag.of(value);
    }

    public static ByteTag readByte(NBTInputStream input) throws IOException {
        return ByteTag.of(input.readByte());
    }

    public static ByteArrayTag readByteArray(NBTInputStream input) throws IOException {
        var length = input.readInt();
        var bytes = new byte[length];
        input.readFully(bytes);
        return ByteArrayTag.of(bytes);
    }
}
