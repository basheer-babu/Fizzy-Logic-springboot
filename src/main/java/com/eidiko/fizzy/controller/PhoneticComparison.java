package com.eidiko.fizzy.controller;

import org.apache.commons.codec.language.Metaphone;

import me.xdrop.fuzzywuzzy.FuzzySearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;



public class PhoneticComparison {
    public static void main(String[] args) {
        String input1 = "బాబు";
        String input2 = "బ";

//        boolean arePhoneticallyMatched = arePhoneticallyMatched(input1, input2);
//        System.out.println("Are the inputs phonetically matched? " + arePhoneticallyMatched);
//        System.out.println("cp"+comparePhonetically(input1, input2));
//        System.out.println("letter match perce::"+calculateMatchedPercentage(input1, input2));
//        System.out.println("letter match order spec"+calculateMatchedPercentageOrderSpec(input1, input2));
//        LanguageIdentifier identifier = new LanguageIdentifier("Hello, this is javatpoint.");  
//        String language = identifier.getLanguage();  
//        System.out.println("Language code is : " + language);  
        System.out.println("RATIO::"+FuzzySearch.ratio(input1, input2));
        System.out.println("బాబు".equalsIgnoreCase("బాబు బాబు"));
        System.out.println("compareph"+comparePhonetically(input1, input2));
       System.out.println("partialRatio::"+FuzzySearch.partialRatio(input1, input2));
       System.out.println("tokenSortRatio::"+FuzzySearch.tokenSortRatio(input1, input2));
       System.out.println("tokenSetRatio::"+FuzzySearch.tokenSetRatio(input1, input2));
    }

    public static boolean arePhoneticallyMatched(String input1, String input2) {

    	

            String[] s1Arr=input1.replaceAll("\\.", " ").toLowerCase().split(" ");
            String[] s2Arr=input2.replaceAll("\\.", " ").toLowerCase().split(" ");
            List<String> ls=new ArrayList<String>(Arrays.asList(s2Arr));

            boolean flag=true;
            
            int count =0;

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
                        count++;
                        break;
                    }
                }

                if(!flag) break;
            }
            System.out.println("match count"+count);

            return flag;
        

    }

    public static String removeDuplicates(String input) {
        String[] words = input.split("\\s+");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            if (result.indexOf(word) == -1) {
                result.append(word).append(" ");
            }
        }

        return result.toString().trim();
    }

	public static boolean comparePhonetically(String word1, String word2) {
		Metaphone metaphone = new Metaphone();
		metaphone.setMaxCodeLen(100);
		System.out.println(word1+"::"+word2);
        String phonetic1 = metaphone.encode(word1);
        String phonetic2 = metaphone.encode(word2);
        System.out.println("mpw1"+phonetic1);
        System.out.println("mpw2"+phonetic2);

        return phonetic1.equals(phonetic2);
    }
	 public static double calculateMatchedPercentageOrderSpec(String str1, String str2) {
	        int length = Math.min(str1.length(), str2.length());
	        
	        long matchedCount = IntStream.range(0, length)
	                .filter(i -> str1.charAt(i) == str2.charAt(i))
	                .count();
	        
	        return (matchedCount * 100.0) / length;
	    }
	 
	 
	 public static double calculateMatchedPercentage(String str1, String str2) {
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
