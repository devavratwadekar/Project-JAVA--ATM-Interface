package in.ineuron.controller;

import java.util.Scanner;

import in.ineuron.service.*;

public class ATM {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		// nintialiaze the Bank
		Bank theBank = new Bank("Bank of DKW-India");

		// add a useRS which also creates a savings account
		User addUser = theBank.addUser("Devavrat", "Wadekar", "2023");

		// add a checking account for a user
		Account newAccount = new Account("Checking", "1000", addUser, theBank);
		addUser.addAccount(newAccount);
		theBank.addAccount(newAccount);

		User curUser;
		while (true) {

			// stay in the login prompt until successful login
			curUser = ATM.mainMenuPrompt(theBank, sc);

			// stay in main menu until user quit
			ATM.printUserMenu(curUser, sc);
		}

	}

	private static void printUserMenu(User curUser, Scanner sc) {

		// print a summary of the user account
		curUser.printAccountSummary();

		// init
		int choice;

		// user menu
		do {
			System.out.println(" Welcome " + curUser.getFirstName() + ", What would you like to do?");
			System.out.println(" 1)Show account transaction history");
			System.out.println(" 2)Withdraw");
			System.out.println(" 3)Deposite");
			System.out.println(" 4)Transfer");
			System.out.println(" 5)Quit");
			System.out.println();

			System.out.print(" Enter the choice : ");
			choice = sc.nextInt();

			if (choice < 1 || choice > 5) {
				System.out.println("Invalide choise.Please choose 1-5 \n");
			}

		} while (choice < 1 || choice > 5);

		// process the choice
		switch (choice) {
		case 1:
			ATM.showTranHistory(curUser, sc);
			break;
		case 2:
			ATM.withdrawAmount(curUser, sc);
			break;
		case 3:
			ATM.depositeAmount(curUser, sc);
			break;
		case 4:
			ATM.transferAmount(curUser, sc);
			break;

		case 5:
			// gobble up rest of previous input
			sc.nextLine();
			break;
		}

		// rediplay this menu unless the user want quit
		if (choice != 5) {
			ATM.printUserMenu(curUser, sc);
		}
	}

	/**
	 * Process a amount deposite to an account
	 * 
	 * @param curUser the logged-in user object
	 * @param sc      the Scanner object used for user input
	 */
	private static void depositeAmount(User curUser, Scanner sc) {

		// inits
		int toAcct;
		double amount;
		double acctBal;
		String memo;

		// get the account to transfer from
		do {
			System.out.printf(" Enter the number (1-%d) of the account to DEPOSITE IN : ", curUser.numAccounts());
			toAcct = sc.nextInt() - 1;

			if (toAcct < 0 || toAcct >= curUser.numAccounts()) {
				System.out.println(" Invalid account. Please try again.");
			}
		} while (toAcct < 0 || toAcct >= curUser.numAccounts());
		acctBal = curUser.getAcctBalance(toAcct);

		// get the account to transfer
		do {
			System.out.print(" Enter the amount to DEPOSIT IN (Balance : "+acctBal+"rs) RS : " );
			amount = sc.nextDouble();
			if (amount < 0) {
				System.out.println(" Amount must be greater than zero.");
			}
		} while (amount < 0);

		// gobble up rest of previous input
		sc.nextLine();

		// get a memo
		System.out.print(" Enter a msg : ");
		memo = sc.nextLine();

		// do the withdraw
		curUser.addAccountTransaction(toAcct, amount, memo);

	}

	/**
	 * Process a amount withdraw from account
	 * 
	 * @param curUser the logged-in user object
	 * @param sc      the Scanner object used for user input
	 */
	private static void withdrawAmount(User curUser, Scanner sc) {
		// inits
		int fromAcct;
		double amount;
		double acctBal;
		String memo;

		// get the account to transfer from
		do {
			System.out.printf(" Enter the number (1-%d) of the account for WITHDRAW FROM : ",
					curUser.numAccounts());
			fromAcct = sc.nextInt() - 1;

			if (fromAcct < 0 || fromAcct >= curUser.numAccounts()) {
				System.out.println(" Invalid account. Please try again.");
			}
		} while (fromAcct < 0 || fromAcct >= curUser.numAccounts());
		acctBal = curUser.getAcctBalance(fromAcct);

		// get the account to transfer
		do {
			System.out.print(" Enter the aomunt to WITHDRAW (Balance : "+ acctBal+"rs) RS :" );
			amount = sc.nextDouble();
			if (amount < 0) {
				System.out.println(" Amount must be greater than zero.");
			} else if (amount > acctBal) {
				System.out.println(" Amount must not be greater than balance "+ acctBal+"rs :\n");
			}
		} while (amount < 0 || amount > acctBal);

		// get a memo
//		System.out.print(" Enter a msg : ");
//		memo = sc.nextLine();

		// do the withdraw
		curUser.addAccountTransaction(fromAcct, -1 * amount, "WITHDRAW ");

	}

	/**
	 * Process transfering amount from one account to another account
	 * 
	 * @param curUser the logged-in user object
	 * @param sc      the Scanner object used for user input
	 */
	private static void transferAmount(User curUser, Scanner sc) {
		// inits
		int fromAcct;
		int toAcct;
		double amount;
		double acctBal;

		// get the account to transfer from
		do {
			System.out.printf(" Enter the number (1-%d) of the account for TRANSFER FROM : ",
					curUser.numAccounts());
			fromAcct = sc.nextInt() - 1;

			if (fromAcct < 0 || fromAcct >= curUser.numAccounts()) {
				System.out.println(" Invalid account.Please try again.");
			}
		} while (fromAcct < 0 || fromAcct >= curUser.numAccounts());
		acctBal = curUser.getAcctBalance(fromAcct);

		// get the account to transfer to
		do {
			System.out.printf(" Enter the number (1-%d) of the account for TRANSFER TO : ",
					curUser.numAccounts());
			toAcct = sc.nextInt() - 1;

			if (toAcct < 0 || toAcct >= curUser.numAccounts()) {
				System.out.println("Invalid account.Please try again.");
			}
		} while (toAcct < 0 || toAcct >= curUser.numAccounts());

		// get the account to transfer
		do {
			System.out.print(" Enter the aomunt for TRANSFER (Balance : " + acctBal + "rs) RS : ");
			amount = sc.nextDouble();
			if (amount < 0) {
				System.out.println(" Amount must be greater than zero.");
			} else if (amount > acctBal) {
				System.out.println(" Amount must not be greater than balance of "+ acctBal+"RS :\n" );
			}
		} while (amount < 0 || amount > acctBal);

		// finaly, do the transfer
		curUser.addAccountTransaction(fromAcct, -1 * amount,
				String.format(" Transfer account to %s ", curUser.getAcctUUID(toAcct)));
		curUser.addAccountTransaction(toAcct, amount,
				String.format(" Transfer account to %s ", curUser.getAcctUUID(fromAcct)));

	}

	/**
	 * Show the transaction history for the account
	 * 
	 * @param curUser the logged-in the user
	 * @param sc      the scanner object used for the user input
	 */
	private static void showTranHistory(User curUser, Scanner sc) {

		int theAcct;
		// get account whose transaction history to look at
		do {
			System.out.printf(" Enter the number (1-%d) of the accounts those thransactions you want to see : ",
					curUser.numAccounts());
			theAcct = sc.nextInt() - 1;
			if (theAcct < 0 || theAcct >= curUser.numAccounts()) {
				System.out.println(" Invalid account.Please try again");
			}
		} while (theAcct < 0 || theAcct >= curUser.numAccounts());

		// print the transaction History
		curUser.printAccountTransactionHistory(theAcct);

	}

	/**
	 * Print the ATM login menu
	 * 
	 * @param theBank the bank object whose accounts to use
	 * @param sc      the Scanner object to use for the user input
	 * @return the authenticated User object
	 */
	private static User mainMenuPrompt(Bank theBank, Scanner sc) {

		// initialize
		String userID;
		String pin;
		User authUser;

		// prompt the user for the user ID/Pin combo until a correct one is reached
		do {
			System.out.println("\n\n Welcome to " + theBank.getName());
			System.out.print(" Enter user ID :: ");
			userID = sc.nextLine();
			System.out.print(" Enter PIN :: ");
			pin = sc.nextLine();

			// try to get user object corresponding to the ID and PIN combo
			authUser = theBank.userLogin(userID, pin);
			if (authUser == null) {
				System.out.println(" Incorrect user ID/PIN Combination. Please try again.");

			}

		} while (authUser == null);// continue looping until successful login

		return authUser;
	}

}
