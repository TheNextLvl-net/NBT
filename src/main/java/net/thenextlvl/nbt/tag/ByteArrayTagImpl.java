package net.thenextlvl.nbt.tag;

import net.thenextlvl.nbt.NBTOutputStream;
import org.jetbrains.annotations.Contract;

import java.io.IOException;
import java.util.Arrays;

final class ByteArrayTagImpl extends ValueTagImpl<byte[]> implements ByteArrayTag {
    public ByteArrayTagImpl(byte... array) {
        super(array);
    }

    @Override
    public byte[] getAsByteArray() {
        return getValue();
    }

    @Override
    public byte getTypeId() {
        return ID;
    }

    @Override
    @Contract(pure = true)
    public int size() {
        return value.length;
    }

    @Override
    @Contract(pure = true)
    public Byte get(int index) {
        return value[index];
    }

    @Override
    public byte[] getValue() {
        return value.clone();
    }

    @Override
    public void write(NBTOutputStream outputStream) throws IOException {
        outputStream.writeInt(value.length);
        outputStream.write(value);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof ByteArrayTagImpl valueTag)) return false;
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
            builder.append(value[i]).append("b");
        }
        return builder.append(']').toString();
    }
}
