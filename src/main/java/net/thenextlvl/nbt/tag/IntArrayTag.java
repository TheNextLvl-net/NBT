package net.thenextlvl.nbt.tag;

import net.thenextlvl.nbt.NBTInputStream;
import net.thenextlvl.nbt.NBTOutputStream;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NullMarked;

import java.io.IOException;

/**
 * The IntArrayTag class represents a tag that holds an array of integers. It extends the ValueTag
 * abstract class with an int array and implements the IterableTag interface for iterating over
 * the integer elements.
 */
@NullMarked
public class IntArrayTag extends ValueTag<int[]> implements IterableTag<Integer> {
    /**
     * Represents the unique identifier for this Tag.
     */
    public static final int ID = 11;

    /**
     * Constructs a new {@code IntArrayTag} with the given array of integer values.
     *
     * @param value the array of integer values to be held by this tag
     */
    public IntArrayTag(int... value) {
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
    public Integer get(int index) {
        return getValue()[index];
    }

    @Override
    public void set(int index, Integer element) {
        getValue()[index] = element;
    }

    @Override
    public void write(NBTOutputStream outputStream) throws IOException {
        outputStream.writeInt(getValue().length);
        for (var i : getValue()) outputStream.writeInt(i);
    }

    /**
     * Reads an {@code IntArrayTag} from the provided {@code NBTInputStream}.
     *
     * @param inputStream the input stream to read the {@code IntArrayTag} from
     * @return the {@code IntArrayTag} read from the input stream
     * @throws IOException if an I/O error occurs while reading from the input stream
     */
    @Contract(value = "_ -> new", mutates = "param1")
    public static IntArrayTag read(NBTInputStream inputStream) throws IOException {
        var length = inputStream.readInt();
        var array = new int[length];
        for (var i = 0; i < length; i++) array[i] = inputStream.readInt();
        return new IntArrayTag(array);
    }
}
