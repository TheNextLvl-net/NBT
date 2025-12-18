package net.thenextlvl.nbt;

import java.io.IOException;

final class ByteTagImpl extends NumberTagImpl<Byte> implements ByteTag {
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

    public static ByteTagImpl read(NBTInputStream input) throws IOException {
        return new ByteTagImpl(input.readByte());
    }
}
