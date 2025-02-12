package br.com.livro.rest;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.livro.domain.Carro;
import br.com.livro.domain.CarroService;
import br.com.livro.domain.Response;
import br.com.livro.domain.UploadService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.FormParam;
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
@Component
public class CarroResource {

	@Autowired
	private CarroService carroService;
	
	@Autowired
	private UploadService uploadService;
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
	
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response postFoto(final FormDataMultiPart multipart) { 
		// FormDataMultiPart: uma classe do Jersey responsável por encapsular os dados enviados no multipart.
		if(multipart != null && multipart.getFields() != null) {
			Set<String> keys = multipart.getFields().keySet();//Obtém-se um set de chaves (nomes dos campos) que foram enviados.
			for(String key : keys) {
				//Obtem a input stream para ler o arquivo
				//Para cada campo, é recuperado um objeto FormDataBodyPart, que representa uma parte do multipart.
				FormDataBodyPart field = multipart.getField(key);
				//O método getValueAs(InputStream.class) é utilizado para obter um InputStream do
				//conteúdo do campo. Isso é especialmente útil para ler os dados de um arquivo enviado
				InputStream in = field.getValueAs(InputStream.class);
				
				//salva o arquivo
				try {
					//O método getFormDataContentDisposition().getFileName() obtém o nome original do arquivo enviado.
					String fileName = field.getFormDataContentDisposition().getFileName();
					String path = uploadService.upload(fileName, in);
					System.out.println("Arquivo: "+path);
					return Response.ok("Arquivo recebido com sucesso");
				} catch (IOException e) {
					e.printStackTrace();
					return Response.error("Erro ao enviar arquivo");
				}
			}
		}
		return Response.ok("Requisição inválida");
	}
	
	@POST
	@Path("/toBase64")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public String toBase64(final FormDataMultiPart multiPart) {
		if(multiPart != null) {
			Set<String> keys = multiPart.getFields().keySet();
			for(String key : keys) {
				try {
					//obtem a InputStream para ler o arquivo
					FormDataBodyPart field = multiPart.getField(key);
					InputStream in = field.getValueAs(InputStream.class);
					byte[] bytes = IOUtils.toByteArray(in);
					String base64 = Base64.getEncoder().encodeToString(bytes);
					return base64;
				} catch (Exception e) {
					e.printStackTrace();
					return "Erro: "+e.getMessage();
				}
			}
		}
		return "Requisição inválida";
	}
	
	@POST
	@Path("/postFotoBase64")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response postFotoBase64(@FormParam("fileName") String fileName,
			  @FormParam("base64") String base64) {
		
		if(fileName != null && base64 != null) {
			try {
				//Decode: converte base64 para byte[]
				byte[] bytes = Base64.getDecoder().decode(base64);
				InputStream in = new ByteArrayInputStream(bytes);
				//faz o upload (salva o arquivo em uma pasta)
				String path = uploadService.upload(fileName, in);
				System.out.println("ARQUIVO: "+path);
				//ok
				return Response.ok("Arquivo recebido com sucesso");
			} catch (Exception e) {
				e.printStackTrace();
				return Response.error("Erro ao enviar o arquivo");
			}
		}
		return Response.error("base64");
	}
	
	private Response salvarOuAtualizarCarro(Carro c) {
		Response response = (carroService.save(c)) 
				? Response.ok("Carro salvo com sucesso")
				: Response.error("Erro ao salvar carro");
		return response;
	}
	
}
