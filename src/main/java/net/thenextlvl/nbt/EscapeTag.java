package net.thenextlvl.nbt;

/**
 * Represents a singleton instance of an escape tag used in NBT (Named Binary Tag) serialization.
 * This class is used as a unique identifier for escape tags in NBT serialization streams.
 */
public sealed interface EscapeTag extends Tag permits EscapeTagImpl {
    /**
     * Singleton instance of {@link EscapeTag}. Represents a unique escape tag used in
     * NBT (Named Binary Tag) serialization to identify escape sequences. This is the
     * only instance of {@link EscapeTag} that exists, ensuring consistency and
     * preventing multiple definitions of escape tags within NBT streams.
     */
    EscapeTag INSTANCE = EscapeTagImpl.INSTANCE;
    /**
     * Represents the unique identifier for this Tag.
     */
    int ID = 0;
}
