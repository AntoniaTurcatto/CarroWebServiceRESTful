package br.com.livro.rest;

	import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

@Path("/hello")
public class HelloResource {

	@GET
	public String get() {
		return "HTTP GET";
	}
	
	@POST
	public String post() {
		return "HTTP POST";
	}
	
	@PUT
	public String put() {
		return "HTTP PUT";
	}
	
	@DELETE
	public String delete() {
		return "HTTP DELETE";
	}
	
}
