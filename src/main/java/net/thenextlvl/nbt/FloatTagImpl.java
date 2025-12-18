package net.thenextlvl.nbt;

import java.io.IOException;

final class FloatTagImpl extends NumberTagImpl<Float> implements FloatTag {
    public FloatTagImpl(Float value) {
        super(value);
    }

    @Override
    public float getAsFloat() {
        return getValue();
    }

    @Override
    public int getTypeId() {
        return ID;
    }

    @Override
    public void write(NBTOutputStream outputStream) throws IOException {
        outputStream.writeFloat(getValue());
    }

    public static FloatTagImpl read(NBTInputStream input) throws IOException {
        return new FloatTagImpl(input.readFloat());
    }
}
