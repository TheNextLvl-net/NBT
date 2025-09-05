package net.thenextlvl.nbt.tag;

import net.thenextlvl.nbt.NBTInputStream;
import net.thenextlvl.nbt.NBTOutputStream;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NullMarked;

import java.io.IOException;

/**
 * The ByteTag class represents a byte value in the tag structure.
 * It extends the NumberTag class with Byte as the specific number type.
 * This class provides methods to manipulate the byte value and read/write it to an NBT stream.
 */
@NullMarked
public class ByteTag extends NumberTag<Byte> {
    /**
     * Represents the unique identifier for this Tag.
     */
    public static final int ID = 1;

    /**
     * Constructs a new ByteTag instance with the specified byte value.
     *
     * @param value the byte value to be stored in this tag
     */
    public ByteTag(Byte value) {
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

    /**
     * Reads a byte value from the provided NBTInputStream and returns it as a ByteTag.
     *
     * @param inputStream the NBTInputStream to read the byte value from
     * @return a ByteTag containing the read byte value
     * @throws IOException if an I/O error occurs while reading from the input stream
     */
    @Contract(value = "_ -> new", mutates = "param1")
    public static ByteTag read(NBTInputStream inputStream) throws IOException {
        return new ByteTag(inputStream.readByte());
    }
}
