package library.items;

public class Movie extends Item{
    public Movie(String title, String author, String genre, String rating) {
        super(title, author, genre, rating);
    }

    @Override
    public String toString() {
        return "Movie \""+this.getTitle()+"\" by "+this.getAuthor()+" (Genre: "+this.getGenre()+", Rating: "+
                this.getRating()+")";
    }

    @Override
    public String getPrefix() {
        return "M";
    }

}
