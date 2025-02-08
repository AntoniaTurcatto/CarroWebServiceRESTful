package br.com.livro.domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CarroDAO extends BaseDAO{
	
	public Carro getCarroById(Long id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet res = null;
		Carro c = null;
		
		try {
			conn = getConnection();
			stmt = conn.prepareStatement("select * from carro where id=?");
			stmt.setLong(1, id);
			res = stmt.executeQuery();
			System.out.println("id recebido no getCarroById: "+id);
			if(res.next()) {
				c = createCarro(res);
				System.out.println("carro buscado criado");
			} else {
				System.out.println("não foram encontrados carros");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			finaliza(res, stmt, conn);
		}
		return c;
	}
	
	public List<Carro> findByName(String name){
		List<Carro> carros = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet res = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement("select * from carro where lower(nome) like ?");
			stmt.setString(1, "%"+name.toLowerCase()+"%");
			res = stmt.executeQuery();
			while(res.next()) {
				Carro c = createCarro(res);
				carros.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			finaliza(res, stmt, conn);
		}
		return carros;
	}
	
	public List<Carro> findByTipo(String tipo){
		List<Carro> carros = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet res = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement("select * from carro where tipo = ?");
			stmt.setString(1, tipo);
			res = stmt.executeQuery();
			
			while(res.next()) {
				Carro c = createCarro(res);
				carros.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			finaliza(res, stmt, conn);
		}
		
		return carros;
	}
	
	public List<Carro> getCarros(){
		List<Carro> carros = new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet res = null;
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			res = stmt.executeQuery("select * from carro");
			while(res.next()) {
				Carro c = createCarro(res);
				carros.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			finaliza(res, stmt, conn);
		}
		return carros;
	}
	
	public boolean save(Carro c) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet res = null;
		
		boolean success = true;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			
			if(c.getId() == null) {
				//insert
				stmt = conn.prepareStatement("insert into carro (nome,descricao,url_foto,url_video,"
						+ "latitude,longitude,tipo) VALUES(?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
				System.out.println("cadastrando carro");
			} else {
				//update
				stmt = conn.prepareStatement("update carro set nome=?,descricao=?,url_foto=?,url_video=?,"
						+"latitude=?,longitude=?,tipo=? WHERE id=?");
				System.out.println("atualizando carro");
			}
			
			stmt.setString(1, c.getNome());
			stmt.setString(2, c.getDesc());
			stmt.setString(3, c.getUrlFoto());
			stmt.setString(4, c.getUrlVideo());
			stmt.setString(5, c.getLatitude());
			stmt.setString(6, c.getLongitude());
			stmt.setString(7, c.getTipo());
			
			if(c.getId() != null) {
				stmt.setLong(8, c.getId());
			}
			System.out.println("carro a ser cadastrado: "+c.toString());
			int count = stmt.executeUpdate();
			System.out.println("registros alterados: "+count);
//			if (count == 0) {
//				success = false;
//			}
			success = count > 0;
			if(c.getId() == null) {
				c.setId(getGeneratedId(stmt));
				System.out.println("id gerado e salvo: "+c.getId());
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				if(conn != null) conn.rollback();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		} finally {
			finaliza(res, stmt, conn);
		}
		return success;
	}
	
	public boolean delete(Long id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet res = null;
		
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement("delete from carro where id = ?");
			stmt.setLong(1, id);
			int count = stmt.executeUpdate();
			conn.commit();
			return count > 0; 
			
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				if(conn != null) conn.rollback();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			return false;
		} finally {
			finaliza(res, stmt, conn);
		}
	}
	
	private Long getGeneratedId(Statement stmt) throws SQLException{
		ResultSet res = stmt.getGeneratedKeys();
		if(res.next()) {
			long id = res.getLong(1);
			return id;
		}
		return 0L;
	}
	
	
	/*
	 * 
	 * Carro(Long id, String tipo, String nome, String desc, String urlFoto, String urlVideo, String latitude,
			String longitude)
	 * 
	 */
	
	private Carro createCarro(ResultSet res) throws SQLException{
		return new Carro(res.getLong("id"),  res.getString("tipo"), res.getString("nome"), res.getString("descricao"),
				res.getString("url_foto"), res.getString("url_video"), res.getString("latitude"),
				res.getString("longitude"));
	}
	


	private void finaliza(ResultSet res, Statement stmt, Connection conn) {
		try {
			if(res != null) res.close();
			if(stmt != null) stmt.close();
			if(conn != null) conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
