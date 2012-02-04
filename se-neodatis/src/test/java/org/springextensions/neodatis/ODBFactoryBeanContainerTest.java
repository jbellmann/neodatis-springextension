package org.springextensions.neodatis;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class ODBFactoryBeanContainerTest {

    @Autowired
    public NeoDatisTemplate template;

    @Test
    public void testTemplate() {
        Assert.assertNotNull(template);
        Assert.assertFalse(template.isClosed());
    }

}
