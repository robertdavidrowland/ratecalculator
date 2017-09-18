package com.example.ratecalculator.model;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

public class LoanTest {

	@Test(expected = IllegalArgumentException.class)
	public void createLoanWithNullBorrowerShouldThrowIllegalArgumentException() {
		new Loan(null);
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

		assertEquals(new BigDecimal("1120.00"), loan.getTotalRepayements());
		assertEquals(new BigDecimal("1120.00"), loan.getMonthyRepayements());
		assertEquals(new BigDecimal("0.1200"), loan.getRate());
	}
	
	@Test
	public void calculateTotalRepaymentOnSimpleLoanOver6Months() {
		
		Loan loan = simpleLoan();
		loan.calculateRepayments(6);

		assertEquals(new BigDecimal("1126.16"), loan.getTotalRepayements());
		assertEquals(new BigDecimal("187.69"), loan.getMonthyRepayements());
		assertEquals(new BigDecimal("0.1262"), loan.getRate());
	}
	
	@Test
	public void calculateTotalRepaymentOnSimpleLoanOver12Months() {
		
		Loan loan = simpleLoan();
		loan.calculateRepayments(12);

		assertEquals(new BigDecimal("1126.83"), loan.getTotalRepayements());
		assertEquals(new BigDecimal("93.90"), loan.getMonthyRepayements());
		assertEquals(new BigDecimal("0.1268"), loan.getRate());
	}
	
	@Test
	public void calculateTotalRepaymentOnSimpleLoanOver36Months() {
		
		Loan loan = simpleLoan();
		loan.calculateRepayments(36);

		assertEquals(new BigDecimal("1430.77"), loan.getTotalRepayements());
		assertEquals(new BigDecimal("39.74"), loan.getMonthyRepayements());
		assertEquals(new BigDecimal("0.1436"), loan.getRate());
	}

	private Loan simpleLoan() {
		Borrower borrower = new Borrower(1000);
		Loan loan = new Loan(borrower);
		loan.addLender(new Lender("Dave", "0.12", "1000"));
		return loan;
	}
	
	@Test
	public void calculateTotalRepaymentOnMultiLoanFor1Month() {
		
		Loan loan = multiLoan();	
		loan.calculateRepayments(1);

		assertEquals(new BigDecimal("1110.00"), loan.getTotalRepayements());
		assertEquals(new BigDecimal("1110.00"), loan.getMonthyRepayements());
		assertEquals(new BigDecimal("0.1100"), loan.getRate());
	}
	
	@Test
	public void calculateTotalRepaymentOnMultiLoanFor6Months() {
		
		Loan loan = multiLoan();	
		loan.calculateRepayments(6);

		assertEquals(new BigDecimal("1115.32"), loan.getTotalRepayements());
		assertEquals(new BigDecimal("185.89"), loan.getMonthyRepayements());
		assertEquals(new BigDecimal("0.1153"), loan.getRate());
	}
	
	@Test
	public void calculateTotalRepaymentOnMultiLoanFor12Months() {
		
		Loan loan = multiLoan();	
		loan.calculateRepayments(12);

		assertEquals(new BigDecimal("1115.55"), loan.getTotalRepayements());
		assertEquals(new BigDecimal("92.96"), loan.getMonthyRepayements());
		assertEquals(new BigDecimal("0.1155"), loan.getRate());
	}
	
	@Test
	public void calculateTotalRepaymentOnMultiLoanFor36Months() {
		
		Loan loan = multiLoan();		
		loan.calculateRepayments(36);

		assertEquals(new BigDecimal("1388.67"), loan.getTotalRepayements());
		assertEquals(new BigDecimal("38.57"), loan.getMonthyRepayements());
		assertEquals(new BigDecimal("0.1296"), loan.getRate());
	}
	
	@Test
	public void prettyPrintOutput() {
		
		Loan loan = multiLoan();		
		loan.calculateRepayments(36);

		assertEquals("£1388.67", loan.getPrettyTotalRepayements());
		assertEquals("£38.57", loan.getPrettyMonthyRepayements());
		assertEquals("%12.96", loan.getPrettyRate());
	}
	
	private Loan multiLoan() {
		Borrower borrower = new Borrower(1000);
		Loan loan = new Loan(borrower);
		loan.addLender(new Lender("Dave", "0.1", "500"));
		loan.addLender(new Lender("Barry", "0.12", "1000"));
		return loan;
	}
}
