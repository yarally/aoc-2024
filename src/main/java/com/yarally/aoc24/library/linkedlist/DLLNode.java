package com.yarally.aoc24.library.linkedlist;

public class DLLNode<T> {

    public final T value;
    private DLLNode<T> next;
    private DLLNode<T> previous;

    public DLLNode(T value) {
        this.value = value;
    }

    public DLLNode<T> getNext() {
        return next;
    }

    public DLLNode<T> getNext(int amount) {
        var current = this;
        for (int i = 0; i < amount-1; i++) {
            current = current.next;
        }
        return current;
    }


    public void setNext(DLLNode<T> next) {
        this.next = next;
        if (next != null) {
            next.previous = this;
        }
    }

    public DLLNode<T> getPrevious() {
        return previous;
    }

    public void setPrevious(DLLNode<T> previous) {
        this.previous = previous;
        if (previous != null) {
            previous.next = this;
        }
    }

    @Override
    public String toString() {
        return "(" + value + ")";
    }
}
