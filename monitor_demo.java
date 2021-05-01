import java.util.concurrent.Semaphore;

class Producer extends Thread{
    @Override
    public void run() {
        for (int i=0;i<100;i++){
            Buffer.criticalSection("increment");
        }
    }
}
class Consumer extends Thread{
    @Override
    public void run() {
        for (int i=0;i<100;i++){
            Buffer.criticalSection("decrement");
        }
    }
}
class Buffer{
    static int count = 0;

    public static synchronized void criticalSection(String operation){
        if (operation.equals("increment")) count++;
        else if (operation.equals("decrement")) count--;

    }
}
public class monitor_demo {

    public static Semaphore mutex = new Semaphore(1);
    public static void main(String[] args) throws InterruptedException {
        Producer p = new Producer();
        Consumer c = new Consumer();

        p.start();
        c.start();

        p.join();
        c.join();

        System.out.println("Buffer count: "+Buffer.count);
    }
}
