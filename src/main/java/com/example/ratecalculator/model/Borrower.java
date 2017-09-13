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
		if (amount == 0) {
			throw new IllegalArgumentException("attempt to create Borrower with 0 value for amount");
		}
		this.amount = amount;
	}
}
