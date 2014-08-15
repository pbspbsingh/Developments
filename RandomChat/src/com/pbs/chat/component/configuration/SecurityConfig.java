package com.pbs.chat.component.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.social.security.SpringSocialConfigurer;

@Configuration
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/static/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers("/mobile/**")
			.hasRole("SOCIAL")
		.and()
			.formLogin()
			.loginPage("/index.html")
			.defaultSuccessUrl("/mobile/index.html")
			.failureUrl("/index.html?action=failure")
		.and()
			.logout()
			.logoutUrl("/logout.html")
			.deleteCookies("JSESSIONID")
			.logoutSuccessUrl("/index.html?action=logout")
		.and()
			.apply(new SpringSocialConfigurer());
	}
	
}
