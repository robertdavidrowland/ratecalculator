package com.example.ratecalculator.dao;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.example.ratecalculator.dao.CSVFileLenderDAO;
import com.example.ratecalculator.model.Lender;

public class CSVFileLenderDAOTest {

	CSVFileLenderDAO dao = new CSVFileLenderDAO();
	
	@Test
	public void getLendersFromGoodFile() throws FileNotFoundException, IOException, IllegalArgumentException {
		
		List<Lender> lenders = dao.getLenders("src/test/data/market-good-data.csv");
	
		assertEquals(7, lenders.size());
		assertEquals("Bob", lenders.get(0).getName());
		assertEquals(0.104, lenders.get(3).getRate(), 0);
		assertEquals(60, lenders.get(6).getAvailable());
	}

	@Test(expected = FileNotFoundException.class)
	public void getLendersFromMissingFileShouldThrowIllegalArgumentException() throws FileNotFoundException, IOException, IllegalArgumentException {
		List<Lender> lenders = dao.getLenders("src/test/data/missing-market-file.csv");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getLendersFromBadFileShouldThrowIllegalArgumentException() throws FileNotFoundException, IOException, IllegalArgumentException {
		List<Lender> lenders = dao.getLenders("src/test/data/market-bad-data.csv");
	}
}
