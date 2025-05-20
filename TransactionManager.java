import java.io.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

public class TransactionManager {
    private List<Transaction> transactions = new ArrayList<>();

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public void loadFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String type = parts[0];
                    String category = parts[1];
                    double amount = Double.parseDouble(parts[2]);
                    LocalDate date = LocalDate.parse(parts[3]);
                    transactions.add(new Transaction(type, category, amount, date));
                }
            }
            System.out.println("Loaded transactions from file.");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public void saveToFile(String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (Transaction t : transactions) {
                pw.println(t.toFileString());
            }
            System.out.println("Transactions saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }

    public void showMonthlySummary(int year, int month) {
        YearMonth targetMonth = YearMonth.of(year, month);
        double totalIncome = 0;
        double totalExpense = 0;

        Map<String, Double> categoryTotals = new HashMap<>();

        System.out.println("\n--- Monthly Summary for " + targetMonth + " ---");

        for (Transaction t : transactions) {
            if (YearMonth.from(t.getDate()).equals(targetMonth)) {
                if (t.getType().equalsIgnoreCase("Income")) {
                    totalIncome += t.getAmount();
                } else {
                    totalExpense += t.getAmount();
                }

                categoryTotals.put(t.getCategory(),
                    categoryTotals.getOrDefault(t.getCategory(), 0.0) + t.getAmount());
            }
        }

        System.out.println("Total Income: $" + totalIncome);
        System.out.println("Total Expenses: $" + totalExpense);
        System.out.println("Category Breakdown:");
        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            System.out.println(" - " + entry.getKey() + ": $" + entry.getValue());
        }
    }
}
