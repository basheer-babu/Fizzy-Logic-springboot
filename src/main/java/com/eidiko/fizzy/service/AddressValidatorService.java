package com.eidiko.fizzy.service;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class AddressValidatorService {

	public static boolean countryCodeMatch(String s1, String s2) throws IOException,Exception{
		String countryName=null;
		String countryCode=null;
		if(s1.length()==2) {
			//s1 is countryCode
			//get country name based on s1
			countryName=getCountryName(s1);
			if(countryName!=null) {
				if(countryName.equalsIgnoreCase(s2) ) {
//					System.out.println("true"+countryName);
					return true;
				}else {
//					System.out.println("false"+countryName);
					return false;
				}
				
			}else {
				return false;
			}
			
			
		}else {
			//s1 is countryName
			//get country code based on s1
			countryCode=getCountryCode(s1);
			if(countryCode!=null) {
				if(countryCode.equalsIgnoreCase(s2) ) {
//					System.out.println("true"+countryCode);
					return true;
				}else {
//					System.out.println("false"+countryCode);
					return false;
				}
				
			}else {
				return false;
			}
		}
			
		
		

	}

	public static String getCountryName(String countryCode) throws IOException {
//		System.out.println("countryCode" + countryCode);

//		String countryCode1 = "DK";
//		String description1 = "DENMARK";

		Resource resource = new ClassPathResource("code.csv");
		File file = new File(resource.getURI());
		try (Reader reader = new FileReader(file); CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {
			boolean temp = true;
			for (CSVRecord record : csvParser) {
				String countryCodeCsv = record.get(0); // Get value from the first column
				String description = record.get(1); // Get value from the second column

				if (countryCodeCsv.equalsIgnoreCase(countryCode)) {
//					System.out.println("Matching Country Code: " + countryCodeCsv);
//					System.out.println("Matching Description: " + description);
					temp = false;
					return description;
				}

			}
			if (temp == true) {
				System.out.println("Coutry Code and Description is Not Match");
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return null;//fizzy/src/main/resources/code.csv
	}

	public static String getCountryCode(String countryName) throws IOException {
//		System.out.println("countryCode" + countryName);

//		String countryCode1 = "DK";
//		String description1 = "DENMARK";

		try (BufferedReader reader = new BufferedReader(new FileReader("src\\main\\resources\\code.csv"));
				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {
			boolean temp = true;
			for (CSVRecord record : csvParser) {
				String countryCodeCsv = record.get(0); // Get value from the first column
				String description = record.get(1); // Get value from the second column

				if (description.equalsIgnoreCase(countryName)) {
//					System.out.println("Matching Country Code: " + countryCodeCsv);
//					System.out.println("Matching Description: " + description);
					temp = false;
					return countryCodeCsv;
				}

			}
			if (temp == true) {
				System.out.println("Coutry Code and Description is Not Match");
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return null;
	}

}
