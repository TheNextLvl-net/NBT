package net.thenextlvl.nbt.tag;

import net.thenextlvl.nbt.NBTInputStream;
import net.thenextlvl.nbt.NBTOutputStream;
import org.jspecify.annotations.NullMarked;

import java.io.IOException;

/**
 * Represents a tag that contains a byte array.
 * This class extends {@code ValueTag<byte[]>} and implements {@code IterableTag<Byte>},
 * providing functionality for handling and manipulating a byte array within an NBT (Named Binary Tag) structure.
 * It provides methods for reading from and writing to an {@code NBTInputStream}
 * and {@code NBTOutputStream} respectively.
 */
@NullMarked
public class ByteArrayTag extends ValueTag<byte[]> implements IterableTag<Byte> {
    /**
     * Represents the unique identifier for this Tag.
     */
    public static final int ID = 7;

    /**
     * Constructs a new ByteArrayTag with the given byte array.
     *
     * @param array the byte array to be encapsulated by this tag
     */
    public ByteArrayTag(byte... array) {
        super(array);
    }

    @Override
    public int getTypeId() {
        return ID;
    }

    @Override
    public int size() {
        return getValue().length;
    }

    @Override
    public Byte get(int index) {
        return getValue()[index];
    }

    @Override
    public void set(int index, Byte element) {
        getValue()[index] = element;
    }

    @Override
    public void write(NBTOutputStream outputStream) throws IOException {
        outputStream.writeInt(getValue().length);
        outputStream.write(getValue());
    }

    /**
     * Reads a ByteArrayTag from the given NBTInputStream.
     *
     * @param inputStream the NBT input stream to read the byte array from
     * @return a ByteArrayTag containing the byte array read from the input stream
     * @throws IOException if an I/O error occurs while reading from the input stream
     */
    public static ByteArrayTag read(NBTInputStream inputStream) throws IOException {
        var length = inputStream.readInt();
        var bytes = new byte[length];
        inputStream.readFully(bytes);
        return new ByteArrayTag(bytes);
    }
}
