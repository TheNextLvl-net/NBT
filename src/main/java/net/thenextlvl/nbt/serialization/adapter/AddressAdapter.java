package net.thenextlvl.nbt.serialization.adapter;

import net.thenextlvl.nbt.serialization.ParserException;
import net.thenextlvl.nbt.serialization.TagAdapter;
import net.thenextlvl.nbt.serialization.TagDeserializationContext;
import net.thenextlvl.nbt.serialization.TagSerializationContext;
import net.thenextlvl.nbt.tag.CompoundTag;
import net.thenextlvl.nbt.tag.Tag;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.net.InetSocketAddress;

@NullMarked
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
        var tag = new CompoundTag();
        tag.add("hostname", address.getHostName());
        tag.add("port", address.getPort());
        return tag;
    }
}
