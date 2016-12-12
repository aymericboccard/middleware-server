package model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyBank {
	static List<BankClient> clientsList = new ArrayList<BankClient>(
			Arrays.asList(new BankClient(new BigInteger("1111111111111111"), "joseph"),
					new BankClient(new BigInteger("7777777777777777"), "aymeric")));

	public boolean checkCardValidity(BankClient bankClient) {
		System.out.println(bankClient);
		System.out.println(clientsList);

		boolean contains = false;
		for (int i = 0; i < clientsList.size(); i++) {
			if (clientsList.get(i).getCardHolderName().equals(bankClient.cardHolderName)
					&& clientsList.get(i).getCardNumber().equals(bankClient.cardNumber)) {
				contains = true;
			}
		}

		return contains;

	}

}
