package net.thenextlvl.nbt.tag;

import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NullMarked;

/**
 * The ByteTag class represents a byte value in the tag structure.
 * It extends the NumberTag class with Byte as the specific number type.
 * This class provides methods to manipulate the byte value and read/write it to an NBT stream.
 */
@NullMarked
public sealed interface ByteTag extends NumberTag<Byte> permits ByteTagImpl {
    /**
     * Represents the unique identifier for this Tag.
     */
    int ID = 1;

    /**
     * Creates a new instance of ByteTag with the specified byte value.
     *
     * @param value the byte value to encapsulate within the ByteTag
     * @return a new ByteTag instance containing the given byte value
     * @since 3.0.0
     */
    @Contract(value = "_ -> new", pure = true)
    static ByteTag of(byte value) {
        return new ByteTagImpl(value);
    }
}
