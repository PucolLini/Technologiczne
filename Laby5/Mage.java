package org.example;

public class Mage {
    private String name;
    private int level;

    public Mage(String name, int level){
        this.name=name;
        this.level=level;
    }

    public String getName(){
        return this.name;
    }

    public int getLevel(){
        return this.level;
    }

    public void setName(String name){
        this.name=name;
    }

    public void setLevel(int level){
        this.level=level;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        if(obj == null || getClass() != obj.getClass()){
            return false;
        }
        Mage mage = (Mage) obj;
        return mage.getName().equals(this.name) && mage.getLevel() == level;
    }

    @Override
    public String toString(){
        return "Mage {name: " + name + ", lvl: " + level + "}";
    }
}