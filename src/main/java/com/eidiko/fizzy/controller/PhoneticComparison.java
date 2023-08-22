package com.eidiko.fizzy.controller;

import org.apache.commons.codec.language.Metaphone;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



public class PhoneticComparison {
    public static void main(String[] args) {
        String input1 = "s.p.rao";
        String input2 = "pilli Srinivas";

        boolean arePhoneticallyMatched = arePhoneticallyMatched(input1, input2);
        System.out.println("Are the inputs phonetically matched? " + arePhoneticallyMatched);
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
}
