package net.thenextlvl.nbt.tag;

import org.intellij.lang.annotations.MagicConstant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to expect NBT tag type constants.
 *
 * @since 4.0.0
 */
@Retention(RetentionPolicy.SOURCE)
@Target({
        ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE,
        ElementType.ANNOTATION_TYPE,
        ElementType.METHOD
})
@MagicConstant(intValues = {
        ByteTag.ID, ShortTag.ID, IntTag.ID, LongTag.ID,
        FloatTag.ID, DoubleTag.ID, ByteArrayTag.ID,
        StringTag.ID, ListTag.ID, CompoundTag.ID,
        IntArrayTag.ID, LongArrayTag.ID
})
public @interface TagType {
}
