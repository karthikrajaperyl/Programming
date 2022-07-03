package atmCardDesign;

import java.util.Scanner;
import java.util.Date;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;

enum CardType {
	DEDITCARD, CREDITCARD
}

class ATMCard {
	private static int id;
	int atmPin;
	int atmId;
	BankService bankService;
	ATMCardDetails cardInfo;
	Account accountObj;
	Transaction TransactionObj;

	ATMCard(Builder builder) {
		this.atmPin = builder.atmPin;
		this.atmPin = ++id;
	}

	ATMCard cardObj;

	public static class Builder {
		private static int id;
		private int atmPin;
		private int atmId;
		private BankService bankService;
		private ATMCardDetails cardInfo;
		private Account accountObj;
		private Transaction TransactionObj;

		public static Builder newInstance() {
			return new Builder();
		}

		private Builder() {
		}

		public Builder setAccount(Account accountObj) {
			this.accountObj = accountObj;
			return this;
		}

		public Builder setAtmCardDetails(ATMCardDetails cardDetails) {
			this.cardInfo = cardDetails;
			return this;
		}

		public ATMCard build(int ATMPin) {
			return new ATMCard(this);
		}
	}

	public void setBankService(BankService bankService) {
		this.bankService = bankService;
	}
}

class ATMCardDetails {
	final int CCVPin;
	final Date validity;
	final CardType cardType;

	ATMCardDetails(int ccvPin, Date validity, CardType cardType) {
		this.CCVPin = ccvPin;
		this.validity = validity;
		this.cardType = cardType;
	}

	public Date getValidity() {
		return this.validity;
	}
}

class Account {
	private int accountNumber;
	private float amount;

	Account(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public int getAccountNumber() {
		return this.accountNumber;
	}

	public void setBalance(float amount) {
		this.amount = amount;
	}

	public float getBalance() {
		return this.amount;
	}
}

abstract class Transaction {
	private TransactionDetails transactionObj;
	TransactionType transactionType;

	public void setTransactionDetails(TransactionDetails transactionObj) {
		this.transactionObj = transactionObj;
	}

	public TransactionDetails getTransactionDetails() {
		return this.transactionObj;
	}

	public float transactionCharge(float amount) {
		if (amount <= 100)
			return (amount * 2) / 100;
		else
			return (amount * 4) / 100;
	}

	abstract float getAmount();

	abstract void setTransactionType();
}

class TransactionDetails {
	static int id;
	int transactionId;
	String transactionDate;
	TransactionStatus transactionStatus;

	TransactionDetails() {
		id++;
		this.transactionId = ++id;
	}

	public void setTransactionDate() {
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// dd/MM/yyyy
		Date now = new Date();
		String strDate = sdfDate.format(now);
		this.transactionDate = strDate;
	}
}

enum TransactionType {
	WITHDRAW, SWIPETYPE
}

enum TransactionStatus {
	PENDING, SUCCESS, FAILED, ERORR
}

interface BankService {
	void validUser(int pin, ATMCardDetails cardInfo);

	Transaction executeTransaction(Account account, ATMCard cardinfo);
}

class WithDraw extends Transaction {
	private float amount;

	WithDraw(float amount) {
		this.amount = amount;
		this.setTransactionType();
	}

	public float getAmount() {
		return amount + transactionCharge(amount);
	}

	public void setTransactionType() {
		this.transactionType = TransactionType.WITHDRAW;
	}
}

class SwipeType extends Transaction {
	private float amount;

	SwipeType(float amount) {
		this.amount = amount;
		this.setTransactionType();
	}

	private float cashBackCharge(float amount) {
		return (amount * 1) / 100;
	}

	public float getAmount() {
		return amount - transactionCharge(amount) + cashBackCharge(amount);
	}

	public void setTransactionType() {
		this.transactionType = TransactionType.SWIPETYPE;
	}
}

class ATMFactory {
	private volatile ATMCard cardObj;

	public ATMFactory(int ATMPin, Account accountObj, ATMCardDetails cardInfo) {
		cardObj = ATMCard.Builder.newInstance().setAccount(accountObj).setAtmCardDetails(cardInfo).build(ATMPin);
	}

	public ATMCard getInstance() {
		return cardObj;
	}
}

public class Bank implements BankService {
	private String name;

	Bank(String name) {
		this.name = name;
	}

	public void validUser(int pin, ATMCardDetails cardInfo) {
//
	}

	public boolean checkAmount(float amount) {
		return amount % 5 == 0.0;
	}

	private boolean checkBalance(float availableAmount, float currentAmount) {
		if (availableAmount >= currentAmount)
			return true;
		else
			return false;
	}

	private Transaction processTransaction(Account account, ATMCard cardinfo) {
		BufferedReader readerObj = new BufferedReader(new InputStreamReader(System.in));
		Transaction transactionObj = null;
		TransactionDetails transactionDetails = new TransactionDetails();
		try {
			float amount = 0;
			boolean continuation = true;
			System.out.println("Choose the Transaction Type");

			System.out.println("\n1.WithDrawal\n2.Swipe Type");
			int n = Integer.parseInt(readerObj.readLine());
			System.out.println("Enter the Amount");
			amount = Integer.parseInt(readerObj.readLine());
			while (continuation) {
				if (checkAmount(amount))
					break;
				System.out.println("Enter the amount for transaction-Its should be multiple of 5");
				amount = Integer.parseInt(readerObj.readLine());
			}
			if (n == 1) {
				transactionObj = new WithDraw(amount);
				account.setBalance(account.getBalance() - transactionObj.getAmount());
			} else {
				transactionObj = new SwipeType(amount);
				account.setBalance(account.getBalance() + transactionObj.getAmount());
			}
			if (checkBalance(account.getBalance(), transactionObj.getAmount())) {
				transactionDetails.transactionStatus = TransactionStatus.FAILED;
			}
			transactionDetails.setTransactionDate();
			transactionDetails.transactionStatus = TransactionStatus.SUCCESS;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			transactionDetails.transactionStatus = TransactionStatus.ERORR;
		}
		transactionObj.setTransactionDetails(transactionDetails);
		return transactionObj;
	}

	public Transaction executeTransaction(Account accountInfo, ATMCard cardInfo) {
		return processTransaction(accountInfo, cardInfo);
	}

	public static Account getAccount() throws IOException {
		BufferedReader readerObj = new BufferedReader(new InputStreamReader(System.in));
		Account accountObj = new Account(123445);
		System.out.println("Enter the amount");
		int amount = Integer.parseInt(readerObj.readLine());
		accountObj.setBalance(amount);
		return accountObj;
	}

	public static void main(String[] args) {

		try {
			Scanner scannerObj = new Scanner(System.in);
			Account accountObj = getAccount();
			System.out.println("Set the ATM pin");
			int atmPin = scannerObj.nextInt();
			System.out.println("Enter the Card Type");
			System.out.println("\n1.Credit Card\n2.Debit Card");
			int n = scannerObj.nextInt();
			ATMCardDetails cardDetailsInfo = null;
			if (n == 1)
				cardDetailsInfo = new ATMCardDetails(123, new Date(), CardType.CREDITCARD);
			else
				cardDetailsInfo = new ATMCardDetails(123, new Date(), CardType.DEDITCARD);
			ATMFactory cardFactoryInfo = new ATMFactory(atmPin, accountObj, cardDetailsInfo);
			BankService bankObj = new Bank("XYZ");
			ATMCard cardInfo = cardFactoryInfo.getInstance();
			bankObj.executeTransaction(accountObj, cardInfo);
			cardInfo.setBankService(bankObj);
			System.out.println("Customer Balance-" + accountObj.getBalance());
			scannerObj.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
