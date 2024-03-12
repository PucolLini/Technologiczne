package org.example;
import java.util.Objects;
import java.util.Set;

public class Mage implements Comparable<Mage>{
    private String name;
    private int level;
    private double power;
    private Set<Mage> apprentices; //instancja klasy se

    public Mage(String name, int level, double power, Set<Mage> apprentices){
        this.name=name;
        this.level=level;
        this.power=power;
        this.apprentices=apprentices;
    }
    public Mage(String name, int level, double power){
        this.name=name;
        this.level=level;
        this.power=power;
        this.apprentices=null;
    }

    public String getName(){
        return this.name;
    }
    public int getLevel(){
        return this.level;
    }
    public double getPower(){
        return this.power;
    }

    public Set<Mage> getApprentices(){
        return this.apprentices;
    }
    public void addApprentices(Set<Mage> apprentices){
        this.apprentices = apprentices;
    }

    public String toString(){
        return "Mage{" +
                "name='" + name + '\'' +
                ", level=" + level  +
                ", power=" + power + '}';
    }

    //roznica miedzy == a equals jest taka że == porównuje czy obiekty te sa w tym samym miejscu w pamięci (sprawdza referencje)
    // a equals sprawdza czy te dwa obiekty sa te same
    @Override
    public boolean equals(Object other){ //implementacja equals
        if(this==other){
            return true;
        }
        if(other==null){
            return false;
        }
        if(other instanceof Mage){ //upewnienie się że to jest instancja tej samej klasy
            Mage mage = (Mage) other; //rzutowanie pola other na instancje tej samej klasy
            if(this.name.equals(mage.name)){
                if(this.level == mage.level){ //bo jest to typ prymitywny
                    if(this.power == mage.power){
                        Objects.equals(apprentices, mage.apprentices);
                        if(this.apprentices == null){
                            if(mage.apprentices == null){
                                return true;
                            }
                            else {
                                return false;
                            }
                        }

                        if(this.apprentices.equals(mage.apprentices)){
                            return true;
                        }

                    }
                }
            }
        }
        return false;
    }

    // jeżeli (equals = True) => (hashCode = ta sama wartosc), kazdy obiekt bedzie mial inny hashcode
    // implementacja hashCode
    @Override
    public int hashCode(){
        /*int result = 7;
        result = 31 * result + name.hashCode();
        result = 31 * result + level;
        result = 31 * result + (int) power;
        result = 31 * result + apprentices.hashCode();
        return result; */
        return Objects.hash(name, level, power, apprentices);
    }

    //sortowanie zgodnie z naturalnym porządkiem - implementacja Comparable
    //comparable sluzy do porownywania obiektow tej samej klasy z instancja tej klasy
    @Override
    public int compareTo(Mage other) { //idk czy ma być ten sam typ
        /*
        if(this.name == other.name){
            return 0;
        }
        else if(this.name > other.name){
            return 1;
        }
        else return -1;
        */
        int result = this.name.compareTo(other.name);
        if(result==0){
            if(this.level == other.level){
                if(this.power == other.power){
                    result = 0;
                }
                else if ( this.power > other.power){
                    result = 1;
                }
                else{
                    result = -1;
                }
            }
            else if ( this.level > other.level){
                result = 1;
            }
            else{
                result = -1;
            }
            //result = this.level.compareTo(other.level);
        }
        return result;
        //return this.name.compareTo(other.name);
    }

    public void printTree(String hierarchy){
        System.out.println(hierarchy + this);
        if (this.apprentices != null) {
            hierarchy += "-";
            for (Mage apprentice : this.apprentices) {
                apprentice.printTree(hierarchy);
            }
        }
    }
    public void print(String hierarchy, Set<Mage> printedMages) {
        if (printedMages.contains(this)) {
            return; // Jeśli mag został już wyświetlony, to nie powtarzaj
        }

        System.out.println(hierarchy + this);
        printedMages.add(this); // Dodaj maga do listy wyświetlonych

        if (this.apprentices != null) {
            hierarchy += "-";
            for (Mage apprentice : this.apprentices) {
                apprentice.print(hierarchy, printedMages);
            }
        }
    }

    public int countDescendants() {
        if (this.apprentices == null || this.apprentices.isEmpty()) {
            return 0;
        }

        int totalDescendants = this.apprentices.size();

        for (Mage apprentice : this.apprentices) {
            totalDescendants += apprentice.countDescendants();
        }

        return totalDescendants;
    }

}