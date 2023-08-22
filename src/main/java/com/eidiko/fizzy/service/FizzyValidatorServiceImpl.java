package com.eidiko.fizzy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eidiko.fizzy.model.EntityInput;
import com.eidiko.fizzy.model.EntityOutput;

@Service
public class FizzyValidatorServiceImpl implements FizzyValidatorService {

	@Autowired
	NameValidatorService nameValidatorService;
//	@Autowired
//	EntityOutput entityOutput;

	EntityOutput entityOutput;

	public FizzyValidatorServiceImpl() {
		super();
		this.entityOutput = new EntityOutput();
	}

	public static final String part_match = "partially Matched";
	public static final String matched = "Matched";
	public static final String not_match = "Not Matched";
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

					return null;
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
		System.out.println("name Validator");
		String text1 = entityInput.getName1().getText().toLowerCase().trim();
		String text2 = entityInput.getName2().getText().toLowerCase().trim();
		// null check

		int wordCount = nameValidatorService.countWords(nameValidatorService.findLongerString(text1, text2));
		System.out.println("large input word count:" + wordCount);
		if (text1 == " " || text1 == "" || text1 == null || text2 == "" || text2 == " " || text2 == null
				|| wordCount == 0) {
			entityOutput.setMatchResult(not_match);
			entityOutput.setMatchScore(match_score);

			return entityOutput;
		}

		if (nameValidatorService.compareStrings(text1, text2)) {

			entityOutput.setMatchResult(matched + "1");
			entityOutput.setMatchScore(100);

			return entityOutput;

		} else if (nameValidatorService.letterMatch(text1, text2)) {
			entityOutput.setMatchResult(matched + "2");
			entityOutput.setMatchScore(100);

			return entityOutput;

		} else if (nameValidatorService.compareStringsIgnoringRepeats(text1, text2)) {
			entityOutput.setMatchResult(matched + "3");
			entityOutput.setMatchScore(100);

			return entityOutput;
		} else {
			int matchCount = (int) nameValidatorService.countMatchingWordsPhonetically(text1, text2);
			System.out.println("matchCount:" + matchCount);
			if (matchCount == wordCount) {
				entityOutput.setMatchResult(matched + "4");
				entityOutput.setMatchScore(nameValidatorService.calculateMatchPercentage(wordCount, matchCount));
				return entityOutput;
			} else if (matchCount > 0) {
				entityOutput.setMatchResult(part_match);
				entityOutput.setMatchScore(nameValidatorService.calculateMatchPercentage(wordCount, matchCount));

				return entityOutput;
			} else {
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
		System.out.println("Company Validator");
		String text1 = entityInput.getName1().getText().toLowerCase();
		String text2 = entityInput.getName2().getText().toLowerCase();
		int wordCount = nameValidatorService.countWords(nameValidatorService.findLongerString(text1, text2));
		System.out.println("large input word count:" + wordCount);

		if (nameValidatorService.compareStrings(text1, text2)) {

			entityOutput.setMatchResult(matched + "1");
			entityOutput.setMatchScore(100);

			return entityOutput;

		} else if (nameValidatorService.compareStringsIgnoringRepeats(text1, text2)) {
			entityOutput.setMatchResult(matched + "2");
			entityOutput.setMatchScore(100);

			return entityOutput;
		} else {
			int matchCount = (int) nameValidatorService.countMatchingWordsPhonetically(text1, text2);
			System.out.println("matchCount:" + matchCount);
			if (matchCount == wordCount) {
				entityOutput.setMatchResult(matched + "3");
				entityOutput.setMatchScore(nameValidatorService.calculateMatchPercentage(wordCount, matchCount));
				return entityOutput;
			} else if (matchCount > 0) {
				entityOutput.setMatchResult(part_match);
				entityOutput.setMatchScore(nameValidatorService.calculateMatchPercentage(wordCount, matchCount));

				return entityOutput;
			} else {
				entityOutput.setMatchResult(not_match);
				entityOutput.setMatchScore(0);

				return entityOutput;

			}
		}

//		entityOutput.setMatchResult(not_match);
//		entityOutput.setMatchScore(0);

//		return entityOutput;

	}

}
