package org.example.cinema;

import java.util.Arrays;

public class CustomQueue<T> {

    private T[] data;
    private int size = 0;
    private int head = 0;

    public CustomQueue() {
        data = (T[])new Object[8];
    }

    public CustomQueue(T[] data) {
        this.data = data;
    }

    public void enqueue(T element) {//добавить
        if (size == data.length) {
            addMoreSpace();
        }
        data[head + size] = element;
        size++;
    }

    private void addMoreSpace() {
        data = Arrays.copyOf(data, data.length * 2);
    }

    public T dequeue() {//достать первый или кинуть IllegalStateException если пусто
        T element = peek();
        data[0] = null;
        head++;
        if (head > 50) {
            moveElements();
        }
        return element;
    }

    private void moveElements() {
        System.out.println("moving " + data.length);
        data = Arrays.copyOfRange(data, head, data.length);
        head = 0;
    }

    public T peek() {//посмотреть первый или кинуть IllegalStateException если пусто
        if (size == 0) {
            throw new IllegalStateException();
        }
        return (T)data[head];
    }

    public int size() {
        return size;
    }
}
