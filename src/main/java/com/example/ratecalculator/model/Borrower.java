package com.example.ratecalculator.model;

import java.math.BigDecimal;

public final class Borrower {
	
	final private BigDecimal amount;

	public Borrower(BigDecimal amount) {
		checkAmount(amount);
		this.amount = amount;
	}
	
	public Borrower(String amount) {
		this(new BigDecimal(amount));
	}
	
	public Borrower(int amount) {
		this(new BigDecimal(String.valueOf(amount)));
	}
	
	public BigDecimal getAmount() {
		return amount;
	}

	public String getPrettyAmount() {
		return String.format("£%.2f", amount);
	}
	
	public void checkAmount(BigDecimal amount) {
		if (amount.intValue() < 1000) {
			throw new IllegalArgumentException(String.format("Attempt to create Borrower amount with value < 1000 (%d)", amount.intValue()));
		}
		else if (amount.intValue() > 15000) {
			throw new IllegalArgumentException(String.format("Attempt to create Borrower amount with value > 15000 (%d)", amount.intValue()));
		}
		else if (amount.intValue() % 100 != 0) {
			throw new IllegalArgumentException(String.format("Attempt to create Borrower amount with value not divisible by zero (%d)", amount.intValue()));
		}
	}
}
