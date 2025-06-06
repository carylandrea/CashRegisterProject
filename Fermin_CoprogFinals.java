import java.util.*;
import java.util.regex.*;
import java.io.*;
import java.time.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Fermin_CoprogFinals {

    static Scanner scan = new Scanner(System.in);
    static String curUser = "";

    static ArrayList<String> uName = new ArrayList<>();
    static ArrayList<String> uPass = new ArrayList<>();
  
    static ArrayList<String> productNames = new ArrayList<>();
    static ArrayList<Double> productPrices = new ArrayList<>();
    static ArrayList<Integer> productQnty = new ArrayList<>();

    public static void saveTransaction() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.txt", true))) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            
            writer.write("=== TRANSACTION RECORD ===");
            writer.newLine();
            writer.write("Date & Time: " + now.format(formatter));
            writer.newLine();
            writer.write("Cashier/User: " + curUser);
            writer.newLine();
            writer.write("Items Purchased:");
            writer.newLine();
            
            double totalAmount = 0;
            for (int i = 0; i < productNames.size(); i++) {
                double itemTotal = productPrices.get(i) * productQnty.get(i);
                totalAmount += itemTotal;
                writer.write("  - " + productNames.get(i) + 
                           " | Quantity: " + productQnty.get(i) + 
                           " | Unit Price: P" + String.format("%.2f", productPrices.get(i)) + 
                           " | Subtotal: P" + String.format("%.2f", itemTotal));
                writer.newLine();
            }
            
            writer.write("Total Amount: P" + String.format("%.2f", totalAmount));
            writer.newLine();
            writer.write("=== END OF TRANSACTION ===");
            writer.newLine();
            writer.newLine();
            writer.newLine();
            
            System.out.println("Transaction successfully logged to transactions.txt");
            
        } catch (Exception e) {
            System.out.println("Error saving transaction to file.");
        }
    }

    public static void viewTransactionHistory() {
        try (BufferedReader reader = new BufferedReader(new FileReader("transactions.txt"))) {
            String line;
            System.out.println("\n============================================");
            System.out.println("          TRANSACTION HISTORY");
            System.out.println("============================================");
            boolean hasTransactions = false;
            
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                hasTransactions = true;
            }
            
            if (!hasTransactions) {
                System.out.println("No transaction records found.");
            }
            System.out.println("============================================\n");
            
        } catch (Exception e) {
            System.out.println("\nNo transaction history found. Complete a purchase to create transaction records.\n");
        }
    }

    public static void main(String[] args) {
        homePage();
    }

    public static void homePage() {
        while (true) {
            System.out.println("\n~~~~~ Welcome to Caryl's Cash Register System ~~~~~");
            System.out.println("1. Signup");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            try {
                System.out.print("\nEnter choice: ");
                int userChoice = scan.nextInt();
                scan.nextLine();

                switch (userChoice) {
                    case 1:
                        signUp();
                        break;
                    case 2:
                        login();
                        break;
                    case 3:
                        System.out.println("===== System ended. Have a Nice Day! =====");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please choose only from 1-3");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scan.nextLine();
            }
        }
    }

    public static void signUp() {
        String userName;
        String userPassword;

        System.out.println("\n~~~~~~~~~~~~~ Get Started. Register Now! ~~~~~~~~~~~~~");

        while (true) {
            try {
                System.out.println("Note: Username must be alphanumeric and 5 to 15 characters long.");
                System.out.print("Enter username: ");
                userName = scan.nextLine();

                if (userName.trim().isEmpty()) {
                    System.out.println("Username cannot be empty. Please try again.");
                    continue;
                }

                if (uName.contains(userName)) {
                    System.out.println("Username already exists. Please choose a different username.");
                    continue;
                }

                String reg1 = "[\\w]";
                String reg2 = "[\\d]";
                String reg3 = "^.{5,15}$";

                Pattern charac = Pattern.compile(reg1);
                Pattern dig = Pattern.compile(reg2);
                Pattern length = Pattern.compile(reg3);

                Matcher matcher1 = charac.matcher(userName);
                Matcher matcher2 = dig.matcher(userName);
                Matcher matcher3 = length.matcher(userName);

                if ((matcher1.find()) && (matcher2.find()) && (matcher3.find())) {
                    uName.add(userName);
                    break;
                } else {
                    System.out.println("\n===== Invalid username format. =====");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
                scan.nextLine();
            }
        }

        while (true) {
            try {
                System.out.println("\nNote: Password must contain at least one uppercase letter, one number, and be 8 to 20 characters long.");
                System.out.print("Enter password: ");
                userPassword = scan.nextLine();

                if (userPassword.trim().isEmpty()) {
                    System.out.println("Password cannot be empty. Please try again.");
                    continue;
                }

                String regex = "[a-zA-Z0-9]";
                String regex1 = "[A-Z]";
                String regex2 = "[0-9]";
                String regex3 = "^.{8,20}$";

                Pattern charac = Pattern.compile(regex);
                Pattern upperCase = Pattern.compile(regex1);
                Pattern dig = Pattern.compile(regex2);
                Pattern length = Pattern.compile(regex3);

                Matcher matcher = charac.matcher(userPassword);
                Matcher matcher1 = upperCase.matcher(userPassword);
                Matcher matcher2 = dig.matcher(userPassword);
                Matcher matcher3 = length.matcher(userPassword);

                if ((matcher.find()) && (matcher1.find()) && (matcher2.find()) && (matcher3.find())) {
                    uPass.add(userPassword);
                    break;
                } else {
                    System.out.println("\n===== Weak Password =====");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
                scan.nextLine();
            }
        }
        
        System.out.println("\n===== Account Registered! Please Login your Account. =====");
        login();
    }

    public static void login() {
        String un;
        String pw;
        int index;

        if ((uName.isEmpty()) && (uPass.isEmpty())) {
            System.out.println("\n~~~~~~~~~~~~~ Account not registered. Please create an account first! ~~~~~~~~~~~~~");
            return;
        }

        System.out.println("\n~~~~~~~~~~~~~ Login Page ~~~~~~~~~~~~~");
    
        while (true) {
            try {
                System.out.print("Username: ");
                un = scan.nextLine();
                
                if (un.trim().isEmpty()) {
                    System.out.println("Username cannot be empty. Please try again.");
                    continue;
                }
                
                if (uName.contains(un)) {
                    break;
                } else {
                    System.out.println("\n===== Invalid username! =====");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
                scan.nextLine();
            }
        }

        index = uName.indexOf(un);

        while (true) {
            try {
                System.out.print("Password: ");
                pw = scan.nextLine();

                if (pw.trim().isEmpty()) {
                    System.out.println("Password cannot be empty. Please try again.");
                    continue;
                }

                if (uPass.get(index).equals(pw)) {
                    curUser = un;
                    break;
                } else {
                    System.out.println("\nIncorrect password.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
                scan.nextLine();
            }
        }

        System.out.println("\n===== Login successful! Welcome " + curUser + "! =====");
        menuPage();
    }

    public static void menuPage() {
        while (true) {
            System.out.println("\n~~~~~ Cash Register Menu (User: " + curUser + ") ~~~~~");
            System.out.println("1. Add Product");
            System.out.println("2. Edit Product from Cart");
            System.out.println("3. Remove Product from Cart");
            System.out.println("4. Show Products in Cart");
            System.out.println("5. Payment");
            System.out.println("6. View Transaction History");
            System.out.println("7. Exit");
            
            try {
                System.out.print("Enter your choice: ");
                int choice = scan.nextInt();
                scan.nextLine();
                
                switch (choice) {
                    case 1:
                        addProduct();
                        break;
                    case 2:
                        editProduct();
                        break;
                    case 3:
                        removeProduct();
                        break;
                    case 4:
                        showCart();
                        break;
                    case 5:
                        acceptPayment();
                        break;
                    case 6: 
                        viewTransactionHistory();
                        break;
                    case 7:
                        System.out.println("Exiting... Thank you for using Caryl's Cash Register!");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scan.nextLine();
            }
        }
    }

    public static void changeAccount() {
        if (!productNames.isEmpty()) {
            System.out.println("\n===== Warning: You have items in your cart! =====");
            System.out.println("Changing account will clear your current cart.");
            
            String confirm;
            do {
                try {
                    System.out.print("Do you want to continue? (yes/no): ");
                    confirm = scan.nextLine().trim().toLowerCase();
                    if (!confirm.equals("yes") && !confirm.equals("no")) {
                        System.out.println("Invalid input. Please type 'yes' or 'no'.");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input. Please try again.");
                    scan.nextLine();
                    confirm = "";
                }
            } while (!confirm.equals("yes") && !confirm.equals("no"));
            
            if (confirm.equals("no")) {
                System.out.println("Account change cancelled. Returning to menu.");
                return;
            }
            
            productNames.clear();
            productPrices.clear();
            productQnty.clear();
            System.out.println("Cart cleared.");
        }
        
        System.out.println("\n===== Logging out user: " + curUser + " =====");
        curUser = "";
        
        System.out.println("Choose an option:");
        System.out.println("1. Login to existing account");
        System.out.println("2. Create new account");
        System.out.println("3. Return to main menu");
        
        try {
            System.out.print("Enter choice: ");
            int choice = scan.nextInt();
            scan.nextLine();
            
            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    signUp();
                    break;
                case 3:
                    homePage();
                    break;
                default:
                    System.out.println("Invalid choice. Returning to main menu.");
                    homePage();
            }
        } catch (Exception e) {
            System.out.println("Invalid input. Returning to main menu.");
            scan.nextLine();
            homePage();
        }
    }

    public static void addProduct() {
        String again;
        do {
            System.out.println("\n~ Please enter the product details ~");
            
            String name = "";
            while (name.trim().isEmpty()) {
                try {
                    System.out.print("Enter product name: ");
                    name = scan.nextLine();
                    if (name.trim().isEmpty()) {
                        System.out.println("Product name cannot be empty. Please try again.");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input. Please try again.");
                    scan.nextLine();
                }
            }

            double price = 0;
            while (true) {
                try {
                    System.out.print("Enter product price: P ");
                    price = scan.nextDouble();
                    if (price < 0) {
                        System.out.println("Price cannot be negative. Please try again.");
                    } else {
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter a number.");
                    scan.nextLine();
                }
            }

            int quantity = 0;
            while (true) {
                try {
                    System.out.print("Enter product quantity: ");
                    quantity = scan.nextInt();
                    if (quantity < 0) {
                        System.out.println("Quantity cannot be negative. Please try again.");
                    } else {
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter a number.");
                    scan.nextLine();
                }
            }

            scan.nextLine(); 

            productNames.add(name);
            productPrices.add(price);
            productQnty.add(quantity);
            
            System.out.println("\n===== Product added to cart. =====");

            do {
                try {
                    System.out.print("Would you like to add another product? (yes/no): ");
                    again = scan.nextLine().trim().toLowerCase();
                    if (!again.equals("yes") && !again.equals("no")) {
                        System.out.println("Invalid input. Please type 'yes' or 'no'.");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input. Please try again.");
                    scan.nextLine();
                    again = "";
                }
            } while (!again.equals("yes") && !again.equals("no"));

        } while (again.equals("yes"));
    }

    public static void editProduct() {
        if (productNames.isEmpty()) {
            System.out.println("\nYour cart is empty. Nothing to edit.");
            return;
        }

        String again;
        do {
            System.out.println("\n~~~~~~~~~~~~~ Products in Cart ~~~~~~~~~~~~~");
            System.out.println("Product Name         Quantity   Price      Total");
            System.out.println("---------------------------------------------");
            for (int i = 0; i < productNames.size(); i++) {
                double total = productPrices.get(i) * productQnty.get(i);
                System.out.printf("%d. %-15s %-10d P%-8.2f P%-8.2f\n", 
                    i + 1, productNames.get(i), productQnty.get(i), productPrices.get(i), total);
            }
            System.out.println("---------------------------------------------\n");

            int choice = -1;
            while (true) {
                try {
                    System.out.print("Enter the number of the product you want to update: ");
                    choice = scan.nextInt();
                    if (choice < 1 || choice > productNames.size()) {
                        System.out.println("Invalid product number. Please try again.");
                    } else {
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter a number.");
                    scan.nextLine();
                }
            }

            int newQuantity = -1;
            while (true) {
                try {
                    System.out.print("Enter the new quantity: ");
                    newQuantity = scan.nextInt();
                    if (newQuantity < 0) {
                        System.out.println("Quantity cannot be negative. Please try again.");
                    } else {
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter a number.");
                    scan.nextLine();
                }
            }

            scan.nextLine(); 
            productQnty.set(choice - 1, newQuantity);
            System.out.println("Product quantity updated successfully.");

            do {
                try {
                    System.out.print("Would you like to edit another product? (yes/no): ");
                    again = scan.nextLine().trim().toLowerCase();
                    if (!again.equals("yes") && !again.equals("no")) {
                        System.out.println("Invalid input. Please type 'yes' or 'no'.");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input. Please try again.");
                    scan.nextLine();
                    again = "";
                }
            } while (!again.equals("yes") && !again.equals("no"));

        } while (again.equals("yes"));
    }
    
    public static void removeProduct() {
        if (productNames.isEmpty()) {
            System.out.println("\n===== Your cart is empty. Nothing to remove. =====");
            return;
        }

        String again;
        do {
            if (productNames.isEmpty()) {
                System.out.println("\n===== Your cart is now empty. Returning to menu. =====");
                return;
            }

            System.out.println("\n~~~~~~~~~~~~~ Products in Cart ~~~~~~~~~~~~~");
            System.out.println("Product Name         Quantity   Price      Total");
            System.out.println("---------------------------------------------");
            for (int i = 0; i < productNames.size(); i++) {
                double total = productPrices.get(i) * productQnty.get(i);
                System.out.printf("%d. %-15s %-10d P%-8.2f P%-8.2f\n", 
                    i + 1, productNames.get(i), productQnty.get(i), productPrices.get(i), total);
            }
            System.out.println("---------------------------------------------\n");

            int choice = -1;
            while (true) {
                try {
                    System.out.print("Enter the number of the product you want to remove: ");
                    choice = scan.nextInt();
                    scan.nextLine(); 
                    if (choice < 1 || choice > productNames.size()) {
                        System.out.println("Invalid product number. Please try again.");
                    } else {
                        String removedProduct = productNames.get(choice - 1);
                        productNames.remove(choice - 1);
                        productPrices.remove(choice - 1);
                        productQnty.remove(choice - 1);
                        System.out.println("\n===== Product '" + removedProduct + "' removed from cart. =====");
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter a number.");
                    scan.nextLine();
                }
            }

            if (productNames.isEmpty()) {
                System.out.println("\n===== Your cart is now empty. Returning to menu. =====");
                return;
            }

            do {
                try {
                    System.out.print("Would you like to remove another product? (yes/no): ");
                    again = scan.nextLine().trim().toLowerCase();
                    if (!again.equals("yes") && !again.equals("no")) {
                        System.out.println("Invalid input. Please type 'yes' or 'no'.");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input. Please try again.");
                    scan.nextLine();
                    again = "";
                }
            } while (!again.equals("yes") && !again.equals("no"));

        } while (again.equals("yes"));
    }

    public static void showCart() {
        if (productNames.isEmpty()) {
            System.out.println("\n\n ===== Your cart is empty. ===== ");
        } else {
            System.out.println("\n~~~~~~~~~~~~~ Cart Items ~~~~~~~~~~~~~");
            System.out.println("Product Name         Quantity   Price      Total");
            System.out.println("----------------------------------------------");
            double totalPrice = 0;
            for (int i = 0; i < productNames.size(); i++) {
                double total = productPrices.get(i) * productQnty.get(i);
                System.out.printf("%-20s %-10d P%-8.2f P%-8.2f\n",
                        productNames.get(i), productQnty.get(i), productPrices.get(i), total);
                totalPrice += total;
            }
            System.out.println("----------------------------------------------");
            System.out.println("Total Price: P" + String.format("%.2f", totalPrice));
            System.out.println("----------------------------------------------\n");
        }
    }
    
    public static void acceptPayment() {
        if (productNames.isEmpty()) {
            System.out.println("\nYour cart is empty. Please add products first.");
            return;
        }

        System.out.println("\n~~~~~~~~~~~~~ Cart Items ~~~~~~~~~~~~~");
        System.out.println("Product Name         Quantity   Price      Total");
        System.out.println("----------------------------------------------");
        double totalPrice = 0;
        for (int i = 0; i < productNames.size(); i++) {
            double total = productPrices.get(i) * productQnty.get(i);
            System.out.printf("%-20s %-10d P%-8.2f P%-8.2f\n",
                    productNames.get(i), productQnty.get(i), productPrices.get(i), total);
            totalPrice += total;
        }
        System.out.println("----------------------------------------------");
        System.out.println("Total Price: P" + String.format("%.2f", totalPrice));
        System.out.println("----------------------------------------------\n");
        
        boolean paid = false;
        
        while (!paid) {
            try {
                System.out.print("Enter amount to pay: P");
                double payment = scan.nextDouble();
                scan.nextLine();
                
                if (payment >= totalPrice) {
                    double change = payment - totalPrice;
                    System.out.println("Payment accepted. Change to return: P" + String.format("%.2f", change));
                    paid = true;
                    
                    saveTransaction();

                    productNames.clear();
                    productPrices.clear();
                    productQnty.clear();
                    
                } else {
                    System.out.println("Insufficient payment. Type 'proceed' to retry or 'cancel' to cancel.");
                    String input = scan.nextLine();
                    if (input.equalsIgnoreCase("cancel")) {
                        System.out.println("Order canceled.");
                        return;
                    }
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scan.nextLine();
            }
        }

        String choice;
        do {
            try {
                System.out.println("\nWhat would you like to do next?");
                System.out.println("1. Continue with current account (" + curUser + ")");
                System.out.println("2. Change account");
                System.out.println("3. Exit system");
                System.out.print("Enter choice (1/2/3): ");
                choice = scan.nextLine().trim();
                
                switch (choice) {
                    case "1":
                        System.out.println("Continuing with account: " + curUser);
                        return; 
                    case "2":
                        changeAccount();
                        return;
                    case "3":
                        System.out.println("~~~~~~ Thank you for shopping and Have a Nice Day! ~~~~~~");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
                scan.nextLine();
                choice = "";
            }
        } while (!choice.equals("1") && !choice.equals("2") && !choice.equals("3"));
    }
}