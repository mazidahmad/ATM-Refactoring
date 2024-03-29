/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.LinkedHashSet;

public class Account {
   private int accountNumber; // account number
   private int pin; // PIN for authentication
   private double availableBalance; // funds available for withdrawal
   private double totalBalance; // funds available & pending deposits
   private LinkedHashSet<AccountLog> accountLogs = new LinkedHashSet<>();
   private boolean Status;

   // Account constructor initializes attributes
   public Account(int theAccountNumber, int thePIN, 
      double theAvailableBalance, double theTotalBalance) {
      accountNumber = theAccountNumber;
      pin = thePIN;
      
      // myadd
      availableBalance = theAvailableBalance;
      totalBalance = theTotalBalance;
      // end add
      // myadd new
      AccountLog temp = new AccountLog(true, theTotalBalance, theTotalBalance);
      accountLogs.add(temp);
      Status = true;
      // end myadd
   }

   // determines whether a user-specified PIN matches PIN in Account
   public boolean validatePIN(int userPIN) {
      if (userPIN == pin) {
         return true;
      }
      else {
//         return true;
           return false;
      }
   } 

   // returns available balance
   public double getAvailableBalance() {
      return availableBalance;
   } 

   // returns the total balance
   public double getTotalBalance() {
      return totalBalance;
   } 

   public void credit(double amount) {
     // myadd
     this.availableBalance -= amount;
     this.totalBalance -= amount;
     // end add
     // myadd new
     AccountLog temp = new AccountLog(false, amount, this.totalBalance);
     accountLogs.add(temp);
     // end myadd
   }

   public void debit(double amount) {
     // myadd
     this.availableBalance += amount;
     this.totalBalance += amount;
     // end add
     // myadd new
     AccountLog temp = new AccountLog(true, amount, this.totalBalance);
     accountLogs.add(temp);
     // end add
   }

   public int getAccountNumber() {
      return accountNumber;  
   }
   
   // myadd new
   public LinkedHashSet<AccountLog> getAccountLog(){
       return accountLogs;
   }
   
   public void setStatus(boolean Stat){
       this.Status = Stat;
   }
   
   public boolean getStatus(){
       return this.Status;
   }
   // end add

    public void setPin(int pin) {
        this.pin = pin;
    }
    

} 
