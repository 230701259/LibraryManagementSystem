import java.util.*;

public class books {
    public static class Book {
        private String isbn;
        private String title;
        private String author;
        private String genre;
        private String status; // "available", "borrowed"
        private int quantity;
        private int availableQuantity;
        public Book(String isbn, String title, String author, String genre, int quantity) {
            this.isbn = isbn;
            this.title = title;
            this.author = author;
            this.genre = genre;
            this.status = "available";
            this.quantity = quantity;
            this.availableQuantity = quantity;
        }

        public String getIsbn() { return isbn; }
        public String getTitle() { return title; }
        public String getAuthor() { return author; }
        public String getGenre() { return genre; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public int getQuantity() { return quantity; }
        public int getAvailableQuantity() { return availableQuantity; }
        public void setAvailableQuantity(int availableQuantity) { 
            this.availableQuantity = availableQuantity; 
        }
        @Override
        public String toString() {
            return String.format("ISBN: %s | %s by %s (%s) | Qty: %d/%d | Status: %s", 
                isbn, title, author, genre, availableQuantity, quantity, status);
        }
    }
    private static List<Book> bookInventory = new ArrayList<>();
    public static void addBook(Book book) {
        bookInventory.add(book);
        System.out.println("Book added: " + book.getTitle());
    }
    public static void removeBook(String isbn) {
        bookInventory.removeIf(book -> book.getIsbn().equals(isbn));
        System.out.println("Book removed: " + isbn);
    }
    public static Book findBook(String isbn) {
        return bookInventory.stream()
            .filter(book -> book.getIsbn().equals(isbn))
            .findFirst()
            .orElse(null);
    }
    public static void displayAllBooks() {
        if (bookInventory.isEmpty()) {
            System.out.println("No books in inventory");
            return;
        }
        System.out.println("\nBOOK INVENTORY ");
        bookInventory.forEach(book -> System.out.println("  " + book));
    }
    public static List<Book> getBookInventory() {
        return new ArrayList<>(bookInventory);
    }
}