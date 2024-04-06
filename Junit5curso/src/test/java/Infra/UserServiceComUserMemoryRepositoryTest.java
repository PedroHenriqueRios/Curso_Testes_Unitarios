package Infra;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import Domain.Usuario;
import Service.UsuarioService;
import builders.UsuarioBuilder;
import infra.UsuarioMemoryRepository;

public class UserServiceComUserMemoryRepositoryTest {
	private static UsuarioService service = new UsuarioService(new UsuarioMemoryRepository());

	@Test
	public void deveSalvarUsuarioValido() {
		Usuario user = service.salvar(UsuarioBuilder.umUsuario().comId(null).comEmail("novo@mail.com").agora());
		Assertions.assertNotNull(user.id());
		Assertions.assertEquals(3L, user.id());

	}

	@Test
	public void deveSalvarUsuarioValido2() {
		Usuario user = service.salvar(UsuarioBuilder.umUsuario().comId(null).agora());
		Assertions.assertNotNull(user.id());
		Assertions.assertEquals(2L, user.id());
	}
}
