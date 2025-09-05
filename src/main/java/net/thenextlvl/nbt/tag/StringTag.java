package net.thenextlvl.nbt.tag;

import net.thenextlvl.nbt.NBTInputStream;
import net.thenextlvl.nbt.NBTOutputStream;
import org.jspecify.annotations.NullMarked;

import java.io.IOException;

/**
 * Represents a tag that stores a string value.
 * Extends the ValueTag class and implements methods for reading and writing string tags.
 */
@NullMarked
public class StringTag extends ValueTag<String> {
    /**
     * Represents the unique identifier for this Tag.
     */
    public static final int ID = 8;

    /**
     * Constructs a new StringTag with the specified string value.
     *
     * @param value the string value for this tag
     */
    public StringTag(String value) {
        super(value);
    }

    @Override
    public final boolean isString() {
        return true;
    }

    @Override
    public String getAsString() {
        return getValue();
    }

    @Override
    public int getTypeId() {
        return ID;
    }

    @Override
    public void write(NBTOutputStream outputStream) throws IOException {
        var bytes = getValue().getBytes(outputStream.getCharset());
        outputStream.writeShort(bytes.length);
        outputStream.write(bytes);
    }

    /**
     * Reads a StringTag from the given NBTInputStream.
     *
     * @param inputStream the NBTInputStream from which to read the StringTag
     * @return a new StringTag object containing the string read from the input stream
     * @throws IOException if an I/O error occurs while reading from the input stream
     */
    public static StringTag read(NBTInputStream inputStream) throws IOException {
        var length = inputStream.readShort();
        var bytes = new byte[length];
        inputStream.readFully(bytes);
        var value = new String(bytes, inputStream.getCharset());
        return new StringTag(value);
    }
}
