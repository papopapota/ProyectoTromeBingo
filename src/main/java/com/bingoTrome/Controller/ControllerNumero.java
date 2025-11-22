package com.bingoTrome.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

	/**
	 * Upload an image with numbers, extract digits using Tesseract CLI and save the first 6 numbers
	 * into the `numerosobtenidos` table. Requires `tesseract` installed and available in PATH.
	 *
	 * @param file uploaded image (jpg/png/...)
	 * @param diaSemana id of the day (will be stored in the numero.dia_semana)
	 */

	@PostMapping("/upload-numbers")
	public String uploadNumbers(@RequestParam("file") MultipartFile file, @RequestParam(name = "diaSemana", required = false, defaultValue = "1") Integer diaSemana, org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttrs) {
		if (file == null || file.isEmpty()) {
			redirectAttrs.addFlashAttribute("ocrResult", "No file uploaded");
			return "redirect:/cargarCrudNumero";
		}
		try {
			// Save uploaded file to temp
			java.nio.file.Path tempDir = java.nio.file.Files.createTempDirectory("upload-");
			java.nio.file.Path imgPath = tempDir.resolve(file.getOriginalFilename());
			file.transferTo(imgPath.toFile());

			// Prepare tesseract command: only digits and page segmentation mode 6
			String outputBase = tempDir.resolve("out").toString();
			ProcessBuilder pb = new ProcessBuilder(
					"tesseract",
				 	imgPath.toString(),
				  	outputBase, "--oem", "3",
				   	"-c", "tessedit_char_whitelist=0123456789",
					"-c", "tessedit_char_blacklist=abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ",
					"--psm", "11"
				
				   );
			pb.redirectErrorStream(true);
			Process p = pb.start();

			// capture process output
			String procOut;
			try (java.io.InputStream is = p.getInputStream(); java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A")) {
				procOut = s.hasNext() ? s.next() : "";
			}
			p.waitFor();

			// Read tesseract output file if produced, otherwise use process output
			java.nio.file.Path txtPath = java.nio.file.Paths.get(outputBase + ".txt");
			String ocrText = "";
			if (java.nio.file.Files.exists(txtPath)) {
				ocrText = java.nio.file.Files.readString(txtPath);
			} else {
				ocrText = procOut;
			}

			// extract sequences of exactly 3 digits (numbers of 3 digits as requested)
			java.util.regex.Pattern ptn = java.util.regex.Pattern.compile("\\d{3}");
			java.util.regex.Matcher m = ptn.matcher(ocrText);
			java.util.List<Integer> numbers = new java.util.ArrayList<>();
			while (m.find() && numbers.size() < 6) {
				numbers.add(Integer.parseInt(m.group()));
			}

			if (numbers.isEmpty()) {
				redirectAttrs.addFlashAttribute("ocrResult", "No numbers detected. OCR result: " + ocrText);
				try { java.nio.file.Files.walk(tempDir).sorted(java.util.Comparator.reverseOrder()).map(java.nio.file.Path::toFile).forEach(java.io.File::delete); } catch (Exception ex) {}
				return "redirect:/cargarCrudNumero";
			}

			// compute next id base and save
			int nextId = repoNumeros.findAll().stream().mapToInt(Numero::getId).max().orElse(0) + 1;
			java.util.List<Numero> saved = new java.util.ArrayList<>();
			for (Integer num : numbers) {
				Numero n = new Numero();
				n.setId(nextId++);
				n.setNumero(num);
				n.setDia_semana(diaSemana);
				saved.add(n);
			}
			repoNumeros.saveAll(saved);

			// cleanup temp
			try { java.nio.file.Files.walk(tempDir).sorted(java.util.Comparator.reverseOrder()).map(java.nio.file.Path::toFile).forEach(java.io.File::delete); } catch (Exception ex) {}

			redirectAttrs.addFlashAttribute("ocrResult", "Saved numbers: " + numbers.toString());
			return "redirect:/cargarCrudNumero";
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttrs.addFlashAttribute("ocrResult", "Error processing image: " + e.getMessage());
			return "redirect:/cargarCrudNumero";
		}
	}
}
