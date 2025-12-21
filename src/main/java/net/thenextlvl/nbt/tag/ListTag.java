package net.thenextlvl.nbt.tag;

import org.intellij.lang.annotations.Flow;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * This class represents a tag which holds a list of tags.
 * It extends {@link ValueTag} with a specified list type.
 *
 * @param <T> the type of tags contained in the list
 */
public sealed interface ListTag<T extends Tag> extends ValueTag<@Unmodifiable List<T>>, @Unmodifiable List<T> permits ListTagImpl {
    /**
     * Represents the unique identifier for this Tag.
     */
    byte ID = 9;

    /**
     * Retrieves the content type identifier associated with this ListTag.
     *
     * @return the byte value representing the content type ID of the tag
     */
    @Contract(pure = true)
    byte getContentTypeId();

    /**
     * Creates a new instance of {@link ListTag} with the specified content type ID and an array of content.
     *
     * @param <T>           the type of tags contained in the list
     * @param contentTypeId the byte ID representing the content type of the tag
     * @param content       the array of tag instances to be included in the {@link ListTag}
     * @return a new {@link ListTag} containing the specified content with the given content type ID
     * @throws IllegalArgumentException if the content type ID or content array is invalid
     * @since 3.0.0
     */
    @SafeVarargs
    @Contract(value = "_, _ -> new", pure = true)
    static <T extends Tag> ListTag<T> of(byte contentTypeId, T... content) throws IllegalArgumentException {
        return of(contentTypeId, List.of(content));
    }

    /**
     * Creates a new instance of {@link ListTag} with a specified content type ID
     * and a list of tag objects.
     *
     * @param <T>           the type of tags contained in the list
     * @param contentTypeId the byte ID representing the content type of the tag
     * @param content       the list of tag instances to be included in the {@link ListTag}
     * @return a new {@link ListTag} containing the specified list of tags with the given content type ID
     * @throws IllegalArgumentException if the content type ID or content list is invalid
     * @since 3.0.0
     */
    @Contract(value = "_, _ -> new", pure = true)
    static <T extends Tag> ListTag<T> of(byte contentTypeId, List<T> content) throws IllegalArgumentException {
        return ListTag.<T>builder().contentType(contentTypeId).addAll(content).build();
    }

    /**
     * Creates a new instance of {@link ListTag} with the specified array of tag objects.
     *
     * @param <T>     the type of tags contained in the list
     * @param content the array of tag instances to be included in the {@link ListTag}
     * @return a new {@link ListTag} containing the specified tag instances
     * @throws IllegalArgumentException if the provided content array is empty
     * @since 3.0.0
     */
    @SafeVarargs
    @Contract(value = "_ -> new", pure = true)
    static <T extends Tag> ListTag<T> of(T... content) throws IllegalArgumentException {
        return of(List.of(content));
    }

    /**
     * Creates a new instance of {@link ListTag} with the specified list of tag objects.
     *
     * @param <T>     the type of tags contained in the list
     * @param content the list of tag instances to be included in the {@link ListTag}
     * @return a new {@link ListTag} containing the specified list of tags
     * @throws IllegalArgumentException if the provided content list is empty
     * @since 3.0.0
     */
    @Contract(value = "_ -> new", pure = true)
    static <T extends Tag> ListTag<T> of(List<T> content) throws IllegalArgumentException {
        return ListTag.<T>builder().addAll(content).build();
    }

    /**
     * Creates an empty instance of {@link ListTag} with the specified content type ID.
     *
     * @param <T>           the type of tags contained in the list
     * @param contentTypeId the byte ID representing the content type of the tag
     * @return a new empty {@link ListTag} with the given content type ID
     * @since 4.0.0
     */
    @Contract(value = "_ -> new", pure = true)
    static <T extends Tag> ListTag<T> empty(byte contentTypeId) {
        return new ListTagImpl<>(contentTypeId);
    }

    /**
     * Creates a new instance of {@link ListTag.Builder} for constructing a {@link ListTag}.
     *
     * @param <T> the type of tags contained in the list
     * @return a new {@link ListTag.Builder} instance
     * @since 4.0.0
     */
    @Contract(value = " -> new", pure = true)
    static <T extends Tag> Builder<T> builder() {
        return new ListTagImpl.Builder<>();
    }

    @Override
    @Deprecated
    @Contract(value = "_ -> fail")
    default boolean add(T t) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("ListTag is immutable");
    }

    @Override
    @Deprecated
    @Contract(value = "_, _ -> fail")
    default void add(int i, T t) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("ListTag is immutable");
    }

    @Override
    @Deprecated
    @Contract(value = "_ -> fail")
    default boolean addAll(Collection<? extends T> collection) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("ListTag is immutable");
    }

    @Override
    @Deprecated
    @Contract(value = "_, _ -> fail")
    default boolean addAll(int i, Collection<? extends T> collection) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("ListTag is immutable");
    }

    @Override
    @Deprecated
    @Contract(value = "_ -> fail")
    default boolean remove(Object o) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("ListTag is immutable");
    }

    @Override
    @Deprecated
    @Contract(value = "_ -> fail")
    default boolean removeAll(Collection<?> collection) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("ListTag is immutable");
    }

    @Override
    @Deprecated
    @Contract(value = "_ -> fail")
    default boolean retainAll(Collection<?> collection) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("ListTag is immutable");
    }

    @Override
    @Deprecated
    @Contract(value = "_ -> fail")
    default void addFirst(T t) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("ListTag is immutable");
    }

    @Override
    @Deprecated
    @Contract(value = "_ -> fail")
    default void addLast(T t) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("ListTag is immutable");
    }

    @Override
    @Deprecated
    @Contract(value = "_ -> fail")
    default boolean removeIf(Predicate<? super T> filter) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("ListTag is immutable");
    }

    @Override
    @Deprecated
    @Contract(value = "_ -> fail")
    default T remove(int i) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("ListTag is immutable");
    }

    @Override
    @Deprecated
    @Contract(value = " -> fail")
    default T removeFirst() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("ListTag is immutable");
    }

    @Override
    @Deprecated
    @Contract(value = " -> fail")
    default T removeLast() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("ListTag is immutable");
    }

    @Override
    @Deprecated
    @Contract(value = "_ -> fail")
    default void replaceAll(UnaryOperator<T> operator) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("ListTag is immutable");
    }

    @Override
    @Deprecated
    @Contract(value = " -> fail")
    default void clear() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("ListTag is immutable");
    }

    @Override
    @Deprecated
    @Contract(value = "_, _ -> fail")
    default T set(int i, T t) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("ListTag is immutable");
    }

    /**
     * A static nested builder class for constructing instances of {@code ListTag}.
     * Provides methods to configure and build a {@code ListTag} using a fluent API.
     *
     * @param <T> the type of tags contained in the list
     * @since 4.0.0
     */
    sealed interface Builder<T extends Tag> permits ListTagImpl.Builder {
        /**
         * Sets the content type ID for the {@link ListTag} to be built.
         *
         * @param contentTypeId the byte ID representing the content type of the tag
         * @return the builder instance, allowing for method chaining
         * @throws IllegalStateException if the content type ID is already set and values have been added
         */
        @Contract(value = "_ -> this", mutates = "this")
        Builder<T> contentType(byte contentTypeId) throws IllegalStateException;

        /**
         * Adds a tag value to the builder.
         * The tag value is stored within the builder.
         *
         * @param tag the tag value to be inserted
         * @return the builder instance, allowing for method chaining
         * @throws IllegalArgumentException if the content type ID does not match the existing content type
         * @see List#add(Object)
         */
        @Contract(value = "_ -> this", mutates = "this")
        Builder<T> add(T tag) throws IllegalArgumentException;

        /**
         * Adds a tag value at the specified index in the builder.
         * The tag value is stored within the builder.
         *
         * @param tag   the tag value to be inserted
         * @param index the index at which the tag value should be inserted
         * @return the builder instance, allowing for method chaining
         * @throws IllegalArgumentException  if the content type ID does not match the existing content type
         * @throws IndexOutOfBoundsException if the index is out of range
         * @see List#add(int, Object)
         */
        @Contract(value = "_, _ -> this", mutates = "this")
        Builder<T> add(int index, T tag) throws IllegalArgumentException;

        /**
         * Adds a tag value to the beginning of the builder.
         * The tag value is stored within the builder.
         *
         * @param tag the tag value to be inserted
         * @return the builder instance, allowing for method chaining
         * @throws IllegalArgumentException if the content type ID does not match the existing content type
         * @see List#add(int, Object)
         */
        @Contract(value = "_ -> this", mutates = "this")
        Builder<T> addFirst(T tag) throws IllegalArgumentException;

        /**
         * Adds a tag value to the end of the builder.
         * The tag value is stored within the builder.
         *
         * @param tag the tag value to be inserted
         * @return the builder instance, allowing for method chaining
         * @throws IllegalArgumentException if the content type ID does not match the existing content type
         * @see List#add(Object)
         */
        @Contract(value = "_ -> this", mutates = "this")
        Builder<T> addLast(T tag) throws IllegalArgumentException;

        /**
         * Adds all the tag values from the given iterable to the builder.
         * The tag values are stored within the builder.
         *
         * @param iterable the iterable of tag values to be inserted
         * @return the builder instance, allowing for method chaining
         * @throws IllegalArgumentException if the content type ID does not match the existing content type
         * @see List#addAll(Collection)
         */
        @Contract(value = "_ -> this", mutates = "this")
        @Flow(sourceIsContainer = true, targetIsContainer = true)
        Builder<T> addAll(Iterable<? extends T> iterable) throws IllegalArgumentException;

        /**
         * Replaces each element of the builder with the result of applying the given operator to that element.
         *
         * @param operator the operator to apply to each element
         * @return the builder instance, allowing for method chaining
         * @throws IllegalArgumentException if the content type ID does not match the existing content type
         * @see List#replaceAll(UnaryOperator)
         */
        @Contract(value = "_ -> this", mutates = "this")
        Builder<T> replaceAll(UnaryOperator<T> operator) throws IllegalArgumentException;

        /**
         * Sets a tag value at the specified index in the builder.
         *
         * @param index the index at which the tag value should be set
         * @param tag   the tag value to be set
         * @return the builder instance, allowing for method chaining
         * @throws IllegalArgumentException  if the content type ID does not match the existing content type
         * @throws IndexOutOfBoundsException if the index is out of range
         * @see List#set(int, Object)
         */
        @Contract(value = "_, _ -> this", mutates = "this")
        Builder<T> set(int index, T tag) throws IllegalArgumentException;

        /**
         * Removes a tag value from the builder.
         *
         * @param tag the tag value to be removed
         * @return the builder instance, allowing for method chaining
         * @see List#remove(Object)
         */
        @Contract(value = "_ -> this", mutates = "this")
        Builder<T> remove(T tag);

        /**
         * Removes all tag values from the builder that match the given predicate.
         *
         * @param filter the predicate used to filter tag values for removal
         * @return the builder instance, allowing for method chaining
         * @see List#removeIf(Predicate)
         */
        @Contract(value = "_ -> this", mutates = "this")
        Builder<T> removeIf(Predicate<? super T> filter);

        /**
         * Removes all tag values from the builder that are contained in the given iterable.
         *
         * @param iterable the tags to be removed
         * @return the builder instance, allowing for method chaining
         * @throws IllegalArgumentException if the content type ID does not match the existing content type
         * @see List#removeAll(Collection)
         */
        @Contract(value = "_ -> this", mutates = "this")
        Builder<T> removeAll(Iterable<? extends T> iterable) throws IllegalArgumentException;

        /**
         * Clears all the tag values from the builder.
         *
         * @return the builder instance, allowing for method chaining
         */
        @Contract(value = " -> this", mutates = "this")
        Builder<T> clear();

        /**
         * Builds and returns a new {@link ListTag} instance based on the configured values.
         *
         * @return a new {@link ListTag} instance
         */
        @Contract(value = " -> new", pure = true)
        ListTag<T> build();
    }
}
