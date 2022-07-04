package atmCardCreation;

public interface BankService {
	void validUser(int pin, ATMCardDetails cardInfo);

	Transaction executeTransaction(Account account, ATMCard cardinfo);
}