Stock Market Application

Overview
The Stock Market Application is a two-module system built using Spring Boot, Thymeleaf, and MySQL. It allows admin and user roles with different functionalities:

Admin:
Can log in using credentials stored in a properties file.
Can add stocks by entering a stock ticker, which fetches details from the Alpha Vantage API.
Can manage stocks, view an overview of stock transactions (buy, sell), and see platform fees collected.

User:
Can register by verifying their email with an OTP.
Can log in after registration and view stocks available for purchase.
Can recharge their wallet using the Razorpay API to purchase stocks.
Can view their portfolio and sell stocks.

This application handles sessions for authentication and does not use traditional security frameworks like Spring Security.

Technologies Used
Backend: Spring Boot, Spring Data JPA
Frontend: Thymeleaf, HTML, CSS
Database: MySQL

APIs:
Alpha Vantage API (for fetching stock data)
Razorpay API (for wallet recharges)
Java Mail Sender (for OTP email verification)

Features
Admin Features
Login: Admin can log in with credentials stored in the application’s application.properties file.
Add Stock: Admin can add stocks by entering the ticker symbol. Stock details are fetched from Alpha Vantage API using RestTemplate and saved into the database.
Manage Stocks: Admin can manage stock data and view an overview of all transactions, including:
Total transactions (stocks bought and sold)
Platform fee collected from transactions

User Features
Registration: Users can register by entering their details. OTP verification is done via email using Java Mail Sender.
Login: Users can log in after successful registration.
Wallet Recharge: Users can recharge their wallet using the Razorpay API for purchasing stocks.
Stock Purchase: Users can view available stocks and buy them if they have enough funds in their wallet.
Portfolio Management: Users can view their portfolio and sell stocks when desired.

Installation
Prerequisites
Java (JDK 17 or higher)
Maven for dependency management
MySQL database
Razorpay API Key (for wallet recharge functionality)
Alpha Vantage API Key (for fetching stock details)
Email API key (for OTP email verification)

Steps
Clone the repository:
git clone https://github.com/saishkulkarni/stock-market-springboot-thymeleaf.git

Set up the MySQL Database:
Create a new database for the application.
Update the application.properties file with your MySQL database credentials.

Example:
spring.datasource.url=jdbc:mysql://localhost:3306/stock_market
spring.datasource.username=root
spring.datasource.password=password

Configure External APIs:
Alpha Vantage API: Obtain an API key and add it to the application.properties file.
Razorpay API: Obtain your Razorpay API key and add it to the application.properties file.

Run the Application:
mvn spring-boot:run

Access the Application:
Visit http://localhost in your web browser to access the application.

Database Schema
The application uses a MySQL database with the following key tables:
users: Stores user information (email, password, wallet balance).
stocks: Stores information about the stocks available (ticker, stock details).
transactions: Stores transactions related to stock purchases and sales.
admin_details: Stores the platform fees collected from users.

API Integration
Alpha Vantage API
The application uses the Alpha Vantage API to fetch stock data based on the ticker symbol provided by the admin.
The data includes stock prices, company information, etc., which are then stored in the database.

Razorpay API
Razorpay API is used for wallet recharge functionality. Users can add funds to their wallet using Razorpay.

Java Mail Sender
The application uses Java Mail Sender to send OTP emails to users during the registration process.

Session Management
Instead of using Spring Security, the application manages user sessions directly:
When users log in, a session is created to track their logged-in state.
Admin and user sessions are handled separately to ensure proper access control.

Contributing
Feel free to fork the repository, submit issues, or create pull requests for improvements or bug fixes. Contributions are always welcome!