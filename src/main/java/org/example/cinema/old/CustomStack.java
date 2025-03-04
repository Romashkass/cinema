package org.example.cinema.old;

import java.util.Arrays;

public class CustomStack<T> {

    private T[] data;
    private int size = 0;

    public CustomStack() {
        data = (T[])new Object[8];
    }

    public CustomStack(T[] data) {
        this.data = data;
    }

    public void push(T element) {//добавить
        if (size == data.length) {
            addMoreSpace();
        }
        data[size] = element;
        size++;
    }

    private void addMoreSpace() {
        data = Arrays.copyOf(data, data.length * 2);
    }

    public T pop() {//достать первый или кинуть IllegalStateException если пусто
        T element = peek();
        size--;
        data[size] = null;
        return element;
    }

    public T peek() {//посмотреть первый или кинуть IllegalStateException если пусто
        if (size == 0) {
            throw new IllegalStateException();
        }
        return (T)data[size - 1];
    }

    public int size() {
        return size;
    }
}
