package cz.jalasoft.dsa.container.stack;

import java.util.Optional;

/**
 * @author Jan "Honzales" Lastovicka
 */
public interface Stack<T> {

    void push(T elm);

    Optional<T> pop();

    Optional<T> peek();

    int size();

    boolean isEmpty();
}
