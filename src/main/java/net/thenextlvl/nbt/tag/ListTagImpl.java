package net.thenextlvl.nbt.tag;

import net.thenextlvl.nbt.NBTOutputStream;
import org.jetbrains.annotations.Unmodifiable;
import org.jspecify.annotations.Nullable;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

final class ListTagImpl<T extends Tag> extends ValueTagImpl<@Unmodifiable List<T>> implements ListTag<T> {
    private final byte contentTypeId;

    private ListTagImpl(List<T> value, byte contentTypeId) {
        super(Collections.unmodifiableList(new LinkedList<>(value)));
        this.contentTypeId = contentTypeId;
    }

    public ListTagImpl(byte contentTypeId) {
        super(Collections.emptyList());
        this.contentTypeId = contentTypeId;
    }

    @Override
    public byte getContentTypeId() {
        return contentTypeId;
    }

    @Override
    public boolean isList() {
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ListTagImpl<T> getAsList() {
        return this;
    }

    @Override
    public byte getTypeId() {
        return ID;
    }

    @Override
    public int size() {
        return value.size();
    }

    @Override
    public T get(int index) {
        return value.get(index);
    }

    @Override
    public T set(int i, T t) {
        throw new UnsupportedOperationException("ListTag is immutable");
    }

    @Override
    public void add(int i, T t) {
        throw new UnsupportedOperationException("ListTag is immutable");
    }

    @Override
    public T remove(int i) {
        throw new UnsupportedOperationException("ListTag is immutable");
    }

    @Override
    public int indexOf(Object o) {
        return value.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return value.lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return value.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int i) {
        return value.listIterator(i);
    }

    @Override
    public List<T> subList(int i, int i1) {
        return value.subList(i, i1);
    }

    @Override
    public boolean isEmpty() {
        return value.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return value.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return value.iterator();
    }

    @Override
    public Object[] toArray() {
        return value.toArray();
    }

    @Override
    public <V> V[] toArray(V[] vs) {
        return value.toArray(vs);
    }

    @Override
    public boolean add(T t) {
        throw new UnsupportedOperationException("ListTag is immutable");
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("ListTag is immutable");
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return new HashSet<>(value).containsAll(collection);
    }

    @Override
    public boolean addAll(Collection<? extends T> collection) {
        throw new UnsupportedOperationException("ListTag is immutable");
    }

    @Override
    public boolean addAll(int i, Collection<? extends T> collection) {
        throw new UnsupportedOperationException("ListTag is immutable");
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        throw new UnsupportedOperationException("ListTag is immutable");
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException("ListTag is immutable");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("ListTag is immutable");
    }

    @Override
    public String toString() {
        return "ListTag{" +
                "contentTypeId=" + contentTypeId +
                ", value=" + super.value +
                '}';
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ListTagImpl<?> listTag = (ListTagImpl<?>) o;
        return contentTypeId == listTag.contentTypeId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), contentTypeId);
    }

    @Override
    public void write(NBTOutputStream outputStream) throws IOException {
        outputStream.writeByte(contentTypeId);
        outputStream.writeInt(value.size());
        for (var tag : value) tag.write(outputStream);
    }

    public static final class Builder<T extends Tag> implements ListTag.Builder<T> {
        private final List<T> value = new LinkedList<>();
        private @Nullable Byte contentTypeId;

        @Override
        public ListTag.Builder<T> contentType(byte contentTypeId) throws IllegalStateException {
            if (this.contentTypeId != null && !value.isEmpty())
                throw new IllegalStateException("Content type cannot be changed after adding values");
            this.contentTypeId = contentTypeId;
            return this;
        }

        @Override
        public ListTag.Builder<T> add(T tag) throws IllegalArgumentException {
            return addLast(tag);
        }

        @Override
        public ListTag.Builder<T> add(int index, T tag) throws IllegalArgumentException {
            if (contentTypeId == null) contentTypeId = tag.getTypeId();
            else if (contentTypeId != tag.getTypeId())
                throw new IllegalArgumentException("ListTag content type mismatch");
            value.add(index, tag);
            return this;
        }

        @Override
        public ListTag.Builder<T> addFirst(T tag) throws IllegalArgumentException {
            return add(0, tag);
        }

        @Override
        public ListTag.Builder<T> addLast(T tag) throws IllegalArgumentException {
            return add(value.size(), tag);
        }

        @Override
        public ListTag.Builder<T> addAll(Iterable<? extends T> iterable) throws IllegalArgumentException {
            iterable.forEach(this::add);
            return this;
        }

        @Override
        public ListTag.Builder<T> replaceAll(UnaryOperator<T> operator) throws IllegalArgumentException {
            var li = value.listIterator();
            while (li.hasNext()) {
                set(li.nextIndex(), operator.apply(li.next()));
            }
            return this;
        }

        @Override
        public ListTag.Builder<T> set(int index, T tag) throws IllegalArgumentException {
            if (contentTypeId == null) contentTypeId = tag.getTypeId();
            else if (contentTypeId != tag.getTypeId())
                throw new IllegalArgumentException("ListTag content type mismatch");
            value.set(index, tag);
            return this;
        }

        @Override
        public ListTag.Builder<T> remove(T tag) {
            value.remove(tag);
            return this;
        }

        @Override
        public ListTag.Builder<T> removeIf(Predicate<? super T> filter) {
            value.removeIf(filter);
            return this;
        }

        @Override
        public ListTag.Builder<T> removeAll(Iterable<? extends T> iterable) throws IllegalArgumentException {
            iterable.forEach(value::remove);
            return this;
        }

        @Override
        public ListTag.Builder<T> clear() {
            value.clear();
            return this;
        }

        @Override
        public ListTag<T> build() {
            if (contentTypeId == null) throw new IllegalStateException("Empty ListTag must have a content type");
            return new ListTagImpl<>(value, contentTypeId);
        }
    }
}
