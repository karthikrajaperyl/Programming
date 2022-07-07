package atmCardCreation;

public class Display {
	TransactionDetails tranasactionDetails;

	Display(TransactionDetails transactionObj) {
		this.tranasactionDetails = transactionObj;
	}

	public void displayTransactionDetails() {
		System.out.println(this.tranasactionDetails.toString());
	}
}
