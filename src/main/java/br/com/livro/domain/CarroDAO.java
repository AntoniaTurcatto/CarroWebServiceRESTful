package br.com.livro.domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

@Component
public class CarroDAO extends HibernateDAO<Carro>{
	
	public CarroDAO() {
		//informa o tipo de entidade para o Hibernate
		super(Carro.class);
	}

	public Carro getCarroById(Long id) {
		//o hibernate consulta automaticamente pelo ID
		return super.get(id);
	}
	
	public List<Carro> findByName(String name){
		Query<Carro> q = getSession().createQuery("from Carro where lower(nome) like lower(?1)", Carro.class);
		q.setParameter(1, "%"+name+"%");
		return q.list();
	}
	
	public List<Carro> findByTipo(String tipo){
		Query<Carro> q = getSession().createQuery("from Carro where tipo=?1",Carro.class);
		q.setParameter(1, tipo);
		return q.list();
	}
	
	public List<Carro> getCarros(){
		Query<Carro> q = getSession().createQuery("from Carro",Carro.class);
		return q.list();
	}
	
	public void save(Carro c) {
		super.save(c);
	}
	
	public void saveOrUpdate(Carro c) {
		super.saveOrUpdate(c);
	}
	
	public boolean delete(Long id) {
		Carro c = get(id);
		super.delete(c);
		return true;
	}
	
}
