package net.thenextlvl.nbt;

import net.thenextlvl.nbt.tag.EscapeTag;
import net.thenextlvl.nbt.tag.Tag;
import org.jspecify.annotations.Nullable;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

final class NBTOutputStreamImpl extends DataOutputStream implements NBTOutputStream {
    private final Charset charset;

    private NBTOutputStreamImpl(OutputStream outputStream, Charset charset, Compression compression) throws IOException {
        super(new DataOutputStream(new BufferedOutputStream(compression.compress(outputStream))));
        this.charset = charset;
    }

    @Override
    public void writeTag(@Nullable String name, Tag tag) throws IOException, IllegalArgumentException {
        if (tag instanceof EscapeTag) throw new IllegalArgumentException("EscapeTag not allowed");
        var bytes = name != null ? name.getBytes(getCharset()) : new byte[0];
        writeByte(tag.getTypeId());
        writeShort(bytes.length);
        write(bytes);
        tag.write(this);
    }

    @Override
    public Charset getCharset() {
        return charset;
    }

    public static final class Builder implements NBTOutputStream.Builder {
        private Charset charset = StandardCharsets.UTF_8;
        private Compression compression = Compression.GZIP;
        private @Nullable OutputStream outputStream = null;

        @Override
        public NBTOutputStream.Builder charset(Charset charset) {
            this.charset = charset;
            return this;
        }

        @Override
        public NBTOutputStream.Builder compression(Compression compression) {
            this.compression = compression;
            return this;
        }

        @Override
        public NBTOutputStream.Builder outputStream(OutputStream outputStream) {
            this.outputStream = outputStream;
            return this;
        }

        @Override
        public NBTOutputStream.Builder outputFile(Path file) throws IOException {
            return outputStream(Files.newOutputStream(file));
        }

        @Override
        public NBTOutputStream build() throws IOException {
            if (outputStream == null) throw new IllegalStateException("No output stream provided");
            return new NBTOutputStreamImpl(outputStream, charset, compression);
        }
    }
}
