package org.tqs;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class TqsStack<T> {
    private LinkedList<T> stack;
    private int limit = -1;

    public TqsStack() {
        stack = new LinkedList<>();
    }

    public TqsStack(int limit) {
        stack = new LinkedList<>();
        this.limit = limit;
    }

    public void push(T item) {
        if (limit != -1 && stack.size() == limit) {
            throw new IllegalStateException();
        }

        stack.push(item);
    }

    public T pop() {
        if (stack.isEmpty()) {
            throw new NoSuchElementException();
        }

        return stack.pop();
    }

    public T peek() {
        if (stack.isEmpty()) {
            throw new NoSuchElementException();
        }

        return stack.peek();
    }

    public int size() {
        return stack.size();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public T popTopN(int n) {
        T top = null;

        if (n > stack.size()) {
            throw new NoSuchElementException();
        }

        for (int i = 0; i < n; i++) {
            top = stack.removeFirst();
        }

        return top;
    }
}
