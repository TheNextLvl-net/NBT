package net.thenextlvl.nbt.tag;

import net.thenextlvl.nbt.NBTInputStream;
import net.thenextlvl.nbt.NBTOutputStream;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.io.IOException;

@NullMarked
@ApiStatus.Internal
public final class ByteTagImpl extends NumberTagImpl<Byte> implements ByteTag {
    ByteTagImpl(Byte value) {
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
