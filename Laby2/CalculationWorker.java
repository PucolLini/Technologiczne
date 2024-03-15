package org.example;

// Klasa realizująca obliczenia w oddzielnym wątku
public class CalculationWorker implements Runnable {
    private TaskQueue taskQueue;
    private ResultQueue resultQueue;

    public CalculationWorker(TaskQueue taskQueue, ResultQueue resultQueue) {
        this.taskQueue = taskQueue;
        this.resultQueue = resultQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                int task = taskQueue.getTask();
                // Symulacja złożonych obliczeń
                Thread.sleep(1000);
                // Tutaj można umieścić właściwe obliczenia
                boolean isPrime = isPrime(task); // Sprawdzenie czy liczba jest pierwsza
                resultQueue.addResult(task, isPrime);
                System.out.println("Wątek " + Thread.currentThread().getId());
                /*if(isPrime){
                    System.out.println("Liczba " + task + " jest pierwsza");

                    resultQueue.addResult(1);
                }
                else{
                    System.out.println("Liczba " + task + " nie jest pierwsza");
                    resultQueue.addResult(0);
                }*/
                System.out.println("Dodano wynik taska");
            } catch (InterruptedException e) {
                System.out.println("Wątek " + Thread.currentThread().getId() + " został zatrzymany.");
                Thread.currentThread().interrupt();
                //e.printStackTrace();
                return;
            }
        }
    }

    private boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
}