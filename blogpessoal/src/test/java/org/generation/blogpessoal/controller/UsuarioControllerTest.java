package org.generation.blogpessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.generation.blogpessoal.model.Usuario;
import org.generation.blogpessoal.service.UsuarioService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioControllerTest {

	
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Test
	@Order(1)
	@DisplayName("Cadastrar Um Usuario")
	public void deveCriarUmUsuario() {
		
		HttpEntity<Usuario>requisicao =new HttpEntity<Usuario>(new Usuario(0L, "Cristian Wagmaker", "cristianwag@wagmaker.com", "28019", "link_foto"));
		
		ResponseEntity<Usuario>resposta=testRestTemplate
				.exchange("/usuarios/cadastrar", HttpMethod.POST, requisicao, Usuario.class);
		
		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
		assertEquals(requisicao.getBody().getNome(), resposta.getBody().getNome());
		assertEquals(requisicao.getBody().getUsuario(), resposta.getBody().getUsuario());
	}
	
	@Test
	@Order(2)
	@DisplayName("Não pode permitir usuários duplicados")
	public void naoDeveDuplicarUsuario() {
		
		usuarioService.cadastrarUsuario(new Usuario(0L,
				"Pedro Augusto", "pedro@augusto.com","123","link_foto"));
		
		HttpEntity<Usuario>requisicao=new HttpEntity<Usuario>(new Usuario(0L,
				"Pedro Augusto", "pedro@augusto.com","123","link_foto"));
		
		ResponseEntity<Usuario> resposta=testRestTemplate
				.exchange("/usuarios/cadastrar", HttpMethod.POST, requisicao, Usuario.class);
		
		assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
				
	}
	
	@Test
	@Order(3)
	@DisplayName("Alterar um Usuario")
	public void deveAtualizarUmUsuario() {
		
		Optional<Usuario>usuarioCreate=usuarioService.cadastrarUsuario(new Usuario(0L,
				"Henrique", "henrique@email.com","123","link_foto"));
		
		Usuario usuarioUpdate=new Usuario(usuarioCreate.get().getId(),
				"Henrique","henrique@email.com","123","link_foto");
		
		HttpEntity<Usuario>requisicao=new HttpEntity<Usuario>(usuarioUpdate);
		
		ResponseEntity<Usuario>resposta=testRestTemplate
				.withBasicAuth("root", "root")
				.exchange("/usuarios/atualizar", HttpMethod.PUT, requisicao, Usuario.class);
		
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertEquals(usuarioUpdate.getNome(), resposta.getBody().getNome());
		assertEquals(usuarioUpdate.getUsuario(), resposta.getBody().getUsuario());
	}
	
	@Test
	@Order(4)
	@DisplayName("Mostrar todos os usuarios")
	public void deveMostrarTodosUsuarios() {
		
		usuarioService.cadastrarUsuario(new Usuario(0L,
				"Pedro Augusto", "pedro@augusto.com","123","link_foto"));
		
		usuarioService.cadastrarUsuario(new Usuario(0L,
				"Henrique", "henrique@email.com","123","link_foto"));
		
		ResponseEntity<String> resposta= testRestTemplate
				.withBasicAuth("root", "root")
				.exchange("/usuarios/all", HttpMethod.GET, null, String.class);
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}
	
	
	
}