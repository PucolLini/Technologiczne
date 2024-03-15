package org.example;
import javafx.util.Pair;

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        if (args.length == 0) {
            System.out.println("Podaj liczbę wątków jako argument startowy.");
            System.exit(1); // Wyjście z programu z kodem błędu 1
        }
        int numberOfThreads = Integer.parseInt(args[0]);

        Scanner console = new Scanner(System.in);
        Scanner scanner = new Scanner(new File("src/test_1_watki.txt"));

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
            String input = console.nextLine();
            //System.out.println("Podaj liczbę lub exit, aby zakończyć:");
            if (scanner.hasNextLong() && !input.equalsIgnoreCase("exit")) {
                long taskFromFile = scanner.nextLong();
                taskQueue.addTask(taskFromFile);
            } else {
                //czytamy z konsoli
                //String input = console.nextLine();
                if (input.equalsIgnoreCase("exit")) {
                    running = false;
                    //taskQueue.setClosing(true);
                    //taskQueue.interruptAll();
                    // Przebudzenie wątków, które mogły być uśpione w oczekiwaniu na zadanie
                    //synchronized (taskQueue) {
                    //    taskQueue.notifyAll();
                    //}

                } else {
                    try {
                        long taskFromConsole  = Long.parseLong(input);
                        taskQueue.addTask(taskFromConsole);
                    } catch (NumberFormatException e) {
                        System.out.println("Niepoprawne zadanie.");
                    }
                }
            }
        }

        for (Thread thread : threads) {
            thread.interrupt();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //resultQueue.displayResults();
        PrintWriter writer = new PrintWriter("wyniki.txt");
        resultQueue.displayResultsInFile(writer);
        writer.close();

        System.exit(0);
    }
}
