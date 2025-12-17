package dataStructures;

public class Stack<T> {

    private Node<T> top;
    private int size;

    public Stack() {
        top = null;
        size = 0;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return size;
    }

    public void push(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.setNext(top);
        top = newNode;
        size++;
    }

    public T pop() {
        if (top == null)
            return null;

        T popped = top.getData();
        top = top.getNext();
        size--;
        return popped;
    }

    public T peek() {
        return (top == null) ? null : top.getData();
    }

    public boolean delete(T value) {
        if (top == null) return false;

        if (top.getData().equals(value)) {
            top = top.getNext();
            size--;
            return true;
        }

        Node<T> current = top;
        while (current.getNext() != null && !current.getNext().getData().equals(value)) {
            current = current.getNext();
        }

        if (current.getNext() == null)
            return false;

        current.setNext(current.getNext().getNext());
        size--;
        return true;
    }

    public int search(T value) {
        Node<T> current = top;
        int index = 0;
        while (current != null) {
            if (current.getData().equals(value))
                return index;

            current = current.getNext();
            index++;
        }
        return -1;
    }

    public void display() {
        Node<T> current = top;
        int index = 0;
        while (current != null) {
            System.out.println("[" + index + "] " + current.getData());
            current = current.getNext();
            index++;
        }
    }
}