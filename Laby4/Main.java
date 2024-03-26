package org.example;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int option = 7;

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myPersistenceUnit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        do{
            System.out.println("MENU");
            System.out.println("1. Dodaj nowy wpis do bazy");
            System.out.println("2. Usuń wpis z bazy");
            System.out.println("3. Wyświetl wszystkie wpisy z bazy");
            System.out.println("4. Pobierz wszystkich magów z poziomem większym niż X");
            System.out.println("5. Pobierz wszystkich wież niższych niż X");
            System.out.println("6. Pobierz wszystkich magów z poziomem wyższym niż z dane wieży");
            System.out.println("7. Wyjdz");
            System.out.println("WYBIERZ OPCJE: ");

            option = scanner.nextInt();
            scanner.nextLine(); // Consuming newline character

            switch (option) {
                case 1:
                    System.out.println("1. Dodaj wieżę");
                    System.out.println("2. Dodaj maga");
                    int subOption = scanner.nextInt();
                    scanner.nextLine(); // Consuming newline character
                    if (subOption == 1) {
                        System.out.print("Podaj nazwę wieży: ");
                        String towerName = scanner.nextLine();

                        System.out.print("Podaj wysokość wieży: ");
                        int towerHeight = scanner.nextInt();

                        Tower tower = new Tower(towerName, towerHeight);

                        entityManager.getTransaction().begin();
                        entityManager.persist(tower);
                        entityManager.getTransaction().commit();

                        System.out.println("Dodano nową wieżę - " + tower.getName() + " - do bazy.");

                    } else if (subOption == 2) {
                        System.out.print("Podaj imię maga: ");
                        String mageName = scanner.nextLine();

                        System.out.print("Podaj poziom maga: ");
                        int mageLevel = scanner.nextInt();

                        scanner.nextLine(); // Consuming newline character

                        System.out.print("Podaj nazwę wieży, do której należy mag: ");
                        String towerName = scanner.nextLine();
                        Tower tower = entityManager.find(Tower.class, towerName);

                        if (tower != null) {
                            Mage mage = new Mage(mageName, mageLevel, tower);

                            entityManager.getTransaction().begin();
                            entityManager.persist(mage);
                            entityManager.getTransaction().commit();

                            System.out.println("Dodano nowego maga - " + mage.getName() + " - do bazy.");
                        } else {
                            System.out.println("Nie znaleziono wieży o podanej nazwie.");
                        }
                    } else {
                        System.out.println("Nieprawidłowa opcja.");
                    }
                    break;
                case 2:
                    // Obsługa usuwania wpisu z bazy
                    System.out.println("1. Usuń wieżę");
                    System.out.println("2. Usuń maga");
                    subOption = scanner.nextInt();
                    scanner.nextLine(); // Consuming newline character

                    if (subOption == 1) {
                        System.out.print("Podaj nazwę wieży do usunięcia: ");
                        String towerNameToDelete = scanner.nextLine();

                        Tower towerToDelete = entityManager.find(Tower.class, towerNameToDelete);

                        if (towerToDelete != null) {
                            entityManager.getTransaction().begin();
                            entityManager.remove(towerToDelete);
                            entityManager.getTransaction().commit();

                            System.out.println("Usunięto wieżę - " + towerNameToDelete + " - z bazy danych.");
                        } else {
                            System.out.println("Nie znaleziono wieży o podanej nazwie.");
                        }
                    } else if (subOption == 2) {
                        System.out.print("Podaj nazwę maga do usunięcia: ");
                        String mageNameToDelete = scanner.nextLine();

                        Mage mageToDelete = entityManager.find(Mage.class, mageNameToDelete);

                        if (mageToDelete != null) {
                            entityManager.getTransaction().begin();
                            entityManager.remove(mageToDelete);
                            entityManager.getTransaction().commit();

                            System.out.println("Usunięto maga - " + mageNameToDelete + " - z bazy danych.");
                        } else {
                            System.out.println("Nie znaleziono maga o podanej nazwie.");
                        }
                    } else {
                        System.out.println("Nieprawidłowa opcja.");
                    }
                    break;
                case 3:
                    // Obsługa wyświetlania wszystkich wpisów z bazy
                    System.out.println("Wieże:");
                    List<Tower> towers = entityManager.createQuery("SELECT t FROM Tower t", Tower.class).getResultList();

                    for (Tower tower : towers) {
                        System.out.println("Nazwa: " + tower.getName() + ", Wysokość: " + tower.getHeight());
                    }

                    System.out.println("Magowie:");
                    List<Mage> mages = entityManager.createQuery("SELECT m FROM Mage m", Mage.class).getResultList();

                    for (Mage mage : mages) {
                        System.out.println("Imię: " + mage.getName() + ", Poziom: " + mage.getLevel() + ", Wieża: " + mage.getTower().getName());
                    }

                    break;
                case 4:
                    // Obsługa pobierania magów z poziomem większym niż X
                    System.out.println("PODAJ POZIOM: ");
                    int power = scanner.nextInt();
                    scanner.nextLine();

                    List<Mage> magesAboveLevel = entityManager.createQuery("SELECT m FROM Mage m WHERE level > :power", Mage.class).setParameter("power", power).getResultList();

                    for (Mage mage : magesAboveLevel) {
                        System.out.println("Imię: " + mage.getName() + ", Poziom: " + mage.getLevel() + ", Wieża: " + mage.getTower().getName());
                    }

                    break;
                case 5:
                    // Obsługa pobierania wież niższych niż X
                    System.out.println("PODAJ WYSOKOŚĆ: ");
                    int height = scanner.nextInt();
                    scanner.nextLine();

                    List<Tower> towerBelowHeight = entityManager.createQuery("SELECT t FROM Tower t WHERE height < :height", Tower.class).setParameter("height", height).getResultList();

                    for (Tower tower : towerBelowHeight) {
                        System.out.println("Nazwa: " + tower.getName() + ", Wysokość: " + tower.getHeight());
                    }

                    break;
                case 6:
                    // Obsługa pobierania magów z poziomem wyższym niż z danej wieży
                    System.out.println("PODAJ WIEŻĘ: ");
                    String towerName = scanner.nextLine();
                    System.out.println("PODAJ POZIOM: ");
                    int poziom = scanner.nextInt();
                    scanner.nextLine();

                    List<Mage> magesFromTower = entityManager.createQuery("SELECT m FROM Mage m WHERE m.tower.name = :towerName", Mage.class).setParameter("towerName", towerName).getResultList();
                    
                    if(!magesFromTower.isEmpty()){
                        List<Mage> resultMages = new ArrayList<>();
                        for (Mage mage : magesFromTower) {
                            if(mage.getLevel() > poziom){
                                resultMages.add(mage);
                            }
                        }
                        System.out.println("Wieża: " + towerName);
                        for (Mage mage : resultMages) {
                            System.out.println("Imię: " + mage.getName() + ", Poziom: " + mage.getLevel());
                        }
                    }
                    else{
                        System.out.println("WIEŻA NIE MA MAGÓW");
                    }

                    break;
                case 7:
                    break;
                default:
                    System.out.println("Zły input");
            }


        }while(option != 7);

        entityManager.close();
        entityManagerFactory.close();

        scanner.close();

        System.out.println("KONIEC");
    }
}
