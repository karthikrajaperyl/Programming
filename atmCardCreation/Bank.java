package atmCardCreation;

import java.util.Scanner;
import java.util.Date;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Bank implements BankService {
	static HashMap<Integer, Transaction> transactionMaintenance = new HashMap<>();
	private String name;

	Bank(String name) {
		this.name = name;

	}

	public boolean validUser(int pin, int cardNumber) {
		if (pin == cardNumber)
			return true;
		else
			return false;
	}

	public boolean checkAmount(float amount) {
		return amount % 5 == 0.0;
	}

	private boolean checkBalance(float availableAmount, float currentAmount) {
		if (availableAmount <= currentAmount)
			return true;
		else
			return false;
	}

	private float deductAmountForCardType(int value, Transaction transactionObj, CardType cardType) {
		WithDraw withDrawTransaction = (WithDraw) transactionObj;
		if (cardType == CardType.CREDITCARD) {
			return withDrawTransaction.getAmount(value);
		} else
			return 0;
	}

	private float getAmountForTransaction() throws IOException {
		BufferedReader readerObj = new BufferedReader(new InputStreamReader(System.in));
		float amount = 0;
		boolean continuation = true;
		System.out.println("Enter the Amount");
		amount = Integer.parseInt(readerObj.readLine());
		while (continuation) {
			if (checkAmount(amount))
				break;
			System.out.println("Enter the amount for transaction-Its should be multiple of 5");
			amount = Integer.parseInt(readerObj.readLine());
		}
		return amount;
	}

	private Transaction getTransactionType() throws IOException {
		BufferedReader readerObj = new BufferedReader(new InputStreamReader(System.in));
		Transaction transactionObj = null;
		float amount = getAmountForTransaction();
		System.out.println("Choose the Transaction Type");
		System.out.println("\n1.WithDrawal\n2.Swipe Type");
		int n = Integer.parseInt(readerObj.readLine());
		if (n == 1) {
			transactionObj = new WithDraw(amount);
		} else {
			transactionObj = new SwipeType(amount);
		}
		return transactionObj;
	}

	private Transaction processTransaction(Account account, ATMCard cardInfo) {
		Transaction transactionObj = null;
		TransactionDetails transactionDetails = new TransactionDetails();
		try {
			float amountDeduction = 0;
			transactionObj = getTransactionType();
			if (transactionObj.transactionType == TransactionType.WITHDRAW) {
				amountDeduction = account.getBalance() - transactionObj.getAmount();
				amountDeduction -= deductAmountForCardType(2, transactionObj, cardInfo.getCardDetails().cardType);
				account.setBalance(amountDeduction);
			} else {
				amountDeduction = account.getBalance() + transactionObj.getAmount();
				account.setBalance(amountDeduction);
			}
			transactionDetails.setTransactionDate();
			transactionDetails.transactionStatus = TransactionStatus.SUCCESS;
			if (checkBalance(account.getBalance(), transactionObj.getAmount())) {
				transactionDetails.transactionStatus = TransactionStatus.FAILED;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			transactionDetails.transactionStatus = TransactionStatus.ERORR;
		}
		transactionObj.setTransactionDetails(transactionDetails);
		return transactionObj;
	}

	private boolean checkPinMatch(int ATMPin, ATMCard cardInfo) {
		if (validUser(cardInfo.getATMPin(), ATMPin))
			return true;
		else
			return false;
	}

	public Transaction executeTransaction(Account accountInfo, ATMCard cardInfo) throws IOException {
		BufferedReader readerObj = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter the ATM pin");
		int ATMPin = Integer.parseInt(readerObj.readLine());
		if (checkPinMatch(ATMPin, cardInfo))
			return processTransaction(accountInfo, cardInfo);
		else
			return null;
	}

	public static Account getAccount() throws IOException {
		BufferedReader readerObj = new BufferedReader(new InputStreamReader(System.in));
		Account accountObj = new Account(123445);
		System.out.println("Enter the amount intially for an account");
		int amount = Integer.parseInt(readerObj.readLine());
		accountObj.setBalance(amount);
		return accountObj;
	}

	private ATMCard setATMCardType(int n, int accountNum) {
		Scanner scannerObj = new Scanner(System.in);

		System.out.println("Set the ATM pin");
		int atmPin = scannerObj.nextInt();
		System.out.println("Enter the Card Type");
		System.out.println("\n1.Credit Card\n2.Debit Card");
		int i = scannerObj.nextInt();
		ATMCardDetails cardDetailsInfo = null;
		if (i == 1)
			cardDetailsInfo = new ATMCardDetails(123, new Date(), CardType.CREDITCARD);
		else
			cardDetailsInfo = new ATMCardDetails(123, new Date(), CardType.DEDITCARD);

		ATMFactory cardFactoryInfo = new ATMFactory();
		PlatinumATMCard platinumCard = null;
		ATMCard normalCard = null;
		if (n == 1) {
			cardFactoryInfo.setATMCard(atmPin, accountNum);
			platinumCard = cardFactoryInfo.getInstancePlatinumCard();
			return platinumCard;
		} else {
			cardFactoryInfo.setATMCard(atmPin, accountNum, cardDetailsInfo);
			normalCard = cardFactoryInfo.getInstanceCard();
			return normalCard;
		}

	}

	public ATMCard getATMCard(int accountNum) {
		Scanner scannerObj = new Scanner(System.in);
		System.out.println("Enter the\n1.Platinum type\n2.Normal Type");
		int n = scannerObj.nextInt();
		return setATMCardType(n, accountNum);
	}

	public static void main(String[] args) {

		try {
			Account accountObj = getAccount();
			BankService bankObj = new Bank("XYZ");
			ATMCard cardInfo = bankObj.getATMCard(accountObj.getAccountNumber());
			System.out.println("Trasaction Begins....");
			Transaction transactionObj = bankObj.executeTransaction(accountObj, cardInfo);
			if (transactionObj == null) {
				System.out.println("Transaction Failed");
				System.exit(0);
			}
			cardInfo.setTransaction(transactionObj);
			System.out.println(transactionObj.getTransactionDetails().toString());
			System.out.println("Customer Balance-" + accountObj.getBalance());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception occured due to-" + e.getMessage());
		}
	}
}
