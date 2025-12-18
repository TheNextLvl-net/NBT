package net.thenextlvl.nbt;

import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.Nullable;

import java.io.Closeable;
import java.io.DataOutput;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public sealed interface NBTOutputStream extends DataOutput, Closeable permits NBTOutputStreamImpl {
    /**
     * Retrieves the charset used by this writer for encoding data.
     *
     * @return the {@link Charset} associated with this writer
     */
    @Contract(pure = true)
    Charset getCharset();

    /**
     * Write a tag to the output stream
     *
     * @param name the name to write
     * @param tag  the tag to write
     * @throws IOException              thrown if something goes wrong
     * @throws IllegalArgumentException thrown if an escape tag was provided
     */
    @Contract(mutates = "this,io")
    void writeTag(@Nullable String name, Tag tag) throws IOException, IllegalArgumentException;

    /**
     * Creates a new {@code NBTOutputStream} with the specified output stream, using the default charset and GZIP compression.
     *
     * @param output the output stream
     * @return a new {@code NBTOutputStream}
     * @throws IOException if an exception occurred while creating the stream
     * @since 4.0.0
     */
    @Contract(value = "_ -> new", pure = true)
    static NBTOutputStream create(OutputStream output) throws IOException {
        return create(output, StandardCharsets.UTF_8);
    }

    /**
     * Creates a new {@code NBTOutputStream} with the specified output stream and charset, using GZIP compression.
     *
     * @param output  the output stream
     * @param charset the charset
     * @return a new {@code NBTOutputStream}
     * @throws IOException if an exception occurred while creating the stream
     * @since 4.0.0
     */
    @Contract(value = "_, _ -> new", pure = true)
    static NBTOutputStream create(OutputStream output, Charset charset) throws IOException {
        return create(output, charset, Compression.GZIP);
    }

    /**
     * Creates a new {@code NBTOutputStream} with the specified output stream and compression, using the default charset.
     *
     * @param output      the output stream
     * @param compression the compression
     * @return a new {@code NBTOutputStream}
     * @throws IOException if an exception occurred while creating the stream
     * @since 4.0.0
     */
    @Contract(value = "_, _ -> new", pure = true)
    static NBTOutputStream create(OutputStream output, Compression compression) throws IOException {
        return create(output, StandardCharsets.UTF_8, compression);
    }

    /**
     * Creates a new {@code NBTOutputStream} with the specified output stream, charset, and compression.
     *
     * @param output      the output stream
     * @param charset     the charset
     * @param compression the compression
     * @return a new {@code NBTOutputStream}
     * @throws IOException if an exception occurred while creating the stream
     * @since 4.0.0
     */
    @Contract(value = "_, _, _ -> new", pure = true)
    static NBTOutputStream create(OutputStream output, Charset charset, Compression compression) throws IOException {
        return new NBTOutputStreamImpl(output, charset, compression);
    }

    /**
     * Creates a new {@code NBTOutputStream} with the specified path, using the default charset and GZIP compression.
     *
     * @param path the path
     * @return a new {@code NBTOutputStream}
     * @throws IOException if an exception occurred while creating the stream
     * @since 4.0.0
     */
    @Contract(value = "_ -> new", pure = true)
    static NBTOutputStream create(Path path) throws IOException {
        return create(path, StandardCharsets.UTF_8, Compression.GZIP);
    }

    /**
     * Creates a new {@code NBTOutputStream} with the specified path and charset, using GZIP compression.
     *
     * @param path    the path
     * @param charset the charset
     * @return a new {@code NBTOutputStream}
     * @throws IOException if an exception occurred while creating the stream
     * @since 4.0.0
     */
    @Contract(value = "_, _ -> new", pure = true)
    static NBTOutputStream create(Path path, Charset charset) throws IOException {
        return create(path, charset, Compression.GZIP);
    }

    /**
     * Creates a new {@code NBTOutputStream} with the specified path and compression, using the default charset.
     *
     * @param path        the path
     * @param compression the compression
     * @return a new {@code NBTOutputStream}
     * @throws IOException if an exception occurred while creating the stream
     * @since 4.0.0
     */
    @Contract(value = "_, _ -> new", pure = true)
    static NBTOutputStream create(Path path, Compression compression) throws IOException {
        return create(path, StandardCharsets.UTF_8, compression);
    }

    /**
     * Creates a new {@code NBTOutputStream} with the specified path, charset, and compression.
     *
     * @param path        the path
     * @param charset     the charset
     * @param compression the compression
     * @return a new {@code NBTOutputStream}
     * @throws IOException if an exception occurred while creating the stream
     * @since 4.0.0
     */
    @Contract(value = "_, _, _ -> new", pure = true)
    static NBTOutputStream create(Path path, Charset charset, Compression compression) throws IOException {
        return create(Files.newOutputStream(path), charset, compression);
    }
}
