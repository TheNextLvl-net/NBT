package net.thenextlvl.nbt.test;

import net.thenextlvl.nbt.NBTInputStream;
import net.thenextlvl.nbt.NBTOutputStream;
import net.thenextlvl.nbt.tag.CompoundTag;
import net.thenextlvl.nbt.tag.DoubleTag;
import net.thenextlvl.nbt.tag.IntArrayTag;
import net.thenextlvl.nbt.tag.ListTag;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.READ;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.nio.file.StandardOpenOption.WRITE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NBTFileTest {
    private static final Path path = Path.of("test.dat");

    @Test
    public void createFile() throws IOException {
        var contents = CompoundTag.builder()
                .put("array", IntArrayTag.of())
                .put("list", ListTag.of(DoubleTag.ID))
                .put("compound", CompoundTag.empty())
                .put("number", 1)
                .put("boolean", false)
                .put("string", "Hello World!")
                .build();

        assertFalse(Files.isRegularFile(path), path + " already exists");

        try (var output = Files.newOutputStream(path, WRITE, CREATE, TRUNCATE_EXISTING);
             var nbt = new NBTOutputStream(output)) {
            nbt.writeTag(null, contents);

            assertTrue(Files.isRegularFile(path), "Failed to create file");
        }

        try (var input = Files.newInputStream(path, READ);
             var reader = new NBTInputStream(input)) {
            assertEquals(contents, reader.readTag(), "File was written incorrectly");
        }

        var modified = CompoundTag.empty();
        try (var output = Files.newOutputStream(path, WRITE, CREATE, TRUNCATE_EXISTING);
             var nbt = new NBTOutputStream(output)) {
            nbt.writeTag(null, modified);
        }

        try (var input = Files.newInputStream(path, READ);
             var reader = new NBTInputStream(input)) {
            assertEquals(modified, reader.readTag(), "File was not overridden");
        }
    }

    @AfterAll
    public static void cleanup() throws IOException {
        Files.deleteIfExists(path);
        assertFalse(Files.isRegularFile(path), path + " still exists");
    }
}
