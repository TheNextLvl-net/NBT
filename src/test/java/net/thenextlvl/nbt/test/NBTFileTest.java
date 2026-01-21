package net.thenextlvl.nbt.test;

import net.thenextlvl.nbt.NBTInputStream;
import net.thenextlvl.nbt.NBTOutputStream;
import net.thenextlvl.nbt.serialization.NBT;
import net.thenextlvl.nbt.serialization.ParserException;
import net.thenextlvl.nbt.tag.ByteArrayTag;
import net.thenextlvl.nbt.tag.ByteTag;
import net.thenextlvl.nbt.tag.CompoundTag;
import net.thenextlvl.nbt.tag.DoubleTag;
import net.thenextlvl.nbt.tag.FloatTag;
import net.thenextlvl.nbt.tag.IntArrayTag;
import net.thenextlvl.nbt.tag.IntTag;
import net.thenextlvl.nbt.tag.ListTag;
import net.thenextlvl.nbt.tag.LongArrayTag;
import net.thenextlvl.nbt.tag.LongTag;
import net.thenextlvl.nbt.tag.NumberTag;
import net.thenextlvl.nbt.tag.ShortTag;
import net.thenextlvl.nbt.tag.StringTag;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NBTFileTest {
    private static final Path path = Path.of("test.dat");

    @Test
    public void testSNBT() {
        assertEquals("[1,2,3]", IntArrayTag.of(1, 2, 3).toString());
        assertEquals("[]", IntArrayTag.of().toString());
        assertEquals("[1b,2b]", ByteArrayTag.of((byte) 1, (byte) 2).toString());
        assertEquals("[]", ByteArrayTag.of().toString());
        assertEquals("[1l,2l,3l]", LongArrayTag.of(1L, 2L, 3L).toString());
        assertEquals("[]", LongArrayTag.of().toString());
        assertEquals("{test:1}", CompoundTag.builder().put("test", 1).build().toString());
        assertEquals("{}", CompoundTag.empty().toString());
        assertEquals("[test,test,\"Hello World!\"]", ListTag.of(StringTag.of("test"), StringTag.of("test"), StringTag.of("Hello World!")).toString());
        assertEquals("[]", ListTag.empty(StringTag.ID).toString());
        assertEquals("1", IntTag.of(1).toString());
        assertEquals("1.0", DoubleTag.of(1).toString());
        assertEquals("1.0f", FloatTag.of(1).toString());
        assertEquals("1l", LongTag.of(1).toString());
        assertEquals("1b", ByteTag.of((byte) 1).toString());
        assertEquals("1s", ShortTag.of((short) 1).toString());
        assertEquals("\"Hello World!\"", StringTag.of("Hello World!").toString());
    }

    @Test
    public void testCompoundTagEquality() {
        assertEquals(CompoundTag.empty(), CompoundTag.of(Map.of()));

        var tag = CompoundTag.builder()
                .put("test", 1)
                .build();
        assertEquals(tag, CompoundTag.of(Map.of("test", IntTag.of(1))));
    }

    @Test
    public void testNumberTagEquality() {
        assertEquals(NumberTag.of((byte) 1), ByteTag.of((byte) 1));
        assertEquals(NumberTag.of((short) 1), ShortTag.of((short) 1));
        assertEquals(NumberTag.of(1), IntTag.of(1));
        assertEquals(NumberTag.of(1L), LongTag.of(1));
        assertEquals(NumberTag.of(1d), DoubleTag.of(1));
        assertEquals(NumberTag.of(1f), FloatTag.of(1));
        assertEquals(NumberTag.of(BigDecimal.ONE), DoubleTag.of(1));
    }

    @Test
    public void testArrayTagImmutability() {
        var longs = LongArrayTag.of(1, 2, 3, 4, 5);
        longs.getValue()[0] = 2;
        assertEquals(1, longs.get(0));

        var ints = IntArrayTag.of(1, 2, 3, 4, 5);
        ints.getValue()[0] = 2;
        assertEquals(1, ints.get(0));

        var bytes = ByteArrayTag.of((byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5);
        bytes.getValue()[0] = (byte) 2;
        assertEquals((byte) 1, bytes.get(0));
    }

    @Test
    public void testCompoundTagImmutability() {
        var tag = CompoundTag.of(Map.of("test", IntTag.of(1)));
        assertThrows(UnsupportedOperationException.class, () -> tag.getValue().put("test", IntTag.of(2)));
        assertThrows(UnsupportedOperationException.class, () -> tag.getValue().remove("test"));
        assertThrows(UnsupportedOperationException.class, () -> tag.getValue().clear());
    }

    @Test
    public void testListTagImmutability() {
        var list = ListTag.of(IntTag.of(1));
        assertThrows(UnsupportedOperationException.class, () -> list.add(IntTag.of(2)));
        assertThrows(UnsupportedOperationException.class, list::removeFirst);
        assertThrows(UnsupportedOperationException.class, () -> list.set(0, IntTag.of(2)));
    }

    @Test
    public void testListTagEquality() {
        assertEquals(ListTag.empty(StringTag.ID), ListTag.of(StringTag.ID));
        assertNotEquals(ListTag.empty(StringTag.ID), ListTag.empty(DoubleTag.ID));

        var tag = ListTag.of(IntTag.ID, IntTag.of(1));
        assertEquals(ListTag.of(IntTag.of(1)), tag);

        var numberList = ListTag.builder()
                .add(NumberTag.of(1))
                .add(NumberTag.of(2))
                .add(NumberTag.of(4))
                .build();
        assertEquals(ListTag.of(IntTag.of(1), IntTag.of(2), IntTag.of(4)), numberList);

        assertThrows(IllegalArgumentException.class, () -> ListTag.of(DoubleTag.of(1.0), StringTag.of("Hello World!")));
        assertThrows(IllegalArgumentException.class, () -> ListTag.of(IntTag.ID, DoubleTag.of(1.0)));
    }

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

        try (var nbt = NBTOutputStream.create(path)) {
            nbt.writeTag(null, contents);

            assertTrue(Files.isRegularFile(path), "Failed to create file");
        }

        try (var reader = NBTInputStream.create(path)) {
            assertEquals(contents, reader.readTag(), "File was written incorrectly");
        }

        var modified = CompoundTag.empty();
        try (var nbt = NBTOutputStream.create(path)) {
            nbt.writeTag(null, modified);
        }

        try (var reader = NBTInputStream.create(path)) {
            assertEquals(modified, reader.readTag(), "File was not overridden");
        }
    }

    @Test
    public void testUnregisteredSerializer() {
        var nbt = NBT.builder().build();

        // Create a simple object that has no registered serializer
        var unserializable = new Object();

        // Without the fix, this would cause a StackOverflowError due to infinite recursion
        // With the fix, it throws a ParserException
        assertThrows(ParserException.class, () -> nbt.serialize(unserializable), "StackOverflowError expected");
    }

    @Test
    public void testUnregisteredDeserializer() {
        var nbt = NBT.builder().build();

        // Create a simple tag that has no registered deserializer for Object
        var tag = IntTag.of(42);

        // Without the fix, this would cause a StackOverflowError due to infinite recursion
        // With the fix, it throws a ParserException
        assertThrows(ParserException.class, () -> nbt.deserialize(tag, Object.class), "StackOverflowError expected");
    }

    private static Stream<Arguments> provideDefaultAdapters() {
        return Stream.of(
                // Boolean types
                Arguments.of(true, Boolean.class, Boolean.class),
                Arguments.of(false, Boolean.class, Boolean.class),
                Arguments.of(true, boolean.class, Boolean.class),
                Arguments.of(false, boolean.class, Boolean.class),

                // Byte types
                Arguments.of((byte) 42, Byte.class, Byte.class),
                Arguments.of((byte) -10, Byte.class, Byte.class),
                Arguments.of((byte) 42, byte.class, Byte.class),
                Arguments.of((byte) -10, byte.class, Byte.class),

                // Short types
                Arguments.of((short) 1000, Short.class, Short.class),
                Arguments.of((short) -500, Short.class, Short.class),
                Arguments.of((short) 1000, short.class, Short.class),
                Arguments.of((short) -500, short.class, Short.class),

                // Integer types
                Arguments.of(42, Integer.class, Integer.class),
                Arguments.of(-100, Integer.class, Integer.class),
                Arguments.of(42, int.class, Integer.class),
                Arguments.of(-100, int.class, Integer.class),

                // Long types
                Arguments.of(42L, Long.class, Long.class),
                Arguments.of(-100L, Long.class, Long.class),
                Arguments.of(42L, long.class, Long.class),
                Arguments.of(-100L, long.class, Long.class),

                // Float types
                Arguments.of(42.5f, Float.class, Float.class),
                Arguments.of(-100.75f, Float.class, Float.class),
                Arguments.of(42.5f, float.class, Float.class),
                Arguments.of(-100.75f, float.class, Float.class),

                // Double types
                Arguments.of(42.5, Double.class, Double.class),
                Arguments.of(-100.75, Double.class, Double.class),
                Arguments.of(42.5, double.class, Double.class),
                Arguments.of(-100.75, double.class, Double.class),

                // String types
                Arguments.of("Hello World!", String.class, String.class),
                Arguments.of("", String.class, String.class),
                Arguments.of("Special chars: \n\t\r", String.class, String.class),

                // UUID types
                Arguments.of(UUID.randomUUID(), UUID.class, UUID.class),
                Arguments.of(UUID.fromString("00000000-0000-0000-0000-000000000000"), UUID.class, UUID.class),

                // Duration types
                Arguments.of(Duration.ofSeconds(60), Duration.class, Duration.class),
                Arguments.of(Duration.ofDays(1), Duration.class, Duration.class),
                Arguments.of(Duration.ofMillis(500), Duration.class, Duration.class),

                // File types
                Arguments.of(new File("/tmp/test.txt"), File.class, File.class),
                Arguments.of(new File("relative/path.txt"), File.class, File.class),

                // Path types
                Arguments.of(Path.of("/tmp/test.txt"), Path.class, Path.class),
                Arguments.of(Path.of("relative/path.txt"), Path.class, Path.class),

                // InetSocketAddress types
                Arguments.of(new InetSocketAddress("localhost", 8080), InetSocketAddress.class, InetSocketAddress.class),
                Arguments.of(new InetSocketAddress("127.0.0.1", 25565), InetSocketAddress.class, InetSocketAddress.class)
        );
    }

    @MethodSource("provideDefaultAdapters")
    @ParameterizedTest(name = "[{index}] {1}: {0}")
    public <T> void testDefaultAdapters(T value, Class<T> type, Class<T> expectedClass) {
        var nbt = NBT.builder().build();

        var tag = nbt.serialize(value, type);
        assertNotNull(tag, "Serialization must not return null for type: " + type);

        var deserialized = nbt.deserialize(tag, expectedClass);
        assertNotNull(deserialized, "Deserialization must not return null for type: " + type);

        assertEquals(value, deserialized, "Values must be equal for type: " + type);
    }

    @AfterAll
    public static void cleanup() throws IOException {
        Files.deleteIfExists(path);
        assertFalse(Files.isRegularFile(path), path + " still exists");
    }
}
