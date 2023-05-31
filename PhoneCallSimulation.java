import java.util.concurrent.Semaphore;

class PhoneOperator {
    private final Semaphore lines = new Semaphore(2);
    private final Semaphore ladies = new Semaphore(2);

    public void makeCall(String caller, String receiver) throws InterruptedException {
        // Acquire a lady
        ladies.acquire();
        
        // Acquire a line
        lines.acquire();
        
        // Make the call
        System.out.println(caller + " is talking to " + receiver);
        Thread.sleep(2000); // Simulating the call
        
        // Release the line and lady
        lines.release();
        ladies.release();
    }
}

class PhoneCallThread extends Thread {
    private final String caller;
    private final String receiver;
    private final PhoneOperator operator;

    public PhoneCallThread(String caller, String receiver, PhoneOperator operator) {
        this.caller = caller;
        this.receiver = receiver;
        this.operator = operator;
    }

    @Override
    public void run() {
        try {
            operator.makeCall(caller, receiver);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class PhoneCallSimulation {
    public static void main(String[] args) throws InterruptedException {
        PhoneOperator operator = new PhoneOperator();

        String[] callers = {"Mehmet", "Ali", "Ahmet", "Hasan", "Mustafa", "Ibrahim"};
        String[] receivers = {"Leyla", "Fatma", "Zeynep", "Aysel", "Elif", "Dilara"};

        for (int i = 0; i < callers.length; i++) {
            for (int j = 0; j < receivers.length; j++) {
                PhoneCallThread thread = new PhoneCallThread(callers[i], receivers[j], operator);
                thread.start();
                thread.join(); // Wait for each call to finish
            }
        }
    }
}
