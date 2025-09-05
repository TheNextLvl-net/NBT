package net.thenextlvl.nbt.serialization.adapter;

import net.thenextlvl.nbt.serialization.ParserException;
import net.thenextlvl.nbt.serialization.TagAdapter;
import net.thenextlvl.nbt.serialization.TagDeserializationContext;
import net.thenextlvl.nbt.serialization.TagSerializationContext;
import net.thenextlvl.nbt.tag.IntTag;
import net.thenextlvl.nbt.tag.Tag;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class IntegerAdapter implements TagAdapter<Integer> {
    public static final IntegerAdapter INSTANCE = new IntegerAdapter();

    private IntegerAdapter() {
    }
    
    @Override
    public Integer deserialize(Tag tag, TagDeserializationContext context) throws ParserException {
        return tag.getAsInt();
    }

    @Override
    public Tag serialize(Integer object, TagSerializationContext context) throws ParserException {
        return IntTag.of(object);
    }
}
