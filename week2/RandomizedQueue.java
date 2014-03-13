import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Superman Petrovich Petrovich on 2/13/14.
 */

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int n = 0;
    private Item[] items;

    public RandomizedQueue() {
        items = (Item[]) new Object[1];
    }

    public static void main(String[] args) {
        RandomizedQueue<String> rq1 = new RandomizedQueue<String>();

        rq1.enqueue("a");
        rq1.enqueue("b");
        rq1.enqueue("c");
        rq1.enqueue("d");
        rq1.enqueue("e");
        rq1.enqueue("f");
        rq1.enqueue("g");
        StdOut.printf(rq1.dequeue());
        StdOut.printf(rq1.dequeue());
        StdOut.printf(rq1.dequeue());
        StdOut.printf(rq1.dequeue());

        for(String item : rq1){
            StdOut.printf(item + ", ");
        }
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        if (n == 0) {
            items[n++] = item;
        }
        else {
            if (items.length == n) {
                resize(items.length * 2);
            }

            items[n++] = item;
        }
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int i = getRandomIndex();

        Item itemToReturn = items[i];
        items[i] = items[--n];

        items[n] = null;

        if (n > 0 && n == items.length / 4) {
            resize(items.length / 2);
        }

        return itemToReturn;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        return items[getRandomIndex()];
    }

    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private int getRandomIndex() {
        return StdRandom.uniform(n);
    }

    private void resize(int capacity) {
        Item[] newArray = (Item[]) new Object[capacity];

        for (int i = 0; i < n; i++) {
            newArray[i] = items[i];
        }

        items = newArray;
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        public RandomizedQueueIterator() {

            for (int i = 0; i < n; i++) {
                itemsToIterate[i] = items[i];
            }

            Knuth.shuffle(itemsToIterate);
        }

        private int current = n;

        @Override
        public boolean hasNext() {
            return current > 0;
        }

        private Item[] itemsToIterate = (Item[]) new Object[n];

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            return itemsToIterate[--current];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}