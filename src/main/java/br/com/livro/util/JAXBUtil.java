package br.com.livro.util;

import java.io.IOException;
import java.io.StringWriter;

import br.com.livro.domain.Carro;
import br.com.livro.domain.ListaCarros;
import br.com.livro.domain.Response;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

public class JAXBUtil {

	private static JAXBUtil instance;
	private static JAXBContext context;
	
	public static JAXBUtil getInstance() {
		return instance;
	}
	
	static {
		try {
			//informa ao JAXB que é para gerar XML destas classes
			System.out.println("Initializing context...");
			context = JAXBContext.newInstance(ListaCarros.class, Carro.class, Response.class);
			System.out.print("sucess");
		} catch (JAXBException e) {
			System.err.print("failed!");
			throw new RuntimeException("não foi possivel criar JAXBContext: ",e);
		}
	}
	
	public static String toXML(Object object) throws IOException{
		try {
			StringWriter writer = new StringWriter();
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.marshal(object, writer);
			String xml = writer.toString();
			return xml;
		} catch (JAXBException e) {
			System.err.println("failed!");
			System.err.println("erro ao converter para XML: ");
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main (String[] args) {
		try {
			JAXBContext context = JAXBContext.newInstance(Carro.class);
		} catch (JAXBException e) {
			System.err.print("failed!");
			e.printStackTrace();
		}
		  
	}
}
