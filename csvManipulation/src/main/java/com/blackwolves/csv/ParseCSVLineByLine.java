/**
 * 
 */
package com.blackwolves.csv;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

/**
 * @author gaston.dapice
 *
 */
public class ParseCSVLineByLine {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {

		CSVWriter writer = new CSVWriter(new FileWriter("C:\\Users\\gaston.dapice\\Downloads\\__BW_lists\\250k\\A_depurated.csv"));
		
		CSVReader reader = new CSVReader(new FileReader("C:\\Users\\gaston.dapice\\Downloads\\__BW_lists\\zbounced.csv"),',', '"');

		List<String[]> allBouncedRows = reader.readAll();
		
		reader = new CSVReader(new FileReader("C:\\Users\\gaston.dapice\\Downloads\\__BW_lists\\250k\\A.csv"),',', '"');
		
		List<String[]> allRows = reader.readAll();

		// Read CSV line by line and use the string array as you want
		for (String[] row : allRows) {
			boolean bounced = false;
			for (String[] bouncedRow : allBouncedRows) {
				if(row[0].equals(bouncedRow[0])){
					bounced = true;
					System.out.println("bounced email removed: " + bouncedRow);
				}
			}
			if(!bounced){
				writer.writeNext(row);
			}
		}
		writer.close();
	}
}
