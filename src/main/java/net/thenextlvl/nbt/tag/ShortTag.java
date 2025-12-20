package net.thenextlvl.nbt.tag;

import org.jetbrains.annotations.Contract;

/**
 * The ShortTag class represents a tag that holds a short value.
 * It extends the NumberTag class, providing specific implementations
 * for handling short values in a tag context.
 */
public sealed interface ShortTag extends NumberTag<Short> permits ShortTagImpl {
    /**
     * Represents the unique identifier for this Tag.
     */
    byte ID = 2;

    /**
     * Creates a new instance of {@code ShortTag} with the specified short value.
     *
     * @param value the short value to encapsulate within the {@code ShortTag}
     * @return a new {@code ShortTag} instance containing the given short value
     * @since 3.0.0
     */
    @Contract(value = "_ -> new", pure = true)
    static ShortTag of(short value) {
        return new ShortTagImpl(value);
    }
}
