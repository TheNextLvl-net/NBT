package net.thenextlvl.nbt.serialization.adapters;

import net.thenextlvl.nbt.serialization.ParserException;
import net.thenextlvl.nbt.serialization.TagAdapter;
import net.thenextlvl.nbt.serialization.TagDeserializationContext;
import net.thenextlvl.nbt.serialization.TagSerializationContext;
import net.thenextlvl.nbt.tag.CompoundTag;
import net.thenextlvl.nbt.tag.Tag;
import org.jetbrains.annotations.ApiStatus;

import java.net.InetSocketAddress;

@ApiStatus.Internal
public final class AddressAdapter implements TagAdapter<InetSocketAddress> {
    public static final AddressAdapter INSTANCE = new AddressAdapter();

    private AddressAdapter() {
    }

    @Override
    public InetSocketAddress deserialize(Tag tag, TagDeserializationContext context) throws ParserException {
        var root = tag.getAsCompound();
        var hostname = root.get("hostname").getAsString();
        var port = root.get("port").getAsInt();
        return new InetSocketAddress(hostname, port);
    }

    @Override
    public Tag serialize(InetSocketAddress address, TagSerializationContext context) throws ParserException {
        return CompoundTag.builder()
                .put("hostname", address.getHostName())
                .put("port", address.getPort())
                .build();
    }
}
