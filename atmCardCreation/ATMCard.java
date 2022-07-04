package atmCardCreation;



public class ATMCard {
	private static int id;
	private int atmPin;
	private int atmId;
	private BankService bankService;
	private ATMCardDetails cardInfo;
	private Account accountObj;
	Transaction TransactionObj;

	ATMCard(Builder builder) {
		this.atmPin = builder.atmPin;
		this.atmPin = ++id;
	}

	ATMCard cardObj;

	public static class Builder {
		private static int id;
		private int atmPin;
		private int atmId;
		private BankService bankService;
		private ATMCardDetails cardInfo;
		private Account accountObj;
		private Transaction TransactionObj;

		public static Builder newInstance() {
			return new Builder();
		}

		private Builder() {
		}

		public Builder setAccount(Account accountObj) {
			this.accountObj = accountObj;
			return this;
		}

		public Builder setAtmCardDetails(ATMCardDetails cardDetails) {
			this.cardInfo = cardDetails;
			return this;
		}

		public ATMCard build(int ATMPin) {
			return new ATMCard(this);
		}
	}

	public void setBankService(BankService bankService) {
		this.bankService = bankService;
	}
}

class ATMFactory {
	private volatile ATMCard cardObj;

	public ATMFactory(int ATMPin, Account accountObj, ATMCardDetails cardInfo) {
		cardObj = ATMCard.Builder.newInstance().setAccount(accountObj).setAtmCardDetails(cardInfo).build(ATMPin);
	}

	public ATMCard getInstance() {
		return cardObj;
	}
}