package Service;

import java.time.LocalDateTime;
import java.util.List;

import Domain.Conta;
import Exception.ValidationException;
import Externo.ContaEvent;
import Externo.ContaEvent.EventType;
import Repository.ContaRepository;

public class ContaService {
	
private ContaRepository repository;
private ContaEvent event;	

	public ContaService(ContaRepository repository, ContaEvent event) {
		
		this.repository = repository; 
		this.event = event;
		
	}
	
	public Conta salvar(Conta conta) {
		List<Conta> contas = repository.obterContasPorUsuario(conta.usuario().id());
		contas.stream().forEach(contaExistentes -> {
			if(conta.nome().equalsIgnoreCase(contaExistentes.nome())) {
				throw new ValidationException("Usuario já possui uma conta com este nome");
			}
		});
		Conta contaPersistida = repository.salvar(
	 			new Conta(conta.id(), conta.nome() + LocalDateTime.now(), conta.usuario()));
		try {
			event.dispatch(contaPersistida, EventType.CREATED);
		} catch (Exception e) {
			repository.delete(contaPersistida);
			throw new RuntimeException("Falha na criação da conta");
		}
		return contaPersistida ;
	}

}
