package cz.jalasoft.dsa.container.stack;

import java.util.Optional;

/**
 * @author Jan "Honzales" Lastovicka
 */
public final class LinkedStack<T> implements Stack<T> {

    private final class Node {
        Node next;
        T value;

        Node(Node next, T value) {
            this.next = next;
            this.value = value;
        }
    }

    private Node current;

    @Override
    public void push(T elm) {
        current = new Node(current, elm);
    }

    @Override
    public Optional<T> pop() {
        if (isEmpty()) {
            return Optional.empty();
        }

        var n = current;
        current = current.next;
        return Optional.of(n.value);
    }

    @Override
    public Optional<T> peek() {
        if (isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(current.value);
    }

    @Override
    public int size() {
        var n = current;
        int count = 0;

        while(n != null) {
            count++;
            n = n.next;
        }
        return count;
    }

    @Override
    public boolean isEmpty() {
        return current == null;
    }
}
