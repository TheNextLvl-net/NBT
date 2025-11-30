package net.thenextlvl.nbt.test;

import net.thenextlvl.nbt.file.NBTFile;
import net.thenextlvl.nbt.tag.CompoundTag;
import net.thenextlvl.nbt.tag.DoubleTag;
import net.thenextlvl.nbt.tag.IntArrayTag;
import net.thenextlvl.nbt.tag.ListTag;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NBTFileTest {
    private static final Path path = Path.of("test.dat");

    @Test
    public void createFile() {
        var contents = CompoundTag.builder()
                .put("array", IntArrayTag.of())
                .put("list", ListTag.of(DoubleTag.ID))
                .put("compound", CompoundTag.empty())
                .put("number", 1)
                .put("boolean", false)
                .put("string", "Hello World!")
                .build();

        assertFalse(Files.isRegularFile(path), path + " already exists");

        var file = new NBTFile<>(path, contents).saveIfAbsent();

        assertTrue(Files.isRegularFile(path), "Failed to create file");
        assertEquals(contents, file.getRoot(), "File was not saved to disk");

        var modified = CompoundTag.empty();
        file.setRoot(modified);
        file.save();

        assertEquals(modified, new NBTFile<>(path, contents).getRoot(), "File was not overridden");
    }

    @AfterAll
    public static void cleanup() throws IOException {
        Files.delete(path);
        assertFalse(Files.isRegularFile(path), path + " still exists");
    }
}
