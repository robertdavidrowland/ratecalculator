package com.example.ratecalculator.model;

import java.math.BigDecimal;

public class Lender {
	
	private String name;
	private BigDecimal rate;
	private BigDecimal available;
	
	public Lender(String name, BigDecimal rate, BigDecimal available) {
		this.setName(name);
		this.setRate(rate);
		this.setAvailable(available);
	}

	public Lender(String name, String rate, String available) {
		this.setName(name);
		this.setRate(rate);
		this.setAvailable(available);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name == null) {
			throw new IllegalArgumentException("Attempt to create Lender with null value for name");
		}
		else if (name.equals("")) {
			throw new IllegalArgumentException("Attempt to create Lender with empty string value for name");
		}
		this.name = name;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		if (rate.doubleValue() == 0.0) {
			throw new IllegalArgumentException("Attempt to create Lender with 0.0 value for rate");
		}
		this.rate = rate;
	}

	public void setRate(String rate) {
		this.setRate(new BigDecimal(rate));
	}
	
	public BigDecimal getAvailable() {
		return available;
	}

	public void setAvailable(BigDecimal available) {
		if (available.intValue() == 0) {
			throw new IllegalArgumentException("Attempt to create Lender with 0 value for available");
		}
		this.available = available;;
	}
	
	public void setAvailable(String available) {
		this.setAvailable(new BigDecimal(available));
	}
}
