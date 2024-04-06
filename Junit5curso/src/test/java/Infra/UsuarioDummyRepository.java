package Infra;

import static builders.UsuarioBuilder.umUsuario;

import java.util.Optional;

import Domain.Usuario;
import Repository.UsuarioRepository;
import builders.UsuarioBuilder;

public class UsuarioDummyRepository implements UsuarioRepository {

	@Override
	public Usuario salvar(Usuario usuario) {
		return umUsuario()
				.comNome(usuario.nome()).comEmail(usuario.email())
				.comSenha(usuario.senha()).agora();
	}

	@Override
	public Optional<Usuario> getUserByEmail(String email) {
		if("user@mail.com".equals(email))
		return Optional.of(umUsuario().comEmail(email).agora());
		return Optional.empty();
	}

}
