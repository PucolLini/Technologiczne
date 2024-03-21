package org.example;
import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static int PORT = 2137;
    public static void main(String[] args) throws IOException {
        //tworzenie serwera i klienta
        ServerSocket server = new ServerSocket(PORT);
        System.out.println("Sever started");

        ExecutorService executorService = Executors.newCachedThreadPool();

        try{
            while(true){
                //akceptuj wszystkie polaczenia z klientem
                Socket client = server.accept();
                System.out.println("Client connected");

                System.out.println("READY");
                //stworz nowy watek aby obsluzyc klienta
                executorService.execute(new ClientHandler(client));
            }
        } finally {
            executorService.shutdown();
            server.close();
        }
    }
}
