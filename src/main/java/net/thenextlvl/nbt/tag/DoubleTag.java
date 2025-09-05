package net.thenextlvl.nbt.tag;

import net.thenextlvl.nbt.NBTInputStream;
import net.thenextlvl.nbt.NBTOutputStream;
import org.jspecify.annotations.NullMarked;

import java.io.IOException;

/**
 * The DoubleTag class represents a numerical tag holding a Double value within the NBT (Named Binary Tag) system.
 * This class extends the abstract NumberTag class to provide specific functionality for Double values.
 */
@NullMarked
public class DoubleTag extends NumberTag<Double> {
    /**
     * Represents the unique identifier for this Tag.
     */
    public static final int ID = 6;

    /**
     * Constructs a new instance of DoubleTag with the specified Double value.
     *
     * @param value the Double value to be associated with this tag
     */
    public DoubleTag(Double value) {
        super(value);
    }

    @Override
    public double getAsDouble() {
        return getValue();
    }

    @Override
    public int getTypeId() {
        return ID;
    }

    @Override
    public void write(NBTOutputStream outputStream) throws IOException {
        outputStream.writeDouble(getValue());
    }

    /**
     * Reads a DoubleTag from the specified NBTInputStream.
     *
     * @param inputStream the input stream to read the DoubleTag from
     * @return the DoubleTag read from the input stream
     * @throws IOException if an I/O error occurs while reading from the input stream
     */
    public static DoubleTag read(NBTInputStream inputStream) throws IOException {
        return new DoubleTag(inputStream.readDouble());
    }
}
