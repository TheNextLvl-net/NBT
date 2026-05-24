package net.thenextlvl.nbt.tag;

import net.thenextlvl.nbt.NBTOutputStream;

import java.io.IOException;

final class StringTagImpl extends ValueTagImpl<String> implements StringTag {
    public StringTagImpl(final String value) {
        super(value);
    }

    @Override
    public boolean isString() {
        return true;
    }

    @Override
    public String getAsString() {
        return value;
    }

    @Override
    public byte getTypeId() {
        return ID;
    }

    @Override
    public void write(final NBTOutputStream outputStream) throws IOException {
        final var bytes = value.getBytes(outputStream.getCharset());
        if (bytes.length > 0xffff) throw new IOException("StringTag is too long: " + bytes.length + " bytes");
        outputStream.writeShort(bytes.length);
        outputStream.write(bytes);
    }

    @Override
    public String toString() {
        return shouldQuote(value) ? '"' + value + '"' : value;
    }

    private boolean shouldQuote(final String str) {
        return !str.chars().allMatch(c -> c >= 48 && c <= 57 || c >= 65 && c <= 90 || c >= 97 && c <= 122 || c == 95);
    }
}
