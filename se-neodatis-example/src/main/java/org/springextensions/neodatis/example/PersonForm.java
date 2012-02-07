package org.springextensions.neodatis.example;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class PersonForm {

    @Size(min = 3, max = 20)
    private String firstname;

    @NotEmpty
    private String lastname;

    public PersonForm() {
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

    public Person getPerson() {
        Person p = new Person();
        p.setFirstname(firstname);
        p.setLastname(lastname);
        return p;
    }
}
