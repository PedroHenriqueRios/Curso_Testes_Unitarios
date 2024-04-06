package Repository;

import java.util.Optional;

import Domain.Usuario;

public interface UsuarioRepository {
	
	Usuario salvar(Usuario usuario);
	 
	Optional<Usuario>getUserByEmail(String email);
}
