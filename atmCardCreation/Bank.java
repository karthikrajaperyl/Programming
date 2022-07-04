package atmCardCreation;

import java.util.Scanner;
import java.util.Date;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;



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
