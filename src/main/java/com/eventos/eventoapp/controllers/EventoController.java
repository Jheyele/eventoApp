package com.eventos.eventoapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eventos.eventoapp.models.Convidado;
import com.eventos.eventoapp.models.Evento;
import com.eventos.eventoapp.repository.ConvidadoRepository;
import com.eventos.eventoapp.repository.EventoRepository;
import java.util.List;

@Controller
public class EventoController {
	
	@Autowired
	private EventoRepository eventoRepository;
	
	@Autowired
	private ConvidadoRepository convidadoRepository;
	
	@GetMapping("/cadastrar-evento")
	public String form() {
		return "evento/formEvento";
	}
	
	@PostMapping("/cadastrar-evento")
	public String form(@Validated Evento evento, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			attributes.addFlashAttribute("flashMessage", "Verifique os campos!");
			attributes.addFlashAttribute("flashType", "danger");
			return "redirect:/cadastrar-evento";
		}
		eventoRepository.save(evento);
		attributes.addFlashAttribute("flashMessage", "Evento adicionado com sucesso!");
		attributes.addFlashAttribute("flashType", "success");
		return "redirect:/cadastrar-evento";
	}
	
	@GetMapping("/evento")
	public ModelAndView listaEventos() {
		ModelAndView mv = new ModelAndView("index");
		Iterable<Evento> eventos = eventoRepository.findAll();
		mv.addObject("eventos", eventos);
		return mv;
	}
	
	@GetMapping("/{id}")
	public ModelAndView detalhesEvento(@PathVariable("id") long id) {
		Evento evento = eventoRepository.getById(id);
		ModelAndView mv = new ModelAndView("evento/detalhesEvento");
		mv.addObject("evento", evento);
		
		List<Convidado> convidados = convidadoRepository.findByEvento(evento);
		mv.addObject("convidados", convidados);
		
		return mv;
	}
	
	@RequestMapping("/deletar-evento/{id}")
	public String deleteEvento(@PathVariable("id") long id) {
		Evento evento = eventoRepository.getById(id);
		eventoRepository.delete(evento);
		return "redirect:/evento";
	}
	
	@PostMapping("/{id}")
	public String detalhesEventoPost(@PathVariable("id") long id, @Validated Convidado convidado, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			attributes.addFlashAttribute("flashMessage", "Verifique os campos!");
			attributes.addFlashAttribute("flashType", "danger");
			return "redirect:/{id}";
		}
		Evento evento = eventoRepository.getById(id);
		convidado.setEvento(evento);
		convidadoRepository.save(convidado);
		attributes.addFlashAttribute("flashMessage", "Convidado adicionado com sucesso!");
		attributes.addFlashAttribute("flashType", "success");
		return "redirect:/{id}";
	}
	
	@RequestMapping("/deletar-convidado")
	public String deleteConvidado(String rg) {
		Convidado convidado = convidadoRepository.findByRg(rg);
		convidadoRepository.delete(convidado);
		
		Evento evento = convidado.getEvento();
		long idLong = evento.getId();
		return "redirect:/"+String.valueOf(idLong);
		
	}
	
}
