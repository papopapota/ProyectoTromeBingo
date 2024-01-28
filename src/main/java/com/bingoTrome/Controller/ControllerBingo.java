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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bingoTrome.Model.CabBingo;
import com.bingoTrome.Model.DetalleBingo;
import com.bingoTrome.Model.DetalleBingoForm;
import com.bingoTrome.Model.Numero;
import com.bingoTrome.Repository.ICabBingoRepository;
import com.bingoTrome.Repository.IDetalleBingoRepository;
import com.bingoTrome.Repository.INumeroRepository;

@Controller
public class ControllerBingo {
	@Autowired
	ICabBingoRepository repoCabBingo ;
	@Autowired
	IDetalleBingoRepository repoDetalleBingo ;
	@Autowired
	INumeroRepository repoNumero ;
	
	@GetMapping("/cargarCabs")
	String cargarCabs (Model model ) {

	
		model.addAttribute("lstcab",repoCabBingo.findAll());
		
		return "Index";
	}
	
	@GetMapping("/mostrarDetalle/{idBingo}")
	String listaBingo (Model model , @PathVariable String idBingo) {
		CabBingo cab = new CabBingo();
		List<DetalleBingo> detalleList = new ArrayList<DetalleBingo>();
		DetalleBingoForm detalleBingoForm = new DetalleBingoForm();
		
		cab = repoCabBingo.getById(idBingo);
		
		if (cab != null) {
			
				detalleList =repoDetalleBingo.findAllByIdBingo( cab.getIdBingo());
				
				
				detalleBingoForm.setDetalleList(detalleList);
				//System.out.println(detalleList);
			
		
		}
		
		model.addAttribute("cab",cab);
		model.addAttribute("detalleBingoForm",detalleBingoForm);
		return "DetalleBingo";
	} 
	
	
	@GetMapping("/registrarForm")
	String registrarForm (Model model) {
		
		model.addAttribute("cabBingo",new CabBingo());
		
	
		return "registerCab";
	}// hola
	
	@PostMapping("/registrarAction")
	String registrarAction (@ModelAttribute CabBingo cabBingo) {
		
		cabBingo.setIdUsu(1);
		System.out.println(cabBingo.getIdBingo());
		try {
			repoCabBingo.save(cabBingo);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		
		return "redirect:/cargarCabs";
	}
	
	@PostMapping("/guardarCambios")
	String guardarCambios (@ModelAttribute DetalleBingoForm detalleBingoForm) {
		/*
		int nroLinea = 0 ;
		for (DetalleBingo lineaDetalle : detalleBingoForm.getDetalleList()) {
			nroLinea += 1;
			try {
				repoDetalleBingo.save(lineaDetalle);
			} catch (Exception e) {
				System.out.println("nro linea " + nroLinea + "errro: " + e);
			}
		
		}*/
		try {
			repoDetalleBingo.saveAll(detalleBingoForm.getDetalleList());
		} catch (Exception e) {
			System.out.println("errro: " + e);
		}
		
		return "redirect:/cargarCabs";
	}
	
	@PostMapping("/AddLinea/{idBingo}")
	String AddLinea (@PathVariable String idBingo  ) {
		
		DetalleBingo detalleNuevo = new DetalleBingo();
		
		detalleNuevo.setIdBingo(idBingo);
		
		try {
			System.out.println(idBingo);
			repoDetalleBingo.save(detalleNuevo);
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		
		
		
		return "redirect:/mostrarDetalle/"+ idBingo ;
	}
	
	@GetMapping("/EliminarLinea/{id}")
	String EliminarLinea (@PathVariable int id) {
		
		String idCab = "";
		DetalleBingo detalle = repoDetalleBingo.findById(id).get();
		idCab = detalle.getIdBingo();
		try {
			System.out.println(id);
			repoDetalleBingo.deleteById(id);
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		
		return "redirect:/mostrarDetalle/"+ idCab ;
	}
	
	@GetMapping("/CompararNoBingo/{idBingo}")
	String CompararNoBingo (Model model , @PathVariable String idBingo) {
		String numeroBuenos = "";

		CabBingo cab = new CabBingo();
		List<DetalleBingo> detalleList = new ArrayList<DetalleBingo>();
		List<Numero> NumeroList = new ArrayList<Numero>();
		DetalleBingoForm detalleBingoForm = new DetalleBingoForm();
		
		cab = repoCabBingo.getById(idBingo);
		
		cab.setRevision(true);
		try {
			repoCabBingo.save(cab);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		
		if (cab != null) {
				detalleList =repoDetalleBingo.findAllByIdBingo(cab.getIdBingo());
				NumeroList = repoNumero.findAll();
				
				if (detalleList != null) {
					for (DetalleBingo detalleLinea : detalleList) {
						
						for (Numero numero :NumeroList) {
							
							if (detalleLinea.getB() != "") {
								
								if (numero.getNumero()  == Integer.parseInt(detalleLinea.getB()) ){
									numeroBuenos += "-"+detalleLinea.getB();
									
								}
								if (numero.getNumero()  == Integer.parseInt(detalleLinea.getI()) ){
									numeroBuenos += "-"+detalleLinea.getI();
								}
								if (numero.getNumero()  == Integer.parseInt(detalleLinea.getN()) ){
									numeroBuenos += "-"+detalleLinea.getN();
								}
								if (numero.getNumero()  == Integer.parseInt(detalleLinea.getG()) ){
									numeroBuenos += "-"+detalleLinea.getG();
								}
								if (numero.getNumero()  == Integer.parseInt(detalleLinea.getO()) ){
									numeroBuenos += "-"+detalleLinea.getO();
								}
							}
							
						}
					}
				}
				
		}
		model.addAttribute("numeroBuenos",numeroBuenos);
		model.addAttribute("cab",cab);
		model.addAttribute("detalleList",detalleList);
		System.out.println("hi"+numeroBuenos);
		return "RevisionNumeros";
	}
	
	@GetMapping("/revisarTodosBingos")
	String revisarTodosBingos (Model model  ) {
		
		int B ,I,N,G,O;
		String mensaje = "";
		
		String numeroBuenos = "";

		List<CabBingo>  cabList = new ArrayList<CabBingo>();
		List<DetalleBingo> detalleList = new ArrayList<DetalleBingo>();
		List<Numero> NumeroList = new ArrayList<Numero>();
		DetalleBingoForm detalleBingoForm = new DetalleBingoForm();
		
		cabList =repoCabBingo.findAll();
		for (CabBingo cab:cabList) {
			B=0;
			I= 0 ;
			N= 0 ;
			G= 0 ;
			O= 0 ;
			
			
			detalleList =repoDetalleBingo.findAllByIdBingo(cab.getIdBingo());
			NumeroList = repoNumero.findAll();
			
			if (detalleList != null) {
				for (DetalleBingo detalleLinea : detalleList) {
					
					for (Numero numero :NumeroList) {
						
						if (detalleLinea.getB() != "") {
							
							if (numero.getNumero()  == Integer.parseInt(detalleLinea.getB()) ){
								numeroBuenos += "-"+detalleLinea.getB();
								B += 1 ;
							}
							if (numero.getNumero()  == Integer.parseInt(detalleLinea.getI()) ){
								numeroBuenos += "-"+detalleLinea.getI();
								I += 1 ;
							}
							if (numero.getNumero()  == Integer.parseInt(detalleLinea.getN()) ){
								numeroBuenos += "-"+detalleLinea.getN();
								N += 1 ;
							}
							if (numero.getNumero()  == Integer.parseInt(detalleLinea.getG()) ){
								numeroBuenos += "-"+detalleLinea.getG();
								G += 1 ;
							}
							if (numero.getNumero()  == Integer.parseInt(detalleLinea.getO()) ){
								numeroBuenos += "-"+detalleLinea.getO();
								O += 1 ;
							}
						}
						
					}
				}
			}
			
			if ((B == 5) || (I == 5) || (N == 5) || (G == 5) || (O == 5)) {
				mensaje += "Nro Ganador de bingo: " + cab.getIdBingo() + "<br>"  ; 
			}else {
				if (mensaje=="") {
					mensaje= "no";
				}
				
			}
			
		}
		
				

		System.out.println(mensaje);

		model.addAttribute("mensaje",mensaje);
		model.addAttribute("lstcab",repoCabBingo.findAll());
		return "Index";
	}
	
}
