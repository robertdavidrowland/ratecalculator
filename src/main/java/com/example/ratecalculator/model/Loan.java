package com.example.ratecalculator.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.ratecalculator.RatecalculatorApplication;

public final class Loan {
	
    private static final Logger LOG = LoggerFactory.getLogger(RatecalculatorApplication.class);

	private static int ROUNDING_MODE = BigDecimal.ROUND_HALF_EVEN;
	private static int CURRENCY_DECIMALS = 2;
	private static int PERCENTAGE_DECIMALS = 4;
	
	private final List<Lender> lenders = new ArrayList<Lender>();
	private final Borrower borrower;
	
	private BigDecimal rate = new BigDecimal("0.0000");
	private BigDecimal totalRepayments = new BigDecimal("0.00");
	private BigDecimal monthyRepayments = new BigDecimal("0.00");

	public Loan(Borrower borrower) {
		checkLoan(borrower);
		this.borrower = borrower;
	}
	
	private void checkLoan(Borrower borrower) {
		if (borrower == null) {
			throw new IllegalArgumentException("Attempt to create Loan with null Borrower");
		}	
	}

	public Borrower getBorrower() {
		return borrower;
	}
	
	public List<Lender> getLenders() {
		return lenders;
	}

	public void addLender(Lender lender) {
		if (lender == null) {
			throw new IllegalArgumentException("Attempt to add null Lender to Loan");
		}

		lenders.add(lender);
	} 

	public BigDecimal getRate() {
		return rate;
	}

	public String getPrettyRate() {
		return String.format("%%%.2f", rate.multiply(new BigDecimal("100")));
	}
	
	public BigDecimal getTotalRepayements() {
		return totalRepayments;
	}

	public String getPrettyTotalRepayements() {		
		return String.format("£%.2f", totalRepayments);
	}

	public BigDecimal getMonthyRepayements() {
		return monthyRepayments;
	}

	public String getPrettyMonthyRepayements() {
		return String.format("£%.2f", monthyRepayments);
	}

	public void calculateRepayments(int totalMonths) {
		
		BigDecimal totalAmount = new BigDecimal("0");
		BigDecimal totalRequired = this.getBorrower().getAmount();

		BigDecimal totalRepayments = new BigDecimal("0");
		
		for (Lender lender: this.getLenders()) {
			// loan amount from this lender
			BigDecimal amount = lender.getAvailable();
			if (totalAmount.add(amount).compareTo(totalRequired) > 0){
				amount = totalRequired.subtract(totalAmount);
			}
			
			totalAmount = totalAmount.add(amount);
			
			LOG.debug("Lender {} lends {}, total lent now {}", lender.getName(), amount, totalAmount);
			LOG.debug("Yearly rate from {} is {}", lender.getName(), lender.getRate());
			
			// calculate monthly rate from this lender
			BigDecimal rate = lender.getRate();
			BigDecimal monthlyRate = rate.divide(new BigDecimal("12"), PERCENTAGE_DECIMALS, ROUNDING_MODE);
			if (totalMonths < 12) {
				monthlyRate = rate.divide(new BigDecimal(String.valueOf(totalMonths)), PERCENTAGE_DECIMALS, ROUNDING_MODE);
			}

			LOG.debug("Monthly rate from {} is {}", lender.getName(), monthlyRate);

			// calculate repayments to this lender
			BigDecimal repayments = amount;
	        for(int i=0; i < totalMonths; i++) {
	        	repayments = repayments.add(repayments.multiply(monthlyRate));
				LOG.debug("Compound repayments from {} after {} months is {}", lender.getName(), i + 1, repayments);
	        }
	        
			LOG.debug("Compound repayments from {} is {}", lender.getName(), repayments);
	        
	        // total repayments
	        totalRepayments = totalRepayments.add(repayments);

			LOG.debug("totalRepayments is now {}", totalRepayments);

			LOG.debug("totalAmount {} == totalRequired {}", totalAmount, totalRequired);

	        // have we got everything we need here
	        if (totalAmount == totalRequired) {
				LOG.debug("{} == {} so we have all the loan - break", totalAmount, totalRequired);
	        	break;
	        }
		}

		// calculate compound rate and monthly repayments from total repayments and total loan amount
		BigDecimal totalCompoundRate = totalRepayments.subtract(totalAmount).divide(totalAmount, PERCENTAGE_DECIMALS, ROUNDING_MODE);
		if (totalMonths > 12) {
			totalCompoundRate = totalRepayments.subtract(totalAmount).divide(new BigDecimal(totalMonths), PERCENTAGE_DECIMALS, ROUNDING_MODE).multiply(new BigDecimal(12)).divide(totalAmount, PERCENTAGE_DECIMALS, ROUNDING_MODE);
		}
		
		LOG.debug("totalCompoundRate {}", totalCompoundRate);

		// monthly repayments straight forward
		BigDecimal monthyRepayments = totalRepayments.divide(new BigDecimal(String.valueOf(totalMonths)), CURRENCY_DECIMALS, ROUNDING_MODE);

		LOG.debug("monthyRepayments {}", monthyRepayments);

		// set values on this object, rounding to 2dp
		this.rate = totalCompoundRate.setScale(PERCENTAGE_DECIMALS, ROUNDING_MODE);
		this.totalRepayments = totalRepayments.setScale(CURRENCY_DECIMALS, ROUNDING_MODE);
		this.monthyRepayments = monthyRepayments.setScale(CURRENCY_DECIMALS, ROUNDING_MODE);
		
		LOG.debug("rate {}", this.getRate());
		LOG.debug("totalRepayments {}", this.getTotalRepayements());
		LOG.debug("monthyRepayments {}", this.getMonthyRepayements());
	}
}
