package net.thenextlvl.nbt.tag.impl;

import net.thenextlvl.nbt.NBTInputStream;
import net.thenextlvl.nbt.NBTOutputStream;
import net.thenextlvl.nbt.tag.IntTag;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.io.IOException;

@NullMarked
@ApiStatus.Internal
public final class IntTagImpl extends NumberTagImpl<Integer> implements IntTag {
    public IntTagImpl(Integer value) {
        super(value);
    }

    @Override
    public int getAsInt() {
        return getValue();
    }

    @Override
    public int getTypeId() {
        return ID;
    }

    @Override
    public void write(NBTOutputStream outputStream) throws IOException {
        outputStream.writeInt(getValue());
    }

    public static IntTagImpl read(NBTInputStream inputStream) throws IOException {
        return new IntTagImpl(inputStream.readInt());
    }
}
