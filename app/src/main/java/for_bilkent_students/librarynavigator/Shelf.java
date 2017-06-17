package for_bilkent_students.librarynavigator;

import android.graphics.Point;

/**
 * Created by Lenovo PC on 1/12/2017.
 */

public class Shelf{
    private String shelfBeginning;
    private String shelfEnding;
    private Point shelfLocation;

    public Shelf(String beginning, String ending, Point location) {
        shelfBeginning = beginning;
        shelfEnding = ending;
        shelfLocation = location;
    }


   public boolean  checkShelf(String callNum) {
        return (callNum.compareTo(shelfBeginning)>=0 && callNum.compareTo(shelfEnding)<=0);
    }

    public Point getShelfLocation(){
        return shelfLocation;
    }


}
