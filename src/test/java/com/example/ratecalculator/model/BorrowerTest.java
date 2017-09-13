package com.example.ratecalculator.model;

import org.junit.Test;

public class BorrowerTest {

	@Test(expected = IllegalArgumentException.class)
	public void createBorrowerWithAmountBelow1000PoundsShouldThrowIllegalArgumentException() {
		Borrower borrower = new Borrower(999);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createBorrowerWithAmountAbove15000PoundsShouldThrowIllegalArgumentException() {
		Borrower borrower = new Borrower(15001);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createBorrowerWithAmountNotDivisableBy100PoundsShouldThrowIllegalArgumentException() {
		Borrower borrower = new Borrower(7050);
	}
}
