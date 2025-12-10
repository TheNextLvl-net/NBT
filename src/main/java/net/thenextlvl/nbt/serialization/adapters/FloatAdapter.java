package net.thenextlvl.nbt.serialization.adapters;

import net.thenextlvl.nbt.serialization.ParserException;
import net.thenextlvl.nbt.serialization.TagAdapter;
import net.thenextlvl.nbt.serialization.TagDeserializationContext;
import net.thenextlvl.nbt.serialization.TagSerializationContext;
import net.thenextlvl.nbt.tag.FloatTag;
import net.thenextlvl.nbt.tag.Tag;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public final class FloatAdapter implements TagAdapter<Float> {
    public static final FloatAdapter INSTANCE = new FloatAdapter();

    private FloatAdapter() {
    }

    @Override
    public Float deserialize(Tag tag, TagDeserializationContext context) throws ParserException {
        return tag.getAsFloat();
    }

    @Override
    public Tag serialize(Float object, TagSerializationContext context) throws ParserException {
        return FloatTag.of(object);
    }
}
