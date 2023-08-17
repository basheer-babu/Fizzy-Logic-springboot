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
		
		
		try {
			entityOutput=fizzyValidatorService.mainValidator(Ei);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return entityOutput;
	}

}
