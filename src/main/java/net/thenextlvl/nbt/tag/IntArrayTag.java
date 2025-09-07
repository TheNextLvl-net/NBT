package net.thenextlvl.nbt.tag;

import net.thenextlvl.nbt.tag.impl.IntArrayTagImpl;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NullMarked;

/**
 * The IntArrayTag class represents a tag that holds an array of integers. It extends the ValueTag
 * abstract class with an int array and implements the IterableTag interface for iterating over
 * the integer elements.
 */
@NullMarked
public sealed interface IntArrayTag extends ValueTag<int[]>, IterableTag<Integer> permits IntArrayTagImpl {
    /**
     * Represents the unique identifier for this Tag.
     */
    int ID = 11;

    /**
     * Creates a new instance of IntArrayTag containing the specified integer values.
     *
     * @param value the integer array to initialize the IntArrayTag with
     * @return a new IntArrayTag instance containing the provided integer values
     * @since 3.0.0
     */
    @Contract(value = "_ -> new", pure = true)
    static IntArrayTag of(int... value) {
        return new IntArrayTagImpl(value);
    }
}
