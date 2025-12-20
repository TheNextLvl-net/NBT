package net.thenextlvl.nbt.tag;

import org.jetbrains.annotations.Contract;

/**
 * The FloatTag class extends the NumberTag class with a specific type parameter of Float.
 * It represents a tag that holds a float value and provides methods to manipulate
 * and interact with this value.
 */
public sealed interface FloatTag extends NumberTag<Float> permits FloatTagImpl {
    /**
     * Represents the unique identifier for this Tag.
     */
    byte ID = 5;

    /**
     * Creates a new instance of FloatTag with the specified float value.
     *
     * @param value the float value to encapsulate within the FloatTag
     * @return a new FloatTag instance containing the given float value
     * @since 3.0.0
     */
    @Contract(value = "_ -> new", pure = true)
    static FloatTag of(float value) {
        return new FloatTagImpl(value);
    }
}
