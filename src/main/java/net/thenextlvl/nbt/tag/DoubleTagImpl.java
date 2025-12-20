package net.thenextlvl.nbt.tag;

import net.thenextlvl.nbt.NBTOutputStream;

import java.io.IOException;

final class DoubleTagImpl extends NumberTagImpl<Double> implements DoubleTag {
    public DoubleTagImpl(Double value) {
        super(value);
    }

    @Override
    public double getAsDouble() {
        return getValue();
    }

    @Override
    public byte getTypeId() {
        return ID;
    }

    @Override
    public void write(NBTOutputStream outputStream) throws IOException {
        outputStream.writeDouble(getValue());
    }
}
