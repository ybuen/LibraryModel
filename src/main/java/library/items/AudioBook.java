package library.items;

public class AudioBook extends Item{
    public AudioBook(String title, String author, String genre, String rating) {
        super(title, author, genre, rating);
    }

    @Override
    public String toString() {
        return "AudioBook \""+this.getTitle()+"\" by "+this.getAuthor()+" (Genre: "+this.getGenre()+", Rating: "+
                this.getRating()+")";
    }

    @Override
    public String getPrefix() {
        return "A";
    }

}
