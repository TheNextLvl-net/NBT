package net.thenextlvl.nbt;

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
        return CompoundTagImpl.read(this);
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
        return Map.entry(name, CompoundTagImpl.read(this));
    }

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
    public Tag readTag(int type) throws IOException {
        var mapping = readers.get(type);
        if (mapping != null) return mapping.read(this);
        throw new IllegalArgumentException("Unknown tag type: " + type);
    }

    @Override
    public Charset getCharset() {
        return charset;
    }

    private final Map<Integer, ReadingFunction> readers = new HashMap<>(Map.ofEntries(
            Map.entry(ByteArrayTag.ID, ByteArrayTagImpl::read),
            Map.entry(ByteTag.ID, ByteTagImpl::read),
            Map.entry(CompoundTag.ID, input -> CompoundTagImpl.read((NBTInputStreamImpl) input)),
            Map.entry(DoubleTag.ID, DoubleTagImpl::read),
            Map.entry(EscapeTag.ID, ignored -> EscapeTag.INSTANCE),
            Map.entry(FloatTag.ID, FloatTagImpl::read),
            Map.entry(IntArrayTag.ID, IntArrayTagImpl::read),
            Map.entry(IntTag.ID, IntTagImpl::read),
            Map.entry(ListTag.ID, input -> ListTagImpl.read((NBTInputStreamImpl) input)),
            Map.entry(LongArrayTag.ID, LongArrayTagImpl::read),
            Map.entry(LongTag.ID, LongTagImpl::read),
            Map.entry(ShortTag.ID, ShortTagImpl::read),
            Map.entry(StringTag.ID, StringTagImpl::read)
    ));

    @Override
    public void registerReader(int typeId, ReadingFunction function) throws IllegalArgumentException {
        if (!readers.containsKey(typeId)) readers.put(typeId, function);
        else throw new IllegalArgumentException("Reader for type " + typeId + " is already registered");
    }

    @Override
    public boolean unregisterReader(int typeId) {
        return readers.remove(typeId) != null;
    }
}         
