package net.thenextlvl.nbt.tag.impl;

import net.thenextlvl.nbt.NBTInputStream;
import net.thenextlvl.nbt.NBTOutputStream;
import net.thenextlvl.nbt.tag.ByteArrayTag;
import org.jetbrains.annotations.Contract;

import java.io.IOException;
import java.util.Arrays;

public final class ByteArrayTagImpl extends ValueTagImpl<byte[]> implements ByteArrayTag {
    public ByteArrayTagImpl(byte... array) {
        super(array);
    }

    @Override
    public int getTypeId() {
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
    @Contract(mutates = "this")
    public void set(int index, Byte element) {
        getValue()[index] = element;
    }

    @Override
    public void write(NBTOutputStream outputStream) throws IOException {
        outputStream.writeInt(getValue().length);
        outputStream.write(getValue());
    }

    public static ByteArrayTagImpl read(NBTInputStream inputStream) throws IOException {
        var length = inputStream.readInt();
        var bytes = new byte[length];
        inputStream.readFully(bytes);
        return new ByteArrayTagImpl(bytes);
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
