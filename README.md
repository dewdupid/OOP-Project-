# Inventory Management System (JavaFX)
A desktop-based Inventory Management System built using **JavaFX** and the **Model-View-Controller (MVC)** architectural pattern. 
The application features user authentication, dynamic data visualization through charts, and multi-tenant isolated file storage where 
each user's inventory data is managed independently.

## Features

### 1. User Authentication & Session Isolation
* **Dual-Mode System:** Seamless switching between Login and Account Registration screens.
* **Input Validation:** Real-time checking for duplicate usernames and empty fields.
* **Isolated Multi-Tenancy:** Each registered account is allocated its own private database file (e.g., `dhia_inventory.txt`)
* inside the `src/inventory/` path, ensuring absolute data privacy.

### 2. Live Insights & Dashboard Metrics
* **Dynamic Calculations:** Displays up-to-the-minute statistics for Total Products, Low Stock alerts, Out of Stock counts, Average Price,
  and Peak Inventory Pricing.
* **Visual Data Analytics:** Features an interactive `BarChart` tracking overall stock levels grouped automatically by category.
* **Live Search Filter:** Instant key-matching filter to narrow down inventory by product name or category.

### 3. Comprehensive Product Transaction Management
* **CRUD Capabilities:** Integrated pop-up management window to **Add**, **Edit**, and **Delete** stock records.
* **Robust ID Validation:** Scans current active collections to block ID duplication conflicts during creation.
* **Polymorphic Product Attributes:** Utilizes Object-Oriented inheritance to treat items distinctly based on category,
  auto-appending specific status indicators (e.g., *"Expired/Out of Stock"* for perishables).

## Project Structure

```text
src/
├── inventory/                  # Runtime User Database Files (.txt)
└── main/
    ├── java/
    │   └── oop/                # Core Application Logic Root Package
    │       ├── controller/     # Presentation Logic Layers (Action Handlers)
    │       │   ├── LoginController.java
    │       │   ├── DashboardController.java
    │       │   └── TransactionController.java
    │       ├── handler/        # Execution Entry Drivers (Launchers)
    │       │   ├── Main.java
    │       │   └── Launcher.java
    │       └── model/          # Business Entities (Data Layout Structures)
    │           └── Product.java
    └── resources/
        └── oop/                # Declarative View Presentation Frontends
            ├── Login.fxml
            ├── Dashboard.fxml
            ├── Transaction.fxml
            └── login-style.css # Custom fonts, colors, and UI styling
```
### Step-by-Step User Guide
## Step 1: Accessing the System
i)First-Time Users: Click the "Don't have an account? Sign up here" hyperlink. Enter a unique username and password, then click Register.
                  The system will notify you of a successful setup and switch back to the login view automatically.
ii)Existing Users / Default Admin: Enter your registered credentials. (For testing, you can use Username: admin and Password: 1234). Click Login.

## Step 2: Exploring the Dashboard
Upon entering, your individual user workspace loads:

  Initial Setup: If your account is brand new, the system immediately pre-seeds your file with a list of default starter grocery and household items 
  so the UI isn't blank.

  Review Metrics: Look at the top informational cards to track your current Total Products, Low Stock Warning Alerts, 
  Out of Stock Counters, and the Total Value (calculated in RM).

  Analyze the Graph: The bar chart updates dynamically to show you exactly how many stock units you have remaining per product category.

  Search / Filter: Type an item name or category into the search field to quickly isolate specific items in the main inventory table.

## Step 3: Managing Stock Transactions
Click the main management button to launch the Product Management System pop-up window:

  To Add a Product: Fill out the ID, Name, Category, Quantity, and Price fields, then click Add. The system will block you if the ID is already taken.

  To Modify a Product: Click on any row in the management table. The fields will automatically fill with that item's data. Modify the quantity, 
  price, or name, and click Edit.

  To Delete a Product: Click on a row, click Delete, and select Yes on the pop-up confirmation box.

  Return: Click Return to Dashboard to close the window. All data is saved directly to your text file instantly, and the main dashboard values 
  recalculate automatically.

## Step 4: Signing Out
  When you are done, click the Back to Login button. This safely clears your active user memory variables, 
  closes your inventory workspace file pathway, and returns the application to the startup login screen for the next user.
