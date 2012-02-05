package org.springextensions.neodatis.example;

import java.util.List;

public interface PersonDao {
	
	List<Person> findAll();

	Person findByFirstname(String firstname);

	void save(Person p);

}
