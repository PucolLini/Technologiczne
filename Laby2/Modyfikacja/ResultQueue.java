package org.example;

import javafx.util.Pair;

import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;

// Klasa reprezentująca współdzielony zasób do zbierania wyników
public class ResultQueue {
    private static Queue<Pair<Long, LinkedList<Long>>> results = new LinkedList<>();

    public synchronized void addResult(long task, LinkedList<Long> result) {
        results.add(new Pair<>(task, result));
    }

    public static void displayDividers(LinkedList<Long> dividers){
        for(long divider : dividers){
            System.out.print(divider + ", ");
        }
    }

    public static void displayResults() {
        while (!results.isEmpty()) {
            Pair<Long, LinkedList<Long>> result = results.poll();
            System.out.print(result.getKey() + ": ");
            displayDividers(result.getValue());
            System.out.println();
        }
    }

    public synchronized void displayResultsInFile(PrintWriter writer) {
        while (!results.isEmpty()) {
            Pair<Long, LinkedList<Long>> result = results.poll();
            writer.print(result.getKey() + ": ");
            displayDividers(result.getValue(), writer);
            writer.println();
        }
    }

    private void displayDividers(LinkedList<Long> dividers, PrintWriter writer) {
        for (Long divider : dividers) {
            writer.print(divider + ", ");
        }
    }

}
