package org.springextensions.neodatis.config;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springextensions.neodatis.NeoDatisTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=DataConfig.class)
@ActiveProfiles("test")
public class DataConfigTest {
	
	@Autowired
	private NeoDatisTemplate neoDatisTemplate;
	
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	@Test
	public void testComponentsAvailable(){
		Assert.assertNotNull(neoDatisTemplate);
		Assert.assertNotNull(transactionManager);
	}
}
