package br.com.livro.rest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
//import org.glassfish.jersey.jettison.JettisonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;


@ApplicationPath("/rest")
public class MyApplication extends ResourceConfig {

//	@Override
//	public Set<Object> getSingletons() {
//		Set<Object> singletons = new HashSet<>();
//		//driver do Jettison para gerar JSON
//		singletons.add(new JacksonFeature());
//		return singletons;
//	}
//
//	@Override
//	public Map<String, Object> getProperties() {
//		Map<String,Object> properties = new HashMap<>();
//		//configura o pacote para fazer scan das classes com anotações REST
//		properties.put("jersey.config.server.provider.packages", "br.com.livro");
//		return properties;
//	}

	public MyApplication() {
		// Ativa suporte a JSON (não é mais preciso, fizemos manualmente no GsonMessageBodyHandler)
        //register(JacksonFeature.class);
        register(MultiPartFeature.class);
        // Define o pacote para escanear classes com @Path, @GET, @POST etc.
        packages("br.com.livro");
	}
	
	
	
}
