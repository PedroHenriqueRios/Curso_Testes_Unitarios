package Externo;

import Domain.Conta;

public interface ContaEvent {
	public enum EventType {CREATED, UPDATED, DELETED};

	void dispatch(Conta conta, EventType type) throws Exception;
}
