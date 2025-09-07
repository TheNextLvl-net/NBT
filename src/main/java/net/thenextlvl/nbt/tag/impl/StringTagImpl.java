package net.thenextlvl.nbt.tag.impl;

import net.thenextlvl.nbt.NBTInputStream;
import net.thenextlvl.nbt.NBTOutputStream;
import net.thenextlvl.nbt.tag.StringTag;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.io.IOException;

@NullMarked
@ApiStatus.Internal
public final class StringTagImpl extends ValueTagImpl<String> implements StringTag {
    public StringTagImpl(String value) {
        super(value);
    }

    @Override
    public boolean isString() {
        return true;
    }

    @Override
    public String getAsString() {
        return getValue();
    }

    @Override
    public int getTypeId() {
        return ID;
    }

    @Override
    public void write(NBTOutputStream outputStream) throws IOException {
        var bytes = getValue().getBytes(outputStream.getCharset());
        outputStream.writeShort(bytes.length);
        outputStream.write(bytes);
    }

    public static StringTagImpl read(NBTInputStream inputStream) throws IOException {
        var length = inputStream.readShort();
        var bytes = new byte[length];
        inputStream.readFully(bytes);
        var value = new String(bytes, inputStream.getCharset());
        return new StringTagImpl(value);
    }
}
