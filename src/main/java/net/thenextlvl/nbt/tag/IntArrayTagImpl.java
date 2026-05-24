package net.thenextlvl.nbt.tag;

import net.thenextlvl.nbt.NBTOutputStream;

import java.io.IOException;
import java.util.Arrays;

final class IntArrayTagImpl extends ValueTagImpl<int[]> implements IntArrayTag {
    public IntArrayTagImpl(final int... value) {
        super(value);
    }

    @Override
    public boolean isIntArray() {
        return true;
    }

    @Override
    public int[] getAsIntArray() {
        return getValue();
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
    public Integer get(final int index) {
        return value[index];
    }

    @Override
    public int[] getValue() {
        return value.clone();
    }

    @Override
    public void write(final NBTOutputStream outputStream) throws IOException {
        outputStream.writeInt(value.length);
        for (final var i : value) outputStream.writeInt(i);
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) return true;
        if (!(object instanceof final IntArrayTagImpl valueTag)) return false;
        return Arrays.equals(value, valueTag.value);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(value);
    }

    @Override
    public String toString() {
        if (value.length == 0) return "[]";
        final var builder = new StringBuilder("[");
        for (int i = 0; i < value.length; i++) {
            if (i > 0) builder.append(",");
            builder.append(value[i]);
        }
        return builder.append(']').toString();
    }
}
