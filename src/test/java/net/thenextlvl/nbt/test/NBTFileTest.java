package net.thenextlvl.nbt.test;

import core.io.IO;
import core.io.PathIO;
import net.thenextlvl.nbt.file.NBTFile;
import net.thenextlvl.nbt.tag.CompoundTag;
import net.thenextlvl.nbt.tag.DoubleTag;
import net.thenextlvl.nbt.tag.IntArrayTag;
import net.thenextlvl.nbt.tag.ListTag;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NBTFileTest {
    private static final PathIO path = IO.of("test.dat");

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

        assertFalse(path.exists(), path + " already exists");

        var file = new NBTFile<>(path, contents).saveIfAbsent();

        assertTrue(path.exists(), "Failed to create file");
        assertEquals(contents, file.getRoot(), "File was not saved to disk");

        var modified = CompoundTag.empty();
        file.setRoot(modified);
        file.save();

        assertEquals(modified, new NBTFile<>(path, contents).getRoot(), "File was not overridden");
    }

    @AfterAll
    public static void cleanup() throws IOException {
        path.delete();
        assertFalse(path.exists(), path + " still exists");
    }
}
