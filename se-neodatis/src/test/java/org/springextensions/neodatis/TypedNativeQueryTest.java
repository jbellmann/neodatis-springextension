/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springextensions.neodatis;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.neodatis.odb.ODB;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.nq.TypedNativeQuery;

/**
 *
 * @author Joerg Bellmann
 *
 */
public class TypedNativeQueryTest {

    private ODBFactoryBean odbFactoryBean;

    @Before
    public void setUp() {
        odbFactoryBean = new ODBFactoryBean();
        odbFactoryBean.setFilename("target/MoreNativeQueryTest.neodatis");
        odbFactoryBean.initialize();
    }

    @After
    public void tearDown() {
        odbFactoryBean.destroy();
    }

    @Test
    public void testQuery() throws Exception {
        Person a = new Person("A", "A++");
        Person b = new Person("B", "B++");
        ODB odb = odbFactoryBean.getObject();
        odb.store(a);
        odb.store(b);
        odb.commit();
        Objects<Person> result = odb.getObjects(new PersonByFirstName("B"));
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isEmpty());
        Assert.assertEquals(b.getFirstname(), result.iterator().next().getFirstname());
        //TODO provide a patch to NeoDatis
        // with typedQuery
        Objects<Person> byLastname = odb.getObjects(new PersonByLastname("A++"));
        Assert.assertNotNull(byLastname);
        Assert.assertFalse(byLastname.isEmpty());
        Assert.assertEquals(a.getLastname(), byLastname.iterator().next().getLastname());
    }

    @SuppressWarnings("serial")
    public static final class PersonByFirstName extends TypedNativeQuery<Person> {

        private final String fistname;

        public PersonByFirstName(String firstname) {
            this.fistname = firstname;
        }

        @Override
        public boolean match(Person object) {
            return fistname.equals(object.getFirstname());
        }

    }

    @SuppressWarnings("serial")
    public static final class PersonByLastname extends TypedNativeQuery<Person> {

        private final String lastname;

        public PersonByLastname(String lastname) {
            this.lastname = lastname;
        }

        @Override
        public boolean match(Person object) {
            return this.lastname.equals(object.getLastname());
        }

    }
}
