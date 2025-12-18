package net.thenextlvl.nbt;

import org.jetbrains.annotations.Contract;

/**
 * Represents an integer tag in an NBT (Named Binary Tag) structure.
 * Extends {@link NumberTag} with the specific type parameter {@link Integer}.
 */
public sealed interface IntTag extends NumberTag<Integer> permits IntTagImpl {
    /**
     * Represents the unique identifier for this Tag.
     */
    int ID = 3;

    /**
     * Creates a new instance of {@code IntTag} with the specified integer value.
     *
     * @param value the integer value to encapsulate within the {@code IntTag}
     * @return a new {@code IntTag} instance containing the given integer value
     * @since 3.0.0
     */
    @Contract(value = "_ -> new", pure = true)
    static IntTag of(int value) {
        return new IntTagImpl(value);
    }
}
