package in.ineuron.service;

import java.util.ArrayList;
import java.util.Random;

public class Bank {

	private String name;

	private ArrayList<User> users;

	private ArrayList<Account> accounts;

	/**
	 * Create a new bank object with empty lists of users and accounts
	 * @param name	the name of the bank
	 */
	public Bank(String name) {
		this.name = name;
		this.users = new ArrayList<User>();
		this.accounts = new ArrayList<Account>();
	}
	
	
	
	public String getName() {
		return name;
	}


	/**
	 * Generate a new universally unique ID for a user
	 * 
	 * @return the uuid
	 */
	public String getNewUserUUID() {
		// initiates
		String uuid;
		Random rng = new Random();
		int length = 6;
		boolean nonUnique;

		// continue looping until we get a unique ID
		do {
			// generate the number
			uuid = "";
			for (int i = 0; i < length; i++) {
				uuid += ((Integer) rng.nextInt(10)).toString();
			}

			// check to make sure it is unique
			nonUnique = false;
			for (User u : this.users) {
				if (uuid.compareTo(u.getUUID()) == 0) {
					nonUnique = true;
					break;
				}

			}
		} while (nonUnique);

		return uuid;
	}

	/**
	 * Generate a new universally unique ID for a account
	 * 
	 * @return the uuid
	 */
	public String getNewAccountUUID() {
		// initiates
		String uuid;
		Random rng = new Random();
		int length = 10;
		boolean nonUnique;

		// continue looping until we get a unique ID
		do {
			// generate the number
			uuid = "";
			for (int i = 0; i < length; i++) {
				uuid += ((Integer) rng.nextInt(10)).toString();
			}

			// check to make sure it is unique
			nonUnique = false;
			for (Account a : this.accounts) {
				if (uuid.compareTo(a.getUUID()) == 0) {
					nonUnique = true;
					break;
				}

			}
		} while (nonUnique);

		return uuid;
	}

	/**
	 * Add an account
	 * 
	 * @param anAccount the account to add
	 */
	public void addAccount(Account anAccount) {
		this.accounts.add(anAccount);
	}

	/**
	 * Create a new user of the banck
	 * 
	 * @param firstName the user first name
	 * @param lastName  the user last name
	 * @param pin       the user pin
	 * @return 			the new user object
	 */
	public User addUser(String firstName, String lastName, String pin) {
		// create a new user object and add to our list

		User newUser = new User(firstName, lastName, pin, this);
		this.users.add(newUser);

		// create a saving accounts for the users
		Account newAccount = new Account("Saving", pin, newUser, this); // pin
		newUser.addAccount(newAccount);
		this.addAccount(newAccount);

		return newUser;
	}

	/**
	 * Get the User object of the particular userID and PIN,if the are valid
	 * 
	 * @param userId 	the UUID of the user to login
	 * @param pin   	the pin of the user
	 * @return 			the User object if the login is successful or null if it is not
	 */
	public User userLogin(String userId, String pin) {
		// search through list of the users
		for (User u : this.users) {

			// check user ID is correct
			if (u.getUUID().compareTo(userId) == 0 && u.validatePin(pin)) {
				return u;
			}
		}
		return null;
	}

}
