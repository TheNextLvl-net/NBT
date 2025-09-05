package net.thenextlvl.nbt.serialization.adapter;

import net.thenextlvl.nbt.serialization.ParserException;
import net.thenextlvl.nbt.serialization.TagAdapter;
import net.thenextlvl.nbt.serialization.TagDeserializationContext;
import net.thenextlvl.nbt.serialization.TagSerializationContext;
import net.thenextlvl.nbt.tag.StringTag;
import net.thenextlvl.nbt.tag.Tag;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class EnumAdapter<T extends Enum<T>> implements TagAdapter<T> {
    private final Class<T> enumClass;

    /**
     * Constructs an {@code EnumAdapter} for the provided enum class type.
     *
     * @param enumClass the {@code Class} representing the specific enum type handled by this adapter
     */
    public EnumAdapter(Class<T> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public @NonNull T deserialize(Tag tag, TagDeserializationContext context) throws ParserException {
        return Enum.valueOf(this.enumClass, tag.getAsString());
    }

    @Override
    public Tag serialize(T object, TagSerializationContext context) throws ParserException {
        return new StringTag(object.name());
    }
}
