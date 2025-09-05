package net.thenextlvl.nbt.serialization.adapter;

import net.thenextlvl.nbt.serialization.TagAdapter;
import net.thenextlvl.nbt.serialization.TagDeserializationContext;
import net.thenextlvl.nbt.serialization.TagSerializationContext;
import net.thenextlvl.nbt.tag.CompoundTag;
import net.thenextlvl.nbt.tag.Tag;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.UUID;

@NullMarked
@ApiStatus.Internal
public final class UUIDAdapter implements TagAdapter<UUID> {
    public static final UUIDAdapter INSTANCE = new UUIDAdapter();

    private UUIDAdapter() {
    }

    @Override
    public UUID deserialize(Tag tag, TagDeserializationContext context) {
        var compound = tag.getAsCompound();
        var most = compound.get("most").getAsLong();
        var least = compound.get("least").getAsLong();
        return new UUID(most, least);
    }

    @Override
    public Tag serialize(UUID uuid, TagSerializationContext context) {
        var tag = new CompoundTag();
        tag.add("most", uuid.getMostSignificantBits());
        tag.add("least", uuid.getLeastSignificantBits());
        return tag;
    }
}
