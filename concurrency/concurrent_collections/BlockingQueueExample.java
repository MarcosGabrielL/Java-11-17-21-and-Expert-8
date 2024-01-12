package java8.Expert.and.Java70.cursoJavaAvancado.concurrency.concurrent_collections;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class BlockingQueueExample {
    public static void main(String[] args) {
        BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        // regular Queue methods
        queue.offer("Red");
        queue.offer("Green");
        queue.offer("Blue");
        System.out.println(queue.poll());   // Red
        System.out.println(queue.peek());   // Green 
        System.out.println(queue);          // [Green, Blue]
        
        // special BlockingQueue methods
        try {
            // block is queue full...
            queue.offer("White", 100, TimeUnit.MILLISECONDS);
            // block is queue empty...
            queue.poll(200, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println(queue);          // [Blue, White]
        
    }
}

