package br.com.livro.rest;

import java.util.List;

import br.com.livro.domain.Carro;
import br.com.livro.domain.CarroService;
import br.com.livro.domain.Response;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/carros")
@Consumes(MediaType.APPLICATION_JSON+";charset=utf-8")
@Produces(MediaType.APPLICATION_JSON+";charset=utf-8")
public class CarroResource {

	private CarroService carroService = new CarroService();
	
	@GET
	public List<Carro> get(){
		return carroService.getCarros();
	}
	
	@GET
	@Path("{id}")
	public Carro get(@PathParam("id") long id) {
		return carroService.getCarro(id);
	}
	
	@GET
	@Path("/tipo/{tipo}")
	public List<Carro> getByTipo(@PathParam("tipo") String tipo){
		return carroService.findByTipo(tipo);
	}
	
	@GET
	@Path("/nome/{nome}")
	public List<Carro> getByNome(@PathParam("nome") String nome){
		return carroService.findByName(nome);
	}

	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("id") long id) {
		Response response = (carroService.delete(id)) 
				? Response.ok("Carro deletado com sucesso") 
				: Response.ok("Carro não encontrado");
		return response;
	}
	
	@POST
	public Response post(Carro c) {
		return salvarOuAtualizarCarro(c);
	}
	
	@PUT
	public Response put(Carro c) {
		return salvarOuAtualizarCarro(c);
	}
	
	private Response salvarOuAtualizarCarro(Carro c) {
		Response response = (carroService.save(c)) 
				? Response.ok("Carro salvo com sucesso")
				: Response.error("Erro ao salvar carro");
		return response;
	}
	
}
