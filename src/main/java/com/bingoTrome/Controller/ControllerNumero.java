package com.bingoTrome.Controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bingoTrome.Model.CabBingo;
import com.bingoTrome.Model.Numero;
import com.bingoTrome.Repository.ICabBingoRepository;
import com.bingoTrome.Repository.IDiaSemanaRepository;
import com.bingoTrome.Repository.INumeroRepository;

@Controller
public class ControllerNumero {

	@Autowired
	INumeroRepository repoNumeros ;
	
	@Autowired
	ICabBingoRepository repoCab ;
	
	@Autowired
	IDiaSemanaRepository reporDiaSemana;
	
	@GetMapping("/cargarCrudNumero")
	String cargarRegisterNumero(Model model  ) {
		
		Numero numeros = new Numero ();
		

		model.addAttribute("lstNumero" ,repoNumeros.findAll() );
		model.addAttribute("diasSemana" ,  reporDiaSemana.findAll());
		model.addAttribute("Numeros" ,  numeros);
		return "CrudNumero";
	}
	
	@GetMapping("/eliminarNumero/{id}")
	String eliminarNumero(@PathVariable int id) {
		
		try {
			
			repoNumeros.deleteById(id);
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
	
		return "redirect:/cargarCrudNumero";
	}
	@GetMapping("/ModificarNumero/{id}")
	String ModificarNumero(@PathVariable int id, Model model ) {
		
		
		 	Numero numeros = repoNumeros.findById(id).get();
			model.addAttribute("lstNumero" ,repoNumeros.findAll() );
			model.addAttribute("diasSemana" ,  reporDiaSemana.findAll());
			model.addAttribute("Numeros" ,  numeros);
		return "CrudNumero";
	}
	
	
	@PostMapping("/registrarNumero")
	String registrarNumero(@ModelAttribute Numero numero) {
		try {
			repoNumeros.save(numero);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		
		List<CabBingo> lstCab = repoCab.findAll();
		
		for (CabBingo cab :lstCab) {
			cab.setRevision(null);
			try {
				repoCab.save(cab);
				
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
			}
			
		}
		
		return "redirect:/cargarCrudNumero";
	}
}
