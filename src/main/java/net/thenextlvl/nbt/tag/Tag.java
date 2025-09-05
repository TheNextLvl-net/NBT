package net.thenextlvl.nbt.tag;

import net.thenextlvl.nbt.NBTOutputStream;
import org.jspecify.annotations.NullMarked;

import java.io.IOException;

/**
 * Represents a generic tag with various utility methods to interact with different tag types.
 */
@NullMarked
public interface Tag {
    /**
     * Retrieves the type ID of the tag.
     *
     * @return the integer representing the type ID of the tag
     */
    int getTypeId();

    /**
     * Writes the tag data to the given NBT output stream.
     *
     * @param outputStream the NBTOutputStream to write the tag data to
     * @throws IOException if an I/O error occurs while writing to the stream
     */
    void write(NBTOutputStream outputStream) throws IOException;

    /**
     * Checks whether the current tag is an instance of CompoundTag.
     *
     * @return true if the current tag is a CompoundTag, false otherwise
     */
    default boolean isCompound() {
        return false;
    }

    /**
     * Checks whether the current tag is an instance of ListTag.
     *
     * @return true if the current tag is a ListTag, false otherwise
     */
    default boolean isList() {
        return false;
    }

    /**
     * Checks whether the current tag is an instance of a numeric type.
     *
     * @return true if the current tag is a numeric type, false otherwise
     */
    default boolean isNumber() {
        return false;
    }

    /**
     * Checks whether the current tag is an instance of a boolean type.
     *
     * @return true if the current tag is a boolean type, false otherwise
     */
    default boolean isBoolean() {
        return false;
    }

    /**
     * Checks whether the current tag is an instance of a string type.
     *
     * @return true if the current tag is a string type, false otherwise
     */
    default boolean isString() {
        return false;
    }

    /**
     * Returns the current tag as a CompoundTag if it is an instance of CompoundTag.
     *
     * @return the tag as a CompoundTag
     * @throws UnsupportedOperationException if the current tag is not an instance of CompoundTag
     */
    default CompoundTag getAsCompound() throws UnsupportedOperationException {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    /**
     * Returns the current tag as a ListTag.
     *
     * @param <V> the type of the tags within the ListTag
     * @return the tag as a ListTag
     * @throws UnsupportedOperationException if the current tag is not an instance of ListTag
     */
    default <V extends Tag> ListTag<V> getAsList() throws UnsupportedOperationException {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    /**
     * Returns the current tag as a Number.
     *
     * @return the number representation of the current tag
     * @throws UnsupportedOperationException if the current tag is not a numeric type
     */
    default Number getAsNumber() throws UnsupportedOperationException {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    /**
     * Returns the current tag as a String.
     *
     * @return the string representation of the current tag
     * @throws UnsupportedOperationException if the current tag is not an instance of a string type
     */
    default String getAsString() throws UnsupportedOperationException {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    /**
     * Returns the current tag as a boolean value.
     *
     * @return the boolean representation of the current tag
     * @throws UnsupportedOperationException if the current tag can't be represented as a boolean
     */
    default boolean getAsBoolean() throws UnsupportedOperationException {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    /**
     * Returns the current tag as a double value.
     *
     * @return the double representation of the current tag
     * @throws UnsupportedOperationException if the current tag can't be represented as a double
     */
    default double getAsDouble() throws UnsupportedOperationException {
        return getAsNumber().doubleValue();
    }

    /**
     * Returns the current tag as a float value.
     *
     * @return the float representation of the current tag
     * @throws UnsupportedOperationException if the current tag can't be represented as a float
     */
    default float getAsFloat() throws UnsupportedOperationException {
        return getAsNumber().floatValue();
    }

    /**
     * Returns the current tag as a long value.
     *
     * @return the long representation of the current tag
     * @throws UnsupportedOperationException if the current tag is not a numeric type
     */
    default long getAsLong() throws UnsupportedOperationException {
        return getAsNumber().longValue();
    }

    /**
     * Returns the current tag as an integer.
     *
     * @return the integer representation of the current tag
     * @throws UnsupportedOperationException if the current tag is not a numeric type
     */
    default int getAsInt() throws UnsupportedOperationException {
        return getAsNumber().intValue();
    }

    /**
     * Returns the current tag as a byte value.
     *
     * @return the byte representation of the current tag
     * @throws UnsupportedOperationException if the current tag is not a numeric type
     */
    default byte getAsByte() throws UnsupportedOperationException {
        return getAsNumber().byteValue();
    }

    /**
     * Returns the current tag as a short value.
     *
     * @return the short representation of the current tag
     * @throws UnsupportedOperationException if the current tag is not a numeric type
     */
    default short getAsShort() throws UnsupportedOperationException {
        return getAsNumber().shortValue();
    }
}
