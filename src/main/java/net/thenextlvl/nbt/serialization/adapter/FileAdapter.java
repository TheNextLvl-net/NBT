package net.thenextlvl.nbt.serialization.adapter;

import net.thenextlvl.nbt.serialization.ParserException;
import net.thenextlvl.nbt.serialization.TagAdapter;
import net.thenextlvl.nbt.serialization.TagDeserializationContext;
import net.thenextlvl.nbt.serialization.TagSerializationContext;
import net.thenextlvl.nbt.tag.StringTag;
import net.thenextlvl.nbt.tag.Tag;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.io.File;

@NullMarked
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
