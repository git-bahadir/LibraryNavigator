package for_bilkent_students.librarynavigator;

import android.graphics.Point;

import java.util.ArrayList;

/**
 * Created by Lenovo PC on 1/12/2017.
 */

public class Floor {
   private ArrayList<Shelf> shelves;
    private String beginningShelf;
   private String endShelf;

    public Floor(String beginning, String end, ArrayList<Shelf> shelves) {
        beginningShelf = beginning;
        endShelf = end;
        this.shelves = shelves;
    }


    public void addShelf(String beginning, String end) {
        shelves.add(new Shelf(beginning, end, new Point(100,142)));
    }

    public boolean checkFloor(String callNum) {
        return (callNum.compareTo(beginningShelf)>=0 && callNum.compareTo(endShelf)<=0);
    }

}
