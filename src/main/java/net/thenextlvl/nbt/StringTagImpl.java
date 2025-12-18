package net.thenextlvl.nbt;

import java.io.IOException;

final class StringTagImpl extends ValueTagImpl<String> implements StringTag {
    public StringTagImpl(String value) {
        super(value);
    }

    @Override
    public boolean isString() {
        return true;
    }

    @Override
    public String getAsString() {
        return getValue();
    }

    @Override
    public int getTypeId() {
        return ID;
    }

    @Override
    public void write(NBTOutputStream outputStream) throws IOException {
        var bytes = getValue().getBytes(outputStream.getCharset());
        outputStream.writeShort(bytes.length);
        outputStream.write(bytes);
    }

    public static StringTagImpl read(NBTInputStream input) throws IOException {
        var length = input.readShort();
        var bytes = new byte[length];
        input.readFully(bytes);
        var value = new String(bytes, input.getCharset());
        return new StringTagImpl(value);
    }
}
