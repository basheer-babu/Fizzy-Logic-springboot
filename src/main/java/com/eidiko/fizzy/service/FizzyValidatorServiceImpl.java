package com.eidiko.fizzy.service;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eidiko.fizzy.model.EntityInput;
import com.eidiko.fizzy.model.EntityOutput;

import me.xdrop.fuzzywuzzy.FuzzySearch;

@Service
public class FizzyValidatorServiceImpl implements FizzyValidatorService {

	@Autowired
	NameValidatorService nameValidatorService;

	@Autowired
	AddressValidatorService addressValidatorService;

	@Autowired
	CompanyValidatorService companyValidatorService;

	EntityOutput entityOutput;

	public FizzyValidatorServiceImpl() {
		super();
		this.entityOutput = new EntityOutput();
	}

	public static final String part_match = "Partially Matched";
	public static final String matched = "Matched";
	public static final String not_match = "Not Matched";
	public static final String letter_match = "Letters Matched";
	public static final double match_score = 0.0;

	@Override
	public EntityOutput mainValidator(EntityInput entityInput) throws Exception {

		// EntityOutput entityOutput=new EntityOutput();

		String text1 = entityInput.getName1().getText().toLowerCase();
		String text2 = entityInput.getName2().getText().toLowerCase();
		// null check
		if (text1 == " " || text1 == "" || text1 == null || text2 == " " || text2 == "" || text2 == null) {
			entityOutput.setMatchResult(not_match);
			entityOutput.setMatchScore(match_score);

			return entityOutput;
		}
		if (nameValidatorService.compareStrings(entityInput.getName1().getEntityType(),
				entityInput.getName2().getEntityType())) {
			if (nameValidatorService.compareStrings(entityInput.getName1().getLanguage(),
					entityInput.getName2().getLanguage())) {

				switch (entityInput.getName1().getEntityType()) {
				case "NAME":

					return nameValidator(entityInput);
				case "COMPANY":

					return companyValidator(entityInput);
				case "ADDRESS":

					return addressValidator(entityInput);
				default:
					entityOutput.setMatchResult(not_match);
					entityOutput.setMatchScore(0);
					return entityOutput;
				}

			} else {
				entityOutput.setMatchResult(not_match);
				entityOutput.setMatchScore(0);
				return entityOutput;
			}
			//
		} else {
			entityOutput.setMatchResult(not_match);
			entityOutput.setMatchScore(0);
			return entityOutput;
		}
	}

	public EntityOutput nameValidator(EntityInput entityInput) throws Exception {
//		System.out.println("Name Validator");
		String text1 = nameValidatorService.cleanStrings(entityInput.getName1().getText());
		String text2 = nameValidatorService.cleanStrings(entityInput.getName2().getText());
		// null check

		int wordCount = nameValidatorService.countWords(nameValidatorService.findLongerString(text1, text2));
//		System.out.println("large input word count:" + wordCount);
		if (text1 == " " || text1 == "" || text1 == null || text2 == "" || text2 == " " || text2 == null
				|| wordCount == 0) {
			entityOutput.setMatchResult(not_match);
			entityOutput.setMatchScore(match_score);

			return entityOutput;
		}

		if (nameValidatorService.compareStrings(text1, text2)) {

			entityOutput.setMatchResult(matched );
			entityOutput.setMatchScore(100);

			return entityOutput;

		} else if (nameValidatorService.letterMatch(text1, text2)) {
			entityOutput.setMatchResult(matched );
			entityOutput.setMatchScore(100);

			return entityOutput;

		} else if (nameValidatorService.compareStringsIgnoringRepeats(text1, text2)) {
			entityOutput.setMatchResult(matched );
			entityOutput.setMatchScore(100);

			return entityOutput;
		} else {
			int matchCount = (int) nameValidatorService.countMatchingWordsPhonetically(text1, text2);
//			System.out.println("matchCount:" + matchCount);
			
			if (matchCount == wordCount) {
				entityOutput.setMatchResult(matched);
				entityOutput.setMatchScore(nameValidatorService.calculateMatchPercentage(wordCount, matchCount));
				return entityOutput;
			}else if(wordCount==3 && matchCount==2) {// requested condition total words 3 and matched words 2 100%
				entityOutput.setMatchResult(matched );
				entityOutput.setMatchScore(100);
				return entityOutput;
				
			}else if (matchCount > 0) {
				entityOutput.setMatchResult(part_match);
				entityOutput.setMatchScore(nameValidatorService.calculateMatchPercentage(wordCount, matchCount));

				return entityOutput;
			} else {

				double letterPerc = nameValidatorService.calculateMatchedLetterPercentage(text1, text2);
				
				if(letterPerc== 100.0){
					entityOutput.setMatchResult(matched );
					entityOutput.setMatchScore(100);

					return entityOutput;
					
				}else if(letterPerc != 0.0) {
					entityOutput.setMatchResult(letter_match);
					entityOutput.setMatchScore(letterPerc);

					return entityOutput;
				}

				
				entityOutput.setMatchResult(not_match);
				entityOutput.setMatchScore(0);

				return entityOutput;

			}
		}

//		entityOutput.setMatchResult(not_match);
//		entityOutput.setMatchScore(0);

//		return entityOutput;

	}

	public EntityOutput companyValidator(EntityInput entityInput) throws Exception {
//		System.out.println("Company Validator");
		String text1 = entityInput.getName1().getText().toLowerCase();
		String text2 = entityInput.getName2().getText().toLowerCase();
		int wordCount = nameValidatorService.countWords(nameValidatorService.findLongerString(text1, text2));
//		System.out.println("large input word count:" + wordCount);

		if (nameValidatorService.compareStrings(text1, text2)) {

			entityOutput.setMatchResult(matched);
			entityOutput.setMatchScore(100);

			return entityOutput;

		} else if (nameValidatorService.compareStringsIgnoringRepeats(text1, text2)) {
			entityOutput.setMatchResult(matched);
			entityOutput.setMatchScore(100);

			return entityOutput;
		} else if (companyValidatorService.companyFirstWordMatch(text1, text2)) {
			entityOutput.setMatchResult(matched);
			entityOutput.setMatchScore(100);
			return entityOutput;

		} else {

			entityOutput.setMatchResult(not_match);
			entityOutput.setMatchScore(0);

			return entityOutput;

		}

	}

	public EntityOutput addressValidator(EntityInput entityInput) throws Exception {

//		System.out.println("Address Validator");
		String text1 = nameValidatorService.cleanStrings(entityInput.getName1().getText());
		String text2 = nameValidatorService.cleanStrings(entityInput.getName2().getText());
		// null check

		int wordCount = nameValidatorService.countWords(nameValidatorService.findLongerString(text1, text2));
//		System.out.println("large input word count:" + wordCount);
		if (text1 == " " || text1 == "" || text1 == null || text2 == "" || text2 == " " || text2 == null
				|| wordCount == 0) {
			entityOutput.setMatchResult(not_match);
			entityOutput.setMatchScore(match_score);

			return entityOutput;
		} else if (nameValidatorService.compareStrings(text1, text2)) {

			entityOutput.setMatchResult(matched );
			entityOutput.setMatchScore(100);

			return entityOutput;
		} else if (addressValidatorService.countryCodeMatch(text1, text2)) {
			entityOutput.setMatchResult(matched );
			entityOutput.setMatchScore(100);

			return entityOutput;
		} else {
			entityOutput.setMatchResult(not_match);
			entityOutput.setMatchScore(0);

			return entityOutput;
		}

	}
		/**
		 * service wuzzy validator
		 * 
		 */
	@Override
	public EntityOutput wuzzyValidator(EntityInput entityInput) throws Exception {

		// EntityOutput entityOutput=new EntityOutput();
		
		String text1 = entityInput.getName1().getText().toLowerCase();
		String text2 = entityInput.getName2().getText().toLowerCase();
		
		// null check
		if (text1 == " " || text1 == "" || text1 == null || text2 == " " || text2 == "" || text2 == null) {
			entityOutput.setMatchResult(not_match);
			entityOutput.setMatchScore(match_score);

			return entityOutput;
		}
		if (nameValidatorService.compareStrings(entityInput.getName1().getEntityType(),
				entityInput.getName2().getEntityType())) {
			if (nameValidatorService.compareStrings(entityInput.getName1().getLanguage(),
					entityInput.getName2().getLanguage())) {

				switch (entityInput.getName1().getEntityType()) {
				case "NAME":

					return wuzzyNameValidator(entityInput);
				case "COMPANY":

					return wuzzyNameValidator(entityInput);
				case "ADDRESS":

					return wuzzyNameValidator(entityInput);
				default:
					entityOutput.setMatchResult(not_match);
					entityOutput.setMatchScore(0);
					return entityOutput;
				}

			} else {
				entityOutput.setMatchResult(not_match);
				entityOutput.setMatchScore(0);
				return entityOutput;
			}
			//
		} else {
			entityOutput.setMatchResult(not_match);
			entityOutput.setMatchScore(0);
			return entityOutput;
		}
	}
	public EntityOutput wuzzyNameValidator(EntityInput entityInput) throws Exception {
		String text1 = nameValidatorService.cleanStrings(entityInput.getName1().getText());
		String text2 = nameValidatorService.cleanStrings(entityInput.getName2().getText());
		// null check
		
		if(text1=="") {
			
		System.out.println("not english");
			int ratio=FuzzySearch.ratio(entityInput.getName1().getText(), entityInput.getName2().getText());
			if(ratio==0) {
				entityOutput.setMatchResult(not_match );
				entityOutput.setMatchScore(0);

				return entityOutput;
			}else if(ratio==100) {
				entityOutput.setMatchResult(matched );
				entityOutput.setMatchScore(100);

				return entityOutput;
			}else {
				entityOutput.setMatchResult(part_match );
				entityOutput.setMatchScore(ratio);

				return entityOutput;
			}
		}
		
		int wordCount = nameValidatorService.countWords(nameValidatorService.findLongerString(text1, text2));
//		System.out.println("large input word count:" + wordCount);
		if (text1 == " " || text1 == "" || text1 == null || text2 == "" || text2 == " " || text2 == null
				|| wordCount == 0) {
			entityOutput.setMatchResult(not_match);
			entityOutput.setMatchScore(match_score);

			return entityOutput;
		}else if (nameValidatorService.compareStrings(text1, text2)) {

			entityOutput.setMatchResult(matched );
			entityOutput.setMatchScore(100);

			return entityOutput;

		}else {
			int ratio=FuzzySearch.ratio(text1, text2);
			if(ratio==0) {
				entityOutput.setMatchResult(not_match );
				entityOutput.setMatchScore(0);

				return entityOutput;
			}else {
				entityOutput.setMatchResult(part_match );
				entityOutput.setMatchScore(ratio);

				return entityOutput;
			}
			
		}
		

		
	}

}
