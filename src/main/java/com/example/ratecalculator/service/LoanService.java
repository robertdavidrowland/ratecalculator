package com.example.ratecalculator.service;

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
		lenders.sort((Lender o1, Lender o2) -> Double.valueOf(o1.getRate()).compareTo(Double.valueOf(o2.getRate())));
		
		// iterate through possible loans until we have enough to cover the borrower amount
		int totalRequired = borrower.getAmount();
		for(Lender lender: lenders) {
			int amountAvailable = lender.getAvailable();
			
			totalRequired -= amountAvailable;
			loan.addLender(lender);

			if (totalRequired <= 0) {
				break;				
			}
		}

		if (totalRequired <= 0) {
			return loan;
		}
		else {
			throw new IllegalArgumentException("Not enough lenders to cover loan");
		}
	}

	public int getTotalAmountAvailable(List<Lender> lenders) {
		return lenders.stream().mapToInt(o -> o.getAvailable()).sum();
	}
}
