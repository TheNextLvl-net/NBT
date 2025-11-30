
package net.thenextlvl.nbt.serialization.adapter;

import net.thenextlvl.nbt.serialization.ParserException;
import net.thenextlvl.nbt.serialization.TagAdapter;
import net.thenextlvl.nbt.serialization.TagDeserializationContext;
import net.thenextlvl.nbt.serialization.TagSerializationContext;
import net.thenextlvl.nbt.tag.LongTag;
import net.thenextlvl.nbt.tag.Tag;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.time.Duration;

@ApiStatus.Internal
public final class DurationAdapter implements TagAdapter<Duration> {
    public static final DurationAdapter INSTANCE = new DurationAdapter();

    private DurationAdapter() {
    }

    @Override
    public Duration deserialize(Tag tag, TagDeserializationContext context) throws ParserException {
        return Duration.ofMillis(tag.getAsLong());
    }

    @Override
    public Tag serialize(Duration duration, TagSerializationContext context) throws ParserException {
        return LongTag.of(duration.toMillis());
    }
}
