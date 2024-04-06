package Domain;

import static builders.UsuarioBuilder.umUsuario;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import Exception.ValidationException;
import builders.UsuarioBuilder;

public class UsuarioTest {
	
	@Test
	@DisplayName("Domínio Usuário ")
	public void deveCriarUsuarioValido() {
		Usuario usuario = umUsuario().agora();
		Assertions.assertAll("Usuario",
		() -> assertEquals(1L, usuario.id()),
		() -> assertEquals("Usuario válido", usuario.nome()),
		() -> assertEquals("user@mail.com", usuario.email()),
		() -> assertEquals("12345678", usuario.senha())
		);
		
		assertEquals(1L, usuario.id());
		assertEquals("Usuario válido", usuario.nome());
		assertEquals("user@mail.com", usuario.email());
		assertEquals("12345678", usuario.senha());
	}
	
	@Test
	public void deveRejeitarUsuarioSemNome() {
	ValidationException ex =	Assertions.assertThrows(ValidationException.class, () -> 
		umUsuario().comNome(null).agora());
		assertEquals("Nome é obrigatório", ex.getMessage());
	} 

}
