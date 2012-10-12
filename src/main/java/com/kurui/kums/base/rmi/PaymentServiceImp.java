package com.kurui.kums.base.rmi;

public class PaymentServiceImp implements PaymentService {

	public String authorizeCreditCard(String cardNumber, String cardHolderName,
			int expireMonth, int expireYear, float amount) throws Exception {
		System.out.println("=======authorizeCreditCard()=========");
		String authCode = "001";
		return authCode;
	}

	public void settlePayment(String authCode, int merchantNumber, float amount)
			throws Exception {
		System.out.println("=======settlePayment()=========");

	}

}
