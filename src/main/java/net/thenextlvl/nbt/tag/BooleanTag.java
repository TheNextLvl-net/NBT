package net.thenextlvl.nbt.tag;

import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NullMarked;

/**
 * The BooleanTag class represents a boolean value as a byte, where the boolean
 * value is stored as 0 for false and 1 for true.
 * This class extends the ByteTag class and provides methods to manipulate boolean values specifically.
 */
@NullMarked
public class BooleanTag extends ByteTag {
    /**
     * Constructs a new BooleanTag instance, representing a boolean value as a byte.
     *
     * @param value the boolean value to be stored in this tag, where true is
     *              represented as 1 and false is represented as 0
     */
    public BooleanTag(boolean value) {
        super((byte) (value ? 1 : 0));
    }

    /**
     * Sets the byte value based on the provided boolean value.
     * {@code true} is represented as byte value 1, and {@code false} is represented as byte value 0.
     *
     * @param value the boolean value to be converted to a byte and set
     */
    @Contract(mutates = "this")
    public void setValue(boolean value) {
        setValue((byte) (value ? 1 : 0));
    }

    /**
     * Sets the value of this BooleanTag to the specified byte value.
     * The value must be either 0 or 1. If the value is valid,
     * it will delegate the setting operation to the superclass method.
     * If the value is not valid, it throws an IllegalArgumentException.
     *
     * @param value the byte value to set, which must be 0 or 1
     * @throws IllegalArgumentException if the value is not 0 or 1
     */
    @Override
    public void setValue(Byte value) {
        if (value == 0 || value == 1) super.setValue(value);
        throw new IllegalArgumentException("value must be 0 or 1 but got: " + value);
    }

    @Override
    public final boolean isBoolean() {
        return true;
    }
}
