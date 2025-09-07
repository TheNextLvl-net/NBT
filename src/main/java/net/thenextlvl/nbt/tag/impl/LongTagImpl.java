package net.thenextlvl.nbt.tag.impl;

import net.thenextlvl.nbt.NBTInputStream;
import net.thenextlvl.nbt.NBTOutputStream;
import net.thenextlvl.nbt.tag.LongTag;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.io.IOException;

@NullMarked
@ApiStatus.Internal
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

    public static LongTagImpl read(NBTInputStream inputStream) throws IOException {
        return new LongTagImpl(inputStream.readLong());
    }
}
