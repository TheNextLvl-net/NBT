package net.thenextlvl.nbt.tag.impl;

import net.thenextlvl.nbt.NBTInputStream;
import net.thenextlvl.nbt.NBTOutputStream;
import net.thenextlvl.nbt.tag.FloatTag;

import java.io.IOException;

public final class FloatTagImpl extends NumberTagImpl<Float> implements FloatTag {
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

    public static FloatTagImpl read(NBTInputStream inputStream) throws IOException {
        return new FloatTagImpl(inputStream.readFloat());
    }
}
