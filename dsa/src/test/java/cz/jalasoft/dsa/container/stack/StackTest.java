package cz.jalasoft.dsa.container.stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Jan "Honzales" Lastovicka
 */
public class StackTest {

    private Stack<Integer> stack;

    @BeforeEach
    public void init() {
        this.stack = new LinkedStack<>();
    }

    @Test
    public void emptyStackIsEmpty() {
        assertTrue(stack.isEmpty());
        assertTrue(stack.isEmpty());
    }

    @Test
    public void emptyValueGotWhenStackEmpty() {
        var maybeValue = stack.pop();
        assertTrue(maybeValue.isEmpty());

        maybeValue = stack.peek();
        assertTrue((maybeValue.isEmpty()));
    }

    @Test
    public void stackIsEnlarged() {

        stack.push(3);
        stack.push(5);
        stack.push(7);
        stack.push(23);
        stack.push(44);
        stack.push(34);
        stack.push(90);

        assertEquals(7, stack.size());
        assertFalse(stack.isEmpty());
    }

    @Test
    public void valuesPoppedFromStackAreInCorrectOrder() {

        stack.push(56);
        stack.push(78);
        stack.push(23);

        var top = stack.peek();
        assertFalse(top.isEmpty());
        assertEquals(23, top.get());

        var v1 = stack.pop();
        assertFalse(v1.isEmpty());
        assertEquals(23, v1.get());

        var v2 = stack.pop();
        assertFalse(v2.isEmpty());
        assertEquals(78, v2.get());

        var v3 = stack.pop();
        assertFalse(v3.isEmpty());
        assertEquals(56, v3.get());

        assertTrue(stack.isEmpty());
    }
}
