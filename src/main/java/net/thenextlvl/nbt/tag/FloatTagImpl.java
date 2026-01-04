package net.thenextlvl.nbt.tag;

import net.thenextlvl.nbt.NBTOutputStream;

import java.io.IOException;

final class FloatTagImpl extends NumberTagImpl<Float> implements FloatTag {
    public FloatTagImpl(Float value) {
        super(value);
    }

    @Override
    public float getAsFloat() {
        return value;
    }

    @Override
    public byte getTypeId() {
        return ID;
    }

    @Override
    public void write(NBTOutputStream outputStream) throws IOException {
        outputStream.writeFloat(value);
    }

    @Override
    public String toString() {
        return value + "f";
    }
}
