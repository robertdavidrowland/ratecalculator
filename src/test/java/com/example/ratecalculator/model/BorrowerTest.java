package com.example.ratecalculator.model;

import org.junit.Test;

public class BorrowerTest {

	@Test(expected = IllegalArgumentException.class)
	public void createBorrowerWithAmountBelow1000PoundsShouldThrowIllegalArgumentException() {
		new Borrower(999);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createBorrowerWithAmountAbove15000PoundsShouldThrowIllegalArgumentException() {
		new Borrower(15001);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createBorrowerWithAmountNotDivisableBy100PoundsShouldThrowIllegalArgumentException() {
		new Borrower(7050);
	}
}
