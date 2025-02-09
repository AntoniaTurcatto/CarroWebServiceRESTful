package br.com.livro.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CarroService {
	
	@Autowired
	private CarroDAO db;

	public CarroService() {}
	
	public List<Carro> getCarros(){
		return db.getCarros();
	}
	
	public Carro getCarro(Long id) {
		return db.getCarroById(id);
	}
	//se usa essa anotação em casos que se usaria o setAutoCommit(false)
	@Transactional(rollbackFor=Exception.class)
	public boolean delete(Long id) {
		return db.delete(id);
	}
	
	//salva ou atualiza o carro
	@Transactional(rollbackFor=Exception.class)
	public boolean save(Carro carro) {
		db.save(carro);
		return true;
	}
	
	public List<Carro> findByName(String nome){
		return db.findByName(nome);
	}
	
	public List<Carro> findByTipo(String tipo){
		return db.findByTipo(tipo);
	}
}
