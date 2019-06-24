/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] q;
    private int n = 0;

    public RandomizedQueue() {
        q = (Item[]) new Object[2];
    }

    public boolean isEmpty() {
        return (n == 0);
    }

    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (n == q.length) resize(2*q.length);
        if (n == 0) {
            q[n++] = item;
            return;
        }
        int randomIndex = StdRandom.uniform(n);
        Item temp = q[randomIndex];
        q[randomIndex] = item;
        q[n++] = temp;

    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        if (n > 0 && n == q.length/4) resize(q.length/2);
        int randomIndex = StdRandom.uniform(n);
        Item item = q[randomIndex];
        q[randomIndex] = q[--n];
        q[n] = null;
        return item;
    }

    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        return q[StdRandom.uniform(n)];
    }

    public int size() {
        return n;
    }


    private void resize(int capactiy) {
        Item[] copy = (Item[]) new Object[capactiy];
        for (int i = 0; i < n; i++) {
            copy[i] = q[i];
        }
        q = copy;
    }
    public static void main(String[] args) {

    }

    @Override
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {

        private int[] shuffled;
        private int indiceQ;


        public ArrayIterator() {
            indiceQ = 0;
            shuffled = new int[n];
            for (int k = 0; k < n; k++) {
                shuffled[k] = k;
            }
            StdRandom.shuffle(shuffled);
        }


        @Override
        public boolean hasNext() {
            return indiceQ < n;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return q[shuffled[indiceQ++]];
        }

    }

}
# Coursera-Algorithm_course
