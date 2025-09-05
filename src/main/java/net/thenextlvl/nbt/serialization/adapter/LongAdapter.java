package net.thenextlvl.nbt.serialization.adapter;

import net.thenextlvl.nbt.serialization.ParserException;
import net.thenextlvl.nbt.serialization.TagAdapter;
import net.thenextlvl.nbt.serialization.TagDeserializationContext;
import net.thenextlvl.nbt.serialization.TagSerializationContext;
import net.thenextlvl.nbt.tag.LongTag;
import net.thenextlvl.nbt.tag.Tag;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class LongAdapter implements TagAdapter<Long> {
    public static final LongAdapter INSTANCE = new LongAdapter();

    private LongAdapter() {
    }

    @Override
    public Long deserialize(Tag tag, TagDeserializationContext context) throws ParserException {
        return tag.getAsLong();
    }

    @Override
    public Tag serialize(Long object, TagSerializationContext context) throws ParserException {
        return LongTag.of(object);
    }
}
