package com.assessment.data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Pet extends Base{

	String name;
	
	@ManyToOne
	Person owner;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Person getOwner() {
		return owner;
	}

	public void setOwner(Person owner) {
		this.owner = owner;
	}
	
	
}
