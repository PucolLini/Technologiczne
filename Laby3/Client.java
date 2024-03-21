package org.example;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket client = new Socket("localhost", 2137);

        //tworzenie klasy wiadomosci klienta
        ObjectOutputStream outputStream = new ObjectOutputStream(client.getOutputStream());

        Scanner scanner = new Scanner(System.in);

        boolean validInput = false;
        int n = 0;

        while (!validInput) {
            try {
                System.out.println("Enter the number of messages you want to send: ");
                n = scanner.nextInt();
                validInput = true; // Mark input as valid if no exception is thrown
            } catch (Exception e) {
                // If an exception occurs, it means the input is not an integer
                System.out.println("Invalid input. Enter an integer.");
                scanner.nextLine(); // Consume the invalid input
            }
        }

        scanner.nextLine(); //usuniecie '\n' z buffera scannera

        outputStream.writeInt(n); //wyslanie wiadomosci o typie Int
        outputStream.flush(); //usuwanie wiadomosci z pamieci

        for(int i=0; i<n;i++){
            String input = scanner.nextLine();
            Message message = new Message(i, input);
            outputStream.writeObject(message);
            outputStream.flush();
        }

        System.out.println("Sent all " + n + " messages.");
        outputStream.close();
        scanner.close();
        client.close();
    }
}
