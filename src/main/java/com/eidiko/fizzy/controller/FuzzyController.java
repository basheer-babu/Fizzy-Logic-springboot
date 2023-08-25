package com.eidiko.fizzy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eidiko.fizzy.model.EntityInput;
import com.eidiko.fizzy.model.EntityOutput;
import com.eidiko.fizzy.service.FizzyValidatorService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
public class FuzzyController {

	EntityOutput entityOutput;

	@Autowired
	FizzyValidatorService fizzyValidatorService;

	public FuzzyController() {
		super();
		this.entityOutput = new EntityOutput();
	}

	@PostMapping(value = "/namevalidator")
	public EntityOutput consume(@RequestBody EntityInput Ei) throws JsonProcessingException {
		//input
		System.out.println("---------------------------");
		System.out.println("input1::"+Ei.getName1().getText());
		System.out.println("input2::"+Ei.getName2().getText());
		
		try {
			entityOutput=fizzyValidatorService.mainValidator(Ei);
			System.out.println("match Score::"+entityOutput.getMatchScore());
			System.out.println("match Result::"+entityOutput.getMatchResult());
			System.out.println("---------------------------");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return entityOutput;
	}

}
