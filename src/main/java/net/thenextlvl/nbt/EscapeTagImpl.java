package net.thenextlvl.nbt;

import java.io.IOException;

final class EscapeTagImpl implements EscapeTag {
    public static final EscapeTag INSTANCE = new EscapeTagImpl();

    private EscapeTagImpl() {
    }

    @Override
    public int getTypeId() {
        return ID;
    }

    @Override
    public void write(NBTOutputStream outputStream) throws IOException {
        outputStream.writeByte((byte) getTypeId());
    }

    @Override
    public String toString() {
        return "EscapeTag.INSTANCE";
    }
}
