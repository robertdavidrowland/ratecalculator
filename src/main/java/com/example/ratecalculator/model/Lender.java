package com.example.ratecalculator.model;

import java.math.BigDecimal;

public final class Lender {
	
	private final String name;
	private final BigDecimal rate;
	private final BigDecimal available;
	
	public Lender(String name, BigDecimal rate, BigDecimal available) {
		checkLender(name, rate, available);
		
		this.name = name;
		this.rate = rate;
		this.available = available;
	}

	public Lender(String name, String rate, String available) {
		this(name, new BigDecimal(rate), new BigDecimal(available));
	}
	
	public String getName() {
		return name;
	}
	
	public BigDecimal getRate() {
		return rate;
	}

	public BigDecimal getAvailable() {
		return available;
	}

	private void checkLender(String name, BigDecimal rate, BigDecimal available) {
		if (name == null) {
			throw new IllegalArgumentException("Attempt to create Lender with null value for name");
		}
		else if (name.equals("")) {
			throw new IllegalArgumentException("Attempt to create Lender with empty string value for name");
		}
		else if (rate.doubleValue() == 0.0) {
			throw new IllegalArgumentException("Attempt to create Lender with 0.0 value for rate");
		}
		else if (available.intValue() == 0) {
			throw new IllegalArgumentException("Attempt to create Lender with 0 value for available");
		}
	}
}
