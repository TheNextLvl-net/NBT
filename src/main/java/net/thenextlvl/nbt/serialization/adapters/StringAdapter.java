package net.thenextlvl.nbt.serialization.adapters;

import net.thenextlvl.nbt.serialization.ParserException;
import net.thenextlvl.nbt.serialization.TagAdapter;
import net.thenextlvl.nbt.serialization.TagDeserializationContext;
import net.thenextlvl.nbt.serialization.TagSerializationContext;
import net.thenextlvl.nbt.tag.StringTag;
import net.thenextlvl.nbt.tag.Tag;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public final class StringAdapter implements TagAdapter<String> {
    public static final StringAdapter INSTANCE = new StringAdapter();

    private StringAdapter() {
    }

    @Override
    public String deserialize(Tag tag, TagDeserializationContext context) throws ParserException {
        return tag.getAsString();
    }

    @Override
    public Tag serialize(String object, TagSerializationContext context) throws ParserException {
        return StringTag.of(object);
    }
}
