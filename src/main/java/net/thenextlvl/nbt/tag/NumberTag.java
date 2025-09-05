package net.thenextlvl.nbt.tag;

import org.jspecify.annotations.NullMarked;

/**
 * An abstract class representing a number-based tag value, extending the
 * {@link ValueTag} class with a type parameter of {@link Number}.
 *
 * @param <T> the type of the {@link Number} value held by this tag
 */
@NullMarked
public abstract class NumberTag<T extends Number> extends ValueTag<T> {
    /**
     * Constructs a new instance of NumberTag with the specified number value.
     *
     * @param number the number value to be associated with this tag
     */
    public NumberTag(T number) {
        super(number);
    }

    @Override
    public final boolean isNumber() {
        return true;
    }

    @Override
    public boolean getAsBoolean() {
        return getAsNumber().byteValue() == 1;
    }

    @Override
    public Number getAsNumber() {
        return getValue();
    }
}
