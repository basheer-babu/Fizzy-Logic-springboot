package com.eidiko.fizzy.controller;

import org.apache.commons.codec.language.Metaphone;

import java.util.HashSet;
import java.util.Set;

public class DistinctRepeatedWordComparison {
    public static void main(String[] args) {
        String input1 = "Ahmed Iqbal Khan";
        String input2 = "Ahmed Iqbal Shaikh";

        boolean hasDistinctRepeatedWords = !hasDistinctRepeatedWords(input1, input2);

        if (hasDistinctRepeatedWords) {
            System.out.println("The strings have distinct repeated words.");
        } else {
            System.out.println("The strings do not have distinct repeated words.");
        }
    }

    public static boolean hasDistinctRepeatedWords(String input1, String input2) {
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
}
