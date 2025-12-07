package com.bingoTrome.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bingoTrome.Model.CabBingo;
import com.bingoTrome.Model.DetalleBingo;
import com.bingoTrome.Model.Numero;
import com.bingoTrome.Repository.ICabBingoRepository;
import com.bingoTrome.Repository.IDetalleBingoRepository;
import com.bingoTrome.Repository.INumeroRepository;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    ICabBingoRepository repoCabBingo;
    @Autowired
    IDetalleBingoRepository repoDetalleBingo;
    @Autowired
    INumeroRepository repoNumero;

    @GetMapping("/cabs")
    public List<CabBingo> getCabs() {
        return repoCabBingo.findAll();
    }

    @GetMapping("/cab/{id}")
    public ResponseEntity<CabBingo> getCab(@PathVariable String id) {
        Optional<CabBingo> opt = repoCabBingo.findById(id);
        return opt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/compare/{id}")
    public ResponseEntity<Map<String, Object>> compare(@PathVariable String id) {
        Map<String, Object> result = new HashMap<>();
        CabBingo cab = repoCabBingo.findById(id).orElse(null);
        if (cab == null) {
            return ResponseEntity.notFound().build();
        }
        List<DetalleBingo> detalleList = new ArrayList<>();
        List<Numero> NumeroList = new ArrayList<>();

        detalleList = repoDetalleBingo.findAllByIdBingo(cab.getIdBingo());
        NumeroList = repoNumero.findAll();

        String numeroBuenos = "";
        List<String> listaNumerosBuenos = new ArrayList<>();
        if (detalleList != null) {
            for (DetalleBingo detalleLinea : detalleList) {
                for (Numero numero : NumeroList) {
                    try {
                        if (detalleLinea.getB() != null && !detalleLinea.getB().isEmpty()) {
                            if (numero.getNumero() == Integer.parseInt(detalleLinea.getB())) {
                                numeroBuenos += "-" + detalleLinea.getB();
                                listaNumerosBuenos.add(detalleLinea.getB());
                            }
                        }
                        if (detalleLinea.getI() != null && !detalleLinea.getI().isEmpty()) {
                            if (numero.getNumero() == Integer.parseInt(detalleLinea.getI())) {
                                numeroBuenos += "-" + detalleLinea.getI();
                                listaNumerosBuenos.add(detalleLinea.getI());
                            }
                        }
                        if (detalleLinea.getN() != null && !detalleLinea.getN().isEmpty()) {
                            if (numero.getNumero() == Integer.parseInt(detalleLinea.getN())) {
                                numeroBuenos += "-" + detalleLinea.getN();
                                listaNumerosBuenos.add(detalleLinea.getN());
                            }
                        }
                        if (detalleLinea.getG() != null && !detalleLinea.getG().isEmpty()) {
                            if (numero.getNumero() == Integer.parseInt(detalleLinea.getG())) {
                                numeroBuenos += "-" + detalleLinea.getG();
                                listaNumerosBuenos.add(detalleLinea.getG());
                            }
                        }
                        if (detalleLinea.getO() != null && !detalleLinea.getO().isEmpty()) {
                            if (numero.getNumero() == Integer.parseInt(detalleLinea.getO())) {
                                numeroBuenos += "-" + detalleLinea.getO();
                                listaNumerosBuenos.add(detalleLinea.getO());
                            }
                        }
                    } catch (NumberFormatException e) {
                        // ignore parsing errors for safety
                    }
                }
            }
        }

        result.put("numeroBuenos", listaNumerosBuenos);
        result.put("cab", cab);
        result.put("detalleList", detalleList);
        return ResponseEntity.ok(result);
    }
}
