package net.thenextlvl.nbt;

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
    public int getTypeId() {
        return ID;
    }

    @Override
    public void write(NBTOutputStream outputStream) throws IOException {
        outputStream.writeDouble(getValue());
    }

    public static DoubleTagImpl read(NBTInputStream input) throws IOException {
        return new DoubleTagImpl(input.readDouble());
    }
}
