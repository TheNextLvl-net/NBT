package net.thenextlvl.nbt.tag;

import org.jetbrains.annotations.Contract;

/**
 * Represents a tag containing an array of long values.
 * This class extends ValueTag providing common functionality for holding an array of long values
 * and implements IterableTag to allow iteration over the long array elements.
 */
public sealed interface LongArrayTag extends ValueTag<long[]>, IterableTag<Long> permits LongArrayTagImpl {
    /**
     * Represents the unique identifier for this Tag.
     */
    byte ID = 12;

    /**
     * Creates a new {@code LongArrayTag} instance with the specified array of long values.
     *
     * @param value the array of long values to encapsulate within the {@code LongArrayTag}
     * @return a new {@code LongArrayTag} instance containing the given array of long values
     * @since 3.0.0
     */
    @Contract(value = "_ -> new", pure = true)
    static LongArrayTag of(long... value) {
        return new LongArrayTagImpl(value);
    }
}
