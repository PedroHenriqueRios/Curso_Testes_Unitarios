package Service;

import static builders.ContaBuilder.umaConta;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;

import Domain.Conta;
import Domain.Usuario;
import Exception.ValidationException;
import Externo.ContaEvent;
import Externo.ContaEvent.EventType;
import Repository.ContaRepository;
import builders.ContaBuilder;

@ExtendWith(MockitoExtension.class)
//@MockitoSettings(strictness = Strictness.LENIENT)
public class ContaServiceTest {
	@InjectMocks private ContaService service;
	@Mock private ContaRepository repository;
	@Mock private ContaEvent event;
	
	@Captor private ArgumentCaptor<Conta> contaCaptor;

@Test	
public void deveSalvarComSucessoPrimeiraConta () throws Exception {
		Conta contaToSave = umaConta().comId(null).agora();
		Mockito.when(repository.salvar(Mockito.any(Conta.class))).thenReturn(umaConta().agora());
		Mockito.doNothing().when(event).dispatch(umaConta().agora(), EventType.CREATED);
		
		Conta savedConta = service.salvar(contaToSave);
		Assertions.assertNotNull(savedConta.id()); 
		
		Mockito.verify(repository).salvar(contaCaptor.capture());
	}

@Test	
public void deveRejeitarContaRepetida() {
		Conta contaToSave = umaConta().comId(null).agora();
		Mockito.when(repository.obterContasPorUsuario(contaToSave.usuario().id()))
		.thenReturn(Arrays.asList(umaConta().agora()));
		//Mockito.when(repository.salvar(contaToSave)).thenReturn(umaConta().agora());
		 
		String mensagem = Assertions.assertThrows(ValidationException.class, () ->
		service.salvar(contaToSave)).getMessage();
		Assertions.assertEquals("Usuario já possui uma conta com este nome", mensagem);
	}

@Test	
public void deveSalvarContaMesmoJaExistindoOutras() {
		Conta contaToSave = umaConta().comId(null).agora();
		Mockito.when(repository.obterContasPorUsuario(contaToSave.usuario().id()))
		.thenReturn(Arrays.asList(umaConta().comNome("Outra conta").agora()));
		Mockito.when(repository.salvar(Mockito.any(Conta.class))).thenReturn(umaConta().agora());
		 
		Conta savedConta = service.salvar(contaToSave);
		Assertions.assertNotNull(savedConta);
	}


@Test	
public void NaoDeveContaSemEvento () throws Exception {
		Conta contaToSave = umaConta().comId(null).agora();
		Conta contaSalva = umaConta().agora();
		Mockito.when(repository.salvar(Mockito.any(Conta.class))).thenReturn(contaSalva);
		Mockito.doThrow(new Exception("Falha catastrófica"))
		.when(event).dispatch(contaSalva, EventType.CREATED);
		
		String mensagem = Assertions.assertThrows(Exception.class, () ->
		service.salvar(contaToSave)).getMessage();
		Assertions.assertEquals("Falha na criação da conta", mensagem);
		
		Mockito.verify(repository).delete(contaSalva);
	}
	
}
