package br.com.livro.domain;

import java.io.Serializable;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="carros")
public class ListaCarros implements Serializable{

	private static final long serialVersionUID= 1L; 
	private List<Carro> carros;
	
	public ListaCarros() {}
	
	@XmlElement(name="carro")
	public List<Carro> getCarros(){
		return carros;
	}

	public void setCarros(List<Carro> carros) {
		this.carros = carros;
	}

	@Override
	public String toString() {
		return "ListaCarros [carros="+carros+"]";
	}
	
	
	
}
