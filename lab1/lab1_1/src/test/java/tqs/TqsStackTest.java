package tqs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.tqs.TqsStack;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

public class TqsStackTest {

    @Test
    void isEmptyOnConstruction() {
        TqsStack<String> stack = new TqsStack<String>();
        assertTrue(stack.isEmpty());
    }

    @Test
    void sizeOnConstruction() {
        TqsStack<String> stack = new TqsStack<String>();
        assertEquals(0, stack.size());
    }

    @Test
    void sizeAfterPush() {
        TqsStack<String> stack = new TqsStack<String>();
        stack.push("a");
        assertEquals(1, stack.size());
        assertFalse(stack.isEmpty());
    }

    @Test
    void pop() {
        TqsStack<String> stack = new TqsStack<String>();
        stack.push("a");
        assertEquals("a", stack.pop());
    }

    @Test
    void peek() {
        TqsStack<String> stack = new TqsStack<String>();
        stack.push("a");
        assertEquals("a", stack.peek());
    }

    @Test
    void sizeAfterPeek() {
        TqsStack<String> stack = new TqsStack<String>();
        stack.push("a");
        Object size = stack.size();
        stack.peek();
        assertEquals(size, stack.size());
    }

    @Test
    void emptyAfterPop() {
        TqsStack<String> stack = new TqsStack<String>();
        stack.push("a");
        stack.pop();
        assertTrue(stack.isEmpty());
    }

    @Test
    void sizeAfterPop() {
        TqsStack<String> stack = new TqsStack<String>();
        stack.push("a");
        stack.pop();
        assertEquals(0, stack.size());
    }

    @Test
    void poppingExceptionTest() {
        TqsStack<Integer> stack = new TqsStack<Integer>();
        Assertions.assertThrows(NoSuchElementException.class, () -> stack.pop());
    }

    @Test
    void peekingExceptionTest() {
        TqsStack<Integer> stack = new TqsStack<Integer>();
        Assertions.assertThrows(NoSuchElementException.class, () -> stack.peek());
    }

    @Test
    void illegalStateExceptionTest() {
        TqsStack<Integer> stack = new TqsStack<Integer>(3);
        stack.push(1);
        stack.push(2);
        stack.push(3);
        Assertions.assertThrows(IllegalStateException.class, () -> stack.push(4));
    }

    @Test
    void popTopN() {
        TqsStack<Integer> stack = new TqsStack<Integer>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        assertEquals(2, stack.popTopN(3));
    }

    @Test
    void popTopNExceptionTest() {
        TqsStack<Integer> stack = new TqsStack<Integer>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        Assertions.assertThrows(NoSuchElementException.class, () -> stack.popTopN(5));
    }
}
