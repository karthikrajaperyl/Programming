package atmCardCreation;

import java.util.Date;

enum CardType {
	DEDITCARD, CREDITCARD
}

public class ATMCardDetails {
	final int CCVPin;
	final Date validity;
	final CardType cardType;

	ATMCardDetails(int ccvPin, Date validity, CardType cardType) {
		this.CCVPin = ccvPin;
		this.validity = validity;
		this.cardType = cardType;
	}

	public Date getValidity() {
		return this.validity;
	}
}
