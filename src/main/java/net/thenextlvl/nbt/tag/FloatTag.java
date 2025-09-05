package net.thenextlvl.nbt.tag;

import net.thenextlvl.nbt.NBTInputStream;
import net.thenextlvl.nbt.NBTOutputStream;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NullMarked;

import java.io.IOException;

/**
 * The FloatTag class extends the NumberTag class with a specific type parameter of Float.
 * It represents a tag that holds a float value and provides methods to manipulate
 * and interact with this value.
 */
@NullMarked
public class FloatTag extends NumberTag<Float> {
    /**
     * Represents the unique identifier for this Tag.
     */
    public static final int ID = 5;

    /**
     * Constructs a new instance of the FloatTag class with the specified float value.
     *
     * @param value the float value to be associated with this tag
     */
    public FloatTag(Float value) {
        super(value);
    }

    @Override
    public float getAsFloat() {
        return getValue();
    }

    @Override
    public int getTypeId() {
        return ID;
    }

    @Override
    public void write(NBTOutputStream outputStream) throws IOException {
        outputStream.writeFloat(getValue());
    }

    /**
     * Reads a FloatTag from the given NBTInputStream.
     *
     * @param inputStream the input stream to read from
     * @return the FloatTag that was read
     * @throws IOException if an I/O error occurs while reading from the stream
     */
    @Contract(value = "_ -> new", mutates = "param1")
    public static FloatTag read(NBTInputStream inputStream) throws IOException {
        return new FloatTag(inputStream.readFloat());
    }
}
