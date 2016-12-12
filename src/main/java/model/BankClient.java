package model;

import java.math.BigInteger;

public class BankClient {
	
	BigInteger cardNumber;
	String cardHolderName;

	public BankClient() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BankClient(BigInteger cardNumber, String cardHolderName) {
		super();
		this.cardNumber = cardNumber;
		this.cardHolderName = cardHolderName;
	}
	public BigInteger  getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(BigInteger cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getCardHolderName() {
		return cardHolderName;
	}
	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	@Override
	public String toString() {
		return "BankClient [cardNumber=" + cardNumber + ", cardHolderName=" + cardHolderName + "]";
	}

}
