package net.thenextlvl.nbt.tag.impl;

import net.thenextlvl.nbt.NBTOutputStream;
import net.thenextlvl.nbt.tag.EscapeTag;
import org.jspecify.annotations.NullMarked;

import java.io.IOException;

public final class EscapeTagImpl implements EscapeTag {
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
