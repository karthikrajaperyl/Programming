package atmCardCreation;

public class SwipeType extends Transaction {
	private float amount;

	SwipeType(float amount) {
		this.amount = amount;
		this.setTransactionType();
	}

	private float cashBackCharge(float amount) {
		return (amount * 1) / 100;
	}

	public float getAmount() {
		return amount + transactionCharge(amount) - cashBackCharge(amount);
	}

	public void setTransactionType() {
		this.transactionType = TransactionType.SWIPETYPE;
	}
}