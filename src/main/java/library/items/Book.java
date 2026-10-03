package library.items;

public class Book extends Item{
    public Book(String title, String author, String genre, String rating) {
        super(title, author, genre, rating);
    }

    @Override
    public String toString() {
        return "Book \""+this.getTitle()+"\" by "+this.getAuthor()+" (Genre: "+this.getGenre()+", Rating: "+
                this.getRating()+")";
    }

    @Override
    public String getPrefix() {
        return "B";
    }

}
