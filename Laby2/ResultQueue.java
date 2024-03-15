package org.example;

import javafx.util.Pair;

import java.util.LinkedList;
import java.util.Queue;

// Klasa reprezentująca współdzielony zasób do zbierania wyników
public class ResultQueue {
    private static Queue<Pair<Integer, Boolean>> results = new LinkedList<>();

    // Metoda do dodawania wyniku
    public synchronized void addResult(int task, boolean result) {
        results.add(new Pair<>(task, result));
    }
    public synchronized Pair<Integer, Boolean> getResult() {
        return results.poll();
    }

    public synchronized boolean isEmpty() {
        return results.isEmpty();
    }
    public static void displayResults() {
        while (!results.isEmpty()) {
            Pair<Integer, Boolean> result = results.poll();
            System.out.println("Liczba " + result.getKey() + (result.getValue() ? " jest pierwsza." : " nie jest pierwsza."));
        }
    }
}
