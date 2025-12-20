package net.thenextlvl.nbt.tag;

import net.thenextlvl.nbt.NBTOutputStream;

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
    public byte getTypeId() {
        return ID;
    }

    @Override
    public void write(NBTOutputStream outputStream) throws IOException {
        outputStream.writeFloat(getValue());
    }
}
