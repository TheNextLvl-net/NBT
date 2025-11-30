package net.thenextlvl.nbt.tag.impl;

import net.thenextlvl.nbt.NBTInputStream;
import net.thenextlvl.nbt.NBTOutputStream;
import net.thenextlvl.nbt.tag.ListTag;
import net.thenextlvl.nbt.tag.Tag;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

public final class ListTagImpl<V extends Tag> extends ValueTagImpl<List<V>> implements ListTag<V> {
    private final int contentTypeId;

    public ListTagImpl(List<V> value, int contentTypeId) {
        super(value);
        this.contentTypeId = contentTypeId;
        if (value.isEmpty()) return;
        var first = value.getFirst();
        if (first.getTypeId() != contentTypeId) throw new IllegalArgumentException("ListTag content type mismatch");
    }

    public ListTagImpl(List<V> value) {
        super(value);
        if (value.isEmpty()) throw new IllegalArgumentException("ListTag without type must have at least one element");
        this.contentTypeId = value.getFirst().getTypeId();
    }

    ListTagImpl(int contentTypeId) {
        this(new ArrayList<>(), contentTypeId);
    }

    public int getContentTypeId() {
        return contentTypeId;
    }

    @Override
    public boolean isList() {
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ListTagImpl<V> getAsList() {
        return this;
    }

    @Override
    public int getTypeId() {
        return ID;
    }

    @Override
    public int size() {
        return getValue().size();
    }

    @Override
    public boolean isEmpty() {
        return getValue().isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return getValue().contains(o);
    }

    @Override
    public Iterator<V> iterator() {
        return getValue().iterator();
    }

    @Override
    public Object[] toArray() {
        return getValue().toArray();
    }

    @Override
    public <T> T[] toArray(T[] ts) {
        return getValue().toArray(ts);
    }

    @Override
    public boolean add(V v) {
        if (v.getTypeId() == contentTypeId) return getValue().add(v);
        throw new IllegalArgumentException("ListTag content type mismatch");
    }

    @Override
    public boolean remove(Object o) {
        return getValue().remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return new HashSet<>(getValue()).containsAll(collection);
    }

    @Override
    public boolean addAll(Collection<? extends V> collection) {
        return getValue().addAll(collection);
    }

    @Override
    public boolean addAll(int i, Collection<? extends V> collection) {
        return getValue().addAll(i, collection);
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return getValue().removeAll(collection);
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return getValue().retainAll(collection);
    }

    @Override
    public void clear() {
        getValue().clear();
    }

    @Override
    public V get(int i) {
        return getValue().get(i);
    }

    @Override
    public V set(int i, V v) {
        return getValue().set(i, v);
    }

    @Override
    public void add(int i, V v) {
        if (v.getTypeId() == contentTypeId) getValue().add(i, v);
        else throw new IllegalArgumentException("ListTag content type mismatch");
    }

    @Override
    public V remove(int i) {
        return getValue().remove(i);
    }

    @Override
    public int indexOf(Object o) {
        return getValue().indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return getValue().lastIndexOf(o);
    }

    @Override
    public ListIterator<V> listIterator() {
        return getValue().listIterator();
    }

    @Override
    public ListIterator<V> listIterator(int i) {
        return getValue().listIterator(i);
    }

    @Override
    public List<V> subList(int i, int i1) {
        return getValue().subList(i, i1);
    }

    @Override
    public String toString() {
        return "ListTag{" +
               "contentTypeId=" + contentTypeId +
               ", value=" + super.getValue() +
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
        outputStream.writeByte(getContentTypeId());
        outputStream.writeInt(getValue().size());
        for (var tag : getValue()) tag.write(outputStream);
    }

    @SuppressWarnings("unchecked")
    public static <V extends Tag> ListTagImpl<V> read(NBTInputStream inputStream) throws IOException {
        var type = inputStream.readByte();
        var length = inputStream.readInt();
        var list = new ArrayList<V>();
        for (var i = 0; i < length; i++) list.add((V) inputStream.readTag(type));
        return new ListTagImpl<>(list, type);
    }
}
