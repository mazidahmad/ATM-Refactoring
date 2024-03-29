/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.Screen;

public class Withdrawal extends Transaction {
   private int amount; // amount to withdraw
   private Keypad keypad; // reference to keypad
   private CashDispenser cashDispenser; // reference to cash dispenser
   
   // constant corresponding to menu option to cancel
   private final static int CANCELED = 7;

   // Withdrawal constructor
   public Withdrawal(int userAccountNumber, Screen atmScreen, 
      BankDatabase atmBankDatabase, Keypad atmKeypad, 
      CashDispenser atmCashDispenser) {

      // initialize superclass variables
      super(userAccountNumber, atmScreen, atmBankDatabase);
      
      // myadd
      amount = 0;
      keypad = atmKeypad;
      cashDispenser = atmCashDispenser;
      // end add
   }

   // perform transaction
   @Override
   public void execute() {
       Screen screen = getScreen(); // get screen reference
       // myadd
       double availableBalance;
       amount = displayMenuOfAmounts();
//       if(amount != 0){
       if(amount != 0){
           if(cashDispenser.isSufficientCashAvailable(amount)){
               BankDatabase atmBankDatabase = super.getBankDatabase();
               availableBalance = atmBankDatabase.getAvailableBalance(super.getAccountNumber());
               
               if(amount <= availableBalance){
                   cashDispenser.dispenseCash(amount);
                   atmBankDatabase.getAccount(super.getAccountNumber()).credit(amount);
               } else screen.displayMessage("\nYou don't have enough balance!");
           } else screen.displayMessage("\nYou don't have enough balance!");
       }
       // end add
   } 

   // display a menu of withdrawal amounts and the option to cancel;
   // return the chosen amount or 0 if the user chooses to cancel
   private int displayMenuOfAmounts() {
      int userChoice = 0; // local variable to store return value

      Screen screen = getScreen(); // get screen reference
      
      // array of amounts to correspond to menu numbers
      int[] amounts = {0, 20, 40, 60, 100, 200};

      // loop while no valid choice has been made
      while (userChoice == 0) {
         // display the withdrawal menu
         screen.displayMessageLine("\nWithdrawal Menu:");
         screen.displayMessageLine("1 - $20");
         screen.displayMessageLine("2 - $40");
         screen.displayMessageLine("3 - $60");
         screen.displayMessageLine("4 - $100");
         screen.displayMessageLine("5 - $200");
         screen.displayMessageLine("6 - Custom withdrawal");
         screen.displayMessageLine("7 - Cancel transaction");
         screen.displayMessage("\nChoose a withdrawal amount: ");

         int input = keypad.getInput(); // get user input through keypad

         // determine how to proceed based on the input value
         switch (input) {
             // myadd
            case 1: // if the user chose a withdrawal amount 
            case 2: // (i.e., chose option 1, 2, 3, 4 or 5), return the
            case 3: // corresponding amount from amounts array
            case 4:
            case 5:
               screen.displayMessageLine(
                  "\nYour cash has been dispensed. Please take your cash now.");
               userChoice = amounts[input]; // save user's choice
               break;   
            case 6:
                getScreen().displayMessage("\nInput how much you will withdrawal : ");
                while(!keypad.hasNextInput()){
                    keypad.getLine();
                    screen.displayMessageLine("\nInput is Invalid");
                    screen.displayMessage("Input how much you will withdrawal : ");
                }
                    input = keypad.getInput();
                if( input < 0){
                    screen.displayMessage("\nThe Number Must be Postive");
                    
               }else if(input % 20 != 0){
                   screen.displayMessage("\nThe Number Must be Multiple of 20");
               }else userChoice = input; 
                
                break;
            case CANCELED: // the user chose to cancel
                
               screen.displayMessageLine(
                  "\nCanceling transaction...");
               userChoice = CANCELED; // save user's choice
               break;
            default: // the user did not enter a value from 1-6
               screen.displayMessageLine(
                  "\nInvalid selection. Try again.");
             //end add
         } 
      } 

      return userChoice; // return withdrawal amount or CANCELED
   }
   
   private int displayMenuOfAmountsIDR() {
      int userChoice = 0; // local variable to store return value

      Screen screen = getScreen(); // get screen reference
      
      // array of amounts to correspond to menu numbers
      int[] amounts = {0, 20000, 50000, 100000, 150000, 200000};

      // loop while no valid choice has been made
      while (userChoice == 0) {
         // display the withdrawal menu
         screen.displayMessageLine("\nWithdrawal Menu:");
         screen.displayMessageLine("2 - Rp50000");
         screen.displayMessageLine("3 - Rp100000");
         screen.displayMessageLine("4 - Rp150000");
         screen.displayMessageLine("5 - Rp200000");
         screen.displayMessageLine("7 - Cancel transaction");
         screen.displayMessage("\nChoose a withdrawal amount: ");

         int input = keypad.getInput(); // get user input through keypad

         // determine how to proceed based on the input value
         switch (input) {
            case 2: 
            case 3: 
            case 4:
            case 5:
                screen.displayMessageLine(
                  "\nYour cash has been dispensed. Please take your cash now.");
               userChoice = amounts[input]; // save user's choice
               break;       
            case CANCELED: // the user chose to cancel
               screen.displayMessageLine(
                  "\nCanceling transaction...");
               userChoice = CANCELED; // save user's choice
               break;
            default: // the user did not enter a value from 1-6
               screen.displayMessageLine(
                  "\nInvalid selection. Try again.");
         } 
      } 

      return userChoice; // return withdrawal amount or CANCELED
   }
} 