package net.thenextlvl.nbt.file;

import net.thenextlvl.nbt.NBTInputStream;
import net.thenextlvl.nbt.NBTOutputStream;
import net.thenextlvl.nbt.tag.CompoundTag;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.READ;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.nio.file.StandardOpenOption.WRITE;

/**
 * Represents an NBT (Named Binary Tag) file that can be read from and written to.
 *
 * @param <R> the type of the root object, which extends {@link CompoundTag}
 */
@Deprecated(forRemoval = true)
public class NBTFile<R extends CompoundTag> {
    /**
     * The name of the root tag in an NBT (Named Binary Tag) file.
     * This variable can be null if no root name is specified.
     */
    private @Nullable String rootName;

    private final Path file;
    private final Charset charset;

    private R root;
    private boolean loaded;

    /**
     * Construct a new NBTFile providing a file, charset and default root object
     *
     * @param file    the file to read from and write to
     * @param charset the charset to use for read and write operations
     * @param root    the default root object
     */
    public NBTFile(Path file, Charset charset, R root) {
        this.file = file;
        this.root = root;
        this.charset = charset;
    }

    /**
     * Construct a new NBTFile providing a file and default root object
     *
     * @param file the file to read from and write to
     * @param root the default root object
     */
    public NBTFile(Path file, R root) {
        this(file, StandardCharsets.UTF_8, root);
    }

    public R getRoot() {
        if (loaded) return root;
        loaded = true;
        return root = load();
    }

    private NBTFile<R> setRoot(R root) {
        this.loaded = true;
        this.root = root;
        return this;
    }

    public NBTFile<R> reload() {
        return setRoot(load());
    }

    public NBTFile<R> saveIfAbsent(FileAttribute<?>... attributes) {
        return Files.isRegularFile(file) ? this : save(attributes);
    }

    public Charset getCharset() {
        return charset;
    }

    @SuppressWarnings("unchecked")
    protected R load() {
        if (!Files.isRegularFile(file)) return getRoot();
        try (var inputStream = new NBTInputStream(
                Files.newInputStream(file, READ),
                getCharset()
        )) {
            var entry = inputStream.readNamedTag();
            entry.getValue().ifPresent(this::setRootName);
            return (R) entry.getKey();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public @NonNull NBTFile<R> save(@NonNull FileAttribute<?>... attributes) {
        try {
            var root = getRoot();
            Files.createDirectories(file.getParent());
            try (var outputStream = new NBTOutputStream(
                    Files.newOutputStream(file, WRITE, CREATE, TRUNCATE_EXISTING),
                    getCharset()
            )) {
                outputStream.writeTag(getRootName(), root);
                return this;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public @Nullable String getRootName() {
        return rootName;
    }

    public void setRootName(@Nullable String rootName) {
        this.rootName = rootName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        NBTFile<?> nbtFile = (NBTFile<?>) o;
        return Objects.equals(rootName, nbtFile.rootName) && Objects.equals(file, nbtFile.file) && Objects.equals(charset, nbtFile.charset);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rootName, file, charset);
    }

    @Override
    public String toString() {
        return "NBTFile{" +
                "rootName='" + rootName + '\'' +
                ", file=" + file +
                ", charset=" + charset +
                '}';
    }
}
