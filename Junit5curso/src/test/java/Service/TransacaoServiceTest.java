package Service;

import static builders.TransacaoBuilder.umaTransacao;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import Domain.Conta;
import Domain.Transacao;
import Exception.ValidationException;
import Externo.ClockService;
import Repository.TransacaoDao;
import builders.ContaBuilder;
import builders.TransacaoBuilder;

@ExtendWith(MockitoExtension.class)
public class TransacaoServiceTest {
	@InjectMocks @Spy private TransacaoService service;
	@Mock private TransacaoDao dao; 
	
	@BeforeEach
	public void setup() {
	//	when(clock.getCurrentTime()).thenReturn(LocalDateTime.of(2024, 1, 1, 4, 30, 15));
	//	Mockito.when(service.getTime()).thenReturn(LocalDateTime.of(2024, 1, 1, 4, 30, 15));
		
	}
	
	@Test
	public void deveSalvarTransacaoValida() {
		Transacao transacaoParaSalvar = umaTransacao().comId(null).agora();
	Mockito.when(dao.salvar(transacaoParaSalvar)).thenReturn(umaTransacao().agora());
	//Mockito.when(clock.getCurrentTime()).thenReturn(LocalDateTime.of(2024, 1, 1, 4, 30, 15));
	
		Transacao transacaoSalva = service.salvar(transacaoParaSalvar);
		Assertions.assertEquals(umaTransacao().agora() , transacaoSalva);
		Assertions.assertAll("Transacao",
				() -> assertEquals(1L, transacaoSalva.getId()),
				() -> assertEquals("Transação Válida", transacaoSalva.getDescricao()),
				() -> {
			assertAll("Conta",
					() -> assertEquals("Conta Valida", transacaoSalva.getConta().nome()),
				 	() -> {
						assertAll("Usuário",
								() -> assertEquals("Usuario válido", transacaoSalva.getConta().usuario().nome()),
								() -> assertEquals("12345678", transacaoSalva.getConta().usuario().senha())
								);
						}
					);	
				}
			);
	
	
	}
	
	@ParameterizedTest
	@MethodSource(value = "cenarioObrigatorios")
	public void deveValidarCamposObrigatoriosAoSalvar(Long id, String descricao, Double valor, LocalDate data,Conta conta, Boolean status, String mensagem) {
		String exMessage = Assertions.assertThrows(ValidationException.class, () -> {
		Transacao transacao = umaTransacao().comId(id).comDescricao(descricao).comValor(valor)
				.comData(data).comStatus(status).comConta(conta).agora();
		service.salvar(transacao);
		}).getMessage();
		Assertions.assertEquals(mensagem , exMessage);
	}
	
	static Stream<Arguments> cenarioObrigatorios() {
		return Stream.of(
			Arguments.of(1L, null, 10D, LocalDate.now(), ContaBuilder.umaConta().agora(), true, "Descrição inexistente"),
			Arguments.of(1L, "Descrição", null, LocalDate.now(), ContaBuilder.umaConta().agora(), true, "Valor inexistente"),
			Arguments.of(1L, "Descrição", 10D, null, ContaBuilder.umaConta().agora(), true, "Data inexistente"),
			Arguments.of(1L, "Descrição", 10D, LocalDate.now(), null, true, "Conta inexistente")
		);
	}
	
	@Test
	public void deveRejeitarTransacaoSemValor() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Transacao transacao = umaTransacao().comValor(null).agora();
		
		Method metodo = TransacaoService.class.getDeclaredMethod("validarCamposObrigatorios", Transacao.class);
		metodo.setAccessible(true);
		Exception ex = Assertions.assertThrows(Exception.class, () ->
		metodo.invoke(new TransacaoService(), transacao));
		Assertions.assertEquals("Valor inexistente", ex.getCause().getMessage());
	}
	
}
