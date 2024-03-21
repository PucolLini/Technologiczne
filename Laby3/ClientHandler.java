package org.example;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private final Socket client;

    ClientHandler(Socket client){
        this.client=client;
    }

    @Override
    public void run() {
        try{
            handleClient(client);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void handleClient(Socket client) throws IOException{
        try {
            //stworzenie klasy do obierania wiadomosci klienta
            ObjectInputStream inputStream = new ObjectInputStream(client.getInputStream());

            int numberOfMessages = inputStream.readInt();
            System.out.println("Ready for messages");

            for (int i = 0; i < numberOfMessages; i++) {
                Message message = (Message) inputStream.readObject();
                System.out.println("Received from " + client.getInetAddress().getHostAddress() + ":" + client.getPort() + " message: " + message.getNumber() + " -> " + message.getContent());
            }
            inputStream.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            client.close();
            System.out.println("Finished");
        }
    }
}
