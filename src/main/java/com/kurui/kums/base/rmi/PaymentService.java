package com.kurui.kums.base.rmi;

public interface PaymentService {

	public String authorizeCreditCard(String cardNumber, String cardHolderName,
			int expireMonth, int expireYear, float amount) throws Exception;

	public void settlePayment(String authCode, int merchantNumber, float amount)
			throws Exception;
}
