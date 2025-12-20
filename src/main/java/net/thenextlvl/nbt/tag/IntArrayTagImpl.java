package net.thenextlvl.nbt.tag;

import net.thenextlvl.nbt.NBTOutputStream;

import java.io.IOException;
import java.util.Arrays;

final class IntArrayTagImpl extends ValueTagImpl<int[]> implements IntArrayTag {
    public IntArrayTagImpl(int... value) {
        super(value);
    }

    @Override
    public byte getTypeId() {
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
    public void write(NBTOutputStream outputStream) throws IOException {
        outputStream.writeInt(getValue().length);
        for (var i : getValue()) outputStream.writeInt(i);
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
