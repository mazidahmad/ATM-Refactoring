/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.AccountLog;
import model.BankDatabase;
import model.Transaction;

public class Activity extends Transaction {
   private BankDatabase bankDatabase; // account information database
    public Activity(int userAccountNumber, Screen atmScreen, BankDatabase atmBankDatabase) {
        super(userAccountNumber, atmScreen, atmBankDatabase);
        bankDatabase = atmBankDatabase;
        
    }
    
    
    public void execute(){
        Screen screen = new Screen();
        
        screen.displayMessage("\n Your Account Number: " + super.getAccountNumber());
        screen.displayMessage("\n Your Activity: ");
//        bankDatabase.getAccount(super.getAccountNumber()).getAccountLog();
        for(AccountLog acl : bankDatabase.getAccountLog(super.getAccountNumber())){
            if(acl.isIsDeposit() == true ){
                screen.displayMessage("\n Debit \t ");
                screen.displayDollarAmount(acl.getAmount());
                screen.displayMessage("\t\t\t");
            }
            else{
                screen.displayMessage("\n Credit \t\t\t ");
                screen.displayDollarAmount(acl.getAmount());
                screen.displayMessage("\t");
            }
            
            screen.displayMessage(" Total Balance ");
            screen.displayDollarAmount(acl.getTotalBalance());
        }
        screen.displayMessage("\n");
    }
}