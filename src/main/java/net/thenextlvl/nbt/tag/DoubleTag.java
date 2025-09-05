package net.thenextlvl.nbt.tag;

import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NullMarked;

/**
 * The DoubleTag class represents a numerical tag holding a Double value within the NBT (Named Binary Tag) system.
 * This class extends the abstract NumberTag class to provide specific functionality for Double values.
 */
@NullMarked
public sealed interface DoubleTag extends NumberTag<Double> permits DoubleTagImpl {
    /**
     * Represents the unique identifier for this Tag.
     */
    int ID = 6;

    /**
     * Creates a new instance of {@code DoubleTag} with the specified double value.
     *
     * @param value the double value to associate with the new {@code DoubleTag} instance
     * @return a new {@code DoubleTag} instance holding the given double value
     * @since 3.0.0
     */
    @Contract(value = "_ -> new", pure = true)
    static DoubleTag of(double value) {
        return new DoubleTagImpl(value);
    }
}
