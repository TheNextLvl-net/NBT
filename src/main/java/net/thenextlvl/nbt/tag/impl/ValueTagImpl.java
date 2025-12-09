package net.thenextlvl.nbt.tag.impl;

import net.thenextlvl.nbt.tag.ValueTag;

import java.util.Objects;

abstract class ValueTagImpl<T> implements ValueTag<T> {
    private final T value;

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
