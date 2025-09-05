package net.thenextlvl.nbt.tag;

import net.thenextlvl.nbt.NBTOutputStream;
import org.jspecify.annotations.NullMarked;

import java.io.IOException;

/**
 * Represents a singleton instance of an escape tag used in NBT (Named Binary Tag) serialization.
 * This class is used as a unique identifier for escape tags in NBT serialization streams.
 */
@NullMarked
public final class EscapeTag implements Tag {
    /**
     * Singleton instance of {@link EscapeTag}. Represents a unique escape tag used in
     * NBT (Named Binary Tag) serialization to identify escape sequences. This is the
     * only instance of {@link EscapeTag} that exists, ensuring consistency and
     * preventing multiple definitions of escape tags within NBT streams.
     */
    public static final EscapeTag INSTANCE = new EscapeTag();
    /**
     * Represents the unique identifier for this Tag.
     */
    public static final int ID = 0;

    private EscapeTag() {
    }

    @Override
    public int getTypeId() {
        return ID;
    }

    @Override
    public boolean equals(final Object obj) {
        return obj.getClass().equals(getClass());
    }

    @Override
    public void write(NBTOutputStream outputStream) throws IOException {
        outputStream.writeByte((byte) getTypeId());
    }

    @Override
    public String toString() {
        return "EscapeTag.INSTANCE";
    }
}
