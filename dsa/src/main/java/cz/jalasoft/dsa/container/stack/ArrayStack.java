package cz.jalasoft.dsa.container.stack;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author Jan "Honzales" Lastovicka
 */
public final class ArrayStack<T> implements Stack<T> {

    private static final int INITIAL_SIZE = 5;

    private Object[] elms;
    private int cursor;

    public ArrayStack() {
        this(INITIAL_SIZE);
    }

    public ArrayStack(int initSize) {
        this.elms = new Object[initSize];
        this.cursor = 0;
    }

    @Override
    public void push(Object elm) {
        if (isFull()) {
            enlarge();
        }

        elms[cursor++] = (T) elm;
    }

    private boolean isFull() {
        return this.elms.length == cursor;
    }

    private void enlarge() {
        this.elms = Arrays.copyOf(this.elms, this.elms.length*2);
    }

    @Override
    public Optional<T> pop() {
        if (isEmpty()) {
            return Optional.empty();
        }
        return Optional.of((T)elms[--cursor]);
    }

    @Override
    public Optional<T> peek() {
        if (isEmpty()) {
            return Optional.empty();
        }

        return Optional.of((T)this.elms[cursor-1]);
    }

    @Override
    public int size() {
        return cursor;
    }

    public boolean isEmpty() {
        return cursor == 0;
    }
}
