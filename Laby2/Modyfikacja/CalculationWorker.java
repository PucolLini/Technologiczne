package org.example;

import java.util.LinkedList;

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
                long task = taskQueue.getTask();
                // Symulacja złożonych obliczeń
                Thread.sleep(1000);
                //boolean isPrime = isPrime(task); // Sprawdzenie czy liczba jest pierwsza
                //resultQueue.addResult(task, isPrime);
                LinkedList<Long> dividersList = Divider(task);
                resultQueue.addResult(task, dividersList);
                System.out.println("Wątek " + Thread.currentThread().getId() + " wziął taska");
            } catch (InterruptedException e) {
                System.out.println("Wątek " + Thread.currentThread().getId() + " został zatrzymany.");
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    private LinkedList<Long> Divider(long number){
        LinkedList<Long> result = new LinkedList<>();
        for (long i = 1; i <= number; i++) {
            if (number % i == 0) {
                result.add(i);
            }
        }
        return result;
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