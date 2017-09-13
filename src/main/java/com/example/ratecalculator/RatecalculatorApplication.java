package com.example.ratecalculator;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.ratecalculator.dao.CSVFileLenderDAO;
import com.example.ratecalculator.model.Borrower;
import com.example.ratecalculator.model.Lender;
import com.example.ratecalculator.model.Loan;
import com.example.ratecalculator.service.LoanService;

@SpringBootApplication
public class RatecalculatorApplication implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(RatecalculatorApplication.class);

    @Autowired
    CSVFileLenderDAO lenderDAO;
    
    @Autowired
    LoanService loanService;
    
	@Override
	public void run(String... args) {

		if (args.length < 2) {
			output("useage: java -jar ratecalculator-0.0.1-SNAPSHOT.jar <lenders-file.csv> <loan-amount>");
			return;
		}

		// get lenders from <lenders-file.csv>
		String lendersFilePath = args[0];
		List<Lender> lenders = null;
		try {
			lenders = lenderDAO.getLenders(lendersFilePath);
		} catch (IllegalArgumentException|IOException e) {
			output("Error loading lenders csv: " + e.getMessage());
			System.exit(1);
		}
		
		// set up borrower based <loan-amount> 
		Borrower borrower = null;
		try {
			int loanAmount = Integer.valueOf(args[1]);
			borrower = new Borrower(loanAmount);
		} catch (IllegalArgumentException e) {
			output("Error initiating ratecalculator - <loan-amount> must be a non-zero integer: " + e.getMessage());
			System.exit(1);
		}		

		// double check the initial setup
		if (lenders == null || borrower == null) {
			output("Error initiating ratecalculator - unable to initialise lenders list or borrower");
			System.exit(1);
			
		}
		
		// check we can provide the loan
		int amountAvailable = loanService.getTotalAmountAvailable(lenders);
		if (amountAvailable < borrower.getAmount()) {
			output("Is is not possible to provide a quote at this time");
			LOG.debug("loan of {} not available, max loan with these lenders is {}", borrower.getAmount(), amountAvailable);
			return;
		}

		// set up loan and calculate
		Loan loan = loanService.createBestLoan(borrower, lenders);
		loan.calculateRepayments(36);
		
		output(String.format("Requested amount: £%d", borrower.getAmount()));
		output(String.format("Rate: %s", loan.getPrettyRate()));
		output(String.format("Monthly repayment: %s", loan.getPrettyMonthyRepayements()));
		output(String.format("Total repayment: %s", loan.getPrettyTotalRepayements()));		
	}
	
	private static void output (String output) {
		LOG.debug(output);
		System.out.println(output);		
	}
	
	public static void main(String[] args) {		
        SpringApplication app = new SpringApplication(RatecalculatorApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args); 
	}
}
