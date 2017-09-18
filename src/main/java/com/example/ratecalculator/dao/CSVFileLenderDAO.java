package com.example.ratecalculator.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.ratecalculator.model.Lender;

@Service
public class CSVFileLenderDAO {

	private static final Logger LOG = LoggerFactory.getLogger(CSVFileLenderDAO.class);

	private final static String COMMA = ",";
	
	public List<Lender> getLenders(String filePath) throws FileNotFoundException, IOException, IllegalArgumentException {
			
    	File file = new File(filePath);
    	
	    try (
	    	InputStream is = new FileInputStream(file);
    		BufferedReader br = new BufferedReader(new FileReader(filePath))
	    ) {
	    	AtomicInteger noOfEntries = new AtomicInteger(0);
	    	
	    	List<Lender> inputList = br.lines()
	    			 .skip(1)
	    			 .map(l -> {
	    				noOfEntries.incrementAndGet();
						LOG.debug("parse line {}", l);
						String[] v = l.split(COMMA);
						try {
							return new Lender(v[0], String.valueOf(v[1]), String.valueOf(v[2]));
						} 
						catch (ArrayIndexOutOfBoundsException e) {
							LOG.debug("error: {} expected 3 columns, only found {}", e.getMessage(), v.length);
						} 
						catch (IllegalArgumentException e) {
							LOG.debug("error: {}", e.getMessage());
						}
						return null;	    				 
	    			 })
	    			 .filter(l -> l != null)
	    			 .collect(Collectors.toList());
	    	
	    	if (noOfEntries.get() != inputList.size()) {
				throw new IllegalArgumentException("attempt to load Lender data with invalid Lenders - check logs for details");
	    	}
	    	
			return inputList;     
	    }
	}
}
