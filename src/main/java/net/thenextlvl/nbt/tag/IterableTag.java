package net.thenextlvl.nbt.tag;

import org.jetbrains.annotations.Contract;

import java.util.Iterator;

/**
 * An interface representing a tag that supports iteration over its elements.
 * It extends the {@code Iterable} interface and provides methods to manipulate
 * and retrieve elements in the tag.
 *
 * @param <E> the type of elements contained in the IterableTag
 */
public interface IterableTag<E> extends Iterable<E> {
    /**
     * Returns the number of elements in this IterableTag.
     *
     * @return the number of elements in this IterableTag
     */
    @Contract(pure = true)
    int size();

    /**
     * Retrieves the element at the specified position in this list.
     *
     * @param index index of the element to return
     * @return the element at the specified position in this list
     */
    @Contract(pure = true)
    E get(int index);

    /**
     * Replaces the element at the specified position in this IterableTag with the specified element.
     *
     * @param index   index of the element to replace
     * @param element element to be stored at the specified position
     */
    @Contract(mutates = "this")
    void set(int index, E element);

    /**
     * Returns an iterator over elements of type {@code E}.
     *
     * @return an {@code Iterator<E>} over the elements in this IterableTag
     */
    @Override
    @Contract(pure = true)
    default Iterator<E> iterator() {
        return new Iterator<>() {
            private int index;

            @Override
            public boolean hasNext() {
                return index < IterableTag.this.size();
            }

            @Override
            public E next() {
                return IterableTag.this.get(index++);
            }
        };
    }
}
