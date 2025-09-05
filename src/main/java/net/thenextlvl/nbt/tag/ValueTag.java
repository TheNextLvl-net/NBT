package net.thenextlvl.nbt.tag;

import org.jspecify.annotations.NullMarked;

import java.util.Objects;

/**
 * An abstract class representing a tagged value of type T.
 * Extends the Tag interface providing common functionality for value-based tags.
 *
 * @param <T> the type of the value held by this tag
 */
@NullMarked
public abstract class ValueTag<T> implements Tag {
    private T value;

    public ValueTag(T value) {
        this.value = value;
    }

    protected T getValue() {
        return value;
    }

    protected void setValue(T value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        var valueTag = (ValueTag<?>) object;
        return Objects.equals(getValue(), valueTag.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() +
               "{" +
               "value=" + value +
               '}';
    }
}
