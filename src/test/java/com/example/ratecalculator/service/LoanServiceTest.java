package com.example.ratecalculator.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.example.ratecalculator.model.Borrower;
import com.example.ratecalculator.model.Lender;
import com.example.ratecalculator.model.Loan;

public class LoanServiceTest {

	LoanService service = new LoanService();
	
	@Test
	public void createSimpleLoanFromSingleLender() {
		
		Borrower borrower = new Borrower(10);
		List<Lender> lenders = new ArrayList<>();
		lenders.add(new Lender("Mark", 8.0, 10));
		
		Loan loan = service.createBestLoan(borrower, lenders);
		
		assertEquals(1, loan.getLenders().size());
		assertEquals("Mark", lenders.get(0).getName());
	}
	
	@Test
	public void createLoanFromMultipleIdealLenders() {
		
		Borrower borrower = new Borrower(20);
		List<Lender> lenders = new ArrayList<>();
		lenders.add(new Lender("Phil", 9.0, 20));
		lenders.add(new Lender("Mark", 8.0, 10));
		
		Loan loan = service.createBestLoan(borrower, lenders);
		
		assertEquals(2, loan.getLenders().size());
		assertEquals(1, loan.getLenders().stream().filter(l -> l.getName().equals("Mark")).count());
		assertEquals(1, loan.getLenders().stream().filter(l -> l.getName().equals("Phil")).count());
	}
	
	@Test
	public void createLoanFromMultipleLenders() {
		
		Borrower borrower = new Borrower(20);
		List<Lender> lenders = new ArrayList<>();
		lenders.add(new Lender("Phil", 9.0, 20));
		lenders.add(new Lender("Tracy", 10.0, 30));
		lenders.add(new Lender("Mark", 8.0, 10));
		
		Loan loan = service.createBestLoan(borrower, lenders);
		
		assertEquals(2, loan.getLenders().size());
		assertEquals(1, loan.getLenders().stream().filter(l -> l.getName().equals("Mark")).count());
		assertEquals(1, loan.getLenders().stream().filter(l -> l.getName().equals("Phil")).count());
	}
	
	@Test
	public void getTotalAmountAvailable() {
		
		List<Lender> lenders = new ArrayList<>();
		lenders.add(new Lender("Phil", 9.0, 20));
		lenders.add(new Lender("Tracy", 10.0, 30));
		lenders.add(new Lender("Mark", 8.0, 10));
		
		int totalAmount = service.getTotalAmountAvailable(lenders);
		assertEquals(60, totalAmount);
		
	}
}
