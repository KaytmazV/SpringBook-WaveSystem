package com.kaytmazvolkan.api.model;

import jakarta.persistence.*;
import java.util.List;

/**
 * Entity representing a Book in the database.
 */
@Entity
@Table(name = "book")
public class Book {

    /**
     * Unique identifier for the book, used as the primary key in the database.
     * The id must be unique for every book.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private long book_id;

    /**
     * Represents the author of the book. A book can have one author, but an author can write many books.
     * This is a many-to-one relationship with the {@link Author} entity.
     *
     * @see Author
     */
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    /**
     * The isbn of the book.
     * This field is required and cannot be null.
     */
    @Column(name = "isbn")
    private String isbn;

    /**
     * The title of the book.
     * This field is required and cannot be null.
     */
    @Column(name = "title")
    private String title;

    /**
     * The publisher of the book.
     */
    @Column(name = "publisher")
    private String publisher;

    /**
     * The published year of the book.
     */
    @Column(name = "published_year")
    private int published_year;

    /**
     * The status of the book.
     * This field is required and cannot be null.
     */
    @Column(name = "status")
    private String status;

    /**
     * The quantity of the book.
     * This field is required and cannot be null.
     */
    @Column(name = "quantity")
    private int quantity;

    /**
     * The rate of the book.
     * This field is required and cannot be null.
     */
    @Column(name = "rate")
    private int rate; //(from a scale of 1-5)

    /**
     * The description of the book.
     */
    @Column(name = "description")
    private String description;

    /**
     * The list of genres associated with the book. This is a many-to-many relationship with the {@link Genre} entity,
     * where a book can belong to multiple genres and a genre can include many books.
     *
     * @return A list of genres associated with the book.
     * @see Genre
     */
    @ManyToMany
    @JoinTable(
            name = "bookgenres",  // Join table for many-to-many relationship between books and genres
            joinColumns = @JoinColumn(name = "book_id"),  // Foreign key for the book
            inverseJoinColumns = @JoinColumn(name = "genre_id")  // Foreign key for the genre
    )
    private List<Genre> genres;  // List of genres associated with the book

    /**
     * The image associated with the book. This is a one-to-one relationship with the {@link Image} entity,
     * where each book has one unique image.
     *
     * @return The image associated with the book.
     * @see Image
     */
    @OneToOne
    @JoinColumn(name = "image_id")  // One book has one image, no repetitive images for books
    private Image image;  // The image associated with the book

    /**
     * Default constructor required by JPA.
     */
    public Book() {
    }

    /**
     * Parameterized constructor for creating a Book object.
     *
     * @param title          Title of the book
     * @param ISBN           ISBN number of the book
     * @param publisher      Publisher of the book
     * @param published_year Year the book was published
     * @param status         Status of the book (available, checked out, etc.)
     * @param description    Description or synopsis of the book
     * @param author         The author of the book
     * @param quantity       Quantity available in stock
     * @param rate           Rating of the book
     * @param genres         List of genres associated with the book
     * @param image          The image associated with the book
     */
    public Book(String title, String ISBN, String publisher, int published_year,
                String status, String description, Author author, int quantity, int rate, List<Genre> genres, Image image) {
        setBook(title, ISBN, publisher, published_year, status, description, author, quantity, rate, genres, image);
    }

    /**
     * Sets the attributes of the book.
     *
     * @param title          Title of the book
     * @param ISBN           ISBN number of the book
     * @param publisher      Publisher of the book
     * @param published_year Year the book was published
     * @param status         Status of the book (e.g., available, checked out)
     * @param description    Description or synopsis of the book
     * @param author         Author of the book
     * @param quantity       Quantity available in stock
     * @param rate           Rating of the book
     * @param genres         List of genres associated with the book
     * @param image          Image associated with the book
     */
    public void setBook(String title, String ISBN, String publisher, int published_year,
                        String status, String description, Author author, int quantity, int rate, List<Genre> genres, Image image) {
        setTitle(title);
        setISBN(ISBN);
        setPublisher(publisher);
        setPublished_year(published_year);
        setStatus(status);
        setDescription(description);
        setAuthor(author);
        setQuantity(quantity);
        setRate(rate);
        setGenres(genres);
        setImage(image);
    }

    /**
     * Sets the updated attributes of the book.
     *
     * @param title          Title of the book
     * @param publisher      Publisher of the book
     * @param published_year Year the book was published
     * @param status         Status of the book (e.g., available, checked out)
     * @param description    Description or synopsis of the book
     * @param author         Author of the book
     * @param quantity       Quantity available in stock
     * @param rate           Rating of the book
     * @param genres         List of genres associated with the book
     * @param image          Image associated with the book
     */
    public void setUpdateBook(String title, String publisher, int published_year,
                              String status, String description, Author author, int quantity, int rate, List<Genre> genres, Image image) {
        setTitle(title);
        setPublisher(publisher);
        setPublished_year(published_year);
        setStatus(status);
        setDescription(description);
        setAuthor(author);
        setQuantity(quantity);
        setRate(rate);
        setGenres(genres);
        setImage(image);
    }


    // Getters and Setters

    /**
     * Gets the id of the book.
     *
     * @return The id of the book.
     */
    public long getBook_id() {
        return book_id;
    }

    /**
     * Sets the id of the book.
     *
     * @param book_id The new id to set for the book.
     */
    public void setBook_id(long book_id) {
        this.book_id = book_id;
    }

    /**
     * Gets the title of the book.
     *
     * @return The title of the book.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the book.
     *
     * @param title The new title to set for the book.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the isbn of the book.
     *
     * @return The isbn of the book.
     */
    public String getISBN() {
        return isbn;
    }

    /**
     * Sets the isbn of the book.
     *
     * @param isbn The new isbn to set for the book.
     */
    public void setISBN(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Gets the publisher of the book.
     *
     * @return The publisher of the book.
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * Sets the publisher of the book.
     *
     * @param publisher The new publisher to set for the book.
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     * Gets the published year of the book.
     *
     * @return The published year of the book.
     */
    public int getPublished_year() {
        return published_year;
    }

    /**
     * Sets the published year of the book.
     *
     * @param published_year The new published year to set for the book.
     */
    public void setPublished_year(int published_year) {
        this.published_year = published_year;
    }

    /**
     * Gets the status of the book.
     *
     * @return The status of the book.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the book.
     *
     * @param status The new status to set for the book.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the description of the book.
     *
     * @return The description of the book.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the book.
     *
     * @param description The new description year to set for the book.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the author of the book.
     *
     * @return The author of the book.
     */
    public Author getAuthor() {
        return author;
    }

    /**
     * Sets the author of the book.
     *
     * @param author The new author to set for the book.
     */
    public void setAuthor(Author author) {

        this.author = author;
    }

    /**
     * Gets the quantity of the book.
     *
     * @return The quantity of the book.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the book.
     *
     * @param quantity The new quantity to set for the book.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the rate of the book.
     *
     * @return The rate of the book.
     */
    public int getRate() {
        return rate;
    }

    /**
     * Sets the rate of the book.
     *
     * @param rate The new rate to set for the book.
     */
    public void setRate(int rate) {
        this.rate = rate;
    }

    /**
     * Gets the image of the book.
     *
     * @return The image of the book.
     */
    public Image getImage() {
        return image;
    }

    /**
     * Sets the image of the book.
     *
     * @param image The new image to set for the book.
     */
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * Gets the list of genres associated with the book.
     *
     * @return A list of {@link Genre} objects representing the genres of the book.
     */
    public List<Genre> getGenres() {
        return genres;
    }

    /**
     * Sets the list of genres for the book.
     *
     * @param genres A list of {@link Genre} objects to be associated with the book.
     */
    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    /**
     * Converts the Book object to a string representation.
     *
     * @return String representation of the book details
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Title: ").append(title)
                .append("\nAuthor: ").append(author)
                .append("\nPublished year: ").append(published_year)
                .append("\nPublisher: ").append(publisher)
                .append("\nStatus: ").append(status)
                .append("\nISBN: ").append(isbn);

        // Check if genres are available and append them
        if (genres != null && !genres.isEmpty()) {
            result.append("\nGenres: ");
            for (int i = 0; i < genres.size(); i++) {
                result.append(genres.get(i).getType());
                if (i < genres.size() - 1) {
                    result.append(", ");
                }
            }
        } else {
            result.append("\nNo genres available");
        }

        result.append("\n\nDescription: ").append(description);
        return result.toString();
    }
}
