import java.io.*;
import java.util.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class FlowershopApp extends Application {

    private final ObservableList<Flower> flowers = FXCollections.observableArrayList();
    private TableView<Flower> flowerTable;
    private Customer customer;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        showOpeningScreen(primaryStage);
    }

    private void showOpeningScreen(Stage primaryStage) {
        Label title = new Label("Welcome to AMAY's Flowershop\uD83C\uDF3B\uD83C\uDF3C\uD83C\uDF37");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 36));
        title.setTextFill(Color.DIMGRAY);

        GridPane inputGrid = new GridPane();
        inputGrid.setHgap(10);
        inputGrid.setVgap(10);
        inputGrid.setAlignment(Pos.CENTER);

        Label nameLabel = new Label("Name:");
        nameLabel.setFont(Font.font("Verdana", 14));
        TextField nameField = new TextField();
        nameField.setPromptText("Enter your name");

        Label emailLabel = new Label("Email:");
        emailLabel.setFont(Font.font("Verdana", 14));
        TextField emailField = new TextField();
        emailField.setPromptText("Enter your email");

        Label phoneLabel = new Label("Phone Number:");
        phoneLabel.setFont(Font.font("Verdana", 14));
        TextField phoneField = new TextField();
        phoneField.setPromptText("Enter your phone number");

        inputGrid.add(nameLabel, 0, 0);
        inputGrid.add(nameField, 1, 0);
        inputGrid.add(emailLabel, 0, 1);
        inputGrid.add(emailField, 1, 1);
        inputGrid.add(phoneLabel, 0, 2);
        inputGrid.add(phoneField, 1, 2);

        Button proceedButton = new Button("Proceed to Shop");
        proceedButton.setStyle("-fx-background-color: #A9A9A9; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 16px; -fx-border-radius: 5; -fx-background-radius: 5;");
        proceedButton.setOnAction(event -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();

            if (!name.matches("[a-zA-Z ]+") || !email.matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$") || !phone.matches("\\d{10,}")) {
                showAlert("Error", "Please provide valid details.\nName: Only alphabets allowed.\nEmail: Must be in valid format.\nPhone: Numeric and at least 10 digits.");
            } else {
                customer = new Customer(name, email, phone);
                showFlowerShopScreen(primaryStage);
            }
        });

        VBox layout = new VBox(20, title, inputGrid, proceedButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: linear-gradient(to bottom, #FAFAFA, #ECECEC);");

        Scene scene = new Scene(layout, 800, 600);
        primaryStage.setTitle("AMAY Flowershop");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showFlowerShopScreen(Stage primaryStage) {
        loadFlowers();

        Label title = new Label("Welcome, " + customer.getName() + "!");
        title.setStyle("-fx-font-size: 24px; -fx-text-fill: #555555; -fx-font-family: 'Verdana';");

        flowerTable = createFlowerTable();

        Button checkoutButton = new Button("Checkout");
        checkoutButton.setStyle("-fx-background-color: #A9A9A9; -fx-text-fill: white; -fx-font-size: 14px;");
        checkoutButton.setOnAction(event -> showCheckoutScreen(primaryStage));

        VBox layout = new VBox(20, title, flowerTable, checkoutButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 800, 600);
        primaryStage.setTitle("AMAY Flowershop");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private TableView<Flower> createFlowerTable() {
        TableView<Flower> table = new TableView<>();
        table.setItems(flowers);

        TableColumn<Flower, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        TableColumn<Flower, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPrice()));

        TableColumn<Flower, Integer> quantityColumn = new TableColumn<>("Available Quantity");
        quantityColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getQuantity()));

        TableColumn<Flower, Spinner<Integer>> selectQuantityColumn = new TableColumn<>("Select Quantity");
        selectQuantityColumn.setCellValueFactory(cellData -> {
            Flower flower = cellData.getValue();
            Spinner<Integer> spinner = new Spinner<>(0, flower.getQuantity(), 0);
            spinner.valueProperty().addListener((obs, oldValue, newValue) -> flower.setSelectedQuantity(newValue));
            return new SimpleObjectProperty<>(spinner);
        });

        table.getColumns().addAll(nameColumn, priceColumn, quantityColumn, selectQuantityColumn);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        return table;
    }

    private void showCheckoutScreen(Stage primaryStage) {
        List<Flower> selectedFlowers = new ArrayList<>();
        double totalCost = 0;

        for (Flower flower : flowers) {
            if (flower.getSelectedQuantity() > 0) {
                selectedFlowers.add(flower);
                totalCost += flower.getPrice() * flower.getSelectedQuantity();
            }
        }

        if (selectedFlowers.isEmpty()) {
            showAlert("Error", "No flowers selected. Please select flowers before checking out.");
            return;
        }

        saveCustomerDetails(selectedFlowers, totalCost);

        VBox receiptLayout = new VBox(10);
        receiptLayout.setAlignment(Pos.CENTER);
        receiptLayout.setPadding(new Insets(20));

        Label receiptTitle = new Label("Receipt");
        receiptTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        receiptLayout.getChildren().add(receiptTitle);

        Label customerDetails = new Label("Customer: " + customer.getName() + ", Email: " + customer.getEmail() + ", Phone: " + customer.getPhoneNumber());
        customerDetails.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        receiptLayout.getChildren().add(customerDetails);

        for (Flower flower : selectedFlowers) {
            Label flowerDetail = new Label(flower.getName() + " x " + flower.getSelectedQuantity() + " = $" + String.format("%.2f", (flower.getPrice() * flower.getSelectedQuantity())));
            receiptLayout.getChildren().add(flowerDetail);
            flower.setQuantity(flower.getQuantity() - flower.getSelectedQuantity());
            flower.setSelectedQuantity(0);
        }

        saveFlowers();

        Label totalLabel = new Label("Total: $" + String.format("%.2f", totalCost));
        totalLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        receiptLayout.getChildren().add(totalLabel);

        Button continueShoppingButton = new Button("Continue Shopping");
        continueShoppingButton.setOnAction(event -> showFlowerShopScreen(primaryStage));

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(event -> Platform.exit());

        HBox buttonsBox = new HBox(10, continueShoppingButton, exitButton);
        buttonsBox.setAlignment(Pos.CENTER);
        receiptLayout.getChildren().add(buttonsBox);

        Scene receiptScene = new Scene(receiptLayout, 600, 400);
        primaryStage.setScene(receiptScene);
        primaryStage.show();
    }

    private void loadFlowers() {
        try (Scanner input = new Scanner(new File("Flowers.txt"))) {
            while (input.hasNextLine()) {
                String[] parts = input.nextLine().split(",");
                String name = parts[0];
                double price = Double.parseDouble(parts[1]);
                int quantity = Integer.parseInt(parts[2].trim());
                flowers.add(new Flower(name, price, quantity));
            }
        } catch (FileNotFoundException e) {
            showAlert("Error", "Flowers file not found.");
        }
    }

    private void saveFlowers() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("Flowers.txt"))) {
            for (Flower flower : flowers) {
                writer.println(flower.getName() + "," + flower.getPrice() + "," + flower.getQuantity());
            }
        } catch (IOException e) {
            showAlert("Error", "Failed to save flowers.");
        }
    }

    private void saveCustomerDetails(List<Flower> selectedFlowers, double totalCost) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("CustomerDetails.txt", true))) {
            writer.println("Customer: " + customer.getName() + ", Email: " + customer.getEmail() + ", Phone: " + customer.getPhoneNumber());
            for (Flower flower : selectedFlowers) {
                writer.println(flower.getName() + " x " + flower.getSelectedQuantity() + " = $" + String.format("%.2f", (flower.getPrice() * flower.getSelectedQuantity())));
            }
            writer.println("Total: $" + String.format("%.2f", totalCost));
            writer.println("----------------------------------------");
        } catch (IOException e) {
            showAlert("Error", "Failed to save customer details.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}