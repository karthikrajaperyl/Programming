package atmCardCreation;

public class WithDraw extends Transaction {
	private float amount;

	WithDraw(float amount) {
		this.amount = amount;
		this.setTransactionType();
	}

	public float getAmount() {
		return amount + transactionCharge(amount);
	}

	public float getAmount(int transactionPercent) {
		return transactionCharge(amount + transactionPercent);
	}

	public void setTransactionType() {
		this.transactionType = TransactionType.WITHDRAW;
	}
}
