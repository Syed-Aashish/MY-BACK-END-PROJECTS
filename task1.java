package task1;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Account Class
class Account {
    private final int id; // Account ID
    private double balance; // Account balance
    private String pin; // Account PIN
    private final List<String> transactionHistory; // List to store transaction history

    // Constructor to initialize account with ID, initial balance, and PIN
    public Account(int id, double initialBalance, String pin) {
        this.id = id;
        this.balance = initialBalance;
        this.pin = pin;
        this.transactionHistory = new ArrayList<>();
        recordTransaction("Account created with initial balance: $" + initialBalance);
    }

    // Getters for account ID and balance
    public int getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    // Method to deposit money into the account
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            recordTransaction("Deposited: $" + amount);
            System.out.println("Deposited: $" + amount);
        } else {
            System.out.println("Deposit amount must be positive.");
        }
    }

    // Method to withdraw money from the account
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            recordTransaction("Withdrew: $" + amount);
            System.out.println("Withdrawn: $" + amount);
        } else {
            System.out.println("Insufficient funds or invalid amount.");
        }
    }

    // Method to change the account PIN
    public void changePin(String newPin) {
        if (newPin != null && !newPin.trim().isEmpty()) {
            this.pin = newPin;
            recordTransaction("PIN changed.");
            System.out.println("PIN changed successfully.");
        } else {
            System.out.println("Invalid PIN. Please try again.");
        }
    }

    // Method to check if the provided PIN is correct
    public boolean verifyPin(String pin) {
        return this.pin.equals(pin);
    }

    // Method to get transaction history
    public List<String> getTransactionHistory() {
        return new ArrayList<>(transactionHistory); // Return a copy for safety
    }

    // Private method to record a transaction in the history
    private void recordTransaction(String transaction) {
        transactionHistory.add(transaction);
    }
}

// Task1 Class (ATM)
public class task1 {
    private static final Account[] accounts = new Account[10]; // Array to hold accounts

    public static void main(String[] args) {
        initializeAccounts(); // Initialize accounts with default values
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter your account ID (0-9): ");
            int id = scanner.nextInt();

            if (id < 0 || id >= accounts.length) {
                System.out.println("Invalid ID. Please try again.");
                continue;
            }

            System.out.print("Enter your PIN: ");
            String pin = scanner.next();

            if (!accounts[id].verifyPin(pin)) {
                System.out.println("Incorrect PIN. Please try again.");
                continue;
            }

            Account currentAccount = accounts[id];
            boolean exit = false;

            while (!exit) {
                displayMenu();
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.println("Current Balance: $" + currentAccount.getBalance());
                        break;
                    case 2:
                        System.out.print("Enter deposit amount: ");
                        double depositAmount = scanner.nextDouble();
                        currentAccount.deposit(depositAmount);
                        break;
                    case 3:
                        System.out.print("Enter withdrawal amount: ");
                        double withdrawAmount = scanner.nextDouble();
                        currentAccount.withdraw(withdrawAmount);
                        break;
                    case 4:
                        System.out.print("Enter new PIN: ");
                        String newPin = scanner.next();
                        currentAccount.changePin(newPin);
                        break;
                    case 5:
                        System.out.println("Transaction History:");
                        for (String transaction : currentAccount.getTransactionHistory()) {
                            System.out.println(transaction);
                        }
                        break;
                    case 6:
                        exit = true; // Exit the inner loop
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }

    // Method to display ATM menu options
    private static void displayMenu() {
        System.out.println("\nATM Menu:");
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit Money");
        System.out.println("3. Withdraw Money");
        System.out.println("4. Change PIN");
        System.out.println("5. View Transaction History");
        System.out.println("6. Exit");
        System.out.print("Choose an option: ");
    }

    // Method to initialize accounts with default values
    private static void initializeAccounts() {
        for (int i = 0; i < accounts.length; i++) {
            accounts[i] = new Account(i, 1000, "1234"); // Each account starts with $1000 and default PIN "1234"
        }
    }
}
