package edu.ser222.m01_03;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

/**
 * CompletedList represents an implementation of a list.
 *
 * @author Borys Banaszkiewicz, Acuna
 * @version 1.0
 */
public class CompletedList<T> implements ListADT<T>, Iterable<T> {
    
    public class DoubleLinearNode<T> {
        
        private DoubleLinearNode<T> next;
        private DoubleLinearNode<T> prev;
        private T val;
        
        public DoubleLinearNode() {
            this.next = null;
            this.prev = null;
            this.val = null;
        }
        
        public DoubleLinearNode(T element) {
            this.next = null;
            this.prev = null;
            this.val = element;
        }
        
        public DoubleLinearNode<T> getNext() {
            return this.next;
        }
        
        public void setNext(DoubleLinearNode<T> node) {
            this.next = node;
        }
        
        public DoubleLinearNode<T> getPrevious() {
            return this.prev;
        }
        
        public void setPrevious(DoubleLinearNode<T> node) {
            this.prev = node;
        }
        
        public T getElement() {
            return this.val;
        }
        
        public void setElement(T element) {
            this.val = element;
        }
    }
    //The following three variables are a suggested start if you are using a list implementation.
    protected int count;
    protected int modChange;
    protected DoubleLinearNode<T> head;
    protected DoubleLinearNode<T> tail;

    //TODO: implement this!
    
    public CompletedList() {
        this.count = 0;
        this.modChange = 0;
        this.head = null;
        this.tail = null;
    }
    /**  
     * Removes and returns the first element from this collection.
     * 
     * @return the first element from this collection
     * @throws NoSuchElementException if the collection is empty
     */
    @Override
    public T removeFirst() throws NoSuchElementException {
        if (this.head == null) {
            throw new NoSuchElementException("Cannot remove, collection is empty");
        }
        
        T removed_element = this.head.getElement();
        
        this.head = this.head.getNext();
        
        if (this.head == null) {
            this.tail = null;
        } else {
            this.head.setPrevious(null);
        }
        
        this.modChange++;
        this.count--;
        
        return removed_element;
    }

    /**  
     * Removes and returns the last element from this collection.
     *
     * @return the last element from this collection
     * @throws NoSuchElementException if the collection is empty
     */
    @Override
    public T removeLast() throws NoSuchElementException {
        if (this.tail == null) {
            throw new NoSuchElementException("Cannot remove, collection is empty");
        }
        
        T removed_element = this.tail.getElement();
        
        this.tail = this.tail.getPrevious();

        if (this.tail == null) {
            this.head = null;
        } else {
            this.tail.setNext(null);
        }
        
        this.modChange++;
        this.count--;
        
        return removed_element;
    }

    /**  
     * Removes and returns the specified element from this collection.
     *
     * @param element the element to be removed from the collection
     * @throws NoSuchElementException if the target is not in the collection
     */
    @Override
    public T remove(T element) {
        
        if (this.head == null) {
            throw new NoSuchElementException("Collection is empty");
        }
                
        DoubleLinearNode<T> iter_node = this.head;
        
        while (iter_node != null && !iter_node.getElement().equals(element)) {
            iter_node = iter_node.getNext();
        }
        
        if (iter_node == null) {
            throw new NoSuchElementException("Element not found");
        }
        
        if (iter_node == this.head) {
            return removeFirst();
        }
        else if (iter_node == this.tail) {
            return removeLast();
        } 
        else {
            DoubleLinearNode<T> previous = iter_node.getPrevious();
            DoubleLinearNode<T> next = iter_node.getNext();
            previous.setNext(next);
            next.setPrevious(previous);
            this.modChange++;
            this.count--;

            return iter_node.getElement();
        }
    }

    /**  
     * Returns, without removing, the first element in the collection.
     *
     * @return a reference to the first element in this collection
     * @throws NoSuchElementException if the collection is empty
     */
    @Override
    public T first() {
        if (this.head == null) {
            throw new NoSuchElementException("Collection is empty");
        }
        return this.head.getElement();
    }

    /**  
     * Returns, without removing, the last element in the collection.
     *
     * @return a reference to the last element in this collection
     * @throws NoSuchElementException if the collection is empty
     */
    @Override
    public T last() {
        if (this.tail == null) {
            throw new NoSuchElementException("Collection is empty");
        }
        return this.tail.getElement();
    }

    /**  
     * Returns true if this collection contains the specified target element, false otherwise.
     *
     * @param target the target that is being sought in the collection
     * @return true if the collection contains this element
     */
    @Override
    public boolean contains(T target) {
        DoubleLinearNode<T> iter_node = this.head;
        
        while (iter_node != null) {
            if (iter_node.getElement() == target) {
                return true;
            }
            iter_node = iter_node.getNext();
        }

        return false;
    }

    /**  
     * Returns true if this collection is empty and false otherwise.
     *
     * @return true if this collection empty
     */
    @Override
    public boolean isEmpty() {
        return this.count == 0;
    }

    /**  
     * Returns the number of elements in this collection.
     *
     * @return the number of elements in this collection
     */
    @Override
    public int size() {
        return this.count;
    }

    /**  
     * Returns an iterator for the elements in this collection. The returned object must have implementations of hasNext() and next() that throw ConcurrentModificationException when the contents of the list change. 
     *
     * @return an iterator over the elements in this collection
     */
    @Override
    public Iterator<T> iterator() {
        return new ListIterator();
    }

    /**  
     * Returns a string representation of this collection. If the list is empty, returns "empty".
     *
     * @return a string representation of this collection
     */
    @Override
    public String toString() {
        if (this.head == null) {
            return "empty";
        }
        
        DoubleLinearNode<T> curr = this.head;
        
        StringBuilder str = new StringBuilder();
        
        while (curr != null) {
            str.append(curr.getElement()).append(" ");
            curr = curr.getNext();
        }
        
        return str.toString();
    }
    

    private class ListIterator implements Iterator<T> {
        
        private final int mod_count = modChange;
        private DoubleLinearNode<T> list = head;

        @Override
        public boolean hasNext() {
            if (mod_count != modChange) {
                throw new ConcurrentModificationException();
            }
            
            return list != null;
        }
        
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            T element = list.getElement();
            list = list.getNext();
            
            return element;
        }
        
        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
    }
}