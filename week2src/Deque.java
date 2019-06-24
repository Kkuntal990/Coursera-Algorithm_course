/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first, last;
    private int n;

    public Deque() {
        n = 0;
    }

    public boolean isEmpty() {
        return (n == 0);
    }

    public int size() {
        return n;
    }

    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("item cannot be null");
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        if (isEmpty()) last = first;
        else {
            first.next = oldFirst;
            first.next.prev = first;

        }
        n += 1;

    }

    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("item cannot be null");
        Node oldLast = last;
        last = new Node();
        last.item = item;
        if (isEmpty()) first = last;
        else {
            last.prev = oldLast;
            last.prev.next = last;
        }
        n += 1;
    }

    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Deque is already empty");
        Item item = first.item;
        n -= 1;

        if (isEmpty()) {
            first = null;
            last = null;
        }
        else {
            first = first.next;
            first.prev = null;
        }

        return item;
    }

    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Deque is already empty");
        Item item = last.item;
        n -= 1;

        if (isEmpty()) {
            first = null;
            last = null;
        }
        else {
            last = last.prev;
            last.next = null;
        }
        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;
        @Override
        public boolean hasNext() {
            return (current != null);
        }
        @Override
        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }
        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not allowed");
        }
    }


    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(1);
        deque.addLast(2);
        deque.addFirst(3);
        StdOut.println(deque.size() + ": size");

        while (!deque.isEmpty()) {
            StdOut.println("Current size : " + deque.size());
            StdOut.println(deque.removeLast());
        }
    }




    private class Node {
        Item item;
        Node next;
        Node prev;
    }
}
# Coursera-Algorithm_course
