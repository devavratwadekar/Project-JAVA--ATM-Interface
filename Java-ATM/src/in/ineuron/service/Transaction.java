package in.ineuron.service;

import java.util.Date;

public class Transaction {
	/*
	 * the amount of this transaction
	 */
	private double amount;
	/*
	 * the time and date for of this account
	 */
	private Date timestamp;
	/*
	 * extra text info for this transaction
	 */
	private String memo;
	/*
	 * the account in which is this transaction was perform
	 */
	private Account inAccount; //

	public double getAmount() {
		return this.amount;
	}

	/**
	 * Create a new transaction
	 * 
	 * @param amount    the amount transacted
	 * @param inAccount the amount transaction belong to
	 */
	public Transaction(double amount, Account inAccount) {
		super();
		this.amount = amount;
		this.inAccount = inAccount;
		this.timestamp = new Date();
		this.memo = "";
	}

	/**
	 * Create a new transaction
	 * 
	 * @param amount    the amount translated
	 * @param memo      the memo for the transaction
	 * @param inAccount the account transaction belong to
	 */
	public Transaction(double amount, String memo, Account inAccount) {
		// call the two-args constructor first
		this(amount, inAccount);

		// set the memo
		this.memo = memo;
	}
	
	/**
	 * Get the String summaerizing the transaction
	 * @return	the summary string
	 */
	public String getSummaryLine() {
		if (this.amount >= 0) {
			return String.format(" %s : RS %.02f : %s", this.timestamp.toString(),
						this.amount, this.memo);
		}else {
			return String.format(" %s : RS %.02f : %s", this.timestamp.toString(),
					-this.amount, this.memo);
			
		}
	}

}
