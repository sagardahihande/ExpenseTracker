import java.time.LocalDate;
import java.util.Scanner;

public class ExpenseTracker {
    private static final Scanner scanner = new Scanner(System.in);
    private static final TransactionManager manager = new TransactionManager();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n====== Expense Tracker Menu ======");
            System.out.println("1. Add Income");
            System.out.println("2. Add Expense");
            System.out.println("3. View Monthly Summary");
            System.out.println("4. Load Transactions from File");
            System.out.println("5. Save Transactions to File");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> addTransaction("Income");
                case 2 -> addTransaction("Expense");
                case 3 -> viewMonthlySummary();
                case 4 -> {
                    System.out.print("Enter filename to load: ");
                    String filename = scanner.nextLine();
                    manager.loadFromFile(filename);
                }
                case 5 -> {
                    System.out.print("Enter filename to save: ");
                    String filename = scanner.nextLine();
                    manager.saveToFile(filename);
                }
                case 6 -> {
                    System.out.println("Exiting... Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void addTransaction(String type) {
        String category = "";
        if (type.equals("Income")) {
            System.out.println("Choose Income Category: 1. Salary  2. Business");
            int catChoice = scanner.nextInt();
            scanner.nextLine();
            category = (catChoice == 1) ? "Salary" : "Business";
        } else {
            System.out.println("Choose Expense Category: 1. Food  2. Rent  3. Travel");
            int catChoice = scanner.nextInt();
            scanner.nextLine();
            category = switch (catChoice) {
                case 1 -> "Food";
                case 2 -> "Rent";
                case 3 -> "Travel";
                default -> "Other";
            };
        }

        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        LocalDate date = LocalDate.now(); // use current date
        Transaction transaction = new Transaction(type, category, amount, date);
        manager.addTransaction(transaction);
        System.out.println("Transaction added!");
    }

    private static void viewMonthlySummary() {
        System.out.print("Enter year (e.g., 2025): ");
        int year = scanner.nextInt();
        System.out.print("Enter month (1-12): ");
        int month = scanner.nextInt();
        scanner.nextLine();
        manager.showMonthlySummary(year, month);
    }
}
