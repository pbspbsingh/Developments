package com.pbs.chat.component.dao;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractDAO<T> {

	protected Log logger = LogFactory.getLog(getClass());

	private HibernateTemplate hibernateTemplate;

	@Autowired
	private SessionFactory sessionFactory;

	@PostConstruct
	public void post() {
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	protected Session getCurrentSession() {
		return this.sessionFactory.getCurrentSession();
	}

	protected HibernateTemplate getTemplate() {
		return this.hibernateTemplate;
	}

	@Transactional
	public Serializable save(T t) {
		logger.info("Got request to save object: " + t);

		final Serializable id = hibernateTemplate.save(t);

		logger.info("Object has been saved succesfully, new Id is: " + id);
		return id;
	}

	@Transactional
	public void update(T t) {
		logger.info("Got request to update object: " + t);

		hibernateTemplate.update(t);

		logger.info("Object has been udpated succesfully");
	}

	@Transactional
	public <U> void saveOrUpdate(U u) {
		logger.info("Got request to save/update object: " + u);
		hibernateTemplate.saveOrUpdate(u);
		logger.info("Object has been saved/udpated succesfully");
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public <U> U loadById(Class<U> clzz, Serializable id) {
		logger.info("Got request to load Object of type: " + clzz.getName() + " for id: " + id);
		return (U) getCurrentSession().get(clzz, id);
	}
}
