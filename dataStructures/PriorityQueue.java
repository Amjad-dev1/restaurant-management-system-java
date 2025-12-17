package dataStructures;

import java.util.Comparator;
import java.util.Iterator;

public class PriorityQueue<T> implements Iterable<T> {

    private Node<T> head; 
    private int size;
    private Comparator<T> comparator;

    public PriorityQueue(Comparator<T> comparator) {
        this.comparator = comparator;
        this.head = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void add(T element) {
        Node<T> newNode = new Node<>(element);

        if (head == null || comparator.compare(element, head.getData()) < 0) {
            newNode.setNext(head);
            head = newNode;
        } else {
            Node<T> current = head;
            while (current.getNext() != null &&
                   comparator.compare(element, current.getNext().getData()) >= 0) {
                current = current.getNext();
            }
            newNode.setNext(current.getNext());
            current.setNext(newNode);
        }

        size++;
    }

    public T poll() {
        if (isEmpty())
            return null;

        T removed = head.getData();
        head = head.getNext();
        size--;
        return removed;
    }

    public boolean remove(T element) {
        if (isEmpty())
            return false;

        if (head.getData().equals(element)) {
            head = head.getNext();
            size--;
            return true;
        }

        Node<T> current = head;
        while (current.getNext() != null) {
            if (current.getNext().getData().equals(element)) {
                current.setNext(current.getNext().getNext());
                size--;
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Node<T> curr = head;

            public boolean hasNext() {
                return curr != null;
            }

            public T next() {
                T data = curr.getData();
                curr = curr.getNext();
                return data;
            }
        };
    }
}
