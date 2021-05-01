import java.util.concurrent.Semaphore;

class Producer extends Thread{
    @Override
    public void run() {
        for (int i=0;i<100;i++){
            try {
                mutex_demo.mutex.acquire();//down(mutex) ENTRY
                Buffer.criticalSection("increment");
                mutex_demo.mutex.release();//up(mutex) EXIT
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
class Consumer extends Thread{
    @Override
    public void run() {
        for (int i=0;i<100;i++){
            try {
                mutex_demo.mutex.acquire();//down(mutex) ENTRY
                Buffer.criticalSection("decrement");
                mutex_demo.mutex.release();//up(mutex) EXIT
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
class Buffer{
    static int count = 0;

    public static void criticalSection(String operation){
        if (operation.equals("increment")) count++;
        else if (operation.equals("decrement")) count--;

    }
}
public class mutex_demo {
    public static Semaphore mutex = new Semaphore(1);
    public static void main(String[] args) {
        Producer p = new Producer();
        Consumer c = new Consumer();

        p.start();
        c.start();

        p.join();
        c.join();

        System.out.println("Buffer count: "+Buffer.count);
    }
}
