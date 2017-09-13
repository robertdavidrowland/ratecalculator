package com.example.ratecalculator.model;

import org.junit.Test;

public class LenderTest {

	@Test(expected = IllegalArgumentException.class)
	public void createLenderWithNullNameShouldThrowIllegalArgumentException() {
		Lender lender = new Lender(null, 0.075, 1000);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createLenderWithNoNameShouldThrowIllegalArgumentException() {
		Lender lender = new Lender("", 0.075, 1000);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createLenderWithZeroRateShouldThrowIllegalArgumentException() {
		Lender lender = new Lender("Rob", 0, 1000);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createLenderWithZeroAvailableShouldThrowIllegalArgumentException() {
		Lender lender = new Lender("Rob", 0.075, 0);
	}
}
