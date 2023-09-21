package com.eidiko.fizzy.service;

import org.springframework.stereotype.Service;

import com.eidiko.fizzy.model.EntityInput;
import com.eidiko.fizzy.model.EntityOutput;

@Service
public interface FizzyValidatorService {

	
	public EntityOutput mainValidator(EntityInput entityInput) throws Exception;
	public EntityOutput wuzzyValidator(EntityInput entityInput) throws Exception;
	
}
