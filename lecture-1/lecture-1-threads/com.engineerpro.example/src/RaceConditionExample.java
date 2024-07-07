import java.util.ArrayList;
import java.util.List;

public class RaceConditionExample {
    private static int TRANSACTIONS_PER_MACHINE = 1000;
    private static int balance = 0;

    private static void deposit() {
        balance = balance + 1;
        System.out.println("+, balance=" + balance);
    }

    private static void withdraw() {
        balance = balance - 1;
        System.out.println("-, balance=" + balance);
    }

    public static void main(String[] args) throws Exception {
        List<Thread> allThreads = new ArrayList<>();
        for (int i = 1; i <= TRANSACTIONS_PER_MACHINE; i++) {
            Thread t = new Thread(new Runnable() {
                public void run() {
                    // increase balance by 1
                    deposit();
                    // decrease balance by 1
                    withdraw();
                }
            });
            t.start();
            allThreads.add(t);
        }
        for (Thread t: allThreads) {
            t.join();
        }
        System.out.println("balance=" + balance);
    }
}
