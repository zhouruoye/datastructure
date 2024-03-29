package com.cest.queue.loopqueue;


/**
 * Created by Administrator on 2019/7/6.
 */
public class LoopQueue<E> implements Queue<E>{

    private E[] data;
    private int front,tail;
    private int size;

    //容量加1 防止有数据的时候front 和 tail 相等
    public LoopQueue(int capacity) {
        data = (E[])new Object[capacity + 1];
        front = 0;
        tail = 0;
        size = 0;
    }

    public LoopQueue() {
        this(10);
    }

    //获取容量
    public int getCapacity(){
        return data.length - 1;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return front == tail;
    }

    @Override
    public void enqueue(E e) {

        //    如果满了 tail + 1取余数 == front  则需要扩容
        if((tail + 1) % data.length == front){
            reSize(getCapacity() * 2);
        }

        data[tail] = e;

        tail = (1 + tail) % data.length;

        size ++;
    }

    @Override
    public E dequeue() {

        if(isEmpty()){
            throw new IllegalArgumentException("Cannot dequeue from an empty queue.");
        }

        E msg = data[front];

        front = (front + 1) % data.length;

        size --;

        if(size == getCapacity() / 4 && getCapacity() / 2 != 0){
            reSize(getCapacity() / 2);
        }

        return msg;
    }

    @Override
    public E getFront() {
        if(isEmpty()){
            throw new IllegalArgumentException("Queue is empty.");
        }

        return data[front];
    }

    public void reSize(int newCapacity){
        E[] dataNew = (E[])new Object[newCapacity];

        for (int i = 0; i < size; i++) {
            dataNew[i] = data[(i + front) % data.length];
        }

        data = dataNew;
        front = 0;
        tail = size;
    }

    @Override
    public String toString(){

        StringBuilder res = new StringBuilder();
        res.append(String.format("Queue: size = %d , capacity = %d\n", size, getCapacity()));
        res.append("front [");
        for(int i = front ; i != tail ; i = (i + 1) % data.length){
            res.append(data[i]);
            if((i + 1) % data.length != tail)
                res.append(", ");
        }
        res.append("] tail");
        return res.toString();
    }

    public static void main(String[] args){

        LoopQueue<Integer> queue = new LoopQueue<>();
        for(int i = 0 ; i < 10 ; i ++){
            queue.enqueue(i);
            System.out.println(queue);

            if(i % 3 == 2){
                queue.dequeue();
                System.out.println(queue);
            }
        }
    }


}
