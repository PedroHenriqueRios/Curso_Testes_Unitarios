package Service;

import java.time.LocalDateTime;
import java.util.Date;

import Domain.Transacao;
import Exception.ValidationException;
import Externo.ClockService;
import Repository.TransacaoDao;

public class TransacaoService {
	private TransacaoDao dao;

	public Transacao salvar(Transacao transacao) {
		//if(getTime().getHour() > 10 )
			//throw new RuntimeException("Tente novamente amanhã");
		
		 validarCamposObrigatorios(transacao);
		
		return dao.salvar(transacao);
	}

	private void validarCamposObrigatorios(Transacao transacao) {
		if(transacao.getDescricao() == null) throw new ValidationException("Descrição inexistente");
		if(transacao.getValor() == null) throw new ValidationException("Valor inexistente");
		if(transacao.getData() == null) throw new ValidationException("Data inexistente");
		if(transacao.getConta() == null) throw new ValidationException("Conta inexistente");
		if(transacao.getStatus() == null) transacao.setStatus(false);
	}
	
	protected LocalDateTime getTime() {
		return LocalDateTime.now();
	}
	
}
