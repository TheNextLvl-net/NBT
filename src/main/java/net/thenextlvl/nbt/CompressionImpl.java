package net.thenextlvl.nbt;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterInputStream;

final class CompressionImpl {
    public static final class None implements Compression {
        @Override
        public InputStream decompress(InputStream input) {
            return input;
        }

        @Override
        public OutputStream compress(OutputStream output) {
            return output;
        }

        @Override
        public String toString() {
            return "Compression.NONE";
        }
    }

    public static final class GZIP implements Compression {
        @Override
        public InputStream decompress(InputStream input) throws IOException {
            return new GZIPInputStream(input);
        }

        @Override
        public OutputStream compress(OutputStream output) throws IOException {
            return new GZIPOutputStream(output);
        }

        @Override
        public String toString() {
            return "Compression.GZIP";
        }
    }

    public static final class ZLIB implements Compression {
        @Override
        public InputStream decompress(InputStream input) {
            return new InflaterInputStream(input);
        }

        @Override
        public OutputStream compress(OutputStream output) {
            return new DeflaterOutputStream(output);
        }

        @Override
        public String toString() {
            return "Compression.ZLIB";
        }
    }
}
