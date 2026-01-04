package net.thenextlvl.nbt.serialization;

import net.thenextlvl.nbt.tag.Tag;
import org.jetbrains.annotations.Contract;

import java.lang.reflect.Type;

/**
 * The NBT class provides methods to serialize and deserialize objects to and from NBT tags,
 * as well as to register custom serializers and deserializers for different types.
 */
public sealed interface NBT extends TagSerializationContext, TagDeserializationContext permits SimpleNBT {
    /**
     * Returns whether the NBT output is formatted with indentation and line breaks.
     *
     * @return true if pretty printing is enabled, false otherwise
     * @since 4.1.0
     */
    @Contract(pure = true)
    boolean isPrettyPrinting();

    /**
     * Returns the number of indents used for pretty printing.
     *
     * @return the number of indents used for pretty printing
     * @since 4.1.0
     */
    @Contract(pure = true)
    int getIndents();

    /**
     * Returns a string representation of the given Tag.
     *
     * @param tag the Tag to be converted to a string
     * @return a string representation of the Tag
     */
    @Contract(value = "_ -> new", pure = true)
    String toString(Tag tag);

    /**
     * Creates a new instance of the Builder class for constructing NBT objects.
     *
     * @return a new Builder instance for constructing NBT objects
     */
    @Contract(value = " -> new", pure = true)
    static Builder builder() {
        return new SimpleNBT.Builder();
    }

    /**
     * Builder class for constructing instances of NBT with custom serializers and deserializers.
     */
    sealed interface Builder permits SimpleNBT.Builder {
        /**
         * Registers a custom adapter for both serialization and deserialization of the specified type
         * and its subtypes.
         *
         * @param <T>     the type of the objects handled by the adapter
         * @param type    the class of the type for which the adapter is to be registered
         * @param adapter the instance of TagAdapter to handle both serialization and deserialization
         *                of the specified type and its subtypes
         * @return the current builder instance for chaining
         */
        @Contract(value = "_, _ -> this", mutates = "this")
        <T> Builder registerTypeHierarchyAdapter(Class<?> type, TagAdapter<T> adapter);

        /**
         * Registers a custom deserializer for the specified type or any of its subtypes.
         *
         * @param <T>          the type of the objects handled by the deserializer
         * @param type         the class of the type for which the deserializer is to be registered
         * @param deserializer the instance of TagDeserializer to handle deserializing the specified type
         * @return the current builder instance for chaining
         */
        @Contract(value = "_, _ -> this", mutates = "this")
        <T> Builder registerTypeHierarchyAdapter(Class<?> type, TagDeserializer<T> deserializer);

        /**
         * Registers a custom serializer for the specified type or any of its subtypes.
         *
         * @param <T>        the type of the objects to be serialized
         * @param type       the class of the type for which the serializer is to be registered
         * @param serializer the instance of TagSerializer to handle serializing the specified type
         * @return the current builder instance for chaining
         */
        @Contract(value = "_, _ -> this", mutates = "this")
        <T> Builder registerTypeHierarchyAdapter(Class<?> type, TagSerializer<T> serializer);

        /**
         * Registers a custom adapter for both serialization and deserialization of the specified type.
         *
         * @param <T>     the type of the objects handled by the adapter
         * @param type    the class of the type for which the adapter is to be registered
         * @param adapter the instance of TagAdapter to handle both serialization and deserialization of the specified type
         * @return the current builder instance for chaining
         */
        @Contract(value = "_, _ -> this", mutates = "this")
        <T> Builder registerTypeAdapter(Type type, TagAdapter<T> adapter);

        /**
         * Registers a custom deserializer for the specified type.
         *
         * @param <T>          the type of the objects handled by the deserializer
         * @param type         the class of the type for which the deserializer is to be registered
         * @param deserializer the instance of TagDeserializer to handle deserializing the specified type
         * @return the current builder instance for chaining
         */
        @Contract(value = "_, _ -> this", mutates = "this")
        <T> Builder registerTypeAdapter(Type type, TagDeserializer<T> deserializer);

        /**
         * Registers a custom serializer for the specified type.
         *
         * @param <T>        the type of the objects to be serialized
         * @param type       the class of the type for which the serializer is to be registered
         * @param serializer the instance of TagSerializer to handle serializing the specified type
         * @return the current builder instance for chaining
         */
        @Contract(value = "_, _ -> this", mutates = "this")
        <T> Builder registerTypeAdapter(Type type, TagSerializer<T> serializer);

        /**
         * Sets whether the NBT output should be formatted with indentation and line breaks.
         *
         * @param prettyPrinting whether to enable pretty printing
         * @return the current builder instance for chaining
         * @since 4.1.0
         */
        @Contract(value = "_ -> this", mutates = "this")
        Builder setPrettyPrinting(boolean prettyPrinting);

        /**
         * Sets the number of indents to use for pretty printing.
         *
         * @param indents the number of indents to use
         * @return the current builder instance for chaining
         * @since 4.1.0
         */
        @Contract(value = "_ -> this", mutates = "this")
        Builder setIndents(int indents);

        /**
         * Constructs and returns an instance of NBT using the configured serializers and deserializers.
         *
         * @return a new instance of NBT
         */
        @Contract(value = " -> new", pure = true)
        NBT build();
    }
}
