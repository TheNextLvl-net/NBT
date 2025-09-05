package net.thenextlvl.nbt.tag;

import net.thenextlvl.nbt.NBTInputStream;
import net.thenextlvl.nbt.NBTOutputStream;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.io.IOException;

@NullMarked
@ApiStatus.Internal
public final class ShortTagImpl extends NumberTagImpl<Short> implements ShortTag {
    ShortTagImpl(Short value) {
        super(value);
    }

    @Override
    public short getAsShort() {
        return getValue();
    }

    @Override
    public int getTypeId() {
        return ID;
    }

    @Override
    public void write(NBTOutputStream outputStream) throws IOException {
        outputStream.writeShort(getValue());
    }

    public static ShortTagImpl read(NBTInputStream inputStream) throws IOException {
        return new ShortTagImpl(inputStream.readShort());
    }
}
