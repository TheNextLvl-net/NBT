package net.thenextlvl.nbt;

import net.thenextlvl.nbt.tag.EscapeTag;
import net.thenextlvl.nbt.tag.Tag;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPOutputStream;

/**
 * A specialized DataOutputStream for writing Named Binary Tag (NBT) data.
 * This stream supports GZIP compression and allows for writing various
 * types of NBT tags.
 */
public final class NBTOutputStream extends DataOutputStream {
    private final Charset charset;

    /**
     * Create a nbt output stream
     *
     * @param outputStream the stream to write to
     * @throws IOException thrown if something goes wrong
     */
    public NBTOutputStream(OutputStream outputStream) throws IOException {
        this(outputStream, StandardCharsets.UTF_8);
    }

    /**
     * Create a nbt output stream
     *
     * @param charset      the charset to write the content with
     * @param outputStream the stream to write to
     * @throws IOException thrown if something goes wrong
     */
    public NBTOutputStream(OutputStream outputStream, Charset charset) throws IOException {
        super(new GZIPOutputStream(outputStream));
        this.charset = charset;
    }

    /**
     * Write a tag to the output stream
     *
     * @param name the name to write
     * @param tag  the tag to write
     * @throws IOException              thrown if something goes wrong
     * @throws IllegalArgumentException thrown if an escape tag was provided
     */
    @Contract(mutates = "this")
    public void writeTag(@Nullable String name, Tag tag) throws IOException, IllegalArgumentException {
        if (tag instanceof EscapeTag) throw new IllegalArgumentException("EscapeTag not allowed");
        var bytes = name != null ? name.getBytes(getCharset()) : new byte[0];
        writeByte(tag.getTypeId());
        writeShort(bytes.length);
        write(bytes);
        tag.write(this);
    }

    /**
     * Retrieves the charset associated with this stream.
     *
     * @return the {@link Charset} used for encoding and decoding in the stream
     */
    @Contract(pure = true)
    public Charset getCharset() {
        return charset;
    }
}
