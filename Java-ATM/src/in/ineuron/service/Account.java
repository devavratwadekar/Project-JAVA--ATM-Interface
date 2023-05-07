package in.ineuron.service;

import java.util.ArrayList;

public class Account {
	/*
	 * Name of the account
	 */
	private String name;

	/*
	 * account ID number
	 */
	private String uuid;
	/*
	 * The user object that own this account
	 */
	private User userHolder;
	/*
	 * The list of Transaction for this account
	 */
	private ArrayList<Transaction> transactions;

	/**
	 * Create a new account
	 * 
	 * @param name       the name of the account
	 * @param userHolder the User object that hold this account
	 * @param theBank    the bank that issue the account
	 */
	public Account(String name, String uuid, User userHolder, Bank theBank) {
		// set the account name and holder
		this.name = name;
		this.uuid = uuid;

		// get new account UUID
		this.uuid = theBank.getNewAccountUUID();

		// initialize transaction
		this.transactions = new ArrayList<Transaction>();

		/*
		 * REMOVE // add to userHolder and bank lists userHolder.addAccount(this);
		 * theBank.addAccount(this);
		 */

	}

	/**
	 * Returns the account UUID
	 * 
	 * @return the uuid
	 */
	public String getUUID() {
		return this.uuid;
	}

	public String getSummanryLine() {
		// get the account balance
		double balance = this.getBalance();

		//formating the summary line depending on the weather the balane is -ve
		if (balance >= 0) {
			return String.format("%s : RS %.02f :%s", this.uuid, balance, this.name);
		} else {
			return String.format("%s : RS (%.02f) :%s", this.uuid, balance, this.name);
		}
	}

	public double getBalance() {
		double balance = 0;
		for (Transaction t : this.transactions) {
			balance += t.getAmount();
		}
		return balance;
	}

	/**
	 * Print transaction history of the account
	 */
	public void printTranHistory() {
		System.out.printf("\nTransaction history for acoount : %s\n ",this.uuid);
		for (int t = this.transactions.size() - 1; t >= 0; t--) {
			System.out.println(this.transactions.get(t).getSummaryLine());
		}
		System.out.println();
	}

	/**
	 * Add a new transaction in this account
	 * @param amount	the amount of transacted
	 * @param memo		the transaction memo
	 */
	public void addTransaction(double amount, String memo) {
		
		//create a new transaction object and add it to our list
		Transaction newTransaction = new Transaction(amount, memo, this);
		this.transactions.add(newTransaction);
		
		
	}

}
