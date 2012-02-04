package org.springextensions.neodatis;

import org.junit.After;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.neodatis.odb.ODB;

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
        factoryBean.setFilename("test");
        factoryBean.initialize();
        odb = factoryBean.getObject();
        Assert.assertNotNull(odb);
        Assert.assertFalse(odb.isClosed());
    }
}
