package org.example;

import java.util.LinkedList;
import java.util.Queue;

// Klasa reprezentująca współdzielony zasób do zgłaszania zadań
public class TaskQueue {
    private Queue<Long> tasks = new LinkedList<>();
    private boolean closing = false;
    // Metoda do dodawania zadania
    public synchronized void addTask(long task) {
        tasks.add(task);
        notify();
    }

    // Metoda do pobierania zadania
    public synchronized long getTask() throws InterruptedException {
        while (tasks.isEmpty()) {
            if (closing) {
               System.out.println("zamyka się");
            }
            wait();
        }
        return tasks.poll();
    }

/*    public synchronized void setClosing(boolean closing) {
        this.closing = closing;
    }

    public synchronized void interruptAll() {
        for (Thread t : Thread.getAllStackTraces().keySet()) {
            if (t.getState() == Thread.State.WAITING) {
                t.interrupt();
            }
        }
    }*/

}