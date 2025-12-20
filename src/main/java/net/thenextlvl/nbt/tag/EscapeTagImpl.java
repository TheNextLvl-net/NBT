package net.thenextlvl.nbt.tag;

import net.thenextlvl.nbt.NBTOutputStream;

import java.io.IOException;

final class EscapeTagImpl implements EscapeTag {
    public static final EscapeTag INSTANCE = new EscapeTagImpl();

    private EscapeTagImpl() {
    }

    @Override
    public byte getTypeId() {
        return ID;
    }

    @Override
    public void write(NBTOutputStream outputStream) throws IOException {
        outputStream.writeByte(getTypeId());
    }

    @Override
    public String toString() {
        return "EscapeTag.INSTANCE";
    }
}
