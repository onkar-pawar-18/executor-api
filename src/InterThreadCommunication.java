public class InterThreadCommunication {
    public static void main(String[] args){
        Counter counter = new Counter();
        new Producer(counter);
        new Consumer(counter);
    }
}

class Counter{
    int value=0;
    boolean isValueSet = false;

    public synchronized void increment() throws InterruptedException {

        while (isValueSet) {
            wait();
        }
        value++;
        System.out.println("Producer : "+value);
        isValueSet=true;
        notify();
    }

    public synchronized void getValue() throws InterruptedException {
        while (!isValueSet) {
            wait();
        }
        System.out.println("Consumer : "+value);
        isValueSet=false;
        notify();
    }
}

class Producer implements Runnable{

    Counter counter;

    public Producer(Counter counter) {
        this.counter = counter;
        new Thread(this).start();
    }

    @Override
    public void run() {
        for (int i=1; i<=100; i++){
            try {
                counter.increment();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class Consumer implements Runnable{

    Counter counter;

    public Consumer(Counter counter) {
        this.counter = counter;
        new Thread(this).start();
    }

    @Override
    public void run() {
        for (int i=1; i<=100; i++){
            try {
                counter.getValue();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}