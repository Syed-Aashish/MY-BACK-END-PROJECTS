package intershipTasks;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Product {
    private final String name;
    private final double price;
    private final int quantity;

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}

public class task2 {
    private static final double TAX_RATE = 0.08; // 8% tax rate
    private static final double DISCOUNT_RATE = 0.1; // 10% discount rate

    private static final ArrayList<Product> products = new ArrayList<>();
    private static final Map<String, Integer> productCounts = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean addMoreItems = true;

        while (addMoreItems) {
            System.out.print("Enter item name: ");
            String name = scanner.nextLine();

            double price = getValidDouble(scanner, "Enter item price: ");

            int quantity = getValidInt(scanner, "Enter item quantity: ");
            scanner.nextLine(); // Consume newline character

            Product product = new Product(name, price, quantity);
            products.add(product);

            productCounts.put(name, productCounts.getOrDefault(name, 0) + quantity);

            System.out.print("Do you want to add more items? (yes/no): ");
            String response = scanner.nextLine();
            addMoreItems = response.equalsIgnoreCase("yes");
        }

        generateReceipt();
    }

    private static void generateReceipt() {
        double subtotal = calculateSubtotal();
        double tax = calculateTax(subtotal);
        double discount = calculateDiscount(subtotal);
        double total = calculateTotal(subtotal, tax, discount);

        DecimalFormat df = new DecimalFormat("#.##");

        System.out.println("\nReceipt:");
        System.out.println("Item\t\tPrice\tQuantity\tTotal");
        System.out.println("-------------------------------------------");

        for (Map.Entry<String, Integer> entry : productCounts.entrySet()) {
            String name = entry.getKey();
            int quantity = entry.getValue();
            double price = products.stream()
                    .filter(p -> p.getName().equals(name))
                    .findFirst()
                    .orElse(new Product("", 0, 0))
                    .getPrice();
            double itemTotal = price * quantity;

            System.out.printf("%-8s\t$%-8s\t%-8d\t$%-8s%n", name, df.format(price), quantity, df.format(itemTotal));
        }

        System.out.println("\nSubtotal: $" + df.format(subtotal));
        System.out.println("Tax: $" + df.format(tax));
        System.out.println("Discount: $" + df.format(discount));
        System.out.println("Total: $" + df.format(total));

        saveReceipt(subtotal, tax, discount, total);
    }

    private static double calculateSubtotal() {
        return products.stream()
                .mapToDouble(p -> p.getPrice() * p.getQuantity())
                .sum();
    }

    private static double calculateTax(double subtotal) {
        return subtotal * TAX_RATE;
    }

    private static double calculateDiscount(double subtotal) {
        return subtotal * DISCOUNT_RATE;
    }

    private static double calculateTotal(double subtotal, double tax, double discount) {
        return subtotal + tax - discount;
    }

    private static void saveReceipt(double subtotal, double tax, double discount, double total) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("receipt.txt"))) {
            writer.write("Receipt:\n");
            writer.write("Item\t\tPrice\tQuantity\tTotal\n");
            writer.write("-------------------------------------------\n");

            for (Map.Entry<String, Integer> entry : productCounts.entrySet()) {
                String name = entry.getKey();
                int quantity = entry.getValue();
                double price = products.stream()
                        .filter(p -> p.getName().equals(name))
                        .findFirst()
                        .orElse(new Product("", 0, 0))
                        .getPrice();
                double itemTotal = price * quantity;

                writer.write(String.format("%-8s\t$%-8s\t%-8d\t$%-8s%n", name, price, quantity, itemTotal));
            }

            writer.write("\nSubtotal: $" + subtotal + "\n");
            writer.write("Tax: $" + tax + "\n");
            writer.write("Discount: $" + discount + "\n");
            writer.write("Total: $" + total + "\n");
            System.out.println("\nReceipt saved as receipt.txt");
        } catch (IOException e) {
            System.out.println("Error saving receipt: " + e.getMessage());
        }
    }

    private static double getValidDouble(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private static int getValidInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }
}
