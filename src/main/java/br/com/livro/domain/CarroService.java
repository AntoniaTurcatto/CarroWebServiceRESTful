package br.com.livro.domain;

import java.util.List;

public class CarroService {
	private CarroDAO db;

	public CarroService() {
		db = new CarroDAO();
	}
	
	public List<Carro> getCarros(){
		return db.getCarros();
	}
	
	public Carro getCarro(Long id) {
		return db.getCarroById(id);
	}
	
	public boolean delete(Long id) {
		return db.delete(id);
	}
	
	//salva ou atualiza o carro
	public boolean save(Carro carro) {
		return db.save(carro);
	}
	
	public List<Carro> findByName(String nome){
		return db.findByName(nome);
	}
	
	public List<Carro> findByTipo(String tipo){
		return db.findByTipo(tipo);
	}
}
