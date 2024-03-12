package org.example;
import java.util.Comparator;

public class MageComparator implements Comparator<Mage> {
    @Override
    public int compare(Mage o1, Mage o2) { //przykładowy komparator po levelu
        /*int result = o1.getName().compareTo(o2.getName());
        if(result==0){
            if(o1.getLevel() == o2.getLevel()){
                if(o1.getPower() == o2.getPower()){
                    result = 0;
                }
                else if ( o1.getPower() > o2.getPower()){
                    result = 1;
                }
                else{
                    result = -1;
                }
            }
            else if ( o1.getLevel() > o2.getLevel()){
                result = 1;
            }
            else{
                result = -1;
            }
        }
        return result;
        */
        int result = 0;
        if (o1.getPower() < o2.getPower()){
            result = 1;
        }
        else{
            result = -1;
        }
        return result;
    }

}
