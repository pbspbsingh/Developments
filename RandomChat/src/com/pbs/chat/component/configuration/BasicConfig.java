package com.pbs.chat.component.configuration;

import java.util.Locale;

import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.util.Log4jConfigurer;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.support.ControllerClassNameHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
public class BasicConfig {

	@Bean(name = "viewResolver")
	public ViewResolver viewResolver() {
		final InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	@Bean(name = "messageSource")
	public ReloadableResourceBundleMessageSource createBundleMessageSource() {
		final ReloadableResourceBundleMessageSource bean = new ReloadableResourceBundleMessageSource();
		bean.setBasename("classpath:i18n/messages");
		return bean;
	}

	@Bean(name = "localeResolver")
	public SessionLocaleResolver createLocaleResolver() {
		final SessionLocaleResolver bean = new SessionLocaleResolver();
		bean.setDefaultLocale(Locale.ENGLISH);
		return bean;
	}

	@Bean(name = "localeChangeInterceptor")
	public LocaleChangeInterceptor localeChangeInterceptor() {
		final LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("language");
		return localeChangeInterceptor;
	}

	@Bean(name = "handlerMapping")
	public ControllerClassNameHandlerMapping createHandlerMapping(final LocaleChangeInterceptor localeChangeInterceptor) {
		final ControllerClassNameHandlerMapping bean = new ControllerClassNameHandlerMapping();
		bean.setInterceptors(new Object[] { localeChangeInterceptor });
		return bean;
	}

	@Bean(name = "log4jInitialization")
	public MethodInvokingFactoryBean createMethodInvokingFactoryBean() {
		final MethodInvokingFactoryBean factoryBean = new MethodInvokingFactoryBean();
		factoryBean.setTargetClass(Log4jConfigurer.class);
		factoryBean.setTargetMethod("initLogging");
		factoryBean.setArguments(new Object[] { "classpath:${spring.profiles.active}/log4j.properties" });
		return factoryBean;
	}
}