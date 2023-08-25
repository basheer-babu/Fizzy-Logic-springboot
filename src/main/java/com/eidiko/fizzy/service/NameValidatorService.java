package com.eidiko.fizzy.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.codec.language.Metaphone;
import org.springframework.stereotype.Service;

@Service
public class NameValidatorService {
	
	//removing special characters and lower case.
	public static String cleanStrings(String s) {
		String regex = "[^a-zA-Z0-9]";
		return s.replaceAll(regex, " ").toLowerCase().trim().replaceAll("\\s+", " ");
	}
	public static String findLongerString(String s1, String s2) {
		 String[] words1 = s1.split(" ");
	        String[] words2 = s2.split(" ");

	        if (words1.length > words2.length) {
	            return s1;
	        } else if (words2.length > words1.length) {
	            return s2;
	        } else {
	            return s1;
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
        	return hasDistinctRepeatedWordsMeta(s1, s2);
        }
    }
	public static boolean hasDistinctRepeatedWordsMeta(String input1, String input2) {
		String longString=findLongerString(input1,input2);
		Set<String> words1 = new HashSet<>(Arrays.asList(longString.split(" ")));
		
        return words1.size()==countMatchingWordsPhonetically( input1, input2);
    
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
        return Double.valueOf(new DecimalFormat("##.##").format(percentage)); 
    }
	
	public static boolean comparePhonetically(String word1, String word2) {
		Metaphone metaphone = new Metaphone();
		metaphone.setMaxCodeLen(100);
        String phonetic1 = metaphone.encode(word1);
        String phonetic2 = metaphone.encode(word2);

        return phonetic1.equals(phonetic2);
    }
	
	public static long countMatchingWordsPhonetically(String input1, String input2) {
        Metaphone metaphone = new Metaphone();
        metaphone.setMaxCodeLen(100);
        Set<String> words1 = new HashSet<>(Arrays.asList(input1.split(" ")));
        Set<String> words2 = new HashSet<>(Arrays.asList(input2.split(" ")));

        return words1.stream()
                .filter(word1 -> words2.stream().anyMatch(word2 -> metaphone.isMetaphoneEqual(word1, word2)))
                .count();
    }
	public static boolean letterMatch(String s1 , String s2) {

        String[] s1Arr=s1.split(" ");
        String[] s2Arr=s2.split(" ");
        List<String> ls=new ArrayList<String>(Arrays.asList(s2Arr));

        boolean flag=true;

        if(s1Arr.length!=s2Arr.length) return false;

        for(int i=0;i<s1Arr.length; i++) {

            for(int j=0;j<ls.size(); j++) {

                if(s1Arr[i].length()==1 || ls.get(j).length()==1 ) {
                    //System.out.println("if");
                    flag=s1Arr[i].charAt(0)== ls.get(j).charAt(0);
                }else {
                    //System.out.println("else");
                    flag=s1Arr[i].equalsIgnoreCase( ls.get(j));
                }

                if(flag) {
                    ls.remove(j);
                    break;
                }
            }

            if(!flag) break;
        }

        return flag;
    }
	
	
	public static double calculateMatchedLetterPercentage(String str1, String str2) {
        Map<Character, Long> charFrequency1 = getCharacterFrequency(str1);
        Map<Character, Long> charFrequency2 = getCharacterFrequency(str2);
        
        long totalMatches = charFrequency1.entrySet().stream()
                .filter(entry -> charFrequency2.containsKey(entry.getKey()))
                .mapToLong(entry -> Math.min(entry.getValue(), charFrequency2.get(entry.getKey())))
                .sum();
        
        long totalCharacters = Math.max(str1.length(), str2.length());
        
        return ((double) totalMatches / totalCharacters) * 100;
    }
 public static Map<Character, Long> getCharacterFrequency(String str) {
        return str.chars()
                .mapToObj(ch -> (char) ch)
                .collect(Collectors.groupingBy(
                        character -> character,
                        Collectors.counting()
                ));
    }

	
	
}
