package net.thenextlvl.nbt.tag;

import net.thenextlvl.nbt.tag.impl.ListTagImpl;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a tag which holds a list of tags.
 * It extends {@link ValueTag} with a specified list type.
 *
 * @param <V> the type of tags contained in the list
 */
@NullMarked
public sealed interface ListTag<V extends Tag> extends ValueTag<List<V>>, List<V> permits ListTagImpl {
    /**
     * Represents the unique identifier for this Tag.
     */
    int ID = 9;

    /**
     * Retrieves the content type identifier associated with this ListTag.
     *
     * @return the integer value representing the content type ID of the tag
     */
    @Contract(pure = true)
    int getContentTypeId();

    /**
     * Creates a new instance of {@link ListTag} with the specified content type ID and an array of content.
     *
     * @param <V>           the type of tags contained in the list
     * @param contentTypeId the integer ID representing the content type of the tag
     * @param content       the array of tag instances to be included in the {@link ListTag}
     * @return a new {@link ListTag} containing the specified content with the given content type ID
     * @throws IllegalArgumentException if the content type ID or content array is invalid
     * @since 3.0.0
     */
    @SafeVarargs
    @Contract(value = "_, _ -> new", pure = true)
    static <V extends Tag> ListTag<V> of(int contentTypeId, V... content) throws IllegalArgumentException {
        return of(contentTypeId, new ArrayList<>(List.of(content)));
    }

    /**
     * Creates a new instance of {@link ListTag} with a specified content type ID
     * and a list of tag objects.
     *
     * @param <V>           the type of tags contained in the list
     * @param contentTypeId the integer ID representing the content type of the tag
     * @param content       the list of tag instances to be included in the {@link ListTag}
     * @return a new {@link ListTag} containing the specified list of tags with the given content type ID
     * @throws IllegalArgumentException if the content type ID or content list is invalid
     * @since 3.0.0
     */
    @Contract(value = "_, _ -> new", pure = true)
    static <V extends Tag> ListTag<V> of(int contentTypeId, List<V> content) throws IllegalArgumentException {
        return new ListTagImpl<>(content, contentTypeId);
    }

    /**
     * Creates a new instance of {@link ListTag} with the specified array of tag objects.
     *
     * @param <V>     the type of tags contained in the list
     * @param content the array of tag instances to be included in the {@link ListTag}
     * @return a new {@link ListTag} containing the specified tag instances
     * @throws IllegalArgumentException if the provided content array is empty
     * @since 3.0.0
     */
    @SafeVarargs
    @Contract(value = "_ -> new", pure = true)
    static <V extends Tag> ListTag<V> of(V... content) throws IllegalArgumentException {
        return of(new ArrayList<>(List.of(content)));
    }

    /**
     * Creates a new instance of {@link ListTag} with the specified list of tag objects.
     *
     * @param <V>     the type of tags contained in the list
     * @param content the list of tag instances to be included in the {@link ListTag}
     * @return a new {@link ListTag} containing the specified list of tags
     * @throws IllegalArgumentException if the provided content list is empty
     * @since 3.0.0
     */
    @Contract(value = "_ -> new", pure = true)
    static <V extends Tag> ListTag<V> of(List<V> content) throws IllegalArgumentException {
        return new ListTagImpl<>(content);
    }
}
