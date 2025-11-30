package net.thenextlvl.nbt.tag.impl;

import net.thenextlvl.nbt.NBTInputStream;
import net.thenextlvl.nbt.NBTOutputStream;
import net.thenextlvl.nbt.tag.ShortTag;

import java.io.IOException;

public final class ShortTagImpl extends NumberTagImpl<Short> implements ShortTag {
    public ShortTagImpl(Short value) {
        super(value);
    }

    @Override
    public short getAsShort() {
        return getValue();
    }

    @Override
    public int getTypeId() {
        return ID;
    }

    @Override
    public void write(NBTOutputStream outputStream) throws IOException {
        outputStream.writeShort(getValue());
    }

    public static ShortTagImpl read(NBTInputStream inputStream) throws IOException {
        return new ShortTagImpl(inputStream.readShort());
    }
}
