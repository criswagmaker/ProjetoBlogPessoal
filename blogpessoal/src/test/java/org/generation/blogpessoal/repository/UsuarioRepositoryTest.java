package org.generation.blogpessoal.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.generation.blogpessoal.model.Usuario;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@BeforeAll
	void start() {
		
		usuarioRepository.save(new Usuario(0L, "DJ Cleiton Rasta", "cleitinho@pedra.com", "cabecadegelo","https://twitter.com/djcleitonrasta_/status/1218146215143055367/photo/1"));
		usuarioRepository.save(new Usuario(0L, "TioOrochi", "orochinho@tio.com", "tio","https://pm1.narvii.com/7763/5ae8edd5c91a8ba2389b22d25604db04e40711dar1-1080-1054v2_128.jpg"));
		usuarioRepository.save(new Usuario(0L, "Lucão", "lucas@inutilismo.com", "inutil","https://64.media.tumblr.com/1d412bc01bfe54f24514c7532208d65d/tumblr_oudtpiQel71vsdlfqo2_400.png"));
		usuarioRepository.save(new Usuario(0L, "Lucão22", "lucas@inutilismo.com", "inutil2","https://64.media.tumblr.com/1d412bc01bfe54f24514c7532208d65d/tumblr_oudtpiQel71vsdlfqo2_400.png"));
	}

	@Test
	@DisplayName("Retorna apenas um usuario")
	public void deveRetornarUmUsuario() {

		Optional<Usuario> usuario = usuarioRepository.findByUsuario("lucas@inutilismo.com");
		assertTrue(usuario.get().getUsuario().equals("lucas@inutilismo.com"));
	}

	@Test
	@DisplayName("Retorna dois usuários")
	public void deveRetornarDoisUsuarios() {
		List<Usuario> listaDeUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("Lucão");
		assertEquals(2, listaDeUsuarios.size());
		
		assertTrue(listaDeUsuarios.get(2).getNome().equals("Lucão"));
		assertTrue(listaDeUsuarios.get(3).getNome().equals("Lucão22"));
	}
}
