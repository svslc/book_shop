package main;

public class Account {

	private static String accountID;

	public static String getAccountID() {
		return accountID;
	}

	public static void setAccountID(String accountID) {
		Account.accountID = accountID;
	}

}
