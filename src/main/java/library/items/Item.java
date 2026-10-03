package library.items;

public class Item {
    private String title;
    private String author;
    private String genre;
    private String rating;

    // Constructors
    public Item() {
        this.title = "N/A";
        this.author = "N/A";
        this.genre = "N/A";
        this.rating = "N/A";
    }

    public Item(String title, String author, String genre, String rating) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.rating = rating;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    // Getters
    public String getTitle() {
        return this.title;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getGenre() {
        return this.genre;
    }

    public String getRating() {
        return this.rating;
    }

    @Override
    public String toString() {
        return "Item \"" + title + "\"";
    }

    public String getPrefix() {
        return "I";
    }

    //public void interact() {
        //System.out.println("Interacting with item " + this.title);
    //}

}
