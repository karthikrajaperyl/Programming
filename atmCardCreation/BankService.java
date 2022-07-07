package atmCardCreation;

public interface BankService {
	boolean validUser(int pin, int cardNumber);

	Transaction executeTransaction(Account account, ATMCard cardinfo) throws Exception;

	ATMCard getATMCard(int accountNumber);
}