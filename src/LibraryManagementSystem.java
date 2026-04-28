import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;
public class LibraryManagementSystem {
    static Scanner sc = new Scanner(System.in);
    static int recordCounter = 1;
    public static void main(String[] args) {
        System.out.println("\n\nLIBRARY MANAGEMENT SYSTEM\n");
        // login
        librarian.Librarian lib = new librarian.Librarian("L1", "Admin");
        librarian.login(lib);
        boolean run = true;
        while (run) {
            System.out.println("\n1. Books");
            System.out.println("2. Members");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Calculate Fine");
            System.out.println("0. Exit");
            System.out.print("Choice: ");
            int ch = sc.nextInt();
            sc.nextLine();
            switch (ch) {
                case 1 -> bookMenu();
                case 2 -> memberMenu();
                case 3 -> issueBook();
                case 4 -> returnBook();
                case 5 -> calculateFine();
                case 0 -> run = false;
                default -> System.out.println("Invalid choice!");
            }
        }
    }
    //menu
    static void bookMenu() {
        System.out.println("\n1. Add Book");
        System.out.println("2. View Books");
        System.out.println("3. Remove Book");
        int ch = sc.nextInt();
        sc.nextLine();
        if (ch == 1) {
            System.out.print("ISBN: ");
            String isbn = sc.nextLine();
            System.out.print("Title: ");
            String title = sc.nextLine();
            System.out.print("Author: ");
            String author = sc.nextLine();
            System.out.print("Genre: ");
            String genre = sc.nextLine();
            System.out.print("Quantity: ");
            int qty = sc.nextInt();
            books.addBook(new books.Book(isbn, title, author, genre, qty));
        } else if (ch == 2) {
            books.displayAllBooks();
        } else if (ch == 3) {
            System.out.print("Enter ISBN: ");
            String isbn = sc.nextLine();
            books.removeBook(isbn);
        }
    }
    static void memberMenu() {
        System.out.println("\n1. Add Member");
        System.out.println("2. View Members");
        int ch = sc.nextInt();
        sc.nextLine();
        if (ch == 1) {
            System.out.print("ID: ");
            String id = sc.nextLine();
            System.out.print("Name: ");
            String name = sc.nextLine();
            System.out.print("Email: ");
            String email = sc.nextLine();
            member.addMember(new member.Member(id, name, email));
        } else {
            member.displayAllMembers();
        }
    }
    //issue book
    static void issueBook() {
        System.out.print("Member ID: ");
        String memId = sc.nextLine();

        member.Member m = member.findMember(memId);
        if (m == null) {
            System.out.println("Member not found!");
            return;
        }
        System.out.print("Book ISBN: ");
        String isbn = sc.nextLine();
        books.Book b = books.findBook(isbn);
        if (b == null || b.getAvailableQuantity() == 0) {
            System.out.println("Book not available!");
            return;
        }
        String recordId = "R" + recordCounter++;
        member.BorrowRecord r = new member.BorrowRecord(
                recordId,
                memId,
                isbn,
                LocalDate.now(),
                LocalDate.now().plusDays(7)
        );
        m.issueBook(r);
        b.setAvailableQuantity(b.getAvailableQuantity() - 1);
        b.setStatus("borrowed");
        System.out.println("Book Issued! Record ID: " + recordId);
    }
    // return nook
    static void returnBook() {
        System.out.print("Member ID: ");
        String memId = sc.nextLine();

        member.Member m = member.findMember(memId);
        if (m == null) {
            System.out.println("Member not found!");
            return;
        }
        System.out.print("Record ID: ");
        String recId = sc.nextLine();
        for (member.BorrowRecord r : m.getActiveBorrows()) {
            if (r.getRecordId().equals(recId)) {
                r.setReturnDate(LocalDate.now());
                m.returnBook(recId);
                books.Book b = books.findBook(r.getIsbn());
                if (b != null) {
                    b.setAvailableQuantity(b.getAvailableQuantity() + 1);
                    b.setStatus("available");
                }
                System.out.println("Book Returned!");
                return;
            }
        }
        System.out.println("Record not found!");
    }
    //fine
    static void calculateFine() {
        System.out.print("Member ID: ");
        String memId = sc.nextLine();

        member.Member m = member.findMember(memId);
        if (m == null) {
            System.out.println("Member not found!");
            return;
        }

        librarian.FinePolicy policy = librarian.getFinePolicy();
        double total = 0;
        for (member.BorrowRecord r : m.getActiveBorrows()) {
            if (r.isOverdue()) {
                long days = ChronoUnit.DAYS.between(r.getDueDate(), LocalDate.now());
                double fine = policy.calculateFine((int) days);
                total += fine;
                System.out.println("Book " + r.getIsbn() + " overdue by " + days + " days. Fine = $" + fine);
            }
        }
        System.out.println("Total Fine: $" + total);
        m.addFine(total);
    }
}