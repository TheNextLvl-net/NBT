package net.thenextlvl.nbt.tag;

import java.util.Objects;

abstract class ValueTagImpl<T> implements ValueTag<T> {
    protected final T value;

    protected ValueTagImpl(T value) {
        this.value = value;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        var valueTag = (ValueTagImpl<?>) object;
        return Objects.equals(value, valueTag.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
