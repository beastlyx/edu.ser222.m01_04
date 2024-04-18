package edu.ser222.m01_03;

/**
 * CompletedOrderedList represents an implementation of an ordered list that builds on
 * CompletedList.
 *
 * @author Borys Banaszkiewicz, Acuna
 * @version 1.0
 */
public class CompletedOrderedList<T extends Comparable<T>> extends CompletedList<T>
         implements OrderedListADT<T> {
   
    private DoubleLinearNode<T> list;
    
    public CompletedOrderedList() {
        list = head;
    }
    
    /**
     * Adds the specified element to this collection at the proper location
     *
     * @param element the element to be added to this collection
     * @throws NullPointerException if element is null
     */
    @Override
    public void add(T element) throws NullPointerException {
        if (element == null) {
            throw new NullPointerException();
        }
        
        DoubleLinearNode<T> node_to_add = new DoubleLinearNode<>(element);

        if (head == null) {
            head = node_to_add;
            tail = node_to_add;
        }
        else {
            DoubleLinearNode<T> iter = head;
            while (iter != null && element.compareTo(iter.getElement()) >= 0) {
                iter = iter.getNext();
            }
        
            if (iter == null) {
                tail.setNext(node_to_add);
                node_to_add.setPrevious(tail);
                tail = node_to_add;
            }
            else if (iter.getPrevious() == null) {
                node_to_add.setNext(head);
                head.setPrevious(node_to_add);
                head = node_to_add;
            } else {
                DoubleLinearNode<T> previous = iter.getPrevious();
        
                previous.setNext(node_to_add);
                node_to_add.setPrevious(previous);
                node_to_add.setNext(iter);
                iter.setPrevious(node_to_add);
            }
        }
        
        count++;
        modChange++;
    }
}