/*
 * MIT License
 *
 * Copyright (c) [2022] [Dmitry]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.dm.study.libs;

/**
 * @author Dmitry
 */
public class CircularList<T> {
    private Item current;
    private Item last;
    private Item first;
    private int size = 0;

    public void add(T value) {
        if (size + 1 >= Integer.MAX_VALUE) {
            throw new IndexOutOfBoundsException("Список полон, добавление невозможно");
        }
        if (size == 0) {
            first = current = last = new Item(value);
        } else {
            last.next = new Item(value);
            last = last.next;
        }
        last.next = first;
        size++;
    }

    public T next() {
        Item buffer = current;
        current = current.next;
        return buffer.value;
    }

    public int size() {
        return size;
    }

    public T first() {
        current = first;
        return next();
    }

    private class Item {
        private Item next;
        private final T value;

        public Item(final T value) {
            this.value = value;
        }
    }
}
