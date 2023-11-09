public class Book {
    private String title;
    private String ISBN;
    private Author author;
    private String genre;
    private boolean isAvaliable;

    Book(String title, String ISBN, Author author, String genre){
        this.title = title;
        this.ISBN = ISBN;
        this.author = author;
        this.genre = genre;
        this.isAvaliable = true;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }


    public boolean isAvailable() {
        return isAvaliable;
    }

    public void setAvailable(boolean b) {
        isAvaliable = isAvaliable;
    }
}
