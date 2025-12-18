package net.thenextlvl.nbt;

import java.io.IOException;

final class ShortTagImpl extends NumberTagImpl<Short> implements ShortTag {
    public ShortTagImpl(Short value) {
        super(value);
    }

    @Override
    public short getAsShort() {
        return getValue();
    }

    @Override
    public int getTypeId() {
        return ID;
    }

    @Override
    public void write(NBTOutputStream outputStream) throws IOException {
        outputStream.writeShort(getValue());
    }

    public static ShortTagImpl read(NBTInputStream input) throws IOException {
        return new ShortTagImpl(input.readShort());
    }
}
