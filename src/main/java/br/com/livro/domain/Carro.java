package br.com.livro.domain;

import java.io.Serializable;import org.hibernate.annotations.GeneratorType;
import org.hibernate.annotations.ValueGenerationType;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Carro implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id", unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 50)
	private String tipo;
	
	@Column(nullable = false, length = 100)
	private String nome;
	
	@Column(name = "descricao", nullable = true, length = 500)
	private String desc; 
	
	@Column(name="url_foto", nullable = true, length = 50)
	private String urlFoto;
	
	@Column(name="url_video", nullable = true, length = 50)
	private String urlVideo;
	
	@Column(nullable = true, length = 15)
	private String latitude;
	
	@Column(nullable = true, length = 50)
	private String longitude;
	
	public Carro() {} 
	
	//com id
	public Carro(Long id, String tipo, String nome, String desc, String urlFoto, String urlVideo, String latitude,
			String longitude) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.nome = nome;
		this.desc = desc;
		this.urlFoto = urlFoto;
		this.urlVideo = urlVideo;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	//sem id
	public Carro(String tipo, String nome, String desc, String urlFoto, String urlVideo, String latitude,
			String longitude) {
		super();
		this.tipo = tipo;
		this.nome = nome;
		this.desc = desc;
		this.urlFoto = urlFoto;
		this.urlVideo = urlVideo;
		this.latitude = latitude;
		this.longitude = longitude;
	}



	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getUrlFoto() {
		return urlFoto;
	}
	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}
	public String getUrlVideo() {
		return urlVideo;
	}
	public void setUrlVideo(String urlVideo) {
		this.urlVideo = urlVideo;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString() {
		return "Carro [id="+id+", tipo="+tipo+", nome="+nome+
				", desc="+desc+", urlFoto="+urlFoto+", urlVideo="+urlVideo+
				", latitude="+latitude+", longitude="+longitude;
	}
	
	

}
