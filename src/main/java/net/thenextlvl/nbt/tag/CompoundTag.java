package net.thenextlvl.nbt.tag;

import net.thenextlvl.nbt.NBTInputStream;
import net.thenextlvl.nbt.NBTOutputStream;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.NullUnmarked;
import org.jspecify.annotations.Nullable;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;

/**
 * The `CompoundTag` class represents a compound tag structure containing a map of named tags.
 * It extends the generic `ValueTag` class setting the value type to a `Map` of `String` keys and `Tag` values.
 * This class provides various methods to manipulate and retrieve tags from the compound tag structure.
 */
@NullMarked
public class CompoundTag extends ValueTag<Map<String, Tag>> {
    /**
     * Represents the unique identifier for this Tag.
     */
    public static final int ID = 10;

    /**
     * Initializes a new instance of the CompoundTag class using the provided map of tag values.
     *
     * @param value a map of strings to tags that will serve as the initial values for this compound tag
     */
    public CompoundTag(Map<String, Tag> value) {
        super(value);
    }

    /**
     * Default constructor for CompoundTag.
     * Initializes a new instance of CompoundTag with an empty HashMap.
     */
    public CompoundTag() {
        this(new HashMap<>());
    }

    @Override
    public final boolean isCompound() {
        return true;
    }

    @Override
    public CompoundTag getAsCompound() {
        return this;
    }

    @Override
    public int getTypeId() {
        return ID;
    }

    /**
     * Adds a tag to the compound tag with the specified name.
     *
     * @param name the name of the tag to be added
     * @param tag  the tag to be added
     */
    public void add(String name, Tag tag) {
        getValue().put(name, tag);
    }

    /**
     * Removes a tag from the compound tag with the specified name.
     *
     * @param name the name of the tag to be removed
     * @return the removed tag, or null if no tag with the specified name existed
     */
    public @Nullable Tag remove(String name) {
        return getValue().remove(name);
    }

    /**
     * Adds a tag to the compound tag with the specified name.
     *
     * @param name  the name of the tag to be added
     * @param value the value of the tag to be added
     */
    public void add(String name, String value) {
        add(name, new StringTag(value));
    }

    /**
     * Adds a byte array tag to the compound tag with the specified name.
     *
     * @param name  the name of the tag to be added
     * @param value the byte array to be added as a tag
     */
    public void add(String name, byte[] value) {
        add(name, new ByteArrayTag(value));
    }

    /**
     * Adds an integer array tag to the compound tag with the specified name.
     *
     * @param name  the name of the tag to be added
     * @param value the integer array to be added as a tag
     */
    public void add(String name, int[] value) {
        add(name, new IntArrayTag(value));
    }

    /**
     * Adds a long array tag to the compound tag with the specified name.
     *
     * @param name  the name of the tag to be added
     * @param value the long array to be added as a tag
     */
    public void add(String name, long[] value) {
        add(name, new LongArrayTag(value));
    }

    /**
     * Adds a number tag to the compound tag with the specified name.
     *
     * @param name   the name of the tag to be added
     * @param number the value of the number tag to be added.
     *               The method supports Integer, Float, Short, Long, Byte, and default cases to Double.
     */
    public void add(String name, Number number) {
        switch (number) {
            case Integer value -> add(name, new IntTag(value));
            case Float value -> add(name, new FloatTag(value));
            case Short value -> add(name, new ShortTag(value));
            case Long value -> add(name, new LongTag(value));
            case Byte value -> add(name, new ByteTag(value));
            default -> add(name, new DoubleTag(number.doubleValue()));
        }
    }

    /**
     * Adds a boolean tag to the compound tag with the specified name.
     *
     * @param name  the name of the tag to be added
     * @param value the boolean value of the tag to be added
     */
    public void add(String name, Boolean value) {
        add(name, new ByteTag(value ? (byte) 1 : 0));
    }

    /**
     * Adds all tags from the provided CompoundTag to this CompoundTag.
     *
     * @param tag the CompoundTag containing tags to be added to this CompoundTag
     */
    public void addAll(CompoundTag tag) {
        tag.forEach(this::add);
    }

    /**
     * Performs the given action for each entry in this compound tag.
     *
     * @param action the action to be performed for each entry in this compound tag; must accept two
     *               arguments: the key of type String and the tag of type Tag
     */
    public void forEach(BiConsumer<? super String, ? super Tag> action) {
        getValue().forEach(action);
    }

    /**
     * Returns a set view of the entries contained in this compound tag.
     * Each entry is a key-value pair represented by {@code Map.Entry<String, Tag>}.
     *
     * @return a set view of the entries in this compound tag
     */
    public Set<Map.Entry<String, Tag>> entrySet() {
        return getValue().entrySet();
    }

    /**
     * Returns a set view of the keys contained in this compound tag.
     *
     * @return a set of the keys in this compound tag.
     */
    public Set<String> keySet() {
        return getValue().keySet();
    }

    /**
     * Checks if the compound tag is empty.
     *
     * @return true if the compound tag has no entries, false otherwise
     */
    public boolean isEmpty() {
        return getValue().isEmpty();
    }

    /**
     * Returns the number of tags contained in this compound tag.
     *
     * @return the number of tags in this compound tag
     */
    public int size() {
        return getValue().size();
    }

    /**
     * Checks whether a tag with the specified property name exists in the compound tag.
     *
     * @param property the name of the property to check for
     * @return true if the property exists in the compound tag, false otherwise
     */
    public boolean containsKey(String property) {
        return getValue().containsKey(property);
    }

    /**
     * Retrieves and casts a tag from the compound tag based on the given property name.
     *
     * @param property the name of the property to retrieve the tag for
     * @param <T>      the type of the tag extending Tag
     * @return the tag associated with the given property name, cast to the expected type,
     * or null if no such property exists
     */
    @NullUnmarked
    @SuppressWarnings("unchecked")
    public <T extends Tag> T get(String property) {
        return (T) getValue().get(property);
    }

    /**
     * Retrieves a tag from the compound tag and returns it as a ListTag.
     *
     * @param tag the name of the tag to be retrieved and cast as a ListTag
     * @param <E> the type of the elements in the ListTag, extending Tag
     * @return the ListTag associated with the given tag name
     */
    public <E extends Tag> ListTag<E> getAsList(String tag) {
        return get(tag).getAsList();
    }

    /**
     * Retrieves a tag from the current value and returns it as a CompoundTag.
     *
     * @param tag the name of the tag to be retrieved and cast as a CompoundTag
     * @return the CompoundTag associated with the given tag name
     */
    public CompoundTag getAsCompound(String tag) {
        return get(tag).getAsCompound();
    }

    /**
     * Retrieves the tag associated with the specified name or adds a default tag if it doesn't exist.
     *
     * @param tag          the name of the tag to be retrieved
     * @param defaultValue the default tag to be added if no tag with the specified name exists
     * @param <T>          the type of the tag extending Tag
     * @return the tag associated with the specified name, or the default tag if the name didn't previously exist
     */
    @SuppressWarnings("unchecked")
    public <T extends Tag> T getOrAdd(String tag, T defaultValue) {
        var value = get(tag);
        if (value != null) return (T) value;
        add(tag, defaultValue);
        return defaultValue;
    }

    /**
     * Retrieves the tag associated with the specified name or returns a default tag if it doesn't exist.
     *
     * @param tag          the name of the tag to be retrieved
     * @param defaultValue the default tag to be returned if no tag with the specified name exists
     * @param <T>          the type of the tag extending Tag
     * @return the tag associated with the specified name, or the default tag if the name didn't previously exist
     */
    @NullUnmarked
    @SuppressWarnings("unchecked")
    public <T extends Tag> T getOrDefault(String tag, T defaultValue) {
        return (T) getValue().getOrDefault(tag, defaultValue);
    }

    /**
     * Retrieves an optional tag associated with the specified name.
     * If the tag is not found, an empty Optional is returned.
     *
     * @param tag the name of the tag to be retrieved
     * @param <T> the type of the tag extending Tag
     * @return an Optional containing the tag if found, or an empty Optional if not
     */
    @SuppressWarnings("unchecked")
    public <T extends Tag> Optional<T> optional(String tag) {
        return Optional.ofNullable(get(tag)).map(value -> (T) value);
    }

    /**
     * Converts the current instance of the CompoundTag to a Builder instance.
     * The Builder allows for modifications to the compound tag before creating a new CompoundTag instance.
     *
     * @return a new Builder instance initialized with the current values of this CompoundTag
     */
    public Builder toBuilder() {
        return new Builder(new HashMap<>(getValue()));
    }

    @Override
    public void write(NBTOutputStream outputStream) throws IOException {
        for (var entry : entrySet()) outputStream.writeTag(entry.getKey(), entry.getValue());
        EscapeTag.INSTANCE.write(outputStream);
    }

    /**
     * Reads the compound tag from the specified NBT input stream.
     *
     * @param inputStream the input stream from which to read the compound tag
     * @return the compound tag read from the input stream
     * @throws IOException if an I/O error occurs while reading from the input stream
     */
    public static CompoundTag read(NBTInputStream inputStream) throws IOException {
        var value = new HashMap<String, Tag>();
        while (true) {
            var entry = inputStream.readNamedTag();
            if (entry.getValue().isEmpty()) break;
            value.put(entry.getValue().get(), entry.getKey());
        }
        return new CompoundTag(value);
    }

    /**
     * Returns a new Builder instance for constructing a CompoundTag.
     *
     * @return a new Builder instance to construct a CompoundTag
     */
    public static Builder builder() {
        return new Builder(new HashMap<>());
    }

    /**
     * A static nested builder class for constructing instances of {@code CompoundTag}.
     * Provides methods to configure and build a {@code CompoundTag} using a fluent API.
     */
    public static class Builder {
        private final Map<String, Tag> values;

        private Builder(Map<String, Tag> values) {
            this.values = values;
        }

        /**
         * Adds a boolean value to the builder with the given name.
         * The boolean value is internally represented as a {@link ByteTag},
         * where true is stored as 1 and false as 0.
         *
         * @param name  the name of the value to be inserted
         * @param value the boolean value to insert; true is represented as 1, false as 0
         * @return the builder instance, allowing for method chaining
         */
        public Builder put(String name, Boolean value) {
            return put(name, new ByteTag(value ? (byte) 1 : 0));
        }

        /**
         * Adds a byte array value to the builder with the given name.
         * The byte array is encapsulated within a {@link ByteArrayTag}.
         *
         * @param name  the name of the value to be inserted
         * @param array the byte array to be inserted
         * @return the builder instance, allowing for method chaining
         */
        public Builder put(String name, byte... array) {
            return put(name, new ByteArrayTag(array));
        }

        /**
         * Adds an integer array value to the builder with the given name.
         * The integer array is encapsulated within an {@code IntArrayTag}.
         *
         * @param name  the name of the value to be inserted
         * @param array the integer array to be inserted
         * @return the builder instance, allowing for method chaining
         */
        public Builder put(String name, int... array) {
            return put(name, new IntArrayTag(array));
        }

        /**
         * Adds a long array value to the builder with the given name.
         * The long array is encapsulated within a {@code LongArrayTag}.
         *
         * @param name  the name of the value to be inserted
         * @param array the long array to be inserted
         * @return the builder instance, allowing for method chaining
         */
        public Builder put(String name, long... array) {
            return put(name, new LongArrayTag(array));
        }

        /**
         * Adds a numerical value to the builder with the specified name.
         * The numerical value is internally represented using a tag corresponding to its type:
         * <ul>
         *     <li>Integer values are stored as {@link IntTag}.</li>
         *     <li>Float values are stored as {@link FloatTag}.</li>
         *     <li>Short values are stored as {@link ShortTag}.</li>
         *     <li>Long values are stored as {@link LongTag}.</li>
         *     <li>Byte values are stored as {@link ByteTag}.</li>
         *     <li>Any other numeric type defaults to {@link DoubleTag}.</li>
         * </ul>
         *
         * @param name   the name of the value to be inserted
         * @param number the numerical value to be inserted
         * @return the builder instance, allowing for method chaining
         */
        public Builder put(String name, Number number) {
            return switch (number) {
                case Integer value -> put(name, new IntTag(value));
                case Float value -> put(name, new FloatTag(value));
                case Short value -> put(name, new ShortTag(value));
                case Long value -> put(name, new LongTag(value));
                case Byte value -> put(name, new ByteTag(value));
                default -> put(name, new DoubleTag(number.doubleValue()));
            };
        }

        /**
         * Adds a string value to the builder with the given name.
         * The string value is encapsulated within a {@link StringTag}.
         *
         * @param name  the name of the value to be added
         * @param value the string value to be inserted
         * @return the builder instance, allowing for method chaining
         */
        public Builder put(String name, String value) {
            return put(name, new StringTag(value));
        }

        /**
         * Adds a tag value to the builder with the specified name.
         * The tag value is associated with the provided name and stored within the builder.
         *
         * @param name the name of the value to be inserted
         * @param tag  the tag value to be inserted
         * @return the builder instance, allowing for method chaining
         */
        public Builder put(String name, Tag tag) {
            values.put(name, tag);
            return this;
        }

        /**
         * Adds all the entries from the given {@link CompoundTag} to the builder.
         * The values from the provided tag will be merged into the current builder.
         *
         * @param tag the {@link CompoundTag} containing values to be added
         * @return the builder instance, allowing for method chaining
         */
        public Builder putAll(CompoundTag tag) {
            values.putAll(tag.getValue());
            return this;
        }

        /**
         * Builds and returns a new {@link CompoundTag} using the current state
         * of values within the builder.
         *
         * @return a new {@link CompoundTag} instance containing the values specified in this builder.
         */
        public CompoundTag build() {
            return new CompoundTag(values);
        }
    }
}
