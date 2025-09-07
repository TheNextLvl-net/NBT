package net.thenextlvl.nbt.tag.impl;

import net.thenextlvl.nbt.NBTInputStream;
import net.thenextlvl.nbt.NBTOutputStream;
import net.thenextlvl.nbt.tag.ByteTag;
import org.jspecify.annotations.NullMarked;

import java.io.IOException;

@NullMarked
public final class ByteTagImpl extends NumberTagImpl<Byte> implements ByteTag {
    public ByteTagImpl(Byte value) {
        super(value);
    }

    @Override
    public byte getAsByte() {
        return getValue();
    }

    @Override
    public int getTypeId() {
        return ID;
    }

    @Override
    public void write(NBTOutputStream outputStream) throws IOException {
        outputStream.write(getValue());
    }

    public static ByteTagImpl read(NBTInputStream inputStream) throws IOException {
        return new ByteTagImpl(inputStream.readByte());
    }
}
