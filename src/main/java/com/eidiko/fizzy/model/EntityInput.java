package com.eidiko.fizzy.model;

public class EntityInput {
    private NameEntity name1;
    private NameEntity name2;
    
	public NameEntity getName1() {
		return name1;
	}
	public void setName1(NameEntity name1) {
		this.name1 = name1;
	}
	public NameEntity getName2() {
		return name2;
	}
	public void setName2(NameEntity name2) {
		this.name2 = name2;
	}
	
	@Override
	public String toString() {
		return "EntityInput [name1=" + name1 + ", name2=" + name2 + "]";
	}

   
}