package net.thenextlvl.nbt.tag;

import org.jetbrains.annotations.Contract;

/**
 * The ByteTag class represents a byte value in the tag structure.
 * It extends the NumberTag class with Byte as the specific number type.
 * This class provides methods to manipulate the byte value and read/write it to an NBT stream.
 */
public sealed interface ByteTag extends NumberTag<Byte> permits ByteTagImpl {
    /**
     * Represents the unique identifier for this Tag.
     */
    byte ID = 1;

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

    /**
     * Creates a new instance of ByteTag with the specified boolean value.
     * The boolean value is converted to a byte value (1 for true, 0 for false).
     *
     * @param value the boolean value to encapsulate within the ByteTag
     * @return a new ByteTag instance containing the converted byte value
     * @since 4.0.0
     */
    @Contract(value = "_ -> new", pure = true)
    static ByteTag of(boolean value) {
        return of(value ? (byte) 1 : (byte) 0);
    }
}
