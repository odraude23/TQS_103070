package tqs;

import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;

public class Library {
    private List<Book> books;

    public Library() {
        this.books = new ArrayList<>();
    }

    public List<Book> findBooksByAuthor(String author) {
        List<Book> booksByAuthor = new ArrayList<>();

        for (Book book : books) {
            if (book.getAuthor().equals(author)) {
                booksByAuthor.add(book);
            }
        }
        return booksByAuthor;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public List<Book> findBooks(LocalDate from, LocalDate to) {
        List<Book> booksInPeriod = new ArrayList<>();

        for (Book book : books) {
            if (book.getPublished().isAfter(from) && book.getPublished().isBefore(to)) {
                booksInPeriod.add(book);
            }
        }
        return booksInPeriod;
    }

    public List<Book> findBooksByTitle(String title) {
        List<Book> booksByTitle = new ArrayList<>();

        for (Book book : books) {
            if (book.getTitle().equals(title)) {
                booksByTitle.add(book);
            }
        }
        return booksByTitle;
    }
}
