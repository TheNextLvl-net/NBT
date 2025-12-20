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
        return value.length;
    }

    @Override
    public Long get(int index) {
        return value[index];
    }

    @Override
    public long[] getValue() {
        return value.clone();
    }

    @Override
    public void write(NBTOutputStream outputStream) throws IOException {
        outputStream.writeLong(value.length);
        for (var l : value) outputStream.writeLong(l);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof LongArrayTagImpl valueTag)) return false;
        return Arrays.equals(value, valueTag.value);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(value);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                "{" +
                "value=" + Arrays.toString(value) +
                '}';
    }
}
