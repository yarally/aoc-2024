package com.yarally.aoc24.library.linkedlist;

import java.util.List;

public class DoublyLinkedList<T> {
    private DLLNode<T> head;
    private DLLNode<T> tail;
    private List<DLLNode<T>> nodes;

    public DoublyLinkedList(DLLNode<T> head, DLLNode<T> tail, List<DLLNode<T>> nodes) {
        this.head = head;
        this.tail = tail;
        this.nodes = nodes;
    }

    public DLLNode<T> getHead() {
        return head;
    }

    public DLLNode<T> getTail() {
        return tail;
    }

    public void setHead(DLLNode<T> head) {
        this.head = head;
    }

    public void setTail(DLLNode<T> tail) {
        this.tail = tail;
    }

    @Override
    public String toString() {
        var current = head;
        var stringBuilder = new StringBuilder();
        while (current != null) {
            stringBuilder.append(current);
            current = current.getNext();
            if (current == head) {
                break;
            }
        }
        return "DoublyLinkedList{" + stringBuilder.toString() + "}";
    }
}
