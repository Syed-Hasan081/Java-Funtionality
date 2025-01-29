package MyImplementations;

public class MyArrayList<E> implements MyList<E> {
  public static final int INITIAL_CAPACITY = 16;
  @SuppressWarnings("unchecked")
  private E[] data = (E[]) new Object[INITIAL_CAPACITY];

  private int size = 0; // Number of elements in the list

  /** Create an empty list */
  public MyArrayList() {
  }

  @SuppressWarnings("unchecked")
  public MyArrayList(int capacity) {
    data = (E[]) new Object[capacity];
  }

  /** Create a list from an array of objects */
  public MyArrayList(E[] objects) {
    for (int i = 0; i < objects.length; i++)
      add(objects[i]); // Warning: don’t use super(objects)!
  }

  @Override /** Add a new element at the specified index */
  public void add(int index, E e) {
    // Ensure the index is in the right range
    if (index < 0 || index > size) {
      throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    // Ensure capacity
    if (size >= data.length) {
      ensureCapacity();
    }

    // Shift elements to the right
    for (int i = size - 1; i >= index; i--) {
      data[i + 1] = data[i];
    }

    // Insert the new element
    data[index] = e;
    size++;
  }

  /** Create a new larger array, double the current size + 1 */
  private void ensureCapacity() {
    if (size >= data.length) {
      @SuppressWarnings("unchecked")
      E[] newData = (E[]) new Object[data.length * 2 + 1];
      System.arraycopy(data, 0, newData, 0, size);
      data = newData;
    }
  }

  /** Clear the list */
  @SuppressWarnings("unchecked")
  public void clear() {
    data = (E[]) new Object[INITIAL_CAPACITY];
    size = 0;
  }

  /** Return true if this list contains the element */
  public boolean contains(Object e) {
    return indexOf(e) >= 0;
  }

  @Override /** Return the element at the specified index */
  public E get(int index) {
    checkIndex(index);
    return data[index];
  }

  // Check whether the index is in the range of 0 to size - 1
  // throw IndexOutOfBoundsException if it is not
  private void checkIndex(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }
  }

  @Override /**
             * Return the index of the first matching element
             * in this list. Return -1 if no match.
             */
  public int indexOf(Object e) {
    for (int i = 0; i < size; i++) {
      if (e.equals(data[i])) {
        return i;
      }
    }
    return -1;
  }

  @Override /**
             * Return the index of the last matching element
             * in this list. Return -1 if no match.
             */
  public int lastIndexOf(E e) {
    for (int i = size - 1; i >= 0; i--) {
      if (e.equals(data[i])) {
        return i;
      }
    }
    return -1;
  }

  @Override /**
             * Remove the element at the specified position
             * in this list. Shift any subsequent elements to the left.
             * Return the element that was removed from the list.
             */
  public E remove(int index) {
    checkIndex(index);
    E removed = data[index];

    for (int i = index; i < size - 1; i++) {
      data[i] = data[i + 1];
    }

    data[size - 1] = null;
    size--;
    return removed;
  }

  @Override /**
             * Replace the element at the specified position
             * in this list with the specified element.
             */
  public E set(int index, E e) {
    checkIndex(index);
    E old = data[index];
    data[index] = e;
    return old;
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder("[");

    for (int i = 0; i < size; i++) {
      result.append(data[i]);
      if (i < size - 1)
        result.append(", ");
    }

    return result.toString() + "]";
  }

  /** Trims the capacity to current size */
  public void trimToSize() {
    if (size != data.length) {
      @SuppressWarnings("unchecked")
      E[] newData = (E[]) (new Object[size]);
      System.arraycopy(data, 0, newData, 0, size);
      data = newData;
    } // If size == capacity, no need to trim
  }

  /** Return the number of elements in this list */
  public int size() {
    return size;
  }
}
