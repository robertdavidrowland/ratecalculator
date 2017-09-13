package com.example.ratecalculator.model;

import java.util.ArrayList;
import java.util.List;

public class Loan {
			
	private List<Lender> lenders;
	private Borrower borrower;
	private double rate;
	private double totalRepayements;
	private double monthyRepayements;

	public Loan(Borrower borrower) {
		this.setBorrower(borrower);
	}
	
	public Borrower getBorrower() {
		return borrower;
	}

	public void setBorrower(Borrower borrower) {
		if (borrower == null) {
			throw new IllegalArgumentException("Attempt to create Loan with null Borrower");
		}
		this.borrower = borrower;
	}
	
	public List<Lender> getLenders() {
		return lenders;
	}

	public void setLenders(List<Lender> lenders) {
		this.lenders = lenders;
	}

	public void addLender(Lender lender) {
		if (lender == null) {
			throw new IllegalArgumentException("Attempt to add null Lender to Loan");
		}
		if (lenders == null) {
			lenders = new ArrayList<Lender>();
		}
		lenders.add(lender);
	} 

	public double getRate() {
		return rate;
	}

	public String getPrettyRate() {
		return String.format("%%%.2f", rate * 100);
	}
	
	public void setRate(double rate) {
		this.rate = rate;
	}

	public double getTotalRepayements() {
		return totalRepayements;
	}

	public String getPrettyTotalRepayements() {		
		return String.format("£%.2f", totalRepayements);
	}
	
	public void setTotalRepayements(double totalRepayements) {
		this.totalRepayements = totalRepayements;
	}

	public double getMonthyRepayements() {
		return monthyRepayements;
	}

	public String getPrettyMonthyRepayements() {
		return String.format("£%.2f", monthyRepayements);
	}
	
	public void setMonthyRepayements(double monthyRepayements) {
		this.monthyRepayements = monthyRepayements;
	}

	public void calculateRepayments(int totalMonths) {
		
		int totalAmount = 0;
		int totalRequired = this.getBorrower().getAmount();

		double totalRepayements = 0;
		double totalLoaned = 0;
		
		for (Lender lender: this.getLenders()) {
			// loan amount from this lender
			int amount = lender.getAvailable();
			if (totalAmount + amount > totalRequired){
				amount = totalRequired - totalAmount;
			}
			
			totalAmount += amount;
			
			// calculate monthly rate from this lender
			double rate = lender.getRate();
			double monthlyRate = rate / 12;
			if (totalMonths < 12) {
				monthlyRate = rate / totalMonths;
			}

			// calculate repayments to this lender
			double repayements = amount;
	        for(int i=0; i < totalMonths; i++) {
	        	repayements = repayements + repayements * monthlyRate;
	        }
	        
	        // total repayments
	        totalRepayements += repayements;
	        
	        // have we got everything we need here
	        if (totalAmount == totalRequired) {
	        	break;
	        }
		}

		// calculate compound rate and monthly repayments from total repayments and total loan amount
        double totalCompoundRate = ((totalRepayements - totalAmount) / totalAmount);        
		double compoundRate = totalCompoundRate / totalMonths * 12;
		if (totalMonths < 12) {
			compoundRate = totalCompoundRate;
		}
        
		// monthly repayments straight forward
        double monthyRepayements = totalRepayements / totalMonths;
		
		// set values on this object, rounding to 2dp
		this.setRate((double) Math.round(compoundRate * 10000d) / 10000d);
		this.setTotalRepayements((double) Math.round(totalRepayements * 100d) / 100d);
		this.setMonthyRepayements((double) Math.round(monthyRepayements * 100d) / 100d);
	}
}
