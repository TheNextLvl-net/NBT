package net.thenextlvl.nbt.tag;

import net.thenextlvl.nbt.NBTInputStream;
import net.thenextlvl.nbt.NBTOutputStream;
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

/**
 * This class represents a tag which holds a list of tags.
 * It extends {@link ValueTag} with a specified list type.
 *
 * @param <V> the type of tags contained in the list
 */
@NullMarked
public class ListTag<V extends Tag> extends ValueTag<List<V>> implements List<V> {
    /**
     * Represents the unique identifier for this Tag.
     */
    public static final int ID = 9;
    private final int contentTypeId;

    /**
     * Constructs a new ListTag object with the provided list of values and content type ID.
     * <p>
     * Validates that the type ID of the first element in the list matches the specified content type ID.
     *
     * @param value         the list of values to be encapsulated within the ListTag
     * @param contentTypeId the type ID that all elements in the list must share
     * @throws IllegalArgumentException if the list is not empty and the type ID of the first element
     *                                  does not match the specified content type ID
     */
    public ListTag(List<V> value, int contentTypeId) {
        super(value);
        this.contentTypeId = contentTypeId;
        if (value.isEmpty()) return;
        var first = value.getFirst();
        if (first.getTypeId() != contentTypeId) throw new IllegalArgumentException("ListTag content type mismatch");
    }

    /**
     * Constructs a new ListTag with the specified list of values.
     * <p>
     * Validates that the provided list is not empty and sets the content type ID
     * based on the type ID of the first element in the list.
     *
     * @param value the list of elements to be encapsulated in this ListTag
     * @throws IllegalArgumentException if the provided list is empty
     */
    public ListTag(List<V> value) {
        super(value);
        if (value.isEmpty()) throw new IllegalArgumentException("ListTag without type must have at least one element");
        this.contentTypeId = value.getFirst().getTypeId();
    }

    /**
     * Constructs a new ListTag with the specified content type ID.
     *
     * @param contentTypeId the ID representing the type of content that this ListTag holds
     */
    public ListTag(int contentTypeId) {
        this(new ArrayList<>(), contentTypeId);
    }

    /**
     * Retrieves the content type identifier associated with this ListTag.
     *
     * @return the integer value representing the content type ID of the tag
     */
    public int getContentTypeId() {
        return contentTypeId;
    }

    @Override
    public final boolean isList() {
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ListTag<V> getAsList() {
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
        ListTag<?> listTag = (ListTag<?>) o;
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

    /**
     * Reads a list of tags from the provided NBTInputStream.
     *
     * @param inputStream the NBTInputStream to read the tags from
     * @param <V>         the type of tags that extends Tag
     * @return a ListTag containing the tags read from the inputStream
     * @throws IOException if an I/O error occurs while reading from the inputStream
     */
    @SuppressWarnings("unchecked")
    public static <V extends Tag> ListTag<V> read(NBTInputStream inputStream) throws IOException {
        var type = inputStream.readByte();
        var length = inputStream.readInt();
        var list = new ArrayList<V>();
        for (var i = 0; i < length; i++) list.add((V) inputStream.readTag(type));
        return new ListTag<>(list, type);
    }
}
