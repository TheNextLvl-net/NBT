package net.thenextlvl.nbt;

import net.thenextlvl.nbt.tag.EscapeTag;
import net.thenextlvl.nbt.tag.Tag;
import org.jspecify.annotations.Nullable;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

final class NBTOutputStreamImpl extends DataOutputStream implements NBTOutputStream {
    private final Charset charset;

    public NBTOutputStreamImpl(OutputStream outputStream, Charset charset, Compression compression) throws IOException {
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
}
