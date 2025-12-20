package net.thenextlvl.nbt.tag;

import net.thenextlvl.nbt.NBTOutputStream;

import java.io.IOException;
import java.util.Arrays;

final class LongArrayTagImpl extends ValueTagImpl<long[]> implements LongArrayTag {
    public LongArrayTagImpl(long... value) {
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
    public Long get(int index) {
        return getValue()[index];
    }

    @Override
    public void write(NBTOutputStream outputStream) throws IOException {
        outputStream.writeLong(getValue().length);
        for (var l : getValue()) outputStream.writeLong(l);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof LongArrayTag valueTag)) return false;
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
