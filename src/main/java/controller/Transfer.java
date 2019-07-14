/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Account;
import model.BankDatabase;
import model.CashDispenser;
import model.DepositSlot;
import model.Keypad;
import model.Transaction;

public class Transfer extends Transaction {
   private double amount; // amount to transfer
   private Keypad keypad; // reference to keypad
   private DepositSlot depositSlot; // reference to deposit slot
   private final static int CANCELED = 0; // constant for cancel option
   private int destinationNo;
   private int sourceNo;
   private Account destAccount;
   private BankDatabase bankDatabase; // account information database
   private CashDispenser cashDispenser; // reference to cash dispenser
   
   public Transfer(int userAccountNumber, Screen atmScreen, 
      BankDatabase atmBankDatabase, Keypad atmKeypad, 
      DepositSlot atmDepositSlot, CashDispenser atmCashDispenser) {

      // initialize superclass variables
      super(userAccountNumber, atmScreen, atmBankDatabase);
      
      depositSlot = atmDepositSlot;
      keypad = atmKeypad;
      destinationNo = 0;
      bankDatabase = atmBankDatabase;
      sourceNo = userAccountNumber;
      amount = 0;
      cashDispenser = atmCashDispenser;
   } 

   // perform transaction
   @Override
   public void execute() {
       Screen screen = getScreen();
       double availableBalance;
       
       destinationNo = promptForDestinationAccount();
       if(destinationNo != CANCELED){
           destAccount = bankDatabase.getAccount(destinationNo);
           if(destAccount != null && destinationNo != sourceNo){
               amount = promptForAmount();
               if(amount != CANCELED){
                   availableBalance = bankDatabase.getAvailableBalance(sourceNo);
                   if(amount <= availableBalance){
//                       cashDispenser.dispenseCash((int)amount);
                       bankDatabase.getAccount(sourceNo).credit(amount);
                       bankDatabase.getAccount(destinationNo).debit(amount);
                       screen.displayMessage("\n Transfer success from " + super.getAccountNumber() + " to " + destinationNo + "\n");
                   }
                   else {
                       screen.displayMessageLine("\nInsufficent Funds...");
                   }
               }
           }
           else{
               screen.displayMessageLine(
                  "\nAccount destination invalid...");
           }
       }
   }

   private int promptForDestinationAccount() {
      Screen screen = getScreen(); // get reference to screen

      screen.displayMessage("\nPlease enter destination account number (or 0 to cancel): ");
      int input;
      
      while(!keypad.hasNextInput()){
            keypad.getLine();
            screen.displayMessageLine("\nInput is Invalid");
            screen.displayMessage("Please re-enter destination account number (or 0 to cancel): ");
        }
      
      
      input = keypad.getInput(); // receive input of deposit amount
      
      if (input == CANCELED) {
        screen.displayMessageLine(
           "\nCanceling transaction...");
         return CANCELED;
      }
      else {
         return (int) input; 
      }
   }
   
   private double promptForAmount(){
       Screen screen = getScreen(); // get reference to screen

      screen.displayMessage("\nPlease enter amount to transfer (or 0 to cancel): ");
      int input;
      
      while(!keypad.hasNextInput()){
            keypad.getLine();
            screen.displayMessageLine("\nInput is Invalid");
            screen.displayMessage("Please re-enter amount to transfer (or 0 to cancel): ");
        }
      
      input = keypad.getInput(); // receive input of deposit amount
      if( input < 0){
        screen.displayMessage("\nThe Number Must be Postive");

      }
      
      if (input <= CANCELED) {
        screen.displayMessageLine(
           "\nCanceling transaction...");
         return CANCELED;
      }
      else {
         return (double) input; // return dollar amount
      }
   }
}