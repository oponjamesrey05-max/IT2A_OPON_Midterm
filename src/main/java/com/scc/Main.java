package com.scc;

import com.scc.dao.DatabaseConnection;
import com.scc.dao.ScenarioDao;
import com.scc.waterstation.Order;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final ScenarioDao orderDao = new ScenarioDao();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Initialize DB and table
        DatabaseConnection.initializeDatabase();

        int choice = 0;
        do {
            printMenu();
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline buffer
            } else {
                System.out.println("Invalid input. Please enter a number between 1 and 3.");
                scanner.next(); // Clear invalid token
                continue;
            }

            switch (choice) {
                case 1:
                    addOrderWorkflow();
                    break;
                case 2:
                    viewActiveOrdersWorkflow();
                    break;
                case 3:
                    System.out.println("Exiting Water Refilling Station System. Goodbye!");
                    break;
                default:2
                    System.out.println("Invalid option. Please choose between 1 and 3.");
            }
            System.out.println();
        } while (choice != 3);

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("========================================");
        System.out.println("    WATER REFILLING STATION SYSTEM      ");
        System.out.println("========================================");
        System.out.println("1. Add Delivery Order");
        System.out.println("2. View Active Orders");
        System.out.println("3. Exit System");
        System.out.print("Enter your choice (1-3): ");
    }

    private static void addOrderWorkflow() {
        System.out.println("\n--- Add New Delivery Order ---");

        System.out.print("Enter Customer Name: ");
        String customerName = scanner.nextLine().trim();
        while (customerName.isEmpty()) {
            System.out.print("Customer name cannot be blank. Enter Customer Name: ");
            customerName = scanner.nextLine().trim();
        }

        int qtySlim = getValidatedPositiveInt("Enter Slim Container Quantity: ");
        int qtyRound = getValidatedPositiveInt("Enter Round Container Quantity: ");

        System.out.print("Enter Status (1 for Pending, 2 for Delivered) [Default: Pending]: ");
        String statusInput = scanner.nextLine().trim();
        String status = "Pending";
        if (statusInput.equals("2")) {
            status = "Delivered";
        }

        Order newOrder = new Order(customerName, qtySlim, qtyRound, status);
        orderDao.addOrder(newOrder);
        System.out.println("Successfully recorded delivery order for " + customerName + "!");
    }

    private static int getValidatedPositiveInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                int value = scanner.nextInt();
                scanner.nextLine(); // Consume newline buffer
                if (value >= 0) {
                    return value;
                }
                System.out.println("Please enter a non-negative number.");
            } else {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.next(); // Clear invalid token
            }
        }
    }

    private static void viewActiveOrdersWorkflow() {
        System.out.println("\n--- Active Delivery Orders Registry ---");
        List<Order> orders = orderDao.getAllOrders();

        if (orders.isEmpty()) {
            System.out.println("No delivery orders found in the registry.");
            return;
        }

        // Aligned ASCII Table Format
        String format = "| %-4s | %-20s | %-10s | %-11s | %-10s |\n";
        String divider = "+------+----------------------+------------+-------------+------------+";

        System.out.println(divider);
        System.out.printf(format, "ID", "Customer Name", "Slim Qty", "Round Qty", "Status");
        System.out.println(divider);

        for (Order o : orders) {
            System.out.printf(format,
                    o.getId(),
                    truncate(o.getCustomerName(), 20),
                    o.getQtySlim(),
                    o.getQtyRound(),
                    o.getStatus()
            );
        }
        System.out.println(divider);
    }

    private static String truncate(String value, int maxLength) {
        if (value.length() <= maxLength) {
            return value;
        }
        return value.substring(0, maxLength - 3) + "...";
    }
}
