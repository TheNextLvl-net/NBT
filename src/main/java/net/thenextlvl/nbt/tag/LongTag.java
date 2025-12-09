package net.thenextlvl.nbt.tag;

import net.thenextlvl.nbt.tag.impl.LongTagImpl;
import org.jetbrains.annotations.Contract;

/**
 * LongTag is a specialized implementation of the NumberTag class for handling long type values.
 * This class provides functionality to read and write long values to NBT streams, and identifier management.
 */
public sealed interface LongTag extends NumberTag<Long> permits LongTagImpl {
    /**
     * Represents the unique identifier for this Tag.
     */
    int ID = 4;

    /**
     * Creates a new instance of {@code LongTag} with the specified long value.
     *
     * @param value the long value to encapsulate within the {@code LongTag}
     * @return a new {@code LongTag} instance containing the given long value
     * @since 3.0.0
     */
    @Contract(value = "_ -> new", pure = true)
    static LongTag of(long value) {
        return new LongTagImpl(value);
    }
}
