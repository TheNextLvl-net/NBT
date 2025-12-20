package net.thenextlvl.nbt.tag;

import net.thenextlvl.nbt.NBTOutputStream;

import java.io.IOException;

final class IntTagImpl extends NumberTagImpl<Integer> implements IntTag {
    public IntTagImpl(Integer value) {
        super(value);
    }

    @Override
    public int getAsInt() {
        return getValue();
    }

    @Override
    public byte getTypeId() {
        return ID;
    }

    @Override
    public void write(NBTOutputStream outputStream) throws IOException {
        outputStream.writeInt(getValue());
    }
}
