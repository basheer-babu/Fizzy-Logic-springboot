package com.eidiko.fizzy.model;

public class EntityOutput {
	
    private double matchScore;
    private String matchResult;
    
	public double getMatchScore() {
		return matchScore;
	}
	public void setMatchScore(double matchScore) {
		this.matchScore = matchScore;
	}
	public String getMatchResult() {
		return matchResult;
	}
	public void setMatchResult(String matchResult) {
		this.matchResult = matchResult;
	}
	
	@Override
	public String toString() {
		return "EntityOutput [matchScore=" + matchScore + ", matchResult=" + matchResult + "]";
	}
    
    
    
   
    
}
