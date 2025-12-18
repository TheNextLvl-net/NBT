package net.thenextlvl.nbt;

import java.io.IOException;
import java.util.Arrays;

final class LongArrayTagImpl extends ValueTagImpl<long[]> implements LongArrayTag {
    public LongArrayTagImpl(long... value) {
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
    public Long get(int index) {
        return getValue()[index];
    }

    @Override
    public void set(int index, Long element) {
        getValue()[index] = element;
    }

    @Override
    public void write(NBTOutputStream outputStream) throws IOException {
        outputStream.writeLong(getValue().length);
        for (var l : getValue()) outputStream.writeLong(l);
    }

    public static LongArrayTagImpl read(NBTInputStream input) throws IOException {
        var length = input.readInt();
        var array = new long[length];
        for (var i = 0; i < length; i++)
            array[i] = input.readLong();
        return new LongArrayTagImpl(array);
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
