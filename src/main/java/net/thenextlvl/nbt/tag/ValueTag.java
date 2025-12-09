package net.thenextlvl.nbt.tag;

import org.jetbrains.annotations.Contract;

/**
 * A valued tag type {@code T}.
 * Extends the Tag interface providing common functionality for value-based tags.
 *
 * @param <T> the type of the value held by this tag
 */
public interface ValueTag<T> extends Tag {
    /**
     * Retrieves the value held by this tag.
     *
     * @return the value of type {@code T} associated with this tag
     */
    @Contract(pure = true)
    T getValue();
}
