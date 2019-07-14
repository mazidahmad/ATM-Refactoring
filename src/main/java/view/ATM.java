/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Activity;
import controller.BalanceInquiry;
import controller.Screen;
import controller.Transfer;
import controller.changePin;
import model.Account;
import model.BankDatabase;
import model.CashDispenser;
import model.Deposit;
import model.DepositSlot;
import model.Keypad;
import model.Transaction;
import model.Withdrawal;

public class ATM {
   private boolean userAuthenticated; // whether user is authenticated
   private int currentAccountNumber; // current user's account number
   private Screen screen; // ATM's screen
   private Keypad keypad; // ATM's keypad
   private CashDispenser cashDispenser; // ATM's cash dispenser
   // myadd
   private DepositSlot depositSlot;
   // end add
   private BankDatabase bankDatabase; // account information database
   
   // constants corresponding to main menu options
   private static final int BALANCE_INQUIRY = 1;
   private static final int WITHDRAWAL = 2;
   private static final int DEPOSIT = 3;
   // myadd new
   private static final int TRANSFER = 4;
   private static final int ACTIVITY = 5;
   private static final int CHANGEPIN = 6;
   // end add
   private static final int EXIT = 0;

   // no-argument ATM constructor initializes instance variables
   public ATM() {
      userAuthenticated = false; // user is not authenticated to start
      currentAccountNumber = 0; // no current account number to start
      screen = new Screen(); // create screen
      keypad = new Keypad(); // create keypad 
      cashDispenser = new CashDispenser(); // create cash dispenser
      bankDatabase = new BankDatabase(); // create acct info database
   }

   // start ATM 
   public void run() {
      // welcome and authenticate user; perform transactions
      while (true) {
         // loop while user is not yet authenticated
         while (!userAuthenticated) {
             screen.displayMessageLine("\n================================");  
            screen.displayMessageLine("\nWelcome!");       
            authenticateUser(); // authenticate user
         }
         
         performTransactions(); // user is now authenticated
         userAuthenticated = false; // reset before next ATM session
         currentAccountNumber = 0; // reset before next ATM session
         screen.displayMessageLine("\nThank you! Goodbye!");
      }
   }

   // attempts to authenticate user against database
    private void authenticateUser() {
        int i = 0;
        screen.displayMessage("\nPlease enter your account number: ");
        int accountNumber = 0;
        
        while(!keypad.hasNextInput()){
            keypad.getLine();
            screen.displayMessageLine("\nInput is Invalid");
            screen.displayMessage("\nPlease re-enter your account number: ");
        }
        accountNumber = keypad.getInput();
        userAuthenticated = bankDatabase.authenticateUser(accountNumber);
        if (userAuthenticated){
            while (i < 3){
                Account Akun = bankDatabase.getAccount(accountNumber);
                screen.displayMessage("\nEnter your PIN: "); // prompt for PIN
                
                while(!keypad.hasNextInput()){
                    keypad.getLine();
                    screen.displayMessageLine("\nInput is Invalid");
                    screen.displayMessage("\nPlease re-enter your PIN : ");
                }
                int pin = keypad.getInput(); // input PIN
                
                if (bankDatabase.cekstatus(accountNumber) == true){
                    if (Akun.validatePIN(pin) == true){
                        currentAccountNumber = accountNumber; // save user's account #
                        i = 4;
                    } else {
                        i++;
                        screen.displayMessage("Invalid pin number.\n");
                    }
                } else {
                    i = 4;
                    screen.displayMessage("Account suspended");
                    userAuthenticated = false;
                }
            } 
            if (i == 3) {
                bankDatabase.Blokir(accountNumber, false);
                screen.displayMessage("3X WRONG PIN: YOUR ACCOUNT HAS BEEN BLOCKED");
                userAuthenticated = false;
            }
        } else screen.displayMessage("Invalid account number. Please try again.\n");
    } 

   // display the main menu and perform transactions
   private void performTransactions() {
      // local variable to store transaction currently being processed
      Transaction currentTransaction = null;
      
      boolean userExited = false; // user has not chosen to exit

      // loop while user has not chosen option to exit system
      while (!userExited) {
         // show main menu and get user selection
         int mainMenuSelection = displayMainMenu();

         // decide how to proceed based on user's menu selection
         switch (mainMenuSelection) {
            // user chose to perform one of three transaction types
            case BALANCE_INQUIRY:         

               // initialize as new object of chosen type
               currentTransaction = createTransaction(mainMenuSelection);
               currentTransaction.execute(); // execute transaction
               break; 
            // myadd
            case WITHDRAWAL:
                currentTransaction = createTransaction(mainMenuSelection);
                currentTransaction.execute(); // execute transaction
                break;
            case DEPOSIT:
                currentTransaction = createTransaction(mainMenuSelection);
                currentTransaction.execute(); // execute transaction
                break;
            // end add
            // myadd new
            case TRANSFER:
                currentTransaction = createTransaction(mainMenuSelection);
                currentTransaction.execute(); // execute transaction
                break;
            case ACTIVITY:
                currentTransaction = createTransaction(mainMenuSelection);
                currentTransaction.execute(); // execute transaction
                break;
            case CHANGEPIN:
                currentTransaction = createTransaction(mainMenuSelection);
                currentTransaction.execute();   // execute transaction
                break;
            // end add
            case EXIT: // user chose to terminate session
               screen.displayMessageLine("\nExiting the system...");
               userExited = true; // this ATM session should end
               break;
            default: // 
               screen.displayMessageLine(
                  "\nYou did not enter a valid selection. Try again.");
               break;
         }
      } 
   } 

   // display the main menu and return an input selection
   private int displayMainMenu() {
      screen.displayMessageLine("\nMain menu:");
      screen.displayMessageLine("1 - View my balance");
      screen.displayMessageLine("2 - Withdraw cash");
      screen.displayMessageLine("3 - Deposit funds");
      screen.displayMessageLine("4 - Transfer");
      screen.displayMessageLine("5 - My Activity");
      screen.displayMessageLine("6 - Change pin\n");
      screen.displayMessageLine("0 - Exit\n");
      screen.displayMessage("Enter a choice: ");
      return keypad.getInput(); // return user's selection
   }          
   private Transaction createTransaction(int type) {
      Transaction temp = null; 
          
      switch (type) {
         case BALANCE_INQUIRY: 
            temp = new BalanceInquiry(
               currentAccountNumber, screen, bankDatabase);
            break;
         // myadd
         case WITHDRAWAL:
            temp = new Withdrawal(currentAccountNumber, screen, bankDatabase, keypad, cashDispenser);
            break;
         case DEPOSIT:
            temp = new Deposit(currentAccountNumber, screen, bankDatabase, keypad, depositSlot);
            break;
         // myadd new
         case TRANSFER:
            temp = new Transfer(currentAccountNumber, screen, bankDatabase, keypad, depositSlot, cashDispenser);
            break;
         case ACTIVITY:
             temp = new Activity(currentAccountNumber, screen, bankDatabase);
             break;
         case CHANGEPIN:
             temp = new changePin(currentAccountNumber, screen, bankDatabase, keypad);
            break;
          //end add
         default:
            screen.displayMessageLine(
                  "\nYou did not enter a valid selection. Try again.");
            break;
         // end add
      }

      return temp;
   }
}