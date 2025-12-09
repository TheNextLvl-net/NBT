package net.thenextlvl.nbt.tag;

import net.thenextlvl.nbt.tag.impl.StringTagImpl;

/**
 * Represents a tag that stores a string value.
 * Extends the ValueTag class and implements methods for reading and writing string tags.
 */
public sealed interface StringTag extends ValueTag<String> permits StringTagImpl {
    /**
     * Represents the unique identifier for this Tag.
     */
    int ID = 8;

    /**
     * Creates a new instance of a StringTag with the specified string value.
     *
     * @param value the string value to be stored in the tag
     * @return a new instance of StringTag containing the provided string value
     * @since 3.0.0
     */
    static StringTag of(String value) {
        return new StringTagImpl(value);
    }
}
