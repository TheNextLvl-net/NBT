package net.thenextlvl.nbt.tag;

import net.thenextlvl.nbt.NBTInputStream;
import net.thenextlvl.nbt.NBTOutputStream;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NullMarked;

import java.io.IOException;

/**
 * Represents a tag containing an array of long values.
 * This class extends ValueTag providing common functionality for holding an array of long values
 * and implements IterableTag to allow iteration over the long array elements.
 */
@NullMarked
public class LongArrayTag extends ValueTag<long[]> implements IterableTag<Long> {
    /**
     * Represents the unique identifier for this Tag.
     */
    public static final int ID = 12;

    /**
     * Constructs a new LongArrayTag with the given array of long values.
     *
     * @param value the array of long values to be associated with this tag
     */
    public LongArrayTag(long... value) {
        super(value);
    }

    @Override
    public int getTypeId() {
        return ID;
    }

    @Override
    public int size() {
        return getValue().length;
    }

    @Override
    public Long get(int index) {
        return getValue()[index];
    }

    @Override
    public void set(int index, Long element) {
        getValue()[index] = element;
    }

    @Override
    public void write(NBTOutputStream outputStream) throws IOException {
        outputStream.writeLong(getValue().length);
        for (var l : getValue()) outputStream.writeLong(l);
    }

    /**
     * Reads a LongArrayTag from the specified NBTInputStream.
     *
     * @param inputStream the input stream to read the LongArrayTag from
     * @return the LongArrayTag that was read from the inputStream
     * @throws IOException if an I/O error occurs while reading the stream
     */
    @Contract(value = "_ -> new", mutates = "param1")
    public static LongArrayTag read(NBTInputStream inputStream) throws IOException {
        var length = inputStream.readInt();
        var array = new long[length];
        for (var i = 0; i < length; i++)
            array[i] = inputStream.readLong();
        return new LongArrayTag(array);
    }
}
