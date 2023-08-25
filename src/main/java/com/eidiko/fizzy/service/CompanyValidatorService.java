package com.eidiko.fizzy.service;

import org.springframework.stereotype.Service;

@Service
public class CompanyValidatorService {
	
	public static boolean companyFirstWordMatch(String s1 , String s2) {
		String[] words1 = s1.split(" ");
		String[] words2 = s2.split(" ");
		
		if(words1[0].equalsIgnoreCase(words2[0])) {
			return true;
		}else {
			return false;
		}
		
		
		
	}

}
