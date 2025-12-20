package net.thenextlvl.nbt.tag;

import org.jetbrains.annotations.Contract;

/**
 * A number-based tag value, extending {@link ValueTag} with a type parameter of {@link Number}.
 *
 * @param <T> the type of the {@link Number} value held by this tag
 */
public interface NumberTag<T extends Number> extends ValueTag<T> {
    /**
     * Creates a new instance of {@link NumberTag} based on the provided {@link Number}.
     * The numerical value is represented using a tag corresponding to its type:
     * <ul>
     *     <li>Integer values are stored as {@link IntTag}.</li>
     *     <li>Float values are stored as {@link FloatTag}.</li>
     *     <li>Short values are stored as {@link ShortTag}.</li>
     *     <li>Long values are stored as {@link LongTag}.</li>
     *     <li>Byte values are stored as {@link ByteTag}.</li>
     *     <li>Any other numeric type defaults to {@link DoubleTag}.</li>
     * </ul>
     *
     * @param number the number value to be encapsulated within the tag
     * @return a new {@link NumberTag} instance representing the provided number
     * @since 4.0.0
     */
    @Contract(value = "_ -> new", pure = true)
    static NumberTag<?> of(Number number) {
        return switch (number) {
            case Integer value -> IntTag.of(value);
            case Float value -> FloatTag.of(value);
            case Short value -> ShortTag.of(value);
            case Long value -> LongTag.of(value);
            case Byte value -> ByteTag.of(value);
            default -> DoubleTag.of(number.doubleValue());
        };
    }
}
