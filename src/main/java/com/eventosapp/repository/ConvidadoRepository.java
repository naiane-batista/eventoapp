package com.eventosapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.eventosapp.models.Convidado;
import com.eventosapp.models.Evento;

public interface ConvidadoRepository extends CrudRepository<Convidado,String> {
	Iterable<Convidado>findByEvento(Evento evento);// esse evento faz a busca bd

	Convidado findByRg(String rg);

}
