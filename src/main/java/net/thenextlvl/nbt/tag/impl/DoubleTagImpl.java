package net.thenextlvl.nbt.tag.impl;

import net.thenextlvl.nbt.NBTInputStream;
import net.thenextlvl.nbt.NBTOutputStream;
import net.thenextlvl.nbt.tag.DoubleTag;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.io.IOException;

@NullMarked
public final class DoubleTagImpl extends NumberTagImpl<Double> implements DoubleTag {
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

    public static DoubleTagImpl read(NBTInputStream inputStream) throws IOException {
        return new DoubleTagImpl(inputStream.readDouble());
    }
}
