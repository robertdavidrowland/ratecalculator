package com.example.ratecalculator.model;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

public class LenderTest {

	public void createLender() {
		Lender lender = new Lender("Rob", "0.075", "1000");
		
		assertEquals("Rob", lender.getName());
		assertEquals(new BigDecimal("0.075"), lender.getRate());
		assertEquals(new BigDecimal("1000"), lender.getAvailable());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createLenderWithNullNameShouldThrowIllegalArgumentException() {
		new Lender(null, "0.075", "1000");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createLenderWithNoNameShouldThrowIllegalArgumentException() {
		new Lender("", "0.075", "1000");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createLenderWithZeroRateShouldThrowIllegalArgumentException() {
		new Lender("Rob", "0", "1000");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createLenderWithZeroAvailableShouldThrowIllegalArgumentException() {
		new Lender("Rob", "0.075", "0");
	}
}
