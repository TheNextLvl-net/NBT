package net.thenextlvl.nbt.tag;

import net.thenextlvl.nbt.NBTInputStream;
import net.thenextlvl.nbt.NBTOutputStream;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NullMarked;

import java.io.IOException;

/**
 * LongTag is a specialized implementation of the NumberTag class for handling long type values.
 * This class provides functionality to read and write long values to NBT streams, and identifier management.
 */
@NullMarked
public class LongTag extends NumberTag<Long> {
    /**
     * Represents the unique identifier for this Tag.
     */
    public static final int ID = 4;

    /**
     * Constructs a new instance of LongTag with the specified long value.
     *
     * @param value the long value to be associated with this tag
     */
    public LongTag(Long value) {
        super(value);
    }

    @Override
    public long getAsLong() {
        return getValue();
    }

    @Override
    public int getTypeId() {
        return ID;
    }

    @Override
    public void write(NBTOutputStream outputStream) throws IOException {
        outputStream.writeLong(getValue());
    }

    /**
     * Reads a long value from the provided NBTInputStream and constructs a LongTag with the read value.
     *
     * @param inputStream the NBTInputStream from which to read the long value
     * @return a new LongTag containing the long value read from the inputStream
     * @throws IOException if an I/O error occurs while reading from the inputStream
     */
    @Contract(value = "_ -> new", mutates = "param1")
    public static LongTag read(NBTInputStream inputStream) throws IOException {
        return new LongTag(inputStream.readLong());
    }
}
