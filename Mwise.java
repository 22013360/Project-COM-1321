/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mwise;

/**
 *
 * @author promi
 */

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Mwise {
    public static void main(String[] args) {
        // Create a DecimalFormat instance for currency formatting
        DecimalFormat df = new DecimalFormat("#,###.00");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to The Money Wise Application!");
        System.out.println("");

        System.out.print("Please enter your gross monthly income: ");
        double grossIncome = scanner.nextDouble();

        System.out.print("Enter your estimated monthly tax deductions: ");
        double taxDeductions = scanner.nextDouble();

        // Initialize current balance after tax deductions
        double currentBalance = grossIncome - taxDeductions;
        System.out.println("Your available balance after tax deductions is " + df.format(currentBalance));

        System.out.println("Enter your estimated monthly expenditure on each of the following:");
        
        

        // Create a list to hold the expenses
        List<Double> expenses = new ArrayList<>();
        List<String> expenseNames = new ArrayList<>();

        // Define expense categories
        String[] expenseCategories = {
            "Groceries", 
            "Water and lights", 
            "Travel costs", 
            "Cellphone and telephone", 
            "Other costs"
          
        
        };

        // Loop through categories and get expenses
        for (String category : expenseCategories) {
            System.out.print("Enter amount spent on " + category + ": ");
            double amount = scanner.nextDouble();
            expenses.add(amount);
            expenseNames.add(category);
            
            // Update and display the balance after each expense
            currentBalance -= amount;
        }

        // Abstract class for Property (Base Class)
        abstract class Expense {
            abstract double calculateMonthlyCost();
        }

        // Rent class (Inherits Property)
        class Rent extends Expense {
            private double rentAmount;

            public Rent(double rentAmount) {
                this.rentAmount = rentAmount;
            }
            @Override
            double calculateMonthlyCost() {
                return rentAmount;
            }
        }

        // HomeLoan class (Inherits Property)
        class HomeLoan extends Expense {
            private double homePrice, deposit, interestRate, months;

            public HomeLoan(double homePrice, double deposit, double interestRate, double months) {
                this.homePrice = homePrice;
                this.deposit = deposit;
                this.interestRate = interestRate;
                this.months = months;
            }

            @Override
            double calculateMonthlyCost() {
                double loanBalance = homePrice - deposit;
                double monthlyRates = interestRate / 100 / 12;
                return loanBalance * monthlyRates / (1 - Math.pow(1 + monthlyRates, -months));
            }
        }

        // Abstract class for Vehicle (Base Class)
        abstract class Vehicle {
            abstract double calculateMonthlyCost();
        }

// Car class (Inherits Vehicle)
        class Car extends Vehicle {
            private double price, deposit, interestRate, insurance;

            public Car(double price, double deposit, double interestRate, double insurance) {
                this.price = price;
                this.deposit = deposit;
                this.interestRate = interestRate;
                this.insurance = insurance;
            }

            @Override
            double calculateMonthlyCost() {
                double loanAmount = price - deposit;
                double monthlyInterestRate = interestRate / 100 / 12;
                double monthlyPayment = (loanAmount * monthlyInterestRate) / (1 - Math.pow(1 + monthlyInterestRate, -60));
                return monthlyPayment + insurance;
            }
        }

        Expense property = null;

        System.out.print("To rent an accommodation, press 1; To purchase a property, press 2: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                System.out.print("Enter the rental price of the property: "); 
                double rent = scanner.nextDouble();
                
                // Add rent to the expenses list
                expenses.add(rent);
                expenseNames.add("Rent");

                // Update and display balance after rent
                currentBalance -= rent;

                System.out.println("Do you wish to purchase a car?");
                System.out.print("If yes, press 1; if not, press 0: ");
                int vehicle = scanner.nextInt();

                if (vehicle == 1) {
                    System.out.println("Enter the following values for vehicle financing:");
                    System.out.println("a. Model and make: ");
                    System.out.print("(i) Model: ");
                    String model = scanner.next(); // Assuming model is a string input
                    System.out.print("(ii) Model: ");
                    String make = scanner.next(); // Assuming model is a string input
                    System.out.print("b. Purchase price: ");      
                    double price = scanner.nextDouble();
                    System.out.print("c. Total deposit: "); 
                    double totalDeposit = scanner.nextDouble();
                    System.out.print("d. Interest rate: ");
                    double interestRate = scanner.nextDouble();
                    System.out.print("e. Estimated insurance premium: ");       
                    double insurance = scanner.nextDouble();

                    double monthlyInterestRate = interestRate / 100 / 12;
                    double loanAmount = price - totalDeposit;
                    double monthlyPayment = (loanAmount * monthlyInterestRate) / (1 - Math.pow(1 + monthlyInterestRate, -60));
                    double carPayment = monthlyPayment + insurance;

                    // Add car payment to the expenses list
                    expenses.add(carPayment);
                    expenseNames.add("Car Payment");

                    System.out.println("The total monthly cost of buying the car including insurance premium after 5 years is " + df.format(carPayment)); 

                    // Update and display balance after car payment
                    currentBalance -= carPayment;

                    if ((grossIncome - currentBalance) > 0.75 * grossIncome) {
                        System.out.println("Total expenses exceed 75% of your gross income!"); 
                    } else {
                        System.out.println("Your finances look healthy, you are safe to purchase the vehicle.");   
                    }
                }
                break;

            case 2:
                System.out.print("Enter the purchase price of the property: ");
                double homeLoan = scanner.nextDouble();
                System.out.println("Enter the following:");
                System.out.print("i. Total deposit of the property: ");
                double deposit = scanner.nextDouble();
                System.out.print("ii. Interest rate: ");
                double interest = scanner.nextDouble();
                System.out.print("iii. Number of months from 240 to 360 to repay your home loan: ");
                double months = scanner.nextDouble();  

                double loanBalance = homeLoan - deposit;
                double monthlyRates = interest / 100 / 12;
                double loanRepayments = loanBalance * monthlyRates / (1 - Math.pow(1 + monthlyRates, -months));

                // Add loan repayments to the expenses list
                expenses.add(loanRepayments);
                expenseNames.add("Home Loan Repayment");
                System.out.println("Monthly home loan repayments will be " + df.format(loanRepayments));

                // Update and display balance after home loan
                currentBalance -= loanRepayments;

                if (loanRepayments > grossIncome / 3) {
                    System.out.println("We regret to inform you that your home loan is unlikely, monthly home loan repayments are more than 1/3 your gross monthly income");
                } else {
                    System.out.println("Congratulations! Your home loan is approved");
                }

                System.out.println("Do you wish to purchase a car?");
                System.out.print("If yes, press 1; if not, press 0: ");   
                int choicee = scanner.nextInt();

                if (choicee == 1) {
                    System.out.println("Enter the following values for vehicle financing:");
                    System.out.println("a. Model and make: ");
                    System.out.print("(i) Model: ");
                    String CarModel = scanner.next(); // Assuming model is a string input
                    System.out.print("(ii) Make: ");
                     String CarMake = scanner.next(); // Assuming model is a string input
                    System.out.print("b. Purchase price: ");      
                    double priceCar = scanner.nextDouble();
                    System.out.print("c. Total deposit: "); 
                    double totalDepositCar = scanner.nextDouble();
                    System.out.print("d. Interest rate: ");
                    double interestRateCar = scanner.nextDouble();
                    System.out.print("e. Estimated insurance premium: ");
                    double insuranceCar = scanner.nextDouble();

                    double monthlyInterestRateCar = interestRateCar / 100 / 12;
                    double loanAmountCar = priceCar - totalDepositCar;
                    double monthlyPaymentCar = (loanAmountCar * monthlyInterestRateCar) / (1 - Math.pow(1 + monthlyInterestRateCar, -60));
                    double carPaymentFinal = monthlyPaymentCar + insuranceCar;

                    // Add car payment to the expenses list
                    expenses.add(carPaymentFinal);
                    expenseNames.add("Car Payment");

                    System.out.println("The total monthly cost of buying the car including insurance premium after 5 years is " + df.format(carPaymentFinal)); 

                    // Update and display balance after car payment
                    currentBalance -= carPaymentFinal;
                }
                break;
        }

        // Display the final list of expenses
        System.out.println("A complete list of your expenses is as follows:"); 
        for (int i = 0; i < expenses.size(); i++) {
             System.out.println(expenseNames.get(i) +": R"+df.format(expenses.get(i)));
        }
       System.out.println("Final available balance: " + df.format(currentBalance)); // Displays balance after adding Total expenditure with tax deductions and subtract from gross incomes
            
        System.out.println("Thank you for choosing The Money Wise Application. Goodbye!");

        scanner.close(); // Close the scanner
    }
}
