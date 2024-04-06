package Repository;

import java.util.List;

import Domain.Conta;

public interface ContaRepository {
	
	Conta salvar(Conta conta);
	
	List<Conta> obterContasPorUsuario(Long usuarioId);

	void delete(Conta contaSalva);

}
