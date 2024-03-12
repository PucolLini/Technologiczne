package org.example;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Mage mage10 = new Mage("Geralt10", 41, 31.7);

        Mage mage9 = new Mage("Artemis9", 97, 20.9);

        Mage mage8 = new Mage("Dawid8", 31, 31.1);

        Set<Mage> mageSet7 = new HashSet<>();
        mageSet7.add(mage8);
        Mage mage7 = new Mage("Zuza7", 50, 10.1, mageSet7);

        Mage mage6 = new Mage("Basia6", 18, 55.5);

        Mage mage2 = new Mage("Felix2", 53, 42.13);

        Set<Mage> mageSet5 = new HashSet<>();
        mageSet5.add(mage7);
        mageSet5.add(mage2);
        Mage mage5 = new Mage("Ania5", 66, 34.8, mageSet5);

        Set<Mage> mageSet4 = new HashSet<>();
        mageSet4.add(mage6);
        Mage mage4 = new Mage("Brat4", 24, 51.9, mageSet4);

        Set<Mage> mageSet3 = new HashSet<>();
        mageSet3.add(mage4);
        mageSet3.add(mage9);
        Mage mage3 = new Mage("Miriam3", 50, 12, mageSet3);

        Set<Mage> mageSet1 = new HashSet<>();
        mageSet1.add(mage3);
        mageSet1.add(mage5);
        Mage mage1 = new Mage("Borys1", 50, 99.8, mageSet1);

        //System.out.println(mage1.equals(mage2));
        //System.out.println(mage1.equals(mage3));
        //System.out.println(mage3.equals(mage4));

        Map<Mage, String> mageStringMap = new HashMap<>();
        mageStringMap.put(mage1, "v1");
        mageStringMap.put(mage2, "v2");
        mageStringMap.put(mage3, "v3");

        //System.out.println(mageStringMap.get(mage1));
        //System.out.println(mageStringMap.get(mage2));
        //System.out.println(mageStringMap.get(mage3));


        //for(Mage m: mageArrayList){
        //    System.out.println(m.toString());
        //}

        //Collections.sort(mageArrayList);

        //for(Mage m: mageArrayList){
        //    System.out.println(m.toString());
        //}
        Scanner myObj = new Scanner(System.in);
        System.out.println(
                "Sposoby sortowania:" + '\n' +
                        "1. Brak"  + '\n' +
                        "2. Naturalny porządek"  + '\n' +
                        "3. Dodatkowe kryterium"
        );
        String input = myObj.nextLine();

        if(input.equals("1")){
            Set<Mage> mageSetUltimate = new HashSet<>();
            mageSetUltimate.add(mage1);
            mageSetUltimate.add(mage2);
            mageSetUltimate.add(mage3);
            mageSetUltimate.add(mage4);
            mageSetUltimate.add(mage5);
            mageSetUltimate.add(mage6);
            mageSetUltimate.add(mage7);
            mageSetUltimate.add(mage8);
            mageSetUltimate.add(mage9);
            mageSetUltimate.add(mage10);

            System.out.println("MAGOWIE BEZ SORTOWANIA:");
            for(Mage m: mageSetUltimate){
                System.out.println(m.toString());
            }

            System.out.println("\nMAGOWIE HIERARCHIA:");
            Set<Mage> printedMages = new HashSet<>();

            for (Mage mage : mageSetUltimate) {
                if (!printedMages.contains(mage)) {
                    mage.print("-", printedMages);
                }
            }

            System.out.println("\nSTATYSTYKI:");
            Map<Mage, Integer> mages = new HashMap<>();

            for (Mage mage : mageSetUltimate) {
                int totalDescendants = mage.countDescendants();
                mages.put(mage, totalDescendants);
            }

            for (Map.Entry<Mage, Integer> entry : mages.entrySet()) {
                System.out.println("Mage: " + entry.getKey() + ", Total Descendants: " + entry.getValue());
            }

        }
        else if(input.equals("2")){

            TreeSet<Mage> mageTreeSet = new TreeSet<>();

            mageTreeSet.add(mage1);
            mageTreeSet.add(mage2);
            mageTreeSet.add(mage3);
            mageTreeSet.add(mage4);
            mageTreeSet.add(mage5);
            mageTreeSet.add(mage6);
            mageTreeSet.add(mage7);
            mageTreeSet.add(mage8);
            mageTreeSet.add(mage9);
            mageTreeSet.add(mage10);

            System.out.println("MAGOWIE PO SORTOWANIU:");
            for(Mage m: mageTreeSet){
                System.out.println(m.toString());
            }

            System.out.println("\nMAGOWIE HIERARCHIA:");
            Set<Mage> printedMages = new HashSet<>(); // Przechowuje informacje o wyświetlonych magach

            for (Mage mage : mageTreeSet) {
                if (!printedMages.contains(mage)) {
                    mage.printTree("-");
                }
            }

            System.out.println("\nSTATYSTYKI:");
            Map<Mage, Integer> mages = new TreeMap<>();

            for (Mage mage : mageTreeSet) {
                int totalDescendants = mage.countDescendants();
                mages.put(mage, totalDescendants);
            }

            for (Map.Entry<Mage, Integer> entry : mages.entrySet()) {
                System.out.println("Mage: " + entry.getKey() + ", Total Descendants: " + entry.getValue());
            }
        }
        else if (input.equals("3")){
            ArrayList<Mage> mageArrayList = new ArrayList<>();
            mageArrayList.add(mage1);
            mageArrayList.add(mage2);
            mageArrayList.add(mage3);
            mageArrayList.add(mage4);
            mageArrayList.add(mage5);
            mageArrayList.add(mage6);
            mageArrayList.add(mage7);
            mageArrayList.add(mage8);
            mageArrayList.add(mage9);
            mageArrayList.add(mage10);

            Collections.sort(mageArrayList, new MageComparator());

            System.out.println("MAGOWIE PO SORTOWANIU:");

            for(Mage m: mageArrayList){
                System.out.println(m.toString());
            }

            System.out.println("\nMAGOWIE HIERARCHIA:");

            Set<Mage> printedMages = new HashSet<>(); // Przechowuje informacje o wyświetlonych magach

            for (Mage mage : mageArrayList) {
                if (!printedMages.contains(mage)) {
                    mage.print("-", printedMages);
                }
            }

            System.out.println("\nSTATYSTYKI:");
            Map<Mage, Integer> mages = new TreeMap<>();

            for (Mage mage : mageArrayList) {
                int totalDescendants = mage.countDescendants();
                mages.put(mage, totalDescendants);
            }

            for (Map.Entry<Mage, Integer> entry : mages.entrySet()) {
                System.out.println("Mage: " + entry.getKey() + ", Total Descendants: " + entry.getValue());
            }

        }
        else{
            System.out.println("Niepoprawny wybór");
        }
    }
}
