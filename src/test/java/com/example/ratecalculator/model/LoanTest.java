package com.example.ratecalculator.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LoanTest {

	@Test(expected = IllegalArgumentException.class)
	public void createLoanWithNullBorrowerShouldThrowIllegalArgumentException() {
		Loan loan = new Loan(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void addNullLenderToLoanShouldThrowIllegalArgumentException() {
		Loan loan = new Loan(new Borrower(1000));
		loan.addLender(null);
	}
	
	@Test
	public void calculateTotalRepaymentOnSimpleLoanFor1Month() {
		
		Loan loan = simpleLoan();
		loan.calculateRepayments(1);

		assertEquals(1120, loan.getTotalRepayements(), 0);
		assertEquals(1120, loan.getMonthyRepayements(), 0);
		assertEquals(0.12, loan.getRate(), 0);
	}
	
	@Test
	public void calculateTotalRepaymentOnSimpleLoanOver6Months() {
		
		Loan loan = simpleLoan();
		loan.calculateRepayments(6);

		assertEquals(1126.16, loan.getTotalRepayements(), 0);
		assertEquals(187.69, loan.getMonthyRepayements(), 0);
		assertEquals(0.1262, loan.getRate(), 0);
	}
	
	@Test
	public void calculateTotalRepaymentOnSimpleLoanOver12Months() {
		
		Loan loan = simpleLoan();
		loan.calculateRepayments(12);

		assertEquals(1126.83, loan.getTotalRepayements(), 0);
		assertEquals(93.90, loan.getMonthyRepayements(), 0);
		assertEquals(0.1268, loan.getRate(), 0);
	}
	
	@Test
	public void calculateTotalRepaymentOnSimpleLoanOver36Months() {
		
		Loan loan = simpleLoan();
		loan.calculateRepayments(36);

		assertEquals(1430.77, loan.getTotalRepayements(), 0);
		assertEquals(39.74, loan.getMonthyRepayements(), 0);
		assertEquals(0.1436, loan.getRate(), 0);
	}

	private Loan simpleLoan() {
		Borrower borrower = new Borrower(1000);
		Loan loan = new Loan(borrower);
		loan.addLender(new Lender("Dave", 0.12, 1000));
		return loan;
	}
	
	@Test
	public void calculateTotalRepaymentOnMultiLoanFor1Month() {
		
		Loan loan = multiLoan();	
		loan.calculateRepayments(1);

		assertEquals(1110, loan.getTotalRepayements(), 0);
		assertEquals(1110, loan.getMonthyRepayements(), 0);
		assertEquals(0.11, loan.getRate(), 0);
	}
	
	@Test
	public void calculateTotalRepaymentOnMultiLoanFor6Months() {
		
		Loan loan = multiLoan();	
		loan.calculateRepayments(6);

		assertEquals(1115.21, loan.getTotalRepayements(), 0);
		assertEquals(185.87, loan.getMonthyRepayements(), 0);
		assertEquals(0.1152, loan.getRate(), 0);
	}
	
	@Test
	public void calculateTotalRepaymentOnMultiLoanFor12Months() {
		
		Loan loan = multiLoan();	
		loan.calculateRepayments(12);

		assertEquals(1115.77, loan.getTotalRepayements(), 0);
		assertEquals(92.98, loan.getMonthyRepayements(), 0);
		assertEquals(0.1158, loan.getRate(), 0);
	}
	
	@Test
	public void calculateTotalRepaymentOnMultiLoanFor36Months() {
		
		Loan loan = multiLoan();		
		loan.calculateRepayments(36);

		assertEquals(1389.48, loan.getTotalRepayements(), 0);
		assertEquals(38.60, loan.getMonthyRepayements(), 0);
		assertEquals(0.1298, loan.getRate(), 0);
	}
	
	@Test
	public void prettyPrintOutput() {
		
		Loan loan = multiLoan();		
		loan.calculateRepayments(36);

		assertEquals("£1389.48", loan.getPrettyTotalRepayements());
		assertEquals("£38.60", loan.getPrettyMonthyRepayements());
		assertEquals("%12.98", loan.getPrettyRate());
	}
	
	private Loan multiLoan() {
		Borrower borrower = new Borrower(1000);
		Loan loan = new Loan(borrower);
		loan.addLender(new Lender("Dave", 0.1, 500));
		loan.addLender(new Lender("Barry", 0.12, 1000));
		return loan;
	}
}
