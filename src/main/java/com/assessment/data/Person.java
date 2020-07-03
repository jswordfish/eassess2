package com.assessment.data;

import javax.persistence.Entity;

@Entity
public class Person extends Base {
String first_name;

public String getFirst_name() {
	return first_name;
}

public void setFirst_name(String first_name) {
	this.first_name = first_name;
}


}
