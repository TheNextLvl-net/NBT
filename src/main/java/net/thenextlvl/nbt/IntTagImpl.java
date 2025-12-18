package net.thenextlvl.nbt;

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
    public int getTypeId() {
        return ID;
    }

    @Override
    public void write(NBTOutputStream outputStream) throws IOException {
        outputStream.writeInt(getValue());
    }

    public static IntTagImpl read(NBTInputStream input) throws IOException {
        return new IntTagImpl(input.readInt());
    }
}
