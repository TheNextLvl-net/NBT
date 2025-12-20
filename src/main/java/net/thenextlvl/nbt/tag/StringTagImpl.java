package net.thenextlvl.nbt.tag;

import net.thenextlvl.nbt.NBTOutputStream;

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
    public byte getTypeId() {
        return ID;
    }

    @Override
    public void write(NBTOutputStream outputStream) throws IOException {
        var bytes = getValue().getBytes(outputStream.getCharset());
        outputStream.writeShort(bytes.length);
        outputStream.write(bytes);
    }
}
