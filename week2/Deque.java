import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Superman Petrovich on 2/12/14.
 */

public class Deque<Item> implements Iterable<Item> {
    private int n = 0;

    private Node firstNode = null;
    private Node lastNode = null;

    public Deque() {
    }

    public static void main(String[] args) {
       Deque<String> d = new Deque<String>();

       d.addLast("a");
       String d1 = d.removeFirst();
       d.addLast("b");
       String d2 = d.removeFirst();
       d.addLast("c");
       d.addFirst("d");
       d.addFirst("e");
       String d3 = d.removeFirst();
       String d4 = d.removeLast();
       d.addFirst("e1");
       d.addFirst("e2");
       d.addFirst("e3");
       d.addLast("e4");

       for(String item : d){
           StdOut.printf(item + ", ");
       }
    }

    public boolean isEmpty() {
        return firstNode == null;
    }

    public int size() {
        return n;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        Node newFirstNode = new Node();
        newFirstNode.item = item;

        if (isEmpty()) {
            lastNode = newFirstNode;
        }
        else {
            firstNode.previous = newFirstNode;
            newFirstNode.next = firstNode;
            newFirstNode.previous = null;
        }

        firstNode = newFirstNode;

        n++;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        Node newLastNode = new Node();
        newLastNode.item = item;

        if (isEmpty()) {
            firstNode = newLastNode;
        }
        else {
            lastNode.next = newLastNode;
            newLastNode.next = null;
            newLastNode.previous = lastNode;
        }

        lastNode = newLastNode;

        n++;
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        n--;

        Item item = firstNode.item;

        if(n > 0){
            firstNode = firstNode.next;
            firstNode.previous = null;
        }
        else{
            firstNode = null;
            lastNode = null;
        }

        return item;
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        n--;

        Item item = lastNode.item;

        if(n > 0){
            lastNode = lastNode.previous;
            lastNode.next = null;
        }
        else{
            firstNode = null;
            lastNode = null;
        }

        return item;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Item item = current.item;

            current = current.next;

            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        private Node current = firstNode;
    }

    private class Node {
        private Item item;
        private Node next;
        private Node previous;
    }
}