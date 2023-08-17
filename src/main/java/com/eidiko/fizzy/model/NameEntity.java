package com.eidiko.fizzy.model;

public class NameEntity {
    private String text;
    private String language;
    private String entityType;
    
    
    
	public String getText() {
		return text;
	}



	public void setText(String text) {
		this.text = text;
	}



	public String getLanguage() {
		return language;
	}



	public void setLanguage(String language) {
		this.language = language;
	}



	public String getEntityType() {
		return entityType;
	}



	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}



	@Override
	public String toString() {
		return "NameEntity [text=" + text + ", language=" + language + ", entityType=" + entityType + "]";
	}

   
}
