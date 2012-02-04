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
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.neodatis.odb.ODB;

/**
 * 
 * @author Joerg Bellmann
 *
 */
public class ODBFactoryBeanTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private ODB odb;

    @After
    public void tearDown() {
        if (odb != null && !odb.isClosed()) {
            odb.close();
        }
    }

    @Test
    public void testODBFactoryBean() throws Exception {
        //        String filename = folder.newFile().getAbsolutePath();
        ODBFactoryBean factoryBean = new ODBFactoryBean();
        factoryBean.setFilename("target/ODBFactoryBeanTest.neodatis");
        factoryBean.initialize();
        odb = factoryBean.getObject();
        Assert.assertNotNull(odb);
        Assert.assertFalse(odb.isClosed());
    }
}
