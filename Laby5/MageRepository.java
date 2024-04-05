package org.example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;


//zawiera kolekcję obiektów,
//którymi zarządza oraz udostępnia metody pozwalające na zapisanie nowego, usunięcie
//i wyszukiwanie
//Kolekcja powinna być przechowywana w pamięci aplikacj
//Usunięcie i wyszukanie
//realizowane jest na podstawie przyjętego klucza głównego
public class MageRepository {
    private final Collection<Mage> collection;

    public MageRepository(Collection<Mage> collection) {
        this.collection = collection;
    }

    public MageRepository(){
        this.collection = new ArrayList<>();
    }

    public Optional<Mage> find(String name) {
        for (Mage m : collection){
            if(m.getName().equals(name)){
                return Optional.of(m);
            }
        }
        return Optional.empty();
    }
    public void delete(String name) {
        Optional<Mage> mage = find(name);
        if(mage.isEmpty()){
            throw new IllegalArgumentException("Value for deletion hasn't been found.");
        }
        else{
            Mage mageToRemove = mage.get();
            collection.remove(mageToRemove);
            System.out.println("Mage " + mageToRemove.getName() + " has been removed.");
        }
    }
    public void save(Mage mage) {
        Optional<Mage> mageCollection = find(mage.getName());
        if(mageCollection.isPresent()){
            throw new IllegalArgumentException("Mage is already in the repository.");
        }
        else{
            collection.add(mage);
            System.out.println("Mage "+ mage.getName() + " was added to collection.");
        }


    }
}