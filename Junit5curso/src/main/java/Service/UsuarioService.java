package Service;

import java.util.Optional;

import Domain.Usuario;
import Exception.ValidationException;
import Repository.UsuarioRepository;

public class UsuarioService {
	
	private UsuarioRepository repository;
	
	public UsuarioService(UsuarioRepository repository) {
		
		this.repository = repository; 
		
	}
	
	public Usuario salvar(Usuario usuario) {
		repository.getUserByEmail(usuario.email()).ifPresent(user -> {
			throw new ValidationException(String.format("Usuário %s já cadastrado!", usuario.email()));
			
		});
		return repository.salvar(usuario); 
	}
	
	public Optional<Usuario> getUserByEmail(String email){
		return repository.getUserByEmail(email);
		
	}
}
