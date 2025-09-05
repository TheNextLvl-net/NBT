package net.thenextlvl.nbt.serialization;

import net.thenextlvl.nbt.serialization.adapter.AddressAdapter;
import net.thenextlvl.nbt.serialization.adapter.BooleanAdapter;
import net.thenextlvl.nbt.serialization.adapter.ByteAdapter;
import net.thenextlvl.nbt.serialization.adapter.DoubleAdapter;
import net.thenextlvl.nbt.serialization.adapter.DurationAdapter;
import net.thenextlvl.nbt.serialization.adapter.FileAdapter;
import net.thenextlvl.nbt.serialization.adapter.FloatAdapter;
import net.thenextlvl.nbt.serialization.adapter.IntegerAdapter;
import net.thenextlvl.nbt.serialization.adapter.LongAdapter;
import net.thenextlvl.nbt.serialization.adapter.PathAdapter;
import net.thenextlvl.nbt.serialization.adapter.ShortAdapter;
import net.thenextlvl.nbt.serialization.adapter.StringAdapter;
import net.thenextlvl.nbt.serialization.adapter.UUIDAdapter;
import net.thenextlvl.nbt.tag.Tag;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullMarked;

import java.io.File;
import java.lang.reflect.Type;
import java.net.InetSocketAddress;
import java.nio.file.Path;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@NullMarked
final class SimpleNBT implements NBT {
    private final SerializationRegistry registry;

    private SimpleNBT(SerializationRegistry registry) {
        this.registry = registry;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T deserialize(Tag tag, Class<T> type) throws ParserException {
        var deserializer = registry.hierarchyDeserializers.get(type);
        if (deserializer != null) return (T) deserializer.deserialize(tag, this);
        return registry.hierarchyDeserializers.entrySet().stream()
                .filter(entry -> type.isAssignableFrom(entry.getKey()))
                .findAny()
                .map(entry -> (T) entry.getValue().deserialize(tag, this))
                .orElseGet(() -> deserialize(tag, (Type) type));
    }


    @Override
    @SuppressWarnings("unchecked")
    public <T> T deserialize(Tag tag, Type type) throws ParserException {
        var deserializer = registry.deserializers.get(type);
        if (deserializer != null) return (T) deserializer.deserialize(tag, this);
        throw new ParserException("No tag deserializer registered for type: " + type);
    }

    @Override
    public Tag serialize(Object object) throws ParserException {
        return serialize(object, object.getClass());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Tag serialize(Object object, Class<?> type) throws ParserException {
        var serializer = (TagSerializer<@NonNull Object>) registry.hierarchySerializers.get(object.getClass());
        if (serializer != null) return serializer.serialize(object, this);
        return registry.hierarchySerializers.entrySet().stream()
                .filter(entry -> entry.getKey().isInstance(object))
                .findAny()
                .map(entry -> (TagSerializer<@NonNull Object>) entry.getValue())
                .map(value -> value.serialize(object, this))
                .orElseGet(() -> serialize(object, (Type) object.getClass()));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Tag serialize(Object object, Type type) throws ParserException {
        var serializer = (TagSerializer<@NonNull Object>) registry.serializers.get(type);
        if (serializer != null) return serializer.serialize(object, this);
        throw new ParserException("No tag serializer registered for type: " + type);
    }

    static final class Builder implements NBT.Builder {
        private final SerializationRegistry registry = new SerializationRegistry();

        @Override
        public <T> NBT.Builder registerTypeHierarchyAdapter(Class<?> type, TagAdapter<T> adapter) {
            registry.registerTypeHierarchyAdapter(type, adapter);
            return this;
        }

        @Override
        public <T> NBT.Builder registerTypeHierarchyAdapter(Class<?> type, TagDeserializer<T> deserializer) {
            registry.registerTypeHierarchyAdapter(type, deserializer);
            return this;
        }

        @Override
        public <T> NBT.Builder registerTypeHierarchyAdapter(Class<?> type, TagSerializer<T> serializer) {
            registry.registerTypeHierarchyAdapter(type, serializer);
            return this;
        }

        @Override
        public <T> NBT.Builder registerTypeAdapter(Type type, TagAdapter<T> adapter) {
            registry.registerTypeAdapter(type, adapter);
            return this;
        }

        @Override
        public <T> NBT.Builder registerTypeAdapter(Type type, TagDeserializer<T> deserializer) {
            registry.registerTypeAdapter(type, deserializer);
            return this;
        }

        @Override
        public <T> NBT.Builder registerTypeAdapter(Type type, TagSerializer<T> serializer) {
            registry.registerTypeAdapter(type, serializer);
            return this;
        }

        @Override
        public NBT build() {
            return new SimpleNBT(registry.immutableCopy());
        }
    }

    private record SerializationRegistry(
            Map<Class<?>, TagDeserializer<?>> hierarchyDeserializers,
            Map<Class<?>, TagSerializer<?>> hierarchySerializers,
            Map<Type, TagDeserializer<?>> deserializers,
            Map<Type, TagSerializer<?>> serializers
    ) {
        public SerializationRegistry() {
            this(new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>());
            registerTypeAdapter(Boolean.class, BooleanAdapter.INSTANCE);
            registerTypeAdapter(Byte.class, ByteAdapter.INSTANCE);
            registerTypeAdapter(Double.class, DoubleAdapter.INSTANCE);
            registerTypeAdapter(Duration.class, DurationAdapter.INSTANCE);
            registerTypeAdapter(File.class, FileAdapter.INSTANCE);
            registerTypeAdapter(Float.class, FloatAdapter.INSTANCE);
            registerTypeAdapter(InetSocketAddress.class, AddressAdapter.INSTANCE);
            registerTypeAdapter(Integer.class, IntegerAdapter.INSTANCE);
            registerTypeAdapter(Long.class, LongAdapter.INSTANCE);
            registerTypeAdapter(Path.class, PathAdapter.INSTANCE);
            registerTypeAdapter(Short.class, ShortAdapter.INSTANCE);
            registerTypeAdapter(String.class, StringAdapter.INSTANCE);
            registerTypeAdapter(UUID.class, UUIDAdapter.INSTANCE);
            registerTypeAdapter(boolean.class, BooleanAdapter.INSTANCE);
            registerTypeAdapter(byte.class, ByteAdapter.INSTANCE);
            registerTypeAdapter(double.class, DoubleAdapter.INSTANCE);
            registerTypeAdapter(float.class, FloatAdapter.INSTANCE);
            registerTypeAdapter(int.class, IntegerAdapter.INSTANCE);
            registerTypeAdapter(long.class, LongAdapter.INSTANCE);
            registerTypeAdapter(short.class, ShortAdapter.INSTANCE);
        }

        public void registerTypeHierarchyAdapter(Class<?> clazz, TagAdapter<?> adapter) {
            hierarchyDeserializers.put(clazz, adapter);
            hierarchySerializers.put(clazz, adapter);
        }

        public void registerTypeHierarchyAdapter(Class<?> clazz, TagDeserializer<?> deserializer) {
            hierarchyDeserializers.put(clazz, deserializer);
        }

        public void registerTypeHierarchyAdapter(Class<?> clazz, TagSerializer<?> serializer) {
            hierarchySerializers.put(clazz, serializer);
        }

        public void registerTypeAdapter(Type type, TagAdapter<?> adapter) {
            deserializers.put(type, adapter);
            serializers.put(type, adapter);
        }

        public void registerTypeAdapter(Type type, TagDeserializer<?> deserializer) {
            deserializers.put(type, deserializer);
        }

        public void registerTypeAdapter(Type type, TagSerializer<?> serializer) {
            serializers.put(type, serializer);
        }

        public SerializationRegistry immutableCopy() {
            return new SerializationRegistry(
                    Map.copyOf(hierarchyDeserializers),
                    Map.copyOf(hierarchySerializers),
                    Map.copyOf(deserializers),
                    Map.copyOf(serializers)
            );
        }
    }
}
