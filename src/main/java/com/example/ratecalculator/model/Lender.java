package com.example.ratecalculator.model;

public class Lender {
	private String name;
	private double rate;
	private int	available;
	
	public Lender(String name, double rate, int available) {
		this.setName(name);
		this.setRate(rate);
		this.setAvailable(available);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name == null) {
			throw new IllegalArgumentException("attempt to create Lender with null value for name");
		}
		else if (name.equals("")) {
			throw new IllegalArgumentException("attempt to create Lender with empty string value for name");
		}
		this.name = name;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		if (rate == 0.0) {
			throw new IllegalArgumentException("attempt to create Lender with 0.0 value for rate");
		}
		this.rate = rate;
	}

	public int getAvailable() {
		return available;
	}

	public void setAvailable(int available) {
		if (available == 0) {
			throw new IllegalArgumentException("attempt to create Lender with 0 value for available");
		}
		this.available = available;
	}
}
