package org.springextensions.neodatis.config;

import org.neodatis.odb.ODB;
import org.springextensions.neodatis.NeoDatisTemplate;
import org.springextensions.neodatis.NeoDatisTransactionManager;
import org.springextensions.neodatis.ODBFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement(mode=AdviceMode.ASPECTJ)
public class DataConfig {
	
	@Autowired
	private ODB odb;
	
	@Bean
	public PlatformTransactionManager transactionManager() throws Exception{
		return new NeoDatisTransactionManager(odb);
	}
	
	@Bean
	public NeoDatisTemplate neodatisTemplate() throws Exception{
		return new NeoDatisTemplate(odb);
	}
	
	@Configuration
	@Profile("test")
	static class Test {
		
		@Bean
		public ODBFactoryBean odbFactoryBean(){
			ODBFactoryBean odbFactoryBean = new ODBFactoryBean();
			odbFactoryBean.setFilename("target/test.neodatis");
			return odbFactoryBean;
		}
		
	}
	
	@Configuration
	@Profile("standard")
	static class Standard {
		
		@Autowired
		private Environment environment;
		
		@Bean
		public ODBFactoryBean odbFactoryBean(){
			ODBFactoryBean odbFactoryBean = new ODBFactoryBean();
			odbFactoryBean.setFilename(environment.getProperty("neodatis.filename"));
			return odbFactoryBean;
		}
	}

}
