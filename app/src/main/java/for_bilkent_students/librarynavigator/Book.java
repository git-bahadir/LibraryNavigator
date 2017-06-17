package for_bilkent_students.librarynavigator;

/**
 * Created by Lenovo PC on 9/23/2016.
 */
public class Book {
    private String title;
    private String author;
    private String callNumber;
    private int bookFloor;
    private String bookRoom;

    public Book(String title,String author, String callNumber) {
        this.title = title;
        this.author = author;
        this.callNumber = callNumber;
    }

    public String getAuthor() {
        return author;
    }

    public int getBookFloor() {
        return bookFloor;
    }

    public String getBookRoom() {
        return bookRoom;
    }

    public String getCallNumber() {
        return callNumber;
    }

    public String getTitle() {
        return title;
    }

    public int calcBookFloor(String callNumber) {
        return 0;
    }

    public String calcBookRoom(String callNumber) {
        return "";
    }
}
