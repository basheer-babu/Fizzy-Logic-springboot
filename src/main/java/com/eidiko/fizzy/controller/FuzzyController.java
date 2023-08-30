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
		System.out.println("Hit Name::"+Ei.getName1().getText());
		System.out.println("Watch Name::"+Ei.getName2().getText());
		System.out.println("Entity Type::"+Ei.getName2().getEntityType());
		
		try {
			entityOutput=fizzyValidatorService.mainValidator(Ei);
			System.out.println("Match Score::"+entityOutput.getMatchScore());
			System.out.println("Match Result::"+entityOutput.getMatchResult());
			System.out.println("---------------------------");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return entityOutput;
	}

}
