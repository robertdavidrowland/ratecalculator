package com.example.ratecalculator.model;

public class Borrower {
	private int amount;

	public Borrower(int amount) {
		this.setAmount(amount);
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		if (amount < 1000) {
			throw new IllegalArgumentException(String.format("Attempt to create Borrower amount with value < 1000 (%d)", amount));
		}
		else if (amount > 15000) {
			throw new IllegalArgumentException(String.format("Attempt to create Borrower amount with value > 15000 (%d)", amount));
		}
		else if (amount % 100 != 0) {
			throw new IllegalArgumentException(String.format("Attempt to create Borrower amount with value not divisible by zero (%d)", amount));
		}
		this.amount = amount;
	}
}
