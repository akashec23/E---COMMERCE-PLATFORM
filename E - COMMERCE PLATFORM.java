import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    // Inner classes
    static class User {
        String username;
        String password;
        String email;

        User(String username, String password, String email) {
            this.username = username;
            this.password = password;
            this.email = email;
        }
    }

    static class Product {
        int id;
        String name;
        double price;

        Product(int id, String name, double price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }

        @Override
        public String toString() {
            return id + ". " + name + " - $" + price;
        }
    }

    // Data structures for user and product management
    static HashMap<String, User> users = new HashMap<>();
    static ArrayList<Product> products = new ArrayList<>();
    static ArrayList<Product> cart = new ArrayList<>();

    // Main window frame
    static JFrame frame = new JFrame("E-Commerce Platform");

    // Scanner for user input
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        seedProducts();
        setupMainMenu();
    }

    // Seed initial products
    static void seedProducts() {
        products.add(new Product(1, "Laptop", 800.0));
        products.add(new Product(2, "Smartphone", 500.0));
        products.add(new Product(3, "Headphones", 50.0));
    }

    // Setup main menu with Swing components
    static void setupMainMenu() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new FlowLayout());

        JLabel welcomeLabel = new JLabel("Welcome to the E-Commerce Platform!");
        frame.add(welcomeLabel);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> showRegisterForm());
        frame.add(registerButton);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> showLoginForm());
        frame.add(loginButton);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        frame.add(exitButton);

        frame.setVisible(true);
    }

    // Show the register form
    static void showRegisterForm() {
        JFrame registerFrame = new JFrame("Register");
        registerFrame.setSize(300, 200);
        registerFrame.setLayout(new GridLayout(4, 2));

        JTextField usernameField = new JTextField();
        JTextField passwordField = new JPasswordField();
        JTextField emailField = new JTextField();

        registerFrame.add(new JLabel("Username:"));
        registerFrame.add(usernameField);

        registerFrame.add(new JLabel("Password:"));
        registerFrame.add(passwordField);

        registerFrame.add(new JLabel("Email:"));
        registerFrame.add(emailField);

        JButton submitButton = new JButton("Register");
        submitButton.addActionListener(e -> registerUser(usernameField.getText(), passwordField.getText(), emailField.getText(), registerFrame));
        registerFrame.add(submitButton);

        registerFrame.setVisible(true);
    }

    // Handle user registration
    static void registerUser(String username, String password, String email, JFrame registerFrame) {
        if (users.containsKey(username)) {
            JOptionPane.showMessageDialog(registerFrame, "Username already exists. Try again.");
        } else {
            users.put(username, new User(username, password, email));
            JOptionPane.showMessageDialog(registerFrame, "Registration successful.");
            registerFrame.dispose();
        }
    }

    // Show the login form
    static void showLoginForm() {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setSize(300, 200);
        loginFrame.setLayout(new GridLayout(3, 2));

        JTextField usernameField = new JTextField();
        JTextField passwordField = new JPasswordField();

        loginFrame.add(new JLabel("Username:"));
        loginFrame.add(usernameField);

        loginFrame.add(new JLabel("Password:"));
        loginFrame.add(passwordField);

        JButton submitButton = new JButton("Login");
        submitButton.addActionListener(e -> loginUser(usernameField.getText(), passwordField.getText(), loginFrame));
        loginFrame.add(submitButton);

        loginFrame.setVisible(true);
    }

    // Handle user login
    static void loginUser(String username, String password, JFrame loginFrame) {
        if (users.containsKey(username) && users.get(username).password.equals(password)) {
            JOptionPane.showMessageDialog(loginFrame, "Login successful.");
            loginFrame.dispose();
            userDashboard();
        } else {
            JOptionPane.showMessageDialog(loginFrame, "Invalid credentials. Try again.");
        }
    }

    // User dashboard after login
    static void userDashboard() {
        JFrame dashboardFrame = new JFrame("User Dashboard");
        dashboardFrame.setSize(400, 300);
        dashboardFrame.setLayout(new FlowLayout());

        JButton browseButton = new JButton("Browse Products");
        browseButton.addActionListener(e -> browseProducts());
        dashboardFrame.add(browseButton);

        JButton cartButton = new JButton("View Cart");
        cartButton.addActionListener(e -> viewCart());
        dashboardFrame.add(cartButton);

        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.addActionListener(e -> checkout());
        dashboardFrame.add(checkoutButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> dashboardFrame.dispose());
        dashboardFrame.add(logoutButton);

        dashboardFrame.setVisible(true);
    }

    // Browse products (GUI)
    static void browseProducts() {
        JFrame browseFrame = new JFrame("Browse Products");
        browseFrame.setSize(400, 300);
        browseFrame.setLayout(new BoxLayout(browseFrame.getContentPane(), BoxLayout.Y_AXIS));

        for (Product product : products) {
            JButton productButton = new JButton(product.toString());
            productButton.addActionListener(e -> addToCart(product));
            browseFrame.add(productButton);
        }

        browseFrame.setVisible(true);
    }

    // Add product to cart
    static void addToCart(Product product) {
        cart.add(product);
        JOptionPane.showMessageDialog(frame, "Product added to cart.");
    }

    // View cart (GUI)
    static void viewCart() {
        JFrame cartFrame = new JFrame("Your Cart");
        cartFrame.setSize(400, 300);
        cartFrame.setLayout(new BoxLayout(cartFrame.getContentPane(), BoxLayout.Y_AXIS));

        if (cart.isEmpty()) {
            cartFrame.add(new JLabel("Your cart is empty."));
        } else {
            for (Product product : cart) {
                cartFrame.add(new JLabel(product.toString()));
            }
        }

        cartFrame.setVisible(true);
    }

    // Checkout (simplified)
    static void checkout() {
        JFrame checkoutFrame = new JFrame("Checkout");
        checkoutFrame.setSize(300, 200);
        checkoutFrame.setLayout(new FlowLayout());

        double total = cart.stream().mapToDouble(p -> p.price).sum();
        checkoutFrame.add(new JLabel("Total: $" + total));

        JButton payButton = new JButton("Pay");
        payButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(checkoutFrame, "Payment successful. Thank you for your order.");
            cart.clear();
            checkoutFrame.dispose();
        });
        checkoutFrame.add(payButton);

        checkoutFrame.setVisible(true);
    }
      }
