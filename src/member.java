import java.time.LocalDate;
import java.util.*;

public class member {
    public static class BorrowRecord {
        private String recordId;
        private String memId;
        private String isbn;
        private LocalDate issueDate;
        private LocalDate dueDate;
        private LocalDate returnDate;

        public BorrowRecord(String recordId, String memId, String isbn, LocalDate issueDate, LocalDate dueDate) {
            this.recordId = recordId;
            this.memId = memId;
            this.isbn = isbn;
            this.issueDate = issueDate;
            this.dueDate = dueDate;
            this.returnDate = null;
        }

        public String getRecordId() { return recordId; }
        public String getMemId() { return memId; }
        public String getIsbn() { return isbn; }
        public LocalDate getIssueDate() { return issueDate; }
        public LocalDate getDueDate() { return dueDate; }
        public LocalDate getReturnDate() { return returnDate; }
        public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
        public boolean isOverdue() {
            return returnDate == null && LocalDate.now().isAfter(dueDate);
        }

        @Override
        public String toString() {
            String status = returnDate == null ? "ACTIVE" : "RETURNED";
            return String.format("Record %s | %s | %s | Issue: %s | Due: %s | Return: %s | %s",
                recordId, memId, isbn, issueDate, dueDate,
                returnDate != null ? returnDate.toString() : "N/A", status);
        }
    }

    public static class Member {
        private String memId;
        private String name;
        private String email;
        private List<BorrowRecord> activeBorrows;
        private double fines;
        public Member(String memId, String name, String email) {
            this.memId = memId;
            this.name = name;
            this.email = email;
            this.activeBorrows = new ArrayList<>();
            this.fines = 0.0;
        }
        public String getMemId() { return memId; }
        public String getName() { return name; }
        public String getEmail() { return email; }
        public List<BorrowRecord> getActiveBorrows() { return new ArrayList<>(activeBorrows); }
        public double getFines() { return fines; }
        public void addFine(double amount) { this.fines += amount; }
        public void payFine(double amount) { this.fines = Math.max(0, this.fines - amount); }
        public void issueBook(BorrowRecord record) {
            activeBorrows.add(record);
        }
        public void returnBook(String recordId) {
            activeBorrows.removeIf(record -> record.getRecordId().equals(recordId));
        }
        public boolean hasActiveBorrows() {
            return !activeBorrows.isEmpty();
        }
        @Override
        public String toString() {
            return String.format(" %s (%s) | Email: %s | Fines: $%.2f | Active Borrows: %d",
                memId, name, email, fines, activeBorrows.size());
        }
    }
    private static List<Member> memberList = new ArrayList<>();
    public static void addMember(Member member) {
        memberList.add(member);
        System.out.println(" Member added: " + member.getName());
    }
    public static Member findMember(String memId) {
        return memberList.stream()
            .filter(member -> member.getMemId().equals(memId))
            .findFirst()
            .orElse(null);
    }
    public static void displayAllMembers() {
        if (memberList.isEmpty()) {
            System.out.println(" No members registered");
            return;
        }
        System.out.println("\n === MEMBERS ===");
        memberList.forEach(member -> System.out.println("  " + member));
    }
    public static List<Member> getAllMembers() {
        return new ArrayList<>(memberList);
    }
}