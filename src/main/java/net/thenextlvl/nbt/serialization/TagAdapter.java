package net.thenextlvl.nbt.serialization;

/**
 * Combines functionality of {@link TagDeserializer} and {@link TagSerializer}
 * for handling both serialization and deserialization of the specified type.
 *
 * @param <T> the type of objects handled by this adapter
 */
public interface TagAdapter<T> extends TagDeserializer<T>, TagSerializer<T> {
}
