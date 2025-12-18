package net.thenextlvl.nbt;

import org.jetbrains.annotations.Contract;

/**
 * Represents a tag that contains a byte array.
 * This class extends {@code ValueTag<byte[]>} and implements {@code IterableTag<Byte>},
 * providing functionality for handling and manipulating a byte array within an NBT (Named Binary Tag) structure.
 * It provides methods for reading from and writing to an {@code NBTInputStream}
 * and {@code NBTOutputStream} respectively.
 */
public sealed interface ByteArrayTag extends ValueTag<byte[]>, IterableTag<Byte> permits ByteArrayTagImpl {
    /**
     * Represents the unique identifier for this Tag.
     */
    int ID = 7;

    /**
     * Creates a new instance of {@code ByteArrayTag} with the specified byte array value.
     *
     * @param value the byte array to be wrapped by the {@code ByteArrayTag}
     * @return a new {@code ByteArrayTag} instance containing the provided byte array
     * @since 3.0.0
     */
    @Contract(value = "_ -> new", pure = true)
    static ByteArrayTag of(byte... value) {
        return new ByteArrayTagImpl(value);
    }
}
