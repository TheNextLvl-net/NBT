package net.thenextlvl.nbt;

import net.thenextlvl.nbt.tag.ByteArrayTag;
import net.thenextlvl.nbt.tag.ByteTag;
import net.thenextlvl.nbt.tag.CompoundTag;
import net.thenextlvl.nbt.tag.DoubleTag;
import net.thenextlvl.nbt.tag.EscapeTag;
import net.thenextlvl.nbt.tag.FloatTag;
import net.thenextlvl.nbt.tag.IntArrayTag;
import net.thenextlvl.nbt.tag.IntTag;
import net.thenextlvl.nbt.tag.ListTag;
import net.thenextlvl.nbt.tag.LongArrayTag;
import net.thenextlvl.nbt.tag.LongTag;
import net.thenextlvl.nbt.tag.ShortTag;
import net.thenextlvl.nbt.tag.StringTag;
import net.thenextlvl.nbt.tag.Tag;
import org.jetbrains.annotations.CheckReturnValue;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.Nullable;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

final class NBTInputStreamImpl extends DataInputStream implements NBTInputStream {
    private final Charset charset;

    public NBTInputStreamImpl(InputStream inputStream, Charset charset, Compression compression) throws IOException {
        super(new DataInputStream(new BufferedInputStream(compression.decompress(inputStream))));
        this.charset = charset;
    }

    @Override
    public CompoundTag readTag() throws IOException {
        var type = readByte();
        if (type != CompoundTag.ID) throw new IllegalArgumentException("Root tag must be a CompoundTag");
        skipNBytes(readShort());
        return TagReaders.readCompound(this);
    }

    @Override
    public Map.Entry<String, CompoundTag> readNamedTag() throws IOException, IllegalArgumentException, IllegalStateException {
        var type = readByte();
        if (type != CompoundTag.ID) throw new IllegalArgumentException("Root tag must be a CompoundTag");
        var nameLength = readShort();
        if (nameLength == 0) throw new IllegalStateException("Root tag name is not defined");
        var bytes = new byte[nameLength];
        readFully(bytes);
        var name = new String(bytes, getCharset());
        return Map.entry(name, TagReaders.readCompound(this));
    }

    @CheckReturnValue
    @Contract(mutates = "this")
    public Map.@Nullable Entry<String, Tag> readNamedTagInternal() throws IOException, IllegalArgumentException {
        var type = readByte();
        if (type == EscapeTag.ID) return null;
        var nameLength = readShort();
        if (nameLength == 0) throw new IllegalStateException("Root tag name is not defined");
        var bytes = new byte[nameLength];
        readFully(bytes);
        var name = new String(bytes, getCharset());
        return Map.entry(name, readTag(type));
    }

    @CheckReturnValue
    @Contract(value = "_ -> new", mutates = "this")
    public Tag readTag(byte type) throws IOException {
        var mapping = readers.get(type);
        if (mapping != null) return mapping.read(this);
        throw new IllegalArgumentException("Unknown tag type: " + type);
    }

    @Override
    public Charset getCharset() {
        return charset;
    }

    private final Map<Byte, ReadingFunction> readers = new HashMap<>(Map.ofEntries(
            Map.entry(ByteArrayTag.ID, TagReaders::readByteArray),
            Map.entry(ByteTag.ID, TagReaders::readByte),
            Map.entry(CompoundTag.ID, input -> TagReaders.readCompound((NBTInputStreamImpl) input)),
            Map.entry(DoubleTag.ID, TagReaders::readDouble),
            Map.entry(EscapeTag.ID, ignored -> EscapeTag.INSTANCE),
            Map.entry(FloatTag.ID, TagReaders::readFloat),
            Map.entry(IntArrayTag.ID, TagReaders::readIntArray),
            Map.entry(IntTag.ID, TagReaders::readInt),
            Map.entry(ListTag.ID, input -> TagReaders.readList((NBTInputStreamImpl) input)),
            Map.entry(LongArrayTag.ID, TagReaders::readLongArray),
            Map.entry(LongTag.ID, TagReaders::readLong),
            Map.entry(ShortTag.ID, TagReaders::readShort),
            Map.entry(StringTag.ID, TagReaders::readString)
    ));

    @Override
    public void registerReader(byte typeId, ReadingFunction function) throws IllegalArgumentException {
        if (!readers.containsKey(typeId)) readers.put(typeId, function);
        else throw new IllegalArgumentException("Reader for type " + typeId + " is already registered");
    }

    @Override
    public boolean unregisterReader(byte typeId) {
        return readers.remove(typeId) != null;
    }
}         
