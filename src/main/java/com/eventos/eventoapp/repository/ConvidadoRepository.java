package com.eventos.eventoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventos.eventoapp.models.Convidado;
import com.eventos.eventoapp.models.Evento;
import java.util.List;


public interface ConvidadoRepository extends JpaRepository<Convidado, String>{
	List<Convidado> findByEvento(Evento evento);
	Convidado findByRg(String rg);
}
