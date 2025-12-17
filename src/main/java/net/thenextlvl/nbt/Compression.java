package net.thenextlvl.nbt;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Compression types.
 * <p>
 * This class is inspired by <a href="https://github.com/PaperMC/adventure/blob/main/5/nbt/src/main/java/net/kyori/adventure/nbt/BinaryTagIO.java#L530">net.kyori.adventure.nbt.BinaryTagIO.Compression</a>
 *
 * @since 4.0.0
 */
public interface Compression {
    /**
     * No compression.
     */
    Compression NONE = new CompressionImpl.None();
    /**
     * <a href="https://en.wikipedia.org/wiki/Gzip">GZIP</a> compression.
     */
    Compression GZIP = new CompressionImpl.GZIP();
    /**
     * <a href="https://en.wikipedia.org/wiki/Zlib">ZLIB</a> compression.
     */
    Compression ZLIB = new CompressionImpl.ZLIB();

    /**
     * Decompresses {@code input}.
     *
     * @param input the input stream
     * @return a decompressed input stream
     * @throws IOException if an exception was encountered while decompressing
     */
    InputStream decompress(InputStream input) throws IOException;

    /**
     * Compresses {@code output}.
     *
     * @param output the output stream
     * @return a compressed output stream
     * @throws IOException if an exception was encountered while compressing
     */
    OutputStream compress(OutputStream output) throws IOException;
}
