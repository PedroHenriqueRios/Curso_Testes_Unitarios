package Domain;

import static builders.ContaBuilder.umaConta;
import static builders.UsuarioBuilder.umUsuario;

import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import Exception.ValidationException;



public class ContaTest {

	@Test
	public void criarContaValida() {
		Conta conta = umaConta().agora();
		
		Assertions.assertAll("Conta",
				() -> Assertions.assertEquals(1L, conta.id()),
				 () -> Assertions.assertEquals("Conta Valida", conta.nome()),
				() -> Assertions.assertEquals(umUsuario().agora(), conta.usuario())
				);
	}
	
	@ParameterizedTest
	@MethodSource(value = "dataProvider")
	public void deveRejeitarContaInvalida(Long id, String nome, Usuario usuario, String mensagem) {
		String errorMessage = Assertions.assertThrows(ValidationException.class, () ->
		umaConta().comId(id).comNome(nome).comUsuario(usuario).agora()).getMessage();
		Assertions.assertEquals(mensagem, errorMessage);
	}


	private static Stream<Arguments> dataProvider(){
		return Stream.of(
				Arguments.of(1L,null, umUsuario().agora(),"Nome é obrigatório"),
				Arguments.of(1L,"Conta Valida", null,"Usuário é obrigatório")
				);
	}
	
}