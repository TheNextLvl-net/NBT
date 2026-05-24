package net.thenextlvl.nbt.tag;

import net.thenextlvl.nbt.NBTOutputStream;
import org.jspecify.annotations.NullUnmarked;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;

final class CompoundTagImpl extends ValueTagImpl<Map<String, Tag>> implements CompoundTag {
    public CompoundTagImpl(final Map<String, Tag> value) {
        super(Collections.unmodifiableMap(new LinkedHashMap<>(value)));
    }

    public CompoundTagImpl() {
        super(Collections.emptyMap());
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
    public byte getTypeId() {
        return ID;
    }

    @Override
    public void forEach(final BiConsumer<? super String, ? super Tag> action) {
        value.forEach(action);
    }

    @Override
    public Set<Map.Entry<String, Tag>> entrySet() {
        return value.entrySet();
    }

    @Override
    public Set<String> keySet() {
        return value.keySet();
    }

    @Override
    public boolean isEmpty() {
        return value.isEmpty();
    }

    @Override
    public int size() {
        return value.size();
    }

    @Override
    public boolean containsKey(final String property) {
        return value.containsKey(property);
    }

    @Override
    @NullUnmarked
    @SuppressWarnings("unchecked")
    public <T extends Tag> T get(final String property) {
        return (T) value.get(property);
    }

    @Override
    public <E extends Tag> ListTag<E> getAsList(final String tag) {
        return get(tag).getAsList();
    }

    @Override
    public CompoundTag getAsCompound(final String tag) {
        return get(tag).getAsCompound();
    }

    @Override
    @NullUnmarked
    @SuppressWarnings("unchecked")
    public <T extends Tag> T getOrDefault(final String tag, final T defaultValue) {
        return (T) value.getOrDefault(tag, defaultValue);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Tag> Optional<T> optional(final String tag) {
        return Optional.ofNullable(get(tag)).map(value -> (T) value);
    }

    @Override
    public CompoundTag.Builder toBuilder() {
        return new Builder().putAll(this);
    }

    @Override
    public void write(final NBTOutputStream outputStream) throws IOException {
        for (final var entry : entrySet()) outputStream.writeTag(entry.getKey(), entry.getValue());
        EscapeTagImpl.INSTANCE.write(outputStream);
    }

    public static CompoundTag.Builder builder() {
        return new Builder();
    }

    @Override
    public String toString() {
        if (isEmpty()) return "{}";
        final var builder = new StringBuilder("{");
        final var iterator = entrySet().iterator();
        while (iterator.hasNext()) {
            final var entry = iterator.next();
            builder.append(entry.getKey()).append(':').append(entry.getValue());
            if (iterator.hasNext()) builder.append(",");
        }
        return builder.append('}').toString();
    }

    public static final class Builder implements CompoundTag.Builder {
        private final Map<String, Tag> values = new LinkedHashMap<>();

        @Override
        public Builder put(final String name, final boolean value) {
            return put(name, ByteTag.of(value));
        }

        @Override
        public Builder put(final String name, final byte... array) {
            return put(name, new ByteArrayTagImpl(array));
        }

        @Override
        public Builder put(final String name, final int... array) {
            return put(name, new IntArrayTagImpl(array));
        }

        @Override
        public Builder put(final String name, final long... array) {
            return put(name, new LongArrayTagImpl(array));
        }

        @Override
        public Builder put(final String name, final Number number) {
            return put(name, NumberTag.of(number));
        }

        @Override
        public Builder put(final String name, final String value) {
            return put(name, new StringTagImpl(value));
        }

        @Override
        public Builder put(final String name, final Tag tag) {
            values.put(name, tag);
            return this;
        }

        @Override
        public Builder putAll(final CompoundTag tag) {
            values.putAll(tag.getValue());
            return this;
        }

        @Override
        public boolean isEmpty() {
            return values.isEmpty();
        }

        @Override
        public int size() {
            return values.size();
        }

        @Override
        public CompoundTag build() {
            return new CompoundTagImpl(values);
        }
    }
}
