package com.eidiko.fizzy.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.codec.language.Metaphone;
import org.springframework.stereotype.Service;

@Service
public class NameValidatorService {
	public static String findLongerString(String s1, String s2) {
        if (s1.length() >= s2.length()) {
            return s1;
        } else {
            return s2;
        }
    }
	
	public static int countWords(String input) {
        if (input == null || input.trim().isEmpty()) {
            return 0;
        }

        String[] words = input.split("\\s+"); // Split by whitespace characters
        return words.length;
    }
	
	public static boolean compareStrings(String s1, String s2) {
        // Using the equals() method to compare strings
        if(s1.toLowerCase().equals(s2.toLowerCase())) {
        	return s1.toLowerCase().equals(s2.toLowerCase());
        }else {
        	if(countWords(s1)<=1 && countWords(s2)<=1) {
        	return comparePhonetically(s1.toLowerCase(), s2.toLowerCase());
        	}
        	else {
        	return s1.toLowerCase().equals(s2.toLowerCase());
			}
        }
    }
	
	
	public static boolean compareStringsIgnoringRepeats(String s1, String s2) {
        Set<String> set1 = new HashSet<>(Arrays.asList(s1.split(" ")));
        Set<String> set2 = new HashSet<>(Arrays.asList(s2.split(" ")));
        if(set1.equals(set2)) {
        	return set1.equals(set2);
        }else {
        	return !hasDistinctRepeatedWordsMeta(s1, s2);
        }
    }
	public static boolean hasDistinctRepeatedWordsMeta(String input1, String input2) {
        Metaphone metaphone = new Metaphone();
        Set<String> metaphoneCodes1 = new HashSet<>();
        Set<String> metaphoneCodes2 = new HashSet<>();

        for (String word : input1.split(" ")) {
            metaphoneCodes1.add(metaphone.encode(word));
        }

        for (String word : input2.split(" ")) {
            if (metaphoneCodes1.contains(metaphone.encode(word))) {
                metaphoneCodes2.add(metaphone.encode(word));
            }
        }

        return metaphoneCodes2.size() > 0;
    }

	
	public long wordMatchCount(String s1, String s2) {
		Set<String> wordsFromS1 = new HashSet<>(Arrays.asList(s1.split(" ")));
        long wordMatchCount = Arrays.stream(s2.split(" "))
                .filter(wordsFromS1::contains)
                .count();

//        System.out.println("Number of matching words: " + wordMatchCount);
		return wordMatchCount;
	}
	
	public long distinctWordMatchCount(String s1, String s2) {
		 Set<String> distinctWordsFromS1 = new HashSet<>(Arrays.asList(s1.split(" ")));
	        long distinctWordMatchCount = Arrays.stream(s2.split(" "))
	                .filter(distinctWordsFromS1::contains)
	                .distinct()
	                .count();

//	        System.out.println("Number of distinct matching words: " + distinctWordMatchCount);
	        
	        return distinctWordMatchCount;
	        
	}
	
	public static double calculateMatchPercentage(int total, int matchCount) {
        if (total <= 0 || matchCount < 0 || matchCount > total) {
            throw new IllegalArgumentException("Invalid input values");
        }

        double percentage = ((double) matchCount / total) * 100;
        return percentage;
    }
	
	public static boolean comparePhonetically(String word1, String word2) {
		Metaphone metaphone = new Metaphone();
        String phonetic1 = metaphone.encode(word1);
        String phonetic2 = metaphone.encode(word2);

        return phonetic1.equals(phonetic2);
    }
	
	public static long countMatchingWordsPhonetically(String input1, String input2) {
        Metaphone metaphone = new Metaphone();

        Set<String> words1 = new HashSet<>(Arrays.asList(input1.split(" ")));
        Set<String> words2 = new HashSet<>(Arrays.asList(input2.split(" ")));

        return words1.stream()
                .filter(word1 -> words2.stream().anyMatch(word2 -> metaphone.isMetaphoneEqual(word1, word2)))
                .count();
    }
	
	
}
