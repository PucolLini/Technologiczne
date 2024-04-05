package org.example;

import javax.swing.text.html.Option;
import java.util.Optional;

// powinien korzystać z repozytorium dostarczonego przez wstrzykiwanie
//zależności. W przeciwieństwie do repozytorium, metody kontrolera przyjmują i zwracają
//obiekty String tłumaczone z/na obiekty encyjne
public class MageController {
    private MageRepository repository;
    public MageController(MageRepository repository) {
        this.repository = repository;
    }
    public String find(String name) {
        Optional<Mage> mage = repository.find(name);
        if(mage.isEmpty()){
            return "not found";
        }
        else{
            return mage.get().toString();
        }
    }

    public String delete(String name) {
        try {
            repository.delete(name);
            return "done";
        } catch (IllegalArgumentException e) {
            return "not found";
        }
    }
    public String save(String name, String level) {
        try {
            repository.save(new Mage(name, Integer.parseInt(level)));
            return "done";
        } catch (IllegalArgumentException e) {
            return "bad request";
        }
    }
}