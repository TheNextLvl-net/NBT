package net.thenextlvl.nbt.serialization;

import net.thenextlvl.nbt.tag.Tag;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NullMarked;

/**
 * Interface for deserializing a Tag into a specific type.
 *
 * @param <T> the type into which the Tag should be deserialized
 */
@NullMarked
public interface TagDeserializer<T> {
    /**
     * Deserializes the given Tag into the specified type.
     *
     * @param tag     the tag to be deserialized
     * @param context the context used for deserialization
     * @return the deserialized object of type T
     * @throws ParserException if an error occurs during deserialization
     */
    @Contract(pure = true)
    T deserialize(Tag tag, TagDeserializationContext context) throws ParserException;
}
