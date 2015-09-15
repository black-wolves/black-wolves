package com.blackwolves.persistence.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.blackwolves.persistence.entity.Seed;

public class CsvReader {

   public static ArrayList<String> getAllIps() {
   
	   String ip_file = "/var/www/ip_curl.txt";
	   ArrayList<String> ips =  new ArrayList<String>();
	   try {
			File file = new File(ip_file);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				ips.add(line);
				
			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	   return ips;
	   
   }
  
  public static ArrayList<Seed> getSeedsFromFile() {

	String csvFile = "/var/www/seeds.csv";
	BufferedReader br = null;
	String line = "";
	String cvsSplitBy = ",";
	ArrayList<Seed> seeds = new ArrayList<Seed> ();
	try {

		br = new BufferedReader(new FileReader(csvFile));
		while ((line = br.readLine()) != null) {

		        // use comma as separator
			String[] seed = line.split(cvsSplitBy);

			System.out.println("Seed [email= " + seed[0] 
                                 + " , pass=" + seed[1] + "]");
			seeds.add(new Seed(seed[0], seed[1]));
			

		}

	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	
	} finally {
		if (br != null) {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	System.out.println("Done");
	return seeds;
  }


}