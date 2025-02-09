package br.com.livro.domain;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;


//Hibernate DAO genérico
@SuppressWarnings("unchecked")
public class HibernateDAO<T> {

	protected Class<T> clazz;
	protected Session session;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public HibernateDAO(Class<T> clazz) {
		this.clazz = clazz;
	}
	
	public void delete(T entity) {
		getSession().remove(entity);
	}
	
	public void update(T entity) {
		getSession().merge(entity);
	}
	
	public void save(T entity) {
		getSession().persist(entity);
	}
	
	public void saveOrUpdate(T entity) {
		getSession().persist(entity);
	}
	
	public T load(Serializable id) {
		return (T) getSession().getReference(this.clazz, id);
	}
	
	public T get(Serializable id) {
		return (T) getSession().get(this.clazz, id);
	}
	//ex: Query<Usuario> query = createQuery("FROM Usuario WHERE nome = :nome", Usuario.class);
	protected Query<T> createQuery(String query) {
	    return getSession().createQuery(query, clazz);
	}
	
	protected CriteriaQuery<T> createCriteriaQuery() {
		CriteriaBuilder cb = getSession().getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(clazz); // Usa a classe genérica armazenada
	    cq.from(clazz); // Define a entidade raiz da consulta
	    return cq;
	}
	
	public Session getSession() {
		
		if(sessionFactory != null) 
			session = sessionFactory.getCurrentSession();
		
		if(session == null)
			throw new RuntimeException("Hibernate Session is NULL");
		
		return session;
	}

	public void setFactory(SessionFactory factory) {
		this.sessionFactory = factory;
	}

	public SessionFactory getFactory() {
		return sessionFactory;
	}
	
}
