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

    private static final int DEFAULT_MONTHS = 36;
    
    @Autowired
    CSVFileLenderDAO lenderDAO;
    
    @Autowired
    LoanService loanService;
    
	@Override
	public void run(String... args) {

		if (args.length < 2) {
			output("Usage: java -jar ratecalculator-0.0.1-SNAPSHOT.jar <lenders-file.csv> <loan-amount>");
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
			output("Loan amount must be the loan in pounds, greater than £1000, less than £15000, in £100 increments");
			output(e.getMessage());
			System.exit(1);
		}		

		// double check the initial setup
		if (lenders == null || borrower == null) {
			output("Unable to initialise lenders list or borrower");
			System.exit(1);
		}
		
		// get optional value for number of months
		int months = DEFAULT_MONTHS;
		if (args.length > 2) {
			months = Integer.valueOf(args[2]);
		}

		// set up loan and calculate
		Loan loan = loanService.createBestLoan(borrower, lenders);
		loan.calculateRepayments(months);
		
		output(String.format("Requested amount: %s", borrower.getPrettyAmount()));
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
