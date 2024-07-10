import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T> {
    public Node sentinel;
    public int size;

    public LinkedListDeque61B(){
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        sentinel.item = null;
        size = 0;
    }

    @Override
    public void addFirst(T x) {
        if(size==0){
            Node n = new Node(null, null, null);
            n.item = x;
            sentinel.next = n;
            sentinel.prev = n;
            n.next = sentinel;
            n.prev = sentinel;
            size=1;
        }
        else{
            Node n = new Node(null, null, null);
            n.item = x;
            n.next = sentinel.next;
            sentinel.next.prev = n;
            n.prev = sentinel;
            sentinel.next = n;
            size++;
        }
    }

    @Override
    public void addLast(T x) {
        if(size==0){
            Node n = new Node(null, null, null);
            n.item = x;
            sentinel.next = n;
            sentinel.prev = n;
            n.next = sentinel;
            n.prev = sentinel;
            size=1;
        }
        else{
            Node n = new Node(null, null, null);
            n.item = x;
            n.prev = sentinel.prev;
            sentinel.prev.next = n;
            sentinel.prev = n;
            n.next = sentinel;
            size++;
        }
    }

    @Override
    public List<T> toList() {
        List<T> l = new ArrayList<T>();
        Node temp = sentinel.next;
        for(int i=0;i<size;i++){
            l.add(temp.item);
            temp = temp.next;
        }
        return l;
    }

    @Override
    public boolean isEmpty() {
        if(size==0){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if(size==0){
            return null;
        }
        else if(size==1){
            T x =  sentinel.next.item;
            sentinel.next = sentinel;
            sentinel.prev = sentinel;
            size = 0;
            return x;
        }
        else{
            T x =  sentinel.next.item;
            sentinel.next.next.prev = sentinel;
            sentinel.next = sentinel.next.next;
            size--;
            return x;
        }
    }

    @Override
    public T removeLast() {
        if(size==0){
            return null;
        }
        else if(size==1){
            T x =  sentinel.next.item;
            sentinel.next = sentinel;
            sentinel.prev = sentinel;
            size = 0;
            return x;
        }
        else{
            T x =  sentinel.prev.item;
            sentinel.prev.prev.next = sentinel;
            sentinel.prev = sentinel.prev.prev;
            size--;
            return x;
        }
    }

    @Override
    public T get(int index) {
        if(index<0||index>=size){
            return null;
        }
        else{
            int i = 0;
            Node temp = sentinel.next;
            while(i<index){
                temp = temp.next;
                i++;
            }
            return temp.item;
        }
    }

//    @Override
//    public T getRecursive(int index) {
//        if(this is the first time to call recursive){
//            LinkedListDeque61B<T> temp = a copy of this.
//        }
//        if(index<0||index>=size){
//            return null;
//        }
//        if(index==0){
//            return sentinel.item;
//        }
//        else{
//            temp.removeFirst();
//            return getRecursive(index-1);
//        }
//    }


//    @Override
//    public T getRecursive(int index) {
//        return getRecursive(index, this);
//    }
//
//    private T getRecursive(int index, LinkedListDeque61B<T> temp) {
//        if (index < 0 || index >= temp.size) {
//            return null;
//        }
//        if (index == 0) {
//            return temp.sentinel.next.item;
//        } else {
//            temp.removeFirst();
//            return getRecursive(index - 1, temp);
//        }
//    }

    @Override
    public T getRecursive(int index) {
        return getRecursive(sentinel.next, index);
    }

    private T getRecursive(Node node, int index) {
        if (index < 0 || node == sentinel) {
            return null;
        }
        if (index == 0) {
            return node.item;
        }
        return getRecursive(node.next, index - 1);
    }


    public class Node{ //don't need to use generic in inner class
        public Node prev;
        public T item;
        public Node next;

        public Node(Node p,T i,Node n){
            prev = p;
            item = i;
            next = n;
        }
    }
}

