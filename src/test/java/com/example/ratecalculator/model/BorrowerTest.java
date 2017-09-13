package com.example.ratecalculator.model;

import org.junit.Test;

public class BorrowerTest {

	@Test(expected = IllegalArgumentException.class)
	public void createBorrowerWithZeroAmountShouldThrowIllegalArgumentException() {
		Borrower borrower = new Borrower(0);
	}
}
