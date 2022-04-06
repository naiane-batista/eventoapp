package com.eventosapp.controllers;

import javax.naming.Binding;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eventosapp.models.Convidado;
import com.eventosapp.models.Evento;
import com.eventosapp.repository.ConvidadoRepository;
import com.eventosapp.repository.EventoRepository;

@Controller
public class EventoController {
	
	@Autowired
	private EventoRepository er;

	@Autowired
	private ConvidadoRepository cr;

	
	@RequestMapping(value="/cadastrarEvento",method=RequestMethod.GET)
	public String form() {

		return "evento/formEvento";
	}
	@RequestMapping(value="/cadastrarEvento",method=RequestMethod.POST)
	public String form(Evento evento) {
		
		er.save(evento);//salvar noDB
		
		return "redirect:/cadastrarEvento";
	}

	@RequestMapping("/eventos")
	public ModelAndView listaEventos(){
		ModelAndView mv=new ModelAndView("index");

		//buscar esse evento no DB com metodo de busca
		Iterable<Evento>eventos=er.findAll();

		//a lista de eventos vai passar(aparecer) na view
		mv.addObject("eventos",eventos);
		return mv;
	}

	//@PathVariable mostra como ler uma variável de modelo de URL com anotacao
	@RequestMapping(value ="/{codigo}",method = RequestMethod.GET)//vai retornar o codigo de cada evento
	public ModelAndView detalhesEvento(@PathVariable ("codigo")long codigo){
		
		Evento evento=er.findByCodigo(codigo);//chma o metodo q encintra um codigo especifico
		ModelAndView mv=new ModelAndView("evento/detalhesEvento");
		mv.addObject("evento",evento);
		
		Iterable<Convidado>convidados=cr.findByEvento(evento);
		mv.addObject("convidados",convidados);//enviar pra view detalhesevento
		return mv;
	}
	@RequestMapping("/deletarEvento")
	public String deletarEvento(long codigo) {
		Evento evento= er.findByCodigo(codigo);
		er.delete(evento);
		
		return "redirect:/eventos";
	}
	
	@RequestMapping(value ="/{codigo}",method=RequestMethod.POST)
	public String detalhesEventoPost(@PathVariable("codigo")long codigo, @Valid Convidado convidado,BindingResult result,RedirectAttributes atributes){
		if(result.hasErrors()) { //verificar se tem erros
			atributes.addFlashAttribute("mensagem","Verifique os campos");
			
			return "redirect:/{codigo}";
		}
		Evento evento=er.findByCodigo(codigo);
		convidado.setEvento(evento);
		cr.save(convidado);
		atributes.addFlashAttribute("mensagem","Convidado adicionado com sucesso");

		return "redirect:/{codigo}";
	}

	@RequestMapping("/deletarConvidado")
	public String deletarConvidado(String rg) {
		Convidado convidado=cr.findByRg(rg);
		cr.delete(convidado);
		
		Evento evento=convidado.getEvento();
		long codigoLong=evento.getCodigo();
		String codigo="" +codigoLong;
		return "redirect:/"+codigo;
	}


}
