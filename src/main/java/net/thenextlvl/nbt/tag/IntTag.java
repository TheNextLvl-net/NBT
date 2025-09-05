package net.thenextlvl.nbt.tag;

import net.thenextlvl.nbt.NBTInputStream;
import net.thenextlvl.nbt.NBTOutputStream;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NullMarked;

import java.io.IOException;

/**
 * Represents an integer tag in an NBT (Named Binary Tag) structure.
 * Extends {@link NumberTag} with the specific type parameter {@link Integer}.
 */
@NullMarked
public class IntTag extends NumberTag<Integer> {
    /**
     * Represents the unique identifier for this Tag.
     */
    public static final int ID = 3;

    /**
     * Constructs a new IntTag with the specified integer value.
     *
     * @param value the integer value to be associated with this tag
     */
    public IntTag(Integer value) {
        super(value);
    }

    @Override
    public int getAsInt() {
        return getValue();
    }

    @Override
    public int getTypeId() {
        return ID;
    }

    @Override
    @Contract(mutates = "param1")
    public void write(NBTOutputStream outputStream) throws IOException {
        outputStream.writeInt(getValue());
    }

    /**
     * Reads an integer tag from the provided NBTInputStream.
     *
     * @param inputStream the NBT input stream from which to read the integer tag
     * @return an IntTag read from the input stream
     * @throws IOException if an I/O error occurs while reading from the stream
     */
    @Contract(value = "_ -> new", mutates = "param1")
    public static IntTag read(NBTInputStream inputStream) throws IOException {
        return new IntTag(inputStream.readInt());
    }
}
