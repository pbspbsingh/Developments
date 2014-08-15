package com.pbs.chat.test;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.pbs.chat.component.configuration.BasicConfig;
import com.pbs.chat.component.configuration.DataBaseConfig;
import com.pbs.chat.component.dao.PreferenceDAO;

public class InsertNickNames {

	@Test
	public void insertNickNames() {
		System.setProperty("spring.profiles.active", "development");
		try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BasicConfig.class, DataBaseConfig.class, PreferenceDAO.class);) {
			final PreferenceDAO prefDAO = context.getBean(PreferenceDAO.class);
			prefDAO.insertNickNames();
		}
	}

}
