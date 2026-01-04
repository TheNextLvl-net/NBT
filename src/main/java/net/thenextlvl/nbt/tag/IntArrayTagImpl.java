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
        return value.length;
    }

    @Override
    public Integer get(int index) {
        return value[index];
    }

    @Override
    public int[] getValue() {
        return value.clone();
    }

    @Override
    public void write(NBTOutputStream outputStream) throws IOException {
        outputStream.writeInt(value.length);
        for (var i : value) outputStream.writeInt(i);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof IntArrayTagImpl valueTag)) return false;
        return Arrays.equals(value, valueTag.value);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(value);
    }

    @Override
    public String toString() {
        if (value.length == 0) return "[]";
        var builder = new StringBuilder("[");
        for (int i = 0; i < value.length; i++) {
            if (i > 0) builder.append(",");
            builder.append(value[i]);
        }
        return builder.append(']').toString();
    }
}
