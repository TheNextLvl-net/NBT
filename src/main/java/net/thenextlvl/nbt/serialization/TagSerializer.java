package net.thenextlvl.nbt.serialization;

import net.thenextlvl.nbt.tag.Tag;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NullMarked;

/**
 * Interface for serializing objects of type {@code T} into {@link Tag} representations.
 *
 * @param <T> the type of the objects to be serialized
 */
public interface TagSerializer<T> {
    /**
     * Serializes a given object into its corresponding Tag representation.
     *
     * @param object  the object to be serialized
     * @param context the context used for serialization
     * @return the Tag representation of the provided vector
     * @throws ParserException if an error occurs during serialization
     */
    @Contract(value = "_, _ -> new", pure = true)
    Tag serialize(T object, TagSerializationContext context) throws ParserException;
}
