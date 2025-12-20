package net.thenextlvl.nbt.tag;

import net.thenextlvl.nbt.NBTOutputStream;

import java.io.IOException;

final class LongTagImpl extends NumberTagImpl<Long> implements LongTag {
    public LongTagImpl(Long value) {
        super(value);
    }

    @Override
    public long getAsLong() {
        return getValue();
    }

    @Override
    public byte getTypeId() {
        return ID;
    }

    @Override
    public void write(NBTOutputStream outputStream) throws IOException {
        outputStream.writeLong(getValue());
    }
}
