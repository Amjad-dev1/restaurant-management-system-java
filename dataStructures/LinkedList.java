package dataStructures;

public class LinkedList<T> {

    private Node<T> head;
    private int size;

    public int getSize() {
		return size;
	}

	public LinkedList() {
        this.head = null;
        this.size = 0;
    }

    public int size() {
        return size;
    }

    public void insert(T data) {
        Node<T> newNode = new Node<>(data);

        if (head == null) {
            head = newNode;
        } else {
            Node<T> current = head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(newNode);
        }
        size++;
    }

    public void insertAt(int index, T data) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("Invalid index");

        Node<T> newNode = new Node<>(data);

        if (index == 0) {
            newNode.setNext(head);
            head = newNode;
        } else {
            Node<T> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.getNext();
            }
            newNode.setNext(current.getNext());
            current.setNext(newNode);
        }
        size++;
    }

    public boolean deleteAt(int index) {
        if (index < 0 || index >= size)
            return false;

        if (index == 0) {
            head = head.getNext();
        } else {
            Node<T> current = head;
            for (int i = 0; i < index - 1; i++)
                current = current.getNext();

            current.setNext(current.getNext().getNext());
        }
        size--;
        return true;
    }

    public boolean delete(T value) {
        if (head == null)
            return false;

        if (head.getData().equals(value)) {
            head = head.getNext();
            size--;
            return true;
        }

        Node<T> current = head;
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
        Node<T> current = head;
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
        Node<T> current = head;
        int index = 0;
        while (current != null) {
            System.out.println(index + " -> " + current.getData());
            current = current.getNext();
            index++;
        }
    }

    public T get(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Invalid index");

        Node<T> current = head;
        for (int i = 0; i < index; i++)
            current = current.getNext();

        return current.getData();
    }
}