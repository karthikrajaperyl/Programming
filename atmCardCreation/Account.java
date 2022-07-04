package atmCardCreation;

public class Account {
	private int accountNumber;
	private float amount;

	Account(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public int getAccountNumber() {
		return this.accountNumber;
	}
	public boolean checkMinimumBalance(float amount) 
	{
		if(amount>=100)
			return true;
		else
			return false;
	}
	public void setBalance(float amount) {
		if(checkMinimumBalance(amount))
		this.amount = amount;
	}

	public float getBalance() {
		return this.amount;
	}
}
