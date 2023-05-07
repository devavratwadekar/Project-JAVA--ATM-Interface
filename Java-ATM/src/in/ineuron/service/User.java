package in.ineuron.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * @author DEVAVRAT
 *
 */
public class User {

	/*
	 * The First user name
	 */
	private String firstName;
	/*
	 * The Last user name
	 */
	private String lastName;
	/*
	 * The ID number of user
	 */
	private String uuID;
	/*
	 * MD5 hash of the user pin number-encrypt
	 */
	private byte pinHash[];
	/*
	 * list of the account for this user
	 */
	public ArrayList<Account> accounts;

	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getUuID() {
		return uuID;
	}

	public byte[] getPinHash() {
		return pinHash;
	}

	public ArrayList<Account> getAccounts() {
		return accounts;
	}

	/**
	 * Create a new user
	 * 
	 * @param firstName the user's first name
	 * @param lastName  the user's ;last name
	 * @param pin       the user's account original-pin
	 * @param theBank   the Bank object that the user is a customer of
	 */
	public User(String firstName, String lastName, String pin, Bank theBank) {
		// set user name
		this.firstName = firstName;
		this.lastName = lastName;

		// store the pin's MD5 hash,rather than original value ,for security reason
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			this.pinHash = md.digest(pin.getBytes()); // encryption
		} catch (NoSuchAlgorithmException e) {
			System.err.println("Error,caught NoSuchAlgorithmException");
			e.printStackTrace();
			System.exit(1);
		}

		// get a new unique universal ID for the user
		this.uuID = theBank.getNewUserUUID();

		// create empty list of account
		this.accounts = new ArrayList<Account>();

		// print login message
		System.out.printf("New User %s %s with ID %s created.\n",firstName, lastName,  this.uuID);

	}

	/**
	 * Add an account for the user
	 * 
	 * @param anAccount the account to add
	 */
	public void addAccount(Account anAccount) {
		this.accounts.add(anAccount);
	}

	/**
	 * Returns the users UUID
	 * 
	 * @return the uuid
	 */
	public String getUUID() {
		return this.uuID;
	}

	/**
	 * Check whether a given pin is matches the true User pin
	 * 
	 * @param aPin the pin to check
	 * @return whether the pin is valid or not
	 */
	public boolean validatePin(String aPin) {

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return MessageDigest.isEqual(md.digest(aPin.getBytes()), this.pinHash);
		} catch (NoSuchAlgorithmException e) {
			System.err.println("Error,caught NoSuchAlgorithmException");
			e.printStackTrace();
			System.exit(1);
		}
		return false;
	}

	public void printAccountSummary() {
		System.out.println("\n "+this.firstName + "'s Account Summary ");
		for (int a = 0; a < this.accounts.size(); a++) {
			System.out.printf(" %d) %s\n", (a + 1), this.accounts.get(a).getSummanryLine());

		}
		System.out.println();

	}

	/**
	 * Get the number of accounts of the users
	 * 
	 * @return the number of accounts
	 */
	public int numAccounts() {
		return this.accounts.size();
	}

	/**
	 * Print transaction history for to perticulaor accout
	 * 
	 * @param accIdx the index account to see
	 */
	public void printAccountTransactionHistory(int accIdx) {
		this.accounts.get(accIdx).printTranHistory();

	}

	/**
	 * Get the balcance of a perticulor account
	 * 
	 * @param acctIdx the index account to see
	 * @return the balance of the account
	 */
	public double getAcctBalance(int acctIdx) {
		return this.accounts.get(acctIdx).getBalance();
	}

	/**
	 * Get UUID for a perticular account
	 * 
	 * @param acctIdx the index account to see
	 * @return the UUID of the account
	 */
	public Object getAcctUUID(int acctIdx) {
		return this.accounts.get(acctIdx).getUUID();
	}

	/**
	 * Add a transaction to a perticular account
	 * 
	 * @param acctIdx the index of account
	 * @param amount  the amount of the transaction
	 * @param memo    the memo of the transaction
	 */
	public void addAccountTransaction(int acctIdx, double amount, String memo) {
		this.accounts.get(acctIdx).addTransaction(amount, memo);

	}

}
