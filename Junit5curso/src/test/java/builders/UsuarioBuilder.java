package builders;

import Domain.Usuario;

public class UsuarioBuilder {
	private Long id;
	private String nome;
	private String email;
	private String senha;
	private UsuarioBuilder() {
	}
	
	public static UsuarioBuilder umUsuario() {
		UsuarioBuilder builder = new UsuarioBuilder();
		inicializarDadosPadroes(builder);
		return builder;
		
	}

	private static void inicializarDadosPadroes(UsuarioBuilder builder) {
		builder.id = 1L;
		builder.nome = "Usuario válido";
		builder.email = "user@mail.com";
		builder.senha = "12345678";
	}
	
	public UsuarioBuilder comId(Long param) {
		id = param;
		return this;
		
	}
	
	public UsuarioBuilder comNome(String param) {
		nome = param;
		return this;
		
	}
	
	public UsuarioBuilder comEmail(String param) {
		email = param;
		return this;
		
	}
	
	public UsuarioBuilder comSenha(String param) {
		senha = param;
		return this;
		
	}
	
	public Usuario agora() {
		return new Usuario(id, nome, email, senha);
	}
}
