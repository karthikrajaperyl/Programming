package atmCardCreation;

import java.util.Scanner;
import java.io.*;

class MyException extends Exception {
	MyException(String exceptionName) {
		super(exceptionName);
	}
}

class ATMCard {
	private int cardNumber;
	private int amount;

	ATMCard(int number) {
		this.cardNumber = number;
	}

	private int getCardNumber() {
		return cardNumber;
	}

	public boolean setAmount(int amount, boolean offer) {
		if (offer == true) {
			this.amount += amount;
			return true;
		}
		if ((getAmount() + amount) >= 100) {
			this.amount += amount;
			return true;
		}
		return false;
	}

	public int getAmount() {
		return amount;
	}

	private boolean reduceAmount(int amount, int cardNumber) throws MyException {
		if (cardNumber == getCardNumber()) {
			this.amount -= amount;
			return true;
		} else
			throw new MyException("Password Mismatch");
	}

	public int getTransactionCharges(int amount) {
		int i = 0;
		if (amount <= 100) {
			i = (2 * amount) / 100;
		} else if (amount > 100) {
			i = (4 * amount) / 100;
		}

		return i;
	}

	public boolean cashWithDrawal(int amount, int cardNumber) throws MyException {
		int transactionCharges;
		transactionCharges = getTransactionCharges(amount);
		if (getAmount() > (amount + transactionCharges)) {
			return reduceAmount(amount + transactionCharges, cardNumber);
		}
		else
		throw new MyException("You Don't have an Sufficeint Balance");
	}
}

public class Bank {
	private void showBalance(ATMCard cardObj) {
		System.out.println("Available Balance-$" + cardObj.getAmount());
	}

	private boolean amountDeposit(int amount, ATMCard cardObj) {
		return cardObj.setAmount(amount, false);
	}

	private void setOfferAmount(int amount, ATMCard cardObj) {
		cardObj.setAmount(amount, true);
	}

	private boolean checkMultipleOfFive(int amount) {
		return amount % 5 == 0;
	}

	private boolean withDrawAmount(int amount, ATMCard cardObj) throws IOException, MyException {
		BufferedReader readerObj = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter the ATM card number");
		int cardNumber = Integer.parseInt(readerObj.readLine());
		if (cardObj.cashWithDrawal(amount, cardNumber))
			return true;
		else
			return false;
	}

	private int addOffer(int amount) {
		return (1 * amount) / 100;
	}

	public void performTransaction(ATMCard cardObj) throws IOException {
		BufferedReader readerObj = new BufferedReader(new InputStreamReader(System.in));
		boolean continuation = true;
		int amount;
		while (continuation) {
			System.out.println("\n1.Cash Deposit\n2.Cash WithDrawn\n3.Swipe-Shopping\n4.Exit");
			int n = Integer.parseInt(readerObj.readLine());

			switch (n) {

			case 1:
				System.out.println("Enter the Cash Amount:");
				amount = Integer.parseInt(readerObj.readLine());
				if (amountDeposit(amount, cardObj)) {
					System.out.println("Amount added Successfully");
					showBalance(cardObj);
				} else
					System.out.println("Minimum balance should be $100");
				break;
			case 2:
				System.out.println("Enter the Cash Amount:");
				amount = Integer.parseInt(readerObj.readLine());
				try {
					if (!checkMultipleOfFive(amount)) {
						System.out.println("Please correct the amount of value. It should be multiple of USD 5");
						break;
					}
					if (withDrawAmount(amount, cardObj)) {
						System.out.println("Amount Withdrawn Successfully");
						showBalance(cardObj);
					}
				} catch (MyException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 3:
				System.out.println("Enter the Cash Amount:");
				amount = Integer.parseInt(readerObj.readLine());
				try {
					if (withDrawAmount(amount, cardObj)) {
						int tempAmount = addOffer(amount);
						setOfferAmount(tempAmount, cardObj);
						System.out.println("Swipe Amount-" + amount);
						showBalance(cardObj);
					}
				} catch (MyException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 4:
				continuation = false;
				System.exit(0);
			}
		}
	}

	public static void main(String[] args) {
		try {
			Bank tempObj = new Bank();
			Scanner scannerObj = new Scanner(System.in);
			System.out.println("set ATM card number");
			int cardNumber = scannerObj.nextInt();
			ATMCard cardObj = new ATMCard(cardNumber);
			tempObj.performTransaction(cardObj);
			scannerObj.close();
		}

		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

}
