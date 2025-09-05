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
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NullMarked;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.zip.GZIPInputStream;

/**
 * An input stream for reading NBT (Named Binary Tag) data.
 */
@NullMarked
public final class NBTInputStream extends DataInputStream {
    private final Charset charset;

    /**
     * Constructs an {@code NBTInputStream} for reading NBT data from the specified input stream.
     *
     * @param inputStream the input stream from which the NBT data is to be read
     * @throws IOException if an I/O error occurs while reading from the stream
     */
    public NBTInputStream(InputStream inputStream) throws IOException {
        this(inputStream, StandardCharsets.UTF_8);
    }

    /**
     * Constructs an {@code NBTInputStream} for reading NBT data from the specified input stream
     * and charset.
     *
     * @param inputStream the input stream from which the NBT data is to be read
     * @param charset     the charset to use for reading string data from the stream
     * @throws IOException if an I/O error occurs while reading from the stream
     */
    public NBTInputStream(InputStream inputStream, Charset charset) throws IOException {
        super(new DataInputStream(new GZIPInputStream(inputStream)));
        this.charset = charset;
    }

    /**
     * Read an NBT object from the stream
     *
     * @return the tag that was read
     * @throws IOException thrown if something goes wrong
     */
    @Contract(value = " -> new", mutates = "this")
    public Tag readTag() throws IOException {
        return readNamedTag().getKey();
    }

    /**
     * Read a named NBT object from the stream
     *
     * @return the tag that was read
     * @throws IOException thrown if something goes wrong
     */
    @Contract(value = " -> new", mutates = "this")
    public Map.Entry<Tag, Optional<String>> readNamedTag() throws IOException {
        var type = readByte();
        if (type == EscapeTag.ID) return Map.entry(EscapeTag.INSTANCE, Optional.empty());
        var bytes = new byte[readShort()];
        readFully(bytes);
        var name = bytes.length == 0 ? null : new String(bytes, getCharset());
        return Map.entry(readTag(type), Optional.ofNullable(name));
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
            Map.entry(ByteArrayTag.ID, ByteArrayTag::read),
            Map.entry(ByteTag.ID, ByteTag::read),
            Map.entry(CompoundTag.ID, CompoundTag::read),
            Map.entry(DoubleTag.ID, DoubleTag::read),
            Map.entry(EscapeTag.ID, ignored -> EscapeTag.INSTANCE),
            Map.entry(FloatTag.ID, FloatTag::read),
            Map.entry(IntArrayTag.ID, IntArrayTag::read),
            Map.entry(IntTag.ID, IntTag::read),
            Map.entry(ListTag.ID, ListTag::read),
            Map.entry(LongArrayTag.ID, LongArrayTag::read),
            Map.entry(LongTag.ID, LongTag::read),
            Map.entry(ShortTag.ID, ShortTag::read),
            Map.entry(StringTag.ID, StringTag::read)
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
