package net.thenextlvl.nbt.tag;

import net.thenextlvl.nbt.NBTOutputStream;

import java.io.IOException;

final class ByteTagImpl extends NumberTagImpl<Byte> implements ByteTag {
    public ByteTagImpl(Byte value) {
        super(value);
    }

    @Override
    public byte getAsByte() {
        return value;
    }

    @Override
    public byte getTypeId() {
        return ID;
    }

    @Override
    public void write(NBTOutputStream outputStream) throws IOException {
        outputStream.write(value);
    }

    @Override
    public String toString() {
        return value + "b";
    }
}
