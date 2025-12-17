package net.thenextlvl.nbt;

import net.thenextlvl.nbt.tag.Tag;
import org.jetbrains.annotations.Contract;

import java.io.Closeable;
import java.io.DataInput;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;

public sealed interface NBTInputStream extends DataInput, Closeable permits NBTInputStreamImpl {
    /**
     * Retrieves the charset used by this reader for encoding and decoding data.
     *
     * @return the {@link Charset} associated with this reader
     */
    @Contract(pure = true)
    Charset getCharset();

    /**
     * Read an NBT object from the stream
     *
     * @return the tag that was read
     * @throws IOException thrown if something goes wrong
     */
    @Contract(value = " -> new", mutates = "this")
    Tag readTag() throws IOException;

    /**
     * Read a named NBT object from the stream
     *
     * @return the tag that was read
     * @throws IOException              thrown if something goes wrong
     * @throws IllegalArgumentException thrown if the root tag is not a CompoundTag
     */
    @Contract(value = " -> new", mutates = "this")
    Map.Entry<Tag, Optional<String>> readNamedTag() throws IOException, IllegalArgumentException;

    /**
     * Reads a tag from type
     *
     * @param type The type of the tag
     * @return the tag that was read
     * @throws IOException thrown if something goes wrong
     */
    @Contract(value = "_ -> new", mutates = "this")
    Tag readTag(int type) throws IOException;

    /**
     * Register a custom tag reader
     *
     * @param typeId   the type id of the tag to read
     * @param function the reading function
     * @throws IllegalArgumentException if the type id is already registered
     * @since 4.0.0
     */
    @Contract(mutates = "this")
    void registerReader(int typeId, ReadingFunction function) throws IllegalArgumentException;

    /**
     * Unregister a tag reader
     *
     * @param typeId the type id of the tag to unregister
     * @return {@code true} if a reader was unregistered, {@code false} otherwise
     * @since 4.0.0
     */
    @Contract(mutates = "this")
    boolean unregisterReader(int typeId);

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

    static Builder builder() {
        return new NBTInputStreamImpl.Builder();
    }

    sealed interface Builder permits NBTInputStreamImpl.Builder {
        /**
         * Sets the character encoding for the input stream.
         * <p>
         * Defaults to {@link java.nio.charset.StandardCharsets#UTF_8}.
         *
         * @param charset the character encoding to use
         * @return the builder instance, allowing for method chaining
         * @since 4.0.0
         */
        @Contract(mutates = "this")
        Builder charset(Charset charset);

        /**
         * Sets the compression algorithm for the input stream.
         * <p>
         * Defaults to {@link Compression#GZIP}.
         *
         * @param compression the compression algorithm to use
         * @return the builder instance, allowing for method chaining
         * @since 4.0.0
         */
        @Contract(mutates = "this")
        Builder compression(Compression compression);

        /**
         * Sets the input stream for the NBTInputStream.
         *
         * @param inputStream the input stream to use
         * @return the builder instance, allowing for method chaining
         * @since 4.0.0
         */
        @Contract(mutates = "this")
        Builder inputStream(InputStream inputStream);

        /**
         * Sets the input file for the NBTInputStream.
         *
         * @param file the file to use
         * @return the builder instance, allowing for method chaining
         * @throws IOException if an error occurs while creating the input stream
         * @since 4.0.0
         */
        @Contract(mutates = "this")
        Builder inputFile(Path file) throws IOException;

        /**
         * Builds the NBTInputStream.
         *
         * @throws IllegalStateException if no input stream was provided
         * @throws IOException           if an error occurs while creating the input stream
         * @see #inputStream(InputStream)
         * @see #inputFile(Path)
         * @since 4.0.0
         */
        NBTInputStream build() throws IOException, IllegalStateException;
    }
}          
