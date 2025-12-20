package net.thenextlvl.nbt.tag;

import net.thenextlvl.nbt.NBTOutputStream;

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
    public byte getTypeId() {
        return ID;
    }

    @Override
    public void write(NBTOutputStream outputStream) throws IOException {
        outputStream.writeShort(getValue());
    }
}
