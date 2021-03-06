package atmCardCreation;

enum TransactionType {
	WITHDRAW, SWIPETYPE
}

enum TransactionStatus {
	PENDING, SUCCESS, FAILED, ERORR
}

abstract public class Transaction {
	TransactionType transactionType;
	TransactionDetails transactionObj;

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

	public float transactionCharge(float amount, int deductionPercent) {
		return (amount * deductionPercent) / 100;
	}

	abstract float getAmount();

	abstract void setTransactionType();
}
