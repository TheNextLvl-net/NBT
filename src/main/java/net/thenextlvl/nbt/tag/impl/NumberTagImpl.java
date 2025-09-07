package net.thenextlvl.nbt.tag.impl;

import net.thenextlvl.nbt.tag.NumberTag;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.OverrideOnly
public abstract class NumberTagImpl<T extends Number> extends ValueTagImpl<T> implements NumberTag<T> {
    protected NumberTagImpl(T number) {
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
