package net.thenextlvl.nbt;

import net.thenextlvl.nbt.tag.Tag;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.Nullable;

import java.io.Closeable;
import java.io.DataOutput;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
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
    @Contract(mutates = "this")
    void writeTag(@Nullable String name, Tag tag) throws IOException, IllegalArgumentException;


    @Contract(value = " -> new", pure = true)
    static Builder builder() {
        return new NBTOutputStreamImpl.Builder();
    }

    sealed interface Builder permits NBTOutputStreamImpl.Builder {
        /**
         * Sets the character encoding for the output stream.
         * <p>
         * Defaults to {@link java.nio.charset.StandardCharsets#UTF_8}.
         *
         * @param charset the character encoding to use
         * @return the builder instance, allowing for method chaining
         * @since 4.0.0
         */
        @Contract(value = "_ -> this", mutates = "this")
        Builder charset(Charset charset);

        /**
         * Sets the compression algorithm for the output stream.
         * <p>
         * Defaults to {@link Compression#GZIP}.
         *
         * @param compression the compression algorithm to use
         * @return the builder instance, allowing for method chaining
         * @since 4.0.0
         */
        @Contract(value = "_ -> this", mutates = "this")
        Builder compression(Compression compression);

        /**
         * Sets the output stream for the NBTOutputStream.
         *
         * @param outputStream the output stream to use
         * @return the builder instance, allowing for method chaining
         * @since 4.0.0
         */
        @Contract(value = "_ -> this", mutates = "this")
        Builder outputStream(OutputStream outputStream);

        /**
         * Sets the output file for the NBTOutputStream.
         *
         * @param file the file to use
         * @return the builder instance, allowing for method chaining
         * @throws IOException if an error occurs while creating the output stream
         * @since 4.0.0
         */
        @Contract(value = "_ -> this", mutates = "this")
        Builder outputFile(Path file) throws IOException;

        /**
         * Builds the NBTOutputStream.
         *
         * @throws IllegalStateException if no output stream was provided
         * @throws IOException           if an error occurs while creating the output stream
         * @see #outputStream(OutputStream)
         * @see #outputFile(Path)
         * @since 4.0.0
         */
        @Contract(value = "-> new", pure = true)
        NBTOutputStream build() throws IOException, IllegalStateException;
    }
}
