package org.example;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Podaj liczbę wątków jako argument startowy.");
            System.exit(1); // Wyjście z programu z kodem błędu 1
        }
        int numberOfThreads = Integer.parseInt(args[0]);

        Scanner scanner = new Scanner(System.in);

        TaskQueue taskQueue = new TaskQueue();
        ResultQueue resultQueue = new ResultQueue();

        Thread[] threads = new Thread[numberOfThreads];
        for (int i = 0; i < numberOfThreads; i++) {
            threads[i] = new Thread(new CalculationWorker(taskQueue, resultQueue));
        }

        // Uruchomienie wątków po przeczytaniu liczby wątków
        for (Thread thread : threads) {
            thread.start();
        }

        int activeThreads = numberOfThreads;
        // Obsługa zgłaszania zadań i zamykania aplikacji
        boolean running = true;
        while (running) {
            System.out.println("Podaj zadanie (liczbę do sprawdzenia, lub 'exit' aby zakończyć):");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                running = false;
                taskQueue.setClosing(true);
                taskQueue.interruptAll();
                // Przebudzenie wątków, które mogły być uśpione w oczekiwaniu na zadanie
                synchronized (taskQueue) {
                    taskQueue.notifyAll();
                }
            } else {
                try {
                    int task = Integer.parseInt(input);
                    taskQueue.addTask(task);
                } catch (NumberFormatException e) {
                    System.out.println("Niepoprawne zadanie.");
                }
            }
        }
        while (activeThreads > 0) {
            activeThreads = 0;
            for (Thread thread : threads) {
                thread.interrupt();
                if (thread.isAlive()) {
                    activeThreads++;
                }
            }
            try {
                Thread.sleep(100); // Aby dać czas wątkom na reakcję na przerwanie
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        resultQueue.displayResults();
        System.exit(0);
    }
}
