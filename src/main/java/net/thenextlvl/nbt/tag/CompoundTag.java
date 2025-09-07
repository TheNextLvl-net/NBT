package net.thenextlvl.nbt.tag;

import net.thenextlvl.nbt.tag.impl.CompoundTagImpl;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.NullUnmarked;
import org.jspecify.annotations.Nullable;

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
public sealed interface CompoundTag extends ValueTag<Map<String, Tag>> permits CompoundTagImpl {
    /**
     * Represents the unique identifier for this Tag.
     */
    int ID = 10;

    /**
     * Removes a tag from the compound tag with the specified name.
     *
     * @param name the name of the tag to be removed
     * @return the removed tag, or null if no tag with the specified name existed
     */
    @Nullable
    @Contract(mutates = "this")
    Tag remove(String name);

    /**
     * Adds a tag to the compound tag with the specified name.
     *
     * @param name the name of the tag to be added
     * @param tag  the tag to be added
     */
    @Contract(mutates = "this")
    void add(String name, Tag tag);

    /**
     * Adds a tag to the compound tag with the specified name.
     *
     * @param name  the name of the tag to be added
     * @param value the value of the tag to be added
     */
    @Contract(mutates = "this")
    void add(String name, String value);

    /**
     * Adds a byte array tag to the compound tag with the specified name.
     *
     * @param name  the name of the tag to be added
     * @param value the byte array to be added as a tag
     */
    @Contract(mutates = "this")
    void add(String name, byte[] value);

    /**
     * Adds an integer array tag to the compound tag with the specified name.
     *
     * @param name  the name of the tag to be added
     * @param value the integer array to be added as a tag
     */
    @Contract(mutates = "this")
    void add(String name, int[] value);

    /**
     * Adds a long array tag to the compound tag with the specified name.
     *
     * @param name  the name of the tag to be added
     * @param value the long array to be added as a tag
     */
    @Contract(mutates = "this")
    void add(String name, long[] value);

    /**
     * Adds a number tag to the compound tag with the specified name.
     *
     * @param name   the name of the tag to be added
     * @param number the value of the number tag to be added.
     *               The method supports Integer, Float, Short, Long, Byte, and default cases to Double.
     */
    @Contract(mutates = "this")
    void add(String name, Number number);

    /**
     * Adds a boolean tag to the compound tag with the specified name.
     *
     * @param name  the name of the tag to be added
     * @param value the boolean value of the tag to be added
     */
    @Contract(mutates = "this")
    void add(String name, Boolean value);

    /**
     * Adds all tags from the provided CompoundTag to this CompoundTag.
     *
     * @param tag the CompoundTag containing tags to be added to this CompoundTag
     */
    @Contract(mutates = "this")
    void addAll(CompoundTag tag);

    /**
     * Performs the given action for each entry in this compound tag.
     *
     * @param action the action to be performed for each entry in this compound tag; must accept two
     *               arguments: the key of type String and the tag of type Tag
     */
    void forEach(BiConsumer<? super String, ? super Tag> action);

    /**
     * Returns a set view of the entries contained in this compound tag.
     * Each entry is a key-value pair represented by {@code Map.Entry<String, Tag>}.
     *
     * @return a set view of the entries in this compound tag
     */
    @Contract(pure = true)
    Set<Map.Entry<String, Tag>> entrySet();

    /**
     * Returns a set view of the keys contained in this compound tag.
     *
     * @return a set of the keys in this compound tag.
     */
    @Contract(pure = true)
    Set<String> keySet();

    /**
     * Checks if the compound tag is empty.
     *
     * @return true if the compound tag has no entries, false otherwise
     */
    @Contract(pure = true)
    boolean isEmpty();

    /**
     * Returns the number of tags contained in this compound tag.
     *
     * @return the number of tags in this compound tag
     */
    @Contract(pure = true)
    int size();

    /**
     * Checks whether a tag with the specified property name exists in the compound tag.
     *
     * @param property the name of the property to check for
     * @return true if the property exists in the compound tag, false otherwise
     */
    @Contract(pure = true)
    boolean containsKey(String property);

    /**
     * Retrieves and casts a tag from the compound tag based on the given property name.
     *
     * @param property the name of the property to retrieve the tag for
     * @param <T>      the type of the tag extending Tag
     * @return the tag associated with the given property name, cast to the expected type,
     * or null if no such property exists
     */
    @NullUnmarked
    @Contract(pure = true)
    <T extends Tag> T get(String property);

    /**
     * Retrieves a tag from the compound tag and returns it as a ListTag.
     *
     * @param tag the name of the tag to be retrieved and cast as a ListTag
     * @param <E> the type of the elements in the ListTag, extending Tag
     * @return the ListTag associated with the given tag name
     */
    @Contract(pure = true)
    <E extends Tag> ListTag<E> getAsList(String tag);

    /**
     * Retrieves a tag from the current value and returns it as a CompoundTag.
     *
     * @param tag the name of the tag to be retrieved and cast as a CompoundTag
     * @return the CompoundTag associated with the given tag name
     */
    @Contract(pure = true)
    CompoundTag getAsCompound(String tag);

    /**
     * Retrieves the tag associated with the specified name or adds a default tag if it doesn't exist.
     *
     * @param tag          the name of the tag to be retrieved
     * @param defaultValue the default tag to be added if no tag with the specified name exists
     * @param <T>          the type of the tag extending Tag
     * @return the tag associated with the specified name, or the default tag if the name didn't previously exist
     */
    @Contract(mutates = "this")
    <T extends Tag> T getOrAdd(String tag, T defaultValue);

    /**
     * Retrieves the tag associated with the specified name or returns a default tag if it doesn't exist.
     *
     * @param tag          the name of the tag to be retrieved
     * @param defaultValue the default tag to be returned if no tag with the specified name exists
     * @param <T>          the type of the tag extending Tag
     * @return the tag associated with the specified name, or the default tag if the name didn't previously exist
     */
    @NullUnmarked
    @Contract(pure = true)
    <T extends Tag> T getOrDefault(String tag, T defaultValue);

    /**
     * Retrieves an optional tag associated with the specified name.
     * If the tag is not found, an empty Optional is returned.
     *
     * @param tag the name of the tag to be retrieved
     * @param <T> the type of the tag extending Tag
     * @return an Optional containing the tag if found, or an empty Optional if not
     */
    @Contract(pure = true)
    <T extends Tag> Optional<T> optional(String tag);

    /**
     * Converts the current instance of the CompoundTag to a Builder instance.
     * The Builder allows for modifications to the compound tag before creating a new CompoundTag instance.
     *
     * @return a new Builder instance initialized with the current values of this CompoundTag
     */
    @Contract(value = " -> new", pure = true)
    Builder toBuilder();

    /**
     * Returns a new Builder instance for constructing a CompoundTag.
     *
     * @return a new Builder instance to construct a CompoundTag
     */
    @Contract(value = " -> new", pure = true)
    static Builder builder() {
        return new CompoundTagImpl.Builder();
    }

    /**
     * Creates and returns an empty instance of a CompoundTag.
     *
     * @return a new empty CompoundTag
     * @since 3.0.0
     */
    @Contract(value = " -> new", pure = true)
    static CompoundTag empty() {
        return of(new HashMap<>());
    }

    /**
     * Creates a new instance of a CompoundTag with the given map of string keys and tag values.
     *
     * @param value the map containing the keys and corresponding tags to initialize the CompoundTag
     * @return a new CompoundTag containing the provided map of entries
     * @since 3.0.0
     */
    @Contract(value = "_ -> new", pure = true)
    static CompoundTag of(Map<String, Tag> value) {
        return new CompoundTagImpl(value);
    }

    /**
     * A static nested builder class for constructing instances of {@code CompoundTag}.
     * Provides methods to configure and build a {@code CompoundTag} using a fluent API.
     */
    sealed interface Builder permits CompoundTagImpl.Builder {
        /**
         * Adds a boolean value to the builder with the given name.
         * The boolean value is internally represented as a {@link ByteTag},
         * where true is stored as 1 and false as 0.
         *
         * @param name  the name of the value to be inserted
         * @param value the boolean value to insert; true is represented as 1, false as 0
         * @return the builder instance, allowing for method chaining
         */
        @Contract(value = "_, _ -> this", mutates = "this")
        Builder put(String name, Boolean value);

        /**
         * Adds a byte array value to the builder with the given name.
         * The byte array is encapsulated within a {@link ByteArrayTag}.
         *
         * @param name  the name of the value to be inserted
         * @param array the byte array to be inserted
         * @return the builder instance, allowing for method chaining
         */
        @Contract(value = "_, _ -> this", mutates = "this")
        Builder put(String name, byte... array);

        /**
         * Adds an integer array value to the builder with the given name.
         * The integer array is encapsulated within an {@code IntArrayTag}.
         *
         * @param name  the name of the value to be inserted
         * @param array the integer array to be inserted
         * @return the builder instance, allowing for method chaining
         */
        @Contract(value = "_, _ -> this", mutates = "this")
        Builder put(String name, int... array);

        /**
         * Adds a long array value to the builder with the given name.
         * The long array is encapsulated within a {@code LongArrayTag}.
         *
         * @param name  the name of the value to be inserted
         * @param array the long array to be inserted
         * @return the builder instance, allowing for method chaining
         */
        @Contract(value = "_, _ -> this", mutates = "this")
        Builder put(String name, long... array);

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
        @Contract(value = "_, _ -> this", mutates = "this")
        Builder put(String name, Number number);

        /**
         * Adds a string value to the builder with the given name.
         * The string value is encapsulated within a {@link StringTag}.
         *
         * @param name  the name of the value to be added
         * @param value the string value to be inserted
         * @return the builder instance, allowing for method chaining
         */
        @Contract(value = "_, _ -> this", mutates = "this")
        Builder put(String name, String value);

        /**
         * Adds a tag value to the builder with the specified name.
         * The tag value is associated with the provided name and stored within the builder.
         *
         * @param name the name of the value to be inserted
         * @param tag  the tag value to be inserted
         * @return the builder instance, allowing for method chaining
         */
        @Contract(value = "_, _ -> this", mutates = "this")
        Builder put(String name, Tag tag);

        /**
         * Adds all the entries from the given {@link CompoundTag} to the builder.
         * The values from the provided tag will be merged into the current builder.
         *
         * @param tag the {@link CompoundTag} containing values to be added
         * @return the builder instance, allowing for method chaining
         */
        @Contract(value = "_ -> this", mutates = "this")
        Builder putAll(CompoundTag tag);

        /**
         * Builds and returns a new {@link CompoundTag} using the current state
         * of values within the builder.
         *
         * @return a new {@link CompoundTag} instance containing the values specified in this builder.
         */
        @Contract(value = " -> new", pure = true)
        CompoundTag build();
    }
}
