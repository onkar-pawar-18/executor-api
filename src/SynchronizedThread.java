public class SynchronizedThread {
    public static void main(String[] args) throws InterruptedException {
        Count count = new Count();

        Thread thread1 = new Thread(() -> {
            for (int i = 1; i <= 1000; i++) {
                count.increment();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 1; i <= 1000; i++) {
                count.increment();
            }
        });

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(count.value);
    }
}

class Count {
    int value=0;

    public synchronized void increment(){
        value++;
    }
}