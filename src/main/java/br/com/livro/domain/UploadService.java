package br.com.livro.domain;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

//classe para tratar do updload
@Component
public class UploadService {

	public String upload(String fileName, InputStream in) throws IOException{
		
		if(fileName == null || in == null) {
			throw new IllegalArgumentException("Parâmetros inválidos");
		}
		
		File tmpDir = new File(System.getProperty("java.io.tmpdir"), "carros");//utilizamos a temporária pois depois utilizaremos a nuvem do Google
		if(!tmpDir.exists()) {
			//cria a pasta carros se não existir
			tmpDir.mkdir();
		}
		//cria o arquivo
		/*
		 * Um novo objeto File é criado dentro da pasta temporária,
		 *  usando o nome do arquivo enviado. Em seguida, um FileOutputStream
		 *  é aberto para esse arquivo e o conteúdo lido do 
		 *  InputStream (que contém os dados do arquivo enviado) é copiado para o output
		 *  usando IOUtils.copy(in, out).
		 */
		File file = new File(tmpDir, fileName); //arquivo na pasta temporária
		FileOutputStream out = new FileOutputStream(file);
		IOUtils.copy(in, out);
		//O uso de IOUtils.closeQuietly(out) garante que o stream de saída seja fechado
		//sem lançar exceções, mesmo que ocorra algum problema.
		IOUtils.closeQuietly(out);
		//retorna o caminho do arquivo
		String path = file.getAbsolutePath();
		return path;
		
	}
	
}
