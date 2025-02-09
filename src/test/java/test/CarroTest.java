package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import br.com.livro.domain.Carro;
import br.com.livro.domain.CarroService;

import org.junit.jupiter.api.Test;

public class CarroTest{
	
	private CarroService carroService;	
	
	@Test
	void testListaCarros() {
		carroService = (CarroService) SpringUtil.getInstance().getBean(CarroService.class);
		List<Carro> carros = carroService.getCarros();
		assertNotNull(carros);
		//valida se encontrou algo
		assertTrue(carros.size() > 0);
		
		//valida se encontrou um carro por nome
		Carro gol = carroService.findByName("gol quadrado").get(0);
		//valida se encontrou
		assertNotNull(gol);
		//valida se encontrou o certo
		assertEquals("gol quadrado", gol.getNome());
		
		gol = carroService.findByTipo("tunado").get(0);
		//valida se encontrou
		assertNotNull(gol);
		//valida se encontrou o certo
		assertEquals("tunado", gol.getTipo());
		
	}
	
	@Test
	void testCrudCarros() {
		carroService = (CarroService) SpringUtil.getInstance().getBean(CarroService.class);
		Carro c = new Carro("tipo", "Teste", "Teste desc", "url foto aq", "url video aq", "lat",
				"lng");
		
		//cadastrando
		assertNotEquals(carroService.save(c), false);
		
		//verificando se cadastrou o id
		assertNotNull(c.getId());
		
		//confirmando que o carro foi inserido no banco com busca
		Carro c2 = carroService.getCarro(c.getId());
		System.out.println(c.getId());
		assertNotNull(c2); 
		
		//confirmando se os dados foram cadastrados corretamente
		assertEquals(c.getId(), c2.getId());
		assertEquals("tipo", c2.getTipo());
		assertEquals("Teste", c2.getNome()); 
		assertEquals("Teste desc", c2.getDesc());
		assertEquals("url foto aq", c2.getUrlFoto());
		assertEquals("url video aq", c2.getUrlVideo());
		assertEquals("lat", c2.getLatitude());
		assertEquals("lng", c2.getLongitude());
		
		//atualizando o carro
		c.setNome("Teste UPDATE");
		assertNotEquals(carroService.save(c), false);
		
		//verificando a atualização
		assertEquals(carroService.getCarro(c.getId()).getNome(), "Teste UPDATE");
		
		//deletando o carro
		assertTrue(carroService.delete(c.getId()));
		
		//verificando se o carro não pode mais ser encontrado
		
		assertNull(carroService.getCarro(c.getId()));
	}
}
