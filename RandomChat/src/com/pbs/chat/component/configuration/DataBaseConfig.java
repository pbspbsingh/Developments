package com.pbs.chat.component.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:${spring.profiles.active}/application.properties")
public class DataBaseConfig {

	@Autowired
	private Environment env;

	@Bean
	public DataSource dataSource() {
		final BasicDataSource datasource = new BasicDataSource();
		datasource.setDriverClassName(env.getRequiredProperty("db.driver"));
		datasource.setUrl(env.getRequiredProperty("db.url"));
		datasource.setUsername(env.getRequiredProperty("db.username"));
		datasource.setPassword(env.getRequiredProperty("db.password"));
		return datasource;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactoryBean(final DataSource ds) {
		final LocalSessionFactoryBean sfBean = new LocalSessionFactoryBean();
		sfBean.setDataSource(ds);
		sfBean.setPackagesToScan("com.pbs.chat.entity");
		final Properties hibernateProperties = new Properties();
		hibernateProperties.setProperty("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
		hibernateProperties.setProperty("hibernate.format_sql", env.getRequiredProperty("hibernate.format_sql"));
		hibernateProperties.setProperty("hibernate.hbm2ddl.auto", env.getRequiredProperty("hibernate.hbm2ddl.auto"));
		hibernateProperties.setProperty("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql"));
		sfBean.setHibernateProperties(hibernateProperties);
		return sfBean;
	}

	@Bean
	public HibernateTransactionManager transactionManager(final SessionFactory sessionFactory) {
		final HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);
		return txManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}
}