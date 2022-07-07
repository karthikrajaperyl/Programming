package atmCardCreation;

import java.text.SimpleDateFormat;
import java.util.Date;

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

	@Override
	public String toString() {
		return "TransactionId-" + this.transactionId + "TransactionDate-" + this.transactionDate + "TransactionStatus-"
				+ this.transactionStatus;
	}
}
