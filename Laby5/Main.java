package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        MageRepository repository = new MageRepository();
        MageController controller = new MageController(repository);


        String input = scanner.nextLine();
        while(!(input.equals("exit"))){
            String[] command = input.split(" ");
            switch (command[0]){
                case "find":
                    if(command.length > 1){
                        System.out.println(controller.find(command[1]));
                    }
                    else{
                        System.out.println("Not enough variables.");
                    }
                    break;
                case "delete":
                    if(command.length > 1) {
                        System.out.println(controller.delete(command[1]));
                    }
                    else{
                        System.out.println("Not enough variables.");
                    }
                    break;
                case "save":
                    if(command.length > 2) {
                        System.out.println(controller.save(command[1], command[2]));
                    }
                    else{
                        System.out.println("Not enough variables.");
                    }
                    break;
                default:
                    System.out.println("Wrong command");
                    break;
            }
            input = scanner.nextLine();
        }
        scanner.close();
    }
}
