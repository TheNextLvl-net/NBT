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
import org.jetbrains.annotations.Contract;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * An input stream for reading NBT (Named Binary Tag) data.
 */
public final class NBTInputStream extends DataInputStream {
    private final Charset charset;

    /**
     * Constructs an {@code NBTInputStream} for reading NBT data in a GZIP-compressed format
     * from the specified input stream, using UTF-8 encoding.
     *
     * @param inputStream the input stream from which the NBT data is to be read
     * @throws IOException if an I/O error occurs while reading from the stream
     */
    public NBTInputStream(InputStream inputStream) throws IOException {
        this(inputStream, StandardCharsets.UTF_8);
    }

    /**
     * Constructs an {@code NBTInputStream} for reading NBT data in a GZIP-compressed format
     * from the specified input stream, using the specified charset.
     *
     * @param inputStream the input stream from which the NBT data is to be read
     * @param charset     the charset to use for reading string data from the stream
     * @throws IOException if an I/O error occurs while reading from the stream
     */
    public NBTInputStream(InputStream inputStream, Charset charset) throws IOException {
        this(inputStream, charset, Compression.GZIP);
    }

    /**
     * Constructs an {@code NBTInputStream} for reading NBT data in a specified compression format
     * from the specified input stream, using UTF-8 encoding.
     *
     * @param inputStream the input stream from which the NBT data is to be read
     * @param compression the compression type to use
     * @throws IOException if an I/O error occurs while reading from the stream
     */
    public NBTInputStream(InputStream inputStream, Compression compression) throws IOException {
        this(inputStream, StandardCharsets.UTF_8, compression);
    }

    /**
     * Constructs an {@code NBTInputStream} for reading NBT data in a specified compression format
     * from the specified input stream, using the specified charset.
     *
     * @param inputStream the input stream from which the NBT data is to be read
     * @param charset     the charset to use for reading string data from the stream
     * @param compression the compression type to use
     * @throws IOException if an I/O error occurs while reading from the stream
     */
    public NBTInputStream(InputStream inputStream, Charset charset, Compression compression) throws IOException {
        super(new DataInputStream(new BufferedInputStream(compression.decompress(inputStream))));
        this.charset = charset;
    }

    /**
     * Read an NBT object from the stream
     *
     * @return the tag that was read
     * @throws IOException thrown if something goes wrong
     */
    @Contract(value = " -> new", mutates = "this")
    public CompoundTag readTag() throws IOException {
        return readNamedTag().getKey();
    }

    /**
     * Read a named NBT object from the stream
     *
     * @return the tag that was read
     * @throws IOException              thrown if something goes wrong
     * @throws IllegalArgumentException thrown if the root tag is not a CompoundTag
     */
    @Contract(value = " -> new", mutates = "this")
    public Map.Entry<CompoundTag, Optional<String>> readNamedTag() throws IOException, IllegalArgumentException {
        var type = readByte();
        if (type != CompoundTag.ID) throw new IllegalArgumentException("Expected root tag to be a CompoundTag");
        var bytes = new byte[readShort()];
        readFully(bytes);
        var name = bytes.length == 0 ? null : new String(bytes, getCharset());
        return Map.entry(readTag(type).getAsCompound(), Optional.ofNullable(name));
    }

    /**
     * Reads a tag from type
     *
     * @param type The type of the tag
     * @return the tag that was read
     * @throws IOException thrown if something goes wrong
     */
    @Contract(value = "_ -> new", mutates = "this")
    public Tag readTag(int type) throws IOException {
        var mapping = mapper.get(type);
        if (mapping != null) return mapping.map(this);
        throw new IllegalArgumentException("Unknown tag type: " + type);
    }

    /**
     * Retrieves the charset used by this stream for encoding and decoding data.
     *
     * @return the {@link Charset} associated with this stream
     */
    @Contract(pure = true)
    public Charset getCharset() {
        return charset;
    }

    /**
     * Mappings between tag type ids and the corresponding mapping function
     */
    private final Map<Integer, MappingFunction> mapper = new HashMap<>(Map.ofEntries(
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

    /**
     * Register a custom tag mapping
     *
     * @param typeId   the type id of the tag to map
     * @param function the mapping function
     */
    @Contract(mutates = "this")
    public void registerMapping(int typeId, MappingFunction function) {
        mapper.put(typeId, function);
    }

    /**
     * A functional interface for mapping tags
     */
    @FunctionalInterface
    public interface MappingFunction {
        /**
         * Maps an NBTInputStream to a Tag object.
         *
         * @param inputStream the NBTInputStream to be mapped
         * @return the Tag object mapped from the inputStream
         * @throws IOException if an I/O error occurs while reading from the stream
         */
        @Contract(value = "_ -> new", mutates = "param1")
        Tag map(NBTInputStream inputStream) throws IOException;
    }
}
