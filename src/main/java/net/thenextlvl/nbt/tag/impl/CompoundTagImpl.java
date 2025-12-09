package net.thenextlvl.nbt.tag.impl;

import net.thenextlvl.nbt.NBTInputStream;
import net.thenextlvl.nbt.NBTOutputStream;
import net.thenextlvl.nbt.tag.ByteTag;
import net.thenextlvl.nbt.tag.CompoundTag;
import net.thenextlvl.nbt.tag.DoubleTag;
import net.thenextlvl.nbt.tag.FloatTag;
import net.thenextlvl.nbt.tag.IntTag;
import net.thenextlvl.nbt.tag.ListTag;
import net.thenextlvl.nbt.tag.LongTag;
import net.thenextlvl.nbt.tag.ShortTag;
import net.thenextlvl.nbt.tag.Tag;
import org.jspecify.annotations.NullUnmarked;
import org.jspecify.annotations.Nullable;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;

public final class CompoundTagImpl extends ValueTagImpl<Map<String, Tag>> implements CompoundTag {
    public CompoundTagImpl(Map<String, Tag> value) {
        super(value);
    }

    @Override
    public boolean isCompound() {
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

    @Override
    public void add(String name, Tag tag) {
        getValue().put(name, tag);
    }

    @Override
    public @Nullable Tag remove(String name) {
        return getValue().remove(name);
    }

    @Override
    public void add(String name, String value) {
        add(name, new StringTagImpl(value));
    }

    @Override
    public void add(String name, byte[] value) {
        add(name, new ByteArrayTagImpl(value));
    }

    @Override
    public void add(String name, int[] value) {
        add(name, new IntArrayTagImpl(value));
    }

    @Override
    public void add(String name, long[] value) {
        add(name, new LongArrayTagImpl(value));
    }

    @Override
    public void add(String name, Number number) {
        switch (number) {
            case Integer value -> add(name, IntTag.of(value));
            case Float value -> add(name, FloatTag.of(value));
            case Short value -> add(name, ShortTag.of(value));
            case Long value -> add(name, LongTag.of(value));
            case Byte value -> add(name, ByteTag.of(value));
            default -> add(name, DoubleTag.of(number.doubleValue()));
        }
    }

    @Override
    public void add(String name, Boolean value) {
        add(name, ByteTag.of(value ? (byte) 1 : 0));
    }

    @Override
    public void addAll(CompoundTag tag) {
        tag.forEach(this::add);
    }

    @Override
    public void forEach(BiConsumer<? super String, ? super Tag> action) {
        getValue().forEach(action);
    }

    @Override
    public Set<Map.Entry<String, Tag>> entrySet() {
        return getValue().entrySet();
    }

    @Override
    public Set<String> keySet() {
        return getValue().keySet();
    }

    @Override
    public boolean isEmpty() {
        return getValue().isEmpty();
    }

    @Override
    public int size() {
        return getValue().size();
    }

    @Override
    public boolean containsKey(String property) {
        return getValue().containsKey(property);
    }

    @Override
    @NullUnmarked
    @SuppressWarnings("unchecked")
    public <T extends Tag> T get(String property) {
        return (T) getValue().get(property);
    }

    @Override
    public <E extends Tag> ListTag<E> getAsList(String tag) {
        return get(tag).getAsList();
    }

    @Override
    public CompoundTag getAsCompound(String tag) {
        return get(tag).getAsCompound();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Tag> T getOrAdd(String tag, T defaultValue) {
        var value = get(tag);
        if (value != null) return (T) value;
        add(tag, defaultValue);
        return defaultValue;
    }

    @Override
    @NullUnmarked
    @SuppressWarnings("unchecked")
    public <T extends Tag> T getOrDefault(String tag, T defaultValue) {
        return (T) getValue().getOrDefault(tag, defaultValue);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Tag> Optional<T> optional(String tag) {
        return Optional.ofNullable(get(tag)).map(value -> (T) value);
    }

    @Override
    public CompoundTag.Builder toBuilder() {
        return new Builder().putAll(this);
    }

    @Override
    public void write(NBTOutputStream outputStream) throws IOException {
        for (var entry : entrySet()) outputStream.writeTag(entry.getKey(), entry.getValue());
        EscapeTagImpl.INSTANCE.write(outputStream);
    }

    public static CompoundTag read(NBTInputStream inputStream) throws IOException {
        var value = new HashMap<String, Tag>();
        while (true) {
            var entry = inputStream.readNamedTag();
            if (entry.getValue().isEmpty()) break;
            value.put(entry.getValue().get(), entry.getKey());
        }
        return new CompoundTagImpl(value);
    }

    public static CompoundTag.Builder builder() {
        return new Builder();
    }

    public static final class Builder implements CompoundTag.Builder {
        private final Map<String, Tag> values = new HashMap<>();

        @Override
        public Builder put(String name, Boolean value) {
            return put(name, new ByteTagImpl(value ? (byte) 1 : 0));
        }

        @Override
        public Builder put(String name, byte... array) {
            return put(name, new ByteArrayTagImpl(array));
        }

        @Override
        public Builder put(String name, int... array) {
            return put(name, new IntArrayTagImpl(array));
        }

        @Override
        public Builder put(String name, long... array) {
            return put(name, new LongArrayTagImpl(array));
        }

        @Override
        public Builder put(String name, Number number) {
            return switch (number) {
                case Integer value -> put(name, new IntTagImpl(value));
                case Float value -> put(name, new FloatTagImpl(value));
                case Short value -> put(name, new ShortTagImpl(value));
                case Long value -> put(name, LongTag.of(value));
                case Byte value -> put(name, ByteTag.of(value));
                default -> put(name, new DoubleTagImpl(number.doubleValue()));
            };
        }

        @Override
        public Builder put(String name, String value) {
            return put(name, new StringTagImpl(value));
        }

        @Override
        public Builder put(String name, Tag tag) {
            values.put(name, tag);
            return this;
        }

        @Override
        public Builder putAll(CompoundTag tag) {
            values.putAll(tag.getValue());
            return this;
        }

        @Override
        public CompoundTag build() {
            return new CompoundTagImpl(new HashMap<>(values));
        }
    }
}
