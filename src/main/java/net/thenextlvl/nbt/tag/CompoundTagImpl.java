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
    public CompoundTagImpl(Map<String, Tag> value) {
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
    public void forEach(BiConsumer<? super String, ? super Tag> action) {
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
    public boolean containsKey(String property) {
        return value.containsKey(property);
    }

    @Override
    @NullUnmarked
    @SuppressWarnings("unchecked")
    public <T extends Tag> T get(String property) {
        return (T) value.get(property);
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
    @NullUnmarked
    @SuppressWarnings("unchecked")
    public <T extends Tag> T getOrDefault(String tag, T defaultValue) {
        return (T) value.getOrDefault(tag, defaultValue);
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

    public static CompoundTag.Builder builder() {
        return new Builder();
    }

    @Override
    public String toString() {
        if (isEmpty()) return "{}";
        var builder = new StringBuilder("{");
        var iterator = entrySet().iterator();
        while (iterator.hasNext()) {
            var entry = iterator.next();
            builder.append(entry.getKey()).append(':').append(entry.getValue());
            if (iterator.hasNext()) builder.append(",");
        }
        return builder.append('}').toString();
    }

    public static final class Builder implements CompoundTag.Builder {
        private final Map<String, Tag> values = new LinkedHashMap<>();

        @Override
        public Builder put(String name, boolean value) {
            return put(name, ByteTag.of(value));
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
            return put(name, NumberTag.of(number));
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
