public class librarian {
    public static class FinePolicy {
        private double ratePerDay;
        private double maxFine;
        private int gracePeriodDays;

        public FinePolicy(double ratePerDay, double maxFine, int gracePeriodDays) {
            this.ratePerDay = ratePerDay;
            this.maxFine = maxFine;
            this.gracePeriodDays = gracePeriodDays;
        }

        public double calculateFine(int overdueDays) {
            if (overdueDays <= gracePeriodDays) return 0.0;
            int chargeableDays = overdueDays - gracePeriodDays;
            double fine = chargeableDays * ratePerDay;
            return Math.min(fine, maxFine);
        }

        @Override
        public String toString() {
            return String.format("Fine Policy  Rate: $%.2f/day  Max: $%.2f  Grace: %d days",
                ratePerDay, maxFine, gracePeriodDays);
        }
    }

    public static class Librarian {
        private String staffId;
        private String name;

        public Librarian(String staffId, String name) {
            this.staffId = staffId;
            this.name = name;
        }

        @Override
        public String toString() {
            return String.format("Librarian: %s (%s)", staffId, name);
        }
    }

    private static Librarian currentLibrarian;
    private static FinePolicy finePolicy;

    public static void login(Librarian librarian) {
        currentLibrarian = librarian;
        finePolicy = new FinePolicy(1.0, 50.0, 3);
        System.out.println("Logged in: " + currentLibrarian);
        System.out.println(" " + finePolicy);
    }

    public static Librarian getCurrentLibrarian() {
        return currentLibrarian;
    }

    public static FinePolicy getFinePolicy() {
        return finePolicy;
    }
}