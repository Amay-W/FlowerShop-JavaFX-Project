# FlowerShop JavaFX Application 🌻🌷🌼

Welcome to my **FlowerShop JavaFX Application** repository! This project is a part of my journey to learn and enhance my skills in Java programming and JavaFX for creating GUI applications. Through this project, I explored various concepts such as data binding, event handling, file I/O, and form validations, while also focusing on designing user-friendly interfaces.

---

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Screenshots](#screenshots)
- [Technologies Used](#technologies-used)
- [How It Works](#how-it-works)
- [Setup Instructions](#setup-instructions)
- [Future Improvements](#future-improvements)
- [License](#license)

---

## Overview
This project is a fully interactive **FlowerShop application** built using JavaFX. The application allows users to:
1. Enter their details (Name, Email, Phone).
2. Browse a variety of flowers available for purchase.
3. Select quantities of flowers and generate a receipt.
4. Manage stock quantities of flowers dynamically based on purchases.

The purpose of this project was to simulate a small-scale e-commerce platform for learning JavaFX concepts and file-based data management.

---

## Features
- **Opening Screen**: User enters their name, email, and phone number with proper input validations.
- **Flower Selection**: Displays a table of flowers with prices, available quantities, and an interactive quantity selector.
- **Checkout Screen**: Shows a receipt of the purchase with the total cost.
- **Dynamic Inventory Management**: Stock is updated based on the user's purchase.
- **File I/O**: Uses `Flowers.txt` to store flower data and `CustomerDetails.txt` to record customer transactions.

---

## Screenshots

### Opening Screen
![Opening Screen](https://via.placeholder.com/600x400.png?text=Opening+Screen)

---

### Flower Selection Screen
![Flower Selection](https://via.placeholder.com/600x400.png?text=Flower+Selection+Screen)

---

### Checkout Screen
![Checkout Screen](https://via.placeholder.com/600x400.png?text=Checkout+Screen)

---

## Technologies Used
- **Java**: The programming language used for the backend logic.
- **JavaFX**: For building the GUI interface.
- **File I/O**: To manage flower inventory (`Flowers.txt`) and customer transaction records (`CustomerDetails.txt`).

---

## How It Works
1. **User Details**:
   - The application starts with an opening screen where users must enter their **name**, **email**, and **phone number**.
   - Validations ensure:
     - Name contains only alphabets.
     - Email is in the correct format.
     - Phone number is numeric and at least 10 digits long.

2. **Flower Selection**:
   - A table displays all available flowers, including their names, prices, and available quantities.
   - Users can select the desired quantity of each flower.

3. **Checkout**:
   - The checkout screen provides a detailed receipt, including customer details, selected flowers, total cost, and updated inventory.
   - Customer and transaction data are saved in `CustomerDetails.txt`.

4. **Dynamic Inventory Management**:
   - Once a purchase is confirmed, the flower stock is reduced dynamically in the `Flowers.txt` file.

---

## Setup Instructions
Follow these steps to set up and run the project:

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/FlowerShop-JavaFX-Project.git
   Open the project in your favorite Java IDE (e.g., IntelliJ IDEA or Eclipse or VScode).
2. Make sure you have JavaFX installed and configured in your IDE.
3. Run the FlowershopApp class to start the application.
