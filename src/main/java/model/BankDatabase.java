/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.*;

public class BankDatabase {

    private Account[] accounts; // array of Accounts

    public BankDatabase() {
        accounts = new Account[2]; // just 2 accounts for testing
        accounts[0] = new Account(12345, 54321, 580.0, 780.0);
        accounts[1] = new Account(8765, 5678, 200.0, 200.0);
    }

    public Account getAccount(int accountNumber) {
       // myadd
       for(Account key : accounts){
           if(key.getAccountNumber() == accountNumber) return key;
       }
       // end add
       
      return null; // if no matching account was found, return null
   } 
   
   public boolean getStatusAccount(int accountNumber){
      for (Account key : accounts){
          if(key.getAccountNumber() == accountNumber) return key.getStatus();
      }
       
       return false;
   }
   
   public LinkedHashSet<AccountLog> getAccountLog(int userAccountNumber){
       Account userAccount = getAccount(userAccountNumber);
       return userAccount.getAccountLog();
   }

   public boolean authenticateUser(int userAccountNumber) {
        // attempt to retrieve the account with the account number
        Account userAccount = getAccount(userAccountNumber);

        // if account exists, return result of Account method validatePIN
        if (userAccount != null) {
        // System.out.println(userAccount.getPin());
          return true;
        }
        else {
           return false; // account number not found, so return false
        }
    } 

   public double getAvailableBalance(int userAccountNumber) {
      return getAccount(userAccountNumber).getAvailableBalance();
   } 

   public double getTotalBalance(int userAccountNumber) {
      return getAccount(userAccountNumber).getTotalBalance();
   } 

   public void credit(int userAccountNumber, double amount) {
      getAccount(userAccountNumber).credit(amount);
   }

   public void debit(int userAccountNumber, double amount) {
      getAccount(userAccountNumber).debit(amount);
   } 
   boolean userauthentication(int accountNumber) {
        Account userAccount = getAccount(accountNumber);
        return userAccount != null;
    }
   
   boolean cekstatus(int userAccountNumber){
       boolean cek = false;
       
       cek = getStatusAccount(userAccountNumber);
       
       return cek;
   }
   public void Blokir(int userAccountNumber, boolean Status){
       Account user = getAccount(userAccountNumber);
       
       user.setStatus(Status);
   }
   
   public void ChangePin(int userAccountNumber, int newPin) {
       getAccount(userAccountNumber).setPin(newPin);
    } 
   public boolean authenticateUsertoTransfer (int userAccountNumber) {
       Account userAccount = getAccount (userAccountNumber);
       
       if (userAccount != null) {
           return true;
       }
       else{
           return false;
       }
   }
}