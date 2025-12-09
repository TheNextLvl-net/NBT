package net.thenextlvl.nbt.tag.impl;

import net.thenextlvl.nbt.NBTInputStream;
import net.thenextlvl.nbt.NBTOutputStream;
import net.thenextlvl.nbt.tag.IntArrayTag;

import java.io.IOException;
import java.util.Arrays;

public final class IntArrayTagImpl extends ValueTagImpl<int[]> implements IntArrayTag {
    public IntArrayTagImpl(int... value) {
        super(value);
    }

    @Override
    public int getTypeId() {
        return ID;
    }

    @Override
    public int size() {
        return getValue().length;
    }

    @Override
    public Integer get(int index) {
        return getValue()[index];
    }

    @Override
    public void set(int index, Integer element) {
        getValue()[index] = element;
    }

    @Override
    public void write(NBTOutputStream outputStream) throws IOException {
        outputStream.writeInt(getValue().length);
        for (var i : getValue()) outputStream.writeInt(i);
    }

    public static IntArrayTagImpl read(NBTInputStream inputStream) throws IOException {
        var length = inputStream.readInt();
        var array = new int[length];
        for (var i = 0; i < length; i++) array[i] = inputStream.readInt();
        return new IntArrayTagImpl(array);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof IntArrayTag valueTag)) return false;
        return Arrays.equals(getValue(), valueTag.getValue());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(getValue());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                "{" +
                "value=" + Arrays.toString(getValue()) +
                '}';
    }
}
