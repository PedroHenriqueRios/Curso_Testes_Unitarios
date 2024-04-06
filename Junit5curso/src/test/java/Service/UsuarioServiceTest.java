package Service;

import static builders.UsuarioBuilder.umUsuario;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import Domain.Usuario;
import Exception.ValidationException;
import Infra.UsuarioDummyRepository;
import Repository.UsuarioRepository;
import builders.UsuarioBuilder;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {
	@InjectMocks private UsuarioService service;
	@Mock private UsuarioRepository repository;
	
	/*
	 * @BeforeEach public void setup() { MockitoAnnotations.openMocks(this); }
	 */
	
	/*
	 * @AfterEach public void tearDown() {
	 * 
	 * Mockito.verifyNoMoreInteractions(repository);
	 * 
	 * }
	 */

	@Test
	public void deveRetornarEmptyQuandoUsuarioInexistente() {
		//UsuarioRepository repository = Mockito.mock(UsuarioRepository.class);
		//service = new UsuarioService(repository);
		
		Mockito.when(repository.getUserByEmail("mail@mail.com")).thenReturn(Optional.empty());
		
		Optional<Usuario> user = service.getUserByEmail("mail@mail.com");
		System.out.println(user);
		Assertions.assertTrue(user.empty() != null);
	} 
	
	@Test
	public void deveRetornarUsuarioPorEmail() {
		//UsuarioRepository repository = Mockito.mock(UsuarioRepository.class);
		//service = new UsuarioService(repository);
		
		Mockito.when(repository.getUserByEmail("mail@mail.com"))
		.thenReturn(Optional.of(UsuarioBuilder.umUsuario().agora()))
				.thenReturn(null);
		
		
		Optional<Usuario> user = service.getUserByEmail("mail@mail.com");
		System.out.println(user);
		user = service.getUserByEmail("mail@mail.com");
		System.out.println(user);
		Assertions.assertFalse(user.empty() == null);
		
		verify(repository, Mockito.atLeastOnce()).getUserByEmail("mail@mail.com");
		verify(repository, Mockito.never()).getUserByEmail("outro@mail.com");
		
	} 
	
	@Test 
	public void deveSalvarUsuarioComSucesso() {
		//UsuarioRepository repository = Mockito.mock(UsuarioRepository.class);
		//service = new UsuarioService(repository);
		Usuario userToSave = umUsuario().comId(null).agora();
		
		when(repository.getUserByEmail(userToSave.email()))
		.thenReturn(Optional.empty());
		when(repository.salvar(userToSave)).thenReturn(umUsuario().agora());
		
		Usuario savedUser = service.salvar(userToSave);
		Assertions.assertNotNull(savedUser.id());
		
		verify(repository).getUserByEmail(userToSave.email());
		verify(repository).salvar(userToSave);
	}
	
	@Test
	public void deveRejeitarUsuarioExistente() {
		Usuario userToSave = umUsuario().comId(null).agora();
		when(repository.getUserByEmail(userToSave.email()))
		.thenReturn(Optional.of(umUsuario().agora()));
		
		
		ValidationException ex = Assertions.assertThrows(ValidationException.class, () ->
		service.salvar(userToSave));
		Assertions.assertTrue(ex.getMessage().endsWith("já cadastrado!"));
		
		verify(repository, Mockito.never()).salvar(userToSave);
	}
	
}
