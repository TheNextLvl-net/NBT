package net.thenextlvl.nbt.tag;

import org.jspecify.annotations.NullMarked;

/**
 * A number-based tag value, extending {@link ValueTag} with a type parameter of {@link Number}.
 *
 * @param <T> the type of the {@link Number} value held by this tag
 */
public interface NumberTag<T extends Number> extends ValueTag<T> {
}
