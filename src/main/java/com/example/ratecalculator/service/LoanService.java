package com.example.ratecalculator.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ratecalculator.model.Borrower;
import com.example.ratecalculator.model.Lender;
import com.example.ratecalculator.model.Loan;

@Service
public class LoanService {

	public Loan createBestLoan(Borrower borrower, List<Lender> lenders) {
		
		Loan loan = new Loan(borrower);

		// order possible loans by rate, best rate first
		lenders.sort((Lender o1, Lender o2) -> o1.getRate().compareTo(o2.getRate()));
		
		// iterate through possible loans until we have enough to cover the borrower amount
		BigDecimal totalRequired = borrower.getAmount();
		for(Lender lender: lenders) {
			BigDecimal amountAvailable = lender.getAvailable();
			
			totalRequired = totalRequired.subtract(amountAvailable);
			loan.addLender(lender);

			if (totalRequired.intValue() <= 0) {
				break;				
			}
		}

		if (totalRequired.intValue() <= 0) {
			return loan;
		}
		
		throw new IllegalArgumentException("Not enough lenders to cover loan");
	}

	public int getTotalAmountAvailable(List<Lender> lenders) {
		return lenders.stream().mapToInt(o -> o.getAvailable().intValue()).sum();
	}
}
