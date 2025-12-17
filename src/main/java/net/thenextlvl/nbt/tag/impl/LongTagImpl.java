package net.thenextlvl.nbt.tag.impl;

import net.thenextlvl.nbt.NBTInputStream;
import net.thenextlvl.nbt.NBTOutputStream;
import net.thenextlvl.nbt.tag.LongTag;

import java.io.IOException;

public final class LongTagImpl extends NumberTagImpl<Long> implements LongTag {
    public LongTagImpl(Long value) {
        super(value);
    }

    @Override
    public long getAsLong() {
        return getValue();
    }

    @Override
    public int getTypeId() {
        return ID;
    }

    @Override
    public void write(NBTOutputStream outputStream) throws IOException {
        outputStream.writeLong(getValue());
    }

    public static LongTagImpl read(NBTInputStream input) throws IOException {
        return new LongTagImpl(input.readLong());
    }
}
