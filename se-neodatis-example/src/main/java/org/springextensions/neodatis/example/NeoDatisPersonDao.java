package org.springextensions.neodatis.example;

import java.util.List;

import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.nq.SimpleNativeQuery;
import org.springextensions.neodatis.NeoDatisTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;

@Repository
public class NeoDatisPersonDao implements PersonDao {

    @Autowired
    private NeoDatisTemplate neoDatisTemplate;

    @Override
    public List<Person> findAll() {
        Objects<Person> result = neoDatisTemplate.getObjects(Person.class);
        return Lists.newArrayList(result);
    }

    @Override
    public Person findByFirstname(String firstname) {
        Objects<Person> personsWithFirstname = neoDatisTemplate.getObjects(new PersonByFirstname(firstname));
        if (personsWithFirstname.isEmpty()) {
            return null;
        }
        return personsWithFirstname.getFirst();
    }

    @Override
    public void save(Person p) {
        neoDatisTemplate.store(p);
    }

    @SuppressWarnings("serial")
    static final class PersonByFirstname extends SimpleNativeQuery {

        private final String firstname;

        public PersonByFirstname(String firstname) {
            this.firstname = firstname;
        }

        public boolean match(Person person) {
            return person.getFirstname().equals(firstname);
        }
    }
}
