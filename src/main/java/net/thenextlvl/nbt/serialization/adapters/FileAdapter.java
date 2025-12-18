package net.thenextlvl.nbt.serialization.adapters;

import net.thenextlvl.nbt.serialization.ParserException;
import net.thenextlvl.nbt.serialization.TagAdapter;
import net.thenextlvl.nbt.serialization.TagDeserializationContext;
import net.thenextlvl.nbt.serialization.TagSerializationContext;
import net.thenextlvl.nbt.StringTag;
import net.thenextlvl.nbt.Tag;
import org.jetbrains.annotations.ApiStatus;

import java.io.File;

@ApiStatus.Internal
public final class FileAdapter implements TagAdapter<File> {
    public static final FileAdapter INSTANCE = new FileAdapter();

    private FileAdapter() {
    }

    @Override
    public File deserialize(Tag tag, TagDeserializationContext context) throws ParserException {
        return new File(tag.getAsString());
    }

    @Override
    public Tag serialize(File file, TagSerializationContext context) throws ParserException {
        return StringTag.of(file.getPath());
    }
}
