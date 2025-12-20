package net.thenextlvl.nbt;

import net.thenextlvl.nbt.tag.CompoundTag;
import net.thenextlvl.nbt.tag.Tag;
import org.jetbrains.annotations.CheckReturnValue;
import org.jetbrains.annotations.Contract;

import java.io.Closeable;
import java.io.DataInput;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public sealed interface NBTInputStream extends DataInput, Closeable permits NBTInputStreamImpl {
    /**
     * Retrieves the charset used by this reader for encoding and decoding data.
     *
     * @return the {@link Charset} associated with this reader
     */
    @Contract(pure = true)
    Charset getCharset();

    /**
     * Read the root {@code CompoundTag} from the stream
     *
     * @return the tag that was read
     * @throws IOException thrown if something goes wrong
     * @see #readNamedTag()
     * @since 4.0.0
     */
    @CheckReturnValue
    @Contract(value = " -> new", mutates = "this")
    CompoundTag readTag() throws IOException;

    /**
     * Read the root {@code CompoundTag} and name from the stream
     *
     * @return a {@code Map.Entry} containing the tag and its name
     * @throws IOException              thrown if something goes wrong
     * @throws IllegalArgumentException thrown if the root tag is not a CompoundTag
     * @throws IllegalStateException    thrown if the root tag name is not defined
     * @see #readTag()
     * @since 4.0.0
     */
    @CheckReturnValue
    @Contract(value = " -> new", mutates = "this")
    Map.Entry<String, CompoundTag> readNamedTag() throws IOException, IllegalArgumentException, IllegalStateException;

    /**
     * Register a custom tag reader
     *
     * @param typeId   the type id of the tag to read
     * @param function the reading function
     * @throws IllegalArgumentException if the type id is already registered
     * @since 4.0.0
     */
    @Contract(mutates = "this")
    void registerReader(byte typeId, ReadingFunction function) throws IllegalArgumentException;

    /**
     * Unregister a tag reader
     *
     * @param typeId the type id of the tag to unregister
     * @return {@code true} if a reader was unregistered, {@code false} otherwise
     * @since 4.0.0
     */
    @Contract(mutates = "this")
    boolean unregisterReader(byte typeId);

    /**
     * A functional interface for reading tags
     *
     * @since 4.0.0
     */
    @FunctionalInterface
    interface ReadingFunction {
        /**
         * Reads a Tag object from a DataInputStream.
         *
         * @param input the Reader object
         * @return the Tag object read from the reader
         * @throws IOException if an I/O error occurs while reading from the reader
         */
        @Contract(value = "_ -> new", mutates = "param1")
        Tag read(NBTInputStream input) throws IOException;
    }

    /**
     * Creates a new {@code NBTInputStream} with the specified input stream, using the default charset and GZIP compression.
     *
     * @param input the input stream
     * @return a new {@code NBTInputStream}
     * @throws IOException if an exception occurred while creating the stream
     * @since 4.0.0
     */
    @Contract(value = "_ -> new", pure = true)
    static NBTInputStream create(InputStream input) throws IOException {
        return create(input, StandardCharsets.UTF_8);
    }

    /**
     * Creates a new {@code NBTInputStream} with the specified input stream and charset, using GZIP compression.
     *
     * @param input   the input stream
     * @param charset the charset
     * @return a new {@code NBTInputStream}
     * @throws IOException if an exception occurred while creating the stream
     * @since 4.0.0
     */
    @Contract(value = "_, _ -> new", pure = true)
    static NBTInputStream create(InputStream input, Charset charset) throws IOException {
        return create(input, charset, Compression.GZIP);
    }

    /**
     * Creates a new {@code NBTInputStream} with the specified input stream and compression, using the default charset.
     *
     * @param input       the input stream
     * @param compression the compression
     * @return a new {@code NBTInputStream}
     * @throws IOException if an exception occurred while creating the stream
     * @since 4.0.0
     */
    @Contract(value = "_, _ -> new", pure = true)
    static NBTInputStream create(InputStream input, Compression compression) throws IOException {
        return create(input, StandardCharsets.UTF_8, compression);
    }

    /**
     * Creates a new {@code NBTInputStream} with the specified input stream, charset, and compression.
     *
     * @param input       the input stream
     * @param charset     the charset
     * @param compression the compression
     * @return a new {@code NBTInputStream}
     * @throws IOException if an exception occurred while creating the stream
     * @since 4.0.0
     */
    @Contract(value = "_, _, _ -> new", pure = true)
    static NBTInputStream create(InputStream input, Charset charset, Compression compression) throws IOException {
        return new NBTInputStreamImpl(input, charset, compression);
    }

    /**
     * Creates a new {@code NBTInputStream} with the specified path, using the default charset and GZIP compression.
     *
     * @param path the path
     * @return a new {@code NBTInputStream}
     * @throws IOException if an exception occurred while creating the stream
     * @since 4.0.0
     */
    @Contract(value = "_ -> new", pure = true)
    static NBTInputStream create(Path path) throws IOException {
        return create(path, StandardCharsets.UTF_8, Compression.GZIP);
    }

    /**
     * Creates a new {@code NBTInputStream} with the specified path and charset, using GZIP compression.
     *
     * @param path    the path
     * @param charset the charset
     * @return a new {@code NBTInputStream}
     * @throws IOException if an exception occurred while creating the stream
     * @since 4.0.0
     */
    @Contract(value = "_, _ -> new", pure = true)
    static NBTInputStream create(Path path, Charset charset) throws IOException {
        return create(path, charset, Compression.GZIP);
    }

    /**
     * Creates a new {@code NBTInputStream} with the specified path and compression, using the default charset.
     *
     * @param path        the path
     * @param compression the compression
     * @return a new {@code NBTInputStream}
     * @throws IOException if an exception occurred while creating the stream
     * @since 4.0.0
     */
    @Contract(value = "_, _ -> new", pure = true)
    static NBTInputStream create(Path path, Compression compression) throws IOException {
        return create(path, StandardCharsets.UTF_8, compression);
    }

    /**
     * Creates a new {@code NBTInputStream} with the specified path, charset, and compression.
     *
     * @param path        the path
     * @param charset     the charset
     * @param compression the compression
     * @return a new {@code NBTInputStream}
     * @throws IOException if an exception occurred while creating the stream
     * @since 4.0.0
     */
    @Contract(value = "_, _, _ -> new", pure = true)
    static NBTInputStream create(Path path, Charset charset, Compression compression) throws IOException {
        return create(Files.newInputStream(path), charset, compression);
    }
}
