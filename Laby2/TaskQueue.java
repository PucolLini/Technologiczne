package org.example;

import java.util.LinkedList;
import java.util.Queue;

// Klasa reprezentująca współdzielony zasób do zgłaszania zadań
public class TaskQueue {
    private Queue<Integer> tasks = new LinkedList<>();
    private boolean closing = false;
    // Metoda do dodawania zadania
    public synchronized void addTask(int task) {
        tasks.add(task);
        notify();
    }

    // Metoda do pobierania zadania
    public synchronized int getTask() throws InterruptedException {
        while (tasks.isEmpty()) {
            System.out.println("Czeka");
            if (closing) {
               System.out.println("zamyka się");
            }
            wait();
        }
        System.out.println("Bierze taska");
        return tasks.poll();
    }

    public synchronized void setClosing(boolean closing) {
        this.closing = closing;
    }

    public synchronized void interruptAll() {
        for (Thread t : Thread.getAllStackTraces().keySet()) {
            if (t.getState() == Thread.State.WAITING) {
                t.interrupt();
            }
        }
    }

}