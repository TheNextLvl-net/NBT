package net.thenextlvl.nbt.serialization.adapter;

import net.thenextlvl.nbt.serialization.ParserException;
import net.thenextlvl.nbt.serialization.TagAdapter;
import net.thenextlvl.nbt.serialization.TagDeserializationContext;
import net.thenextlvl.nbt.serialization.TagSerializationContext;
import net.thenextlvl.nbt.tag.ShortTag;
import net.thenextlvl.nbt.tag.Tag;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class ShortAdapter implements TagAdapter<Short> {
    public static final ShortAdapter INSTANCE = new ShortAdapter();

    private ShortAdapter() {
    }

    @Override
    public Short deserialize(Tag tag, TagDeserializationContext context) throws ParserException {
        return tag.getAsShort();
    }

    @Override
    public Tag serialize(Short object, TagSerializationContext context) throws ParserException {
        return ShortTag.of(object);
    }
}
