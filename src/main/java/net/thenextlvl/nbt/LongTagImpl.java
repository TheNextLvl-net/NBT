package net.thenextlvl.nbt;

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
