package br.com.livro.rest;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.MessageBodyReader;
import jakarta.ws.rs.ext.MessageBodyWriter;
import jakarta.ws.rs.ext.Provider;

@Provider

/*
 * @Provider: Indica que a classe é um provider do JAX-RS, 
 * ou seja, ela é registrada e reconhecida automaticamente pelo framework 
 * para realizar operações específicas, neste caso, a conversão de JSON para objetos Java e vice-versa.
 * 
 */

@Consumes(MediaType.APPLICATION_JSON+";charset=utf-8")
@Produces(MediaType.APPLICATION_JSON+";charset=utf-8")



/*
 * 
 * MessageBodyReader<Object>: Responsável por converter (deserializar) o conteúdo da requisição (JSON) para objetos Java.
 * MessageBodyWriter<Object>: Responsável por converter (serializar) objetos Java para JSON na resposta.
 * 
 */
public class GsonMessageBodyHandler implements MessageBodyWriter<Object>, 
	MessageBodyReader<Object>{
	
	private static final Charset UTF_8 = StandardCharsets.UTF_8;
	private Gson gson;
	
	private Gson getGson() {
		if(gson == null)
			gson = new GsonBuilder().setPrettyPrinting().create();
		return gson;
	}
	

	@Override
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return true;
	}

	@Override
	public Object readFrom(Class<Object> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
			throws IOException, WebApplicationException {
		InputStreamReader streamReader = new InputStreamReader(entityStream, UTF_8);
		try {
			Type jsonType;
			//A variável jsonType é definida para determinar qual é o tipo que deverá ser utilizado na deserialização.
			//Se o type (classe) for igual ao genericType, utiliza-se type;
			//caso contrário, utiliza-se genericType. Isso é útil para lidar com tipos genéricos.
			
			if(type.equals(genericType))
				jsonType = type;
			else
				jsonType = genericType;
			//O método fromJson do Gson converte o JSON lido para o objeto Java correspondente.
			return getGson().fromJson(streamReader, jsonType);
			
		} finally {
			streamReader.close();
		}
	}

	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		// TODO Auto-generated method stub
		return true;
	}

	
	@Override
	public long getSize(Object t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		// TODO Auto-generated method stub
		return -1;
	}
	
	@Override
	public void writeTo(Object t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
			throws IOException, WebApplicationException {
		
		OutputStreamWriter writer = new OutputStreamWriter(entityStream, UTF_8);
		//Um OutputStreamWriter é criado para escrever na saída da resposta com codificação UTF-8.
		try {
			Type jsonType;
			//Assim como na leitura, o tipo jsonType é definido com base na comparação entre type e genericType,
			//garantindo que a serialização trate corretamente os tipos genéricos.
			if(type.equals(genericType))
				
				/*TYPE E GENERICTYPE IGUAIS
				 * 
				 * Isso normalmente acontece quando o tipo não é genérico ou quando as informações genéricas não foram passadas.
					Nesse caso, tanto o type quanto o genericType contêm a mesma informação (apenas a classe bruta), e,
					portanto, usamos type para a conversão.
				 */
				
				
				jsonType = type;
			else
				//Aqui, o genericType carrega informações adicionais sobre os parâmetros genéricos que o type não possui.
				//Por exemplo, se estivermos lidando com List<String>, o type seria List.class, enquanto o genericType manteria o parâmetro <String>.
				jsonType = genericType;
			//O método toJson do Gson é utilizado para converter o objeto Java em uma representação JSON, escrevendo diretamente no writer.
			getGson().toJson(t, jsonType, writer);
			
		} finally {
			writer.close();
		}
		
		
	}

}
