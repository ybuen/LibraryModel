package library.items;

public class Magazine extends Item{
    public Magazine(String title, String author, String genre, String rating) {
        super(title, author, genre, rating);
    }

    @Override
    public String toString() {
        return "Magazine \""+this.getTitle()+"\" by "+this.getAuthor()+" (Genre: "+this.getGenre()+", Rating: "+
                this.getRating()+")";
    }

    @Override
    public String getPrefix() {
        return "MA";
    }

}
