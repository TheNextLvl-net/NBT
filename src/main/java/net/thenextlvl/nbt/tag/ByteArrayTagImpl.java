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
    public byte getTypeId() {
        return ID;
    }

    @Override
    @Contract(pure = true)
    public int size() {
        return getValue().length;
    }

    @Override
    @Contract(pure = true)
    public Byte get(int index) {
        return getValue()[index];
    }

    @Override
    public void write(NBTOutputStream outputStream) throws IOException {
        outputStream.writeInt(getValue().length);
        outputStream.write(getValue());
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof ByteArrayTag valueTag)) return false;
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
