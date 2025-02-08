package br.com.livro.rest;



import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.livro.domain.Response;
import br.com.livro.util.JAXBUtil;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
public class HelloResource {

	@GET
	@Consumes(MediaType.TEXT_HTML)
	@Produces(MediaType.TEXT_HTML+";charset=utf-8")
	public String get() {
		return "<b>olá mundo html </b>";
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String post() {
		return "olá mundo texto";
	}
	
	@GET
	@Consumes({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
	@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
	public String put() {
		String xml;
		try {
			xml = JAXBUtil.toXML(Response.ok("olá mundo xml"));
		} catch (IOException e) {
			e.printStackTrace();
			xml = "error";
		}
		return xml;
		
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String delete() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(Response.ok("olá mundo JSON"));
	}
	
}
