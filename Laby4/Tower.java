package org.example;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.*;

@Entity
public class Tower {
    @Id
    private String name;
    private int height;
    @OneToMany(mappedBy = "tower", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Mage> mages;

    public Tower(){}

    public Tower(String name, int height){
        this.name=name;
        this.height=height;
        mages = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<Mage> getMages() {
        return mages;
    }

    public void setMages(List<Mage> mages) {
        this.mages = mages;
    }

    public void printString(){
        System.out.println("Tower: " + name +  " h: " + height);
        int i=0;
        for (Mage mage : mages){
            System.out.println(i + ". ");
            mage.printString();
        }
    }

    public void addMage(Mage mage)
    {
        mages.add(mage);
    }
}
