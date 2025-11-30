package net.thenextlvl.nbt.serialization;

import net.thenextlvl.nbt.tag.Tag;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NullMarked;

import java.lang.reflect.Type;

/**
 * The NBT class provides methods to serialize and deserialize objects to and from NBT tags,
 * as well as to register custom serializers and deserializers for different types.
 */
public sealed interface NBT extends TagSerializationContext, TagDeserializationContext permits SimpleNBT {
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
     * Deserializes a given {@link Tag} into the specified type.
     *
     * @param tag  the tag to be deserialized
     * @param type the class of the type to deserialize into
     * @param <T>  the type of the object to be returned
     * @return the deserialized object of the specified type
     * @deprecated use {@link #deserialize(Tag, Class)} instead
     */
    @Contract(pure = true)
    @Deprecated(forRemoval = true, since = "2.4.0")
    default <T> T fromTag(Tag tag, Class<T> type) {
        return deserialize(tag, type);
    }

    /**
     * Deserializes a given {@link Tag} into the specified type.
     *
     * @param tag  the tag to be deserialized
     * @param type the type to deserialize into
     * @param <T>  the type of the object to be returned
     * @return the deserialized object of the specified type
     * @deprecated use {@link #deserialize(Tag, Type)} instead
     */
    @Contract(pure = true)
    @Deprecated(forRemoval = true, since = "2.4.0")
    default <T> T fromTag(Tag tag, Type type) {
        return deserialize(tag, type);
    }

    /**
     * Serializes the given object into its corresponding Tag representation.
     *
     * @param object the object to be serialized
     * @return the Tag representation of the provided object
     * @deprecated use {@link #serialize(Object)} instead
     */
    @Contract(value = "_ -> new", pure = true)
    @Deprecated(forRemoval = true, since = "2.4.0")
    default Tag toTag(Object object) {
        return serialize(object);
    }

    /**
     * Serializes the given object to a Tag representation for the specified type.
     *
     * @param object the object to be serialized
     * @param type   the class type to be used for serialization
     * @return the serialized tag representation of the object
     * @deprecated use {@link #serialize(Object, Class)} instead
     */
    @Contract(value = "_, _ -> new", pure = true)
    @Deprecated(forRemoval = true, since = "2.4.0")
    default Tag toTag(Object object, Class<?> type) {
        return serialize(object, type);
    }

    /**
     * Serializes the given object to a Tag representation for the specified type.
     *
     * @param object the object to be serialized
     * @param type   the type to be used for serialization
     * @return the serialized tag representation of the object
     * @deprecated use {@link #serialize(Object, Type)} instead
     */
    @Contract(value = "_, _ -> new", pure = true)
    @Deprecated(forRemoval = true, since = "2.4.0")
    default Tag toTag(Object object, Type type) {
        return serialize(object, type);
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
         * Constructs and returns an instance of NBT using the configured serializers and deserializers.
         *
         * @return a new instance of NBT
         */
        @Contract(value = " -> new", pure = true)
        NBT build();
    }
}
