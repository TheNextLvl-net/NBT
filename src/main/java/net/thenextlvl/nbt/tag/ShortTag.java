package net.thenextlvl.nbt.tag;

import net.thenextlvl.nbt.NBTInputStream;
import net.thenextlvl.nbt.NBTOutputStream;
import org.jspecify.annotations.NullMarked;

import java.io.IOException;

/**
 * The ShortTag class represents a tag that holds a short value.
 * It extends the NumberTag class, providing specific implementations
 * for handling short values in a tag context.
 */
@NullMarked
public class ShortTag extends NumberTag<Short> {
    /**
     * Represents the unique identifier for this Tag.
     */
    public static final int ID = 2;

    /**
     * Constructs a new instance of ShortTag with the specified short value.
     *
     * @param value the short value to be associated with this tag
     */
    public ShortTag(Short value) {
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

    /**
     * Reads a short value from the given NBTInputStream and returns it as a ShortTag.
     *
     * @param inputStream the NBTInputStream from which the short value will be read
     * @return a ShortTag containing the short value read from the inputStream
     * @throws IOException if an I/O error occurs while reading from the inputStream
     */
    public static ShortTag read(NBTInputStream inputStream) throws IOException {
        return new ShortTag(inputStream.readShort());
    }
}
