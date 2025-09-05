package net.thenextlvl.nbt.serialization.adapter;

import net.thenextlvl.nbt.serialization.ParserException;
import net.thenextlvl.nbt.serialization.TagAdapter;
import net.thenextlvl.nbt.serialization.TagDeserializationContext;
import net.thenextlvl.nbt.serialization.TagSerializationContext;
import net.thenextlvl.nbt.tag.DoubleTag;
import net.thenextlvl.nbt.tag.Tag;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class DoubleAdapter implements TagAdapter<Double> {
    public static final DoubleAdapter INSTANCE = new DoubleAdapter();

    private DoubleAdapter() {
    }

    @Override
    public Double deserialize(Tag tag, TagDeserializationContext context) throws ParserException {
        return tag.getAsDouble();
    }

    @Override
    public Tag serialize(Double object, TagSerializationContext context) throws ParserException {
        return DoubleTag.of(object);
    }
}
