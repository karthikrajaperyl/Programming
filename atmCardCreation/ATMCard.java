package atmCardCreation;

public class ATMCard {
	private static int id;
	private int atmPin;
	private int atmId;
	private ATMCardDetails cardInfo;
	private int accountNum;
	private Transaction transactionObj;

	ATMCard(Builder builder) {
		this.atmPin = builder.atmPin;
		this.cardInfo = builder.cardInfo;
		this.accountNum = builder.accountNum;
		this.atmId = ++id;
	}

	public static class Builder {
		private int atmPin;
		private int atmId;
		private ATMCardDetails cardInfo;
		private int accountNum;
		private Transaction TransactionObj;

		public static Builder newInstance(int ATMPin) {
			return new Builder(ATMPin);
		}

		private Builder(int ATMPin) {
			this.atmPin = ATMPin;
		}

		public Builder setAccount(int accountNum) {
			this.accountNum = accountNum;
			return this;
		}

		public Builder setAtmCardDetails(ATMCardDetails cardDetails) {
			this.cardInfo = cardDetails;
			return this;
		}

		public ATMCard build() {
			return new ATMCard(this);
		}
	}

	public int getATMPin() {
		return this.atmPin;
	}

	public void setTransaction(Transaction transactionInfo) {
		this.transactionObj = transactionInfo;
		Bank.transactionMaintenance.put(this.accountNum, this.transactionObj);
	}

	public ATMCardDetails getCardDetails() {
		return cardInfo;
	}
}

class PlatinumATMCard extends ATMCard {
	private int earningPoints;
	private int ATMPin;
	private ATMCard atmCard;
	private int fuelOffer;

	PlatinumATMCard(int ATMPin) {
		super(ATMCard.Builder.newInstance(ATMPin));
	}

	public void setFuelOffer(int offer) {
		this.fuelOffer = offer;
	}

	public int getFuelOffer() {
		return this.fuelOffer;
	}
	// cardObj =
	// ATMCard.Builder.newInstance(ATMPin).setAccount(accountObj).setAtmCardDetails(cardInfo).build();
}

class ATMFactory {
	private volatile ATMCard cardObj;
	private volatile PlatinumATMCard platinumCardObj;

	public void setATMCard(int ATMPin, int accountNum, ATMCardDetails cardInfo) {
		cardObj = ATMCard.Builder.newInstance(ATMPin).setAccount(accountNum).setAtmCardDetails(cardInfo).build();
	}

	public void setATMCard(int ATMPin, int accountNum) {
		platinumCardObj = new PlatinumATMCard(ATMPin);
	}

	public ATMCard getInstanceCard() {
		return cardObj;
	}

	public PlatinumATMCard getInstancePlatinumCard() {
		return platinumCardObj;
	}
}