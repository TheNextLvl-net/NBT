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
import net.thenextlvl.nbt.tag.impl.ByteArrayTagImpl;
import net.thenextlvl.nbt.tag.impl.ByteTagImpl;
import net.thenextlvl.nbt.tag.impl.CompoundTagImpl;
import net.thenextlvl.nbt.tag.impl.DoubleTagImpl;
import net.thenextlvl.nbt.tag.impl.FloatTagImpl;
import net.thenextlvl.nbt.tag.impl.IntArrayTagImpl;
import net.thenextlvl.nbt.tag.impl.IntTagImpl;
import net.thenextlvl.nbt.tag.impl.ListTagImpl;
import net.thenextlvl.nbt.tag.impl.LongArrayTagImpl;
import net.thenextlvl.nbt.tag.impl.LongTagImpl;
import net.thenextlvl.nbt.tag.impl.ShortTagImpl;
import net.thenextlvl.nbt.tag.impl.StringTagImpl;
import org.jspecify.annotations.Nullable;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

final class NBTInputStreamImpl extends DataInputStream implements NBTInputStream {
    private final Charset charset;

    public NBTInputStreamImpl(InputStream inputStream, Charset charset, Compression compression) throws IOException {
        super(new DataInputStream(new BufferedInputStream(compression.decompress(inputStream))));
        this.charset = charset;
    }

    @Override
    public Tag readTag() throws IOException {
        return readNamedTag().getKey();
    }

    @Override
    public Map.Entry<Tag, Optional<String>> readNamedTag() throws IOException, IllegalArgumentException {
        var type = readByte();
        if (type == EscapeTag.ID) return Map.entry(EscapeTag.INSTANCE, Optional.empty());
        var bytes = new byte[readShort()];
        readFully(bytes);
        var name = bytes.length == 0 ? null : new String(bytes, getCharset());
        return Map.entry(readTag(type), Optional.ofNullable(name));
    }

    @Override
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
            Map.entry(CompoundTag.ID, CompoundTagImpl::read),
            Map.entry(DoubleTag.ID, DoubleTagImpl::read),
            Map.entry(EscapeTag.ID, ignored -> EscapeTag.INSTANCE),
            Map.entry(FloatTag.ID, FloatTagImpl::read),
            Map.entry(IntArrayTag.ID, IntArrayTagImpl::read),
            Map.entry(IntTag.ID, IntTagImpl::read),
            Map.entry(ListTag.ID, ListTagImpl::read),
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

    public static final class Builder implements NBTInputStream.Builder {
        private Charset charset = StandardCharsets.UTF_8;
        private Compression compression = Compression.GZIP;
        private @Nullable InputStream inputStream = null;

        @Override
        public NBTInputStream.Builder charset(Charset charset) {
            this.charset = charset;
            return this;
        }

        @Override
        public NBTInputStream.Builder compression(Compression compression) {
            this.compression = compression;
            return this;
        }

        @Override
        public NBTInputStream.Builder inputStream(InputStream inputStream) {
            this.inputStream = inputStream;
            return this;
        }

        @Override
        public NBTInputStream.Builder inputFile(Path file) throws IOException {
            return inputStream(Files.newInputStream(file));
        }

        @Override
        public NBTInputStream build() throws IOException {
            if (inputStream == null) throw new IllegalStateException("No input stream provided");
            return new NBTInputStreamImpl(inputStream, charset, compression);
        }
    }
}         
