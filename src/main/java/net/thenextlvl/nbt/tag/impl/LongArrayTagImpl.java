package net.thenextlvl.nbt.tag.impl;

import net.thenextlvl.nbt.NBTInputStream;
import net.thenextlvl.nbt.NBTOutputStream;
import net.thenextlvl.nbt.tag.LongArrayTag;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.io.IOException;

@NullMarked
@ApiStatus.Internal
public final class LongArrayTagImpl extends ValueTagImpl<long[]> implements LongArrayTag {
    public LongArrayTagImpl(long... value) {
        super(value);
    }

    @Override
    public int getTypeId() {
        return ID;
    }

    @Override
    public int size() {
        return getValue().length;
    }

    @Override
    public Long get(int index) {
        return getValue()[index];
    }

    @Override
    public void set(int index, Long element) {
        getValue()[index] = element;
    }

    @Override
    public void write(NBTOutputStream outputStream) throws IOException {
        outputStream.writeLong(getValue().length);
        for (var l : getValue()) outputStream.writeLong(l);
    }

    public static LongArrayTagImpl read(NBTInputStream inputStream) throws IOException {
        var length = inputStream.readInt();
        var array = new long[length];
        for (var i = 0; i < length; i++)
            array[i] = inputStream.readLong();
        return new LongArrayTagImpl(array);
    }
}
