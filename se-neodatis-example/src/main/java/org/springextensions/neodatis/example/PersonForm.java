package org.springextensions.neodatis.example;

public class PersonForm {
	
	private String firstname;
	private String lastname;
	
	public PersonForm(){
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Person getPerson(){
		Person p = new Person();
		p.setFirstname(firstname);
		p.setLastname(lastname);
		return p;
	}
}
