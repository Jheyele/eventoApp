package com.eventos.eventoapp.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eventos.eventoapp.models.Evento;

@Repository
public interface EventoRepository extends JpaRepository<Evento, String>{

	Evento getById(long id);
	
}
