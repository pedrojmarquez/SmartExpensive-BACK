package com.smartexpensive.backend.web;

import com.smartexpensive.backend.domain.converters.GastosMapper;
import com.smartexpensive.backend.domain.dto.CategoriasDTO;
import com.smartexpensive.backend.domain.dto.GastosDTO;
import com.smartexpensive.backend.domain.dto.PresupuestosDTO;
import com.smartexpensive.backend.domain.models.entity.Gasto;
import com.smartexpensive.backend.domain.services.Gastos.IGastosServices;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gastos")
@CrossOrigin(origins = "http://localhost:8100")
public class GastosController {

    @Autowired
    private IGastosServices gastosServices;




    @GetMapping
    public List<Gasto> findAll() {
        return gastosServices.findAll();
    }



    @PostMapping("/save")
    public ResponseEntity<GastosDTO> save(@RequestBody GastosDTO gastosDTO, HttpServletRequest request) {
        try {
            return ResponseEntity.ok(gastosServices.save(gastosDTO,request));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/me")
    public List<GastosDTO> findByUsuarioId(HttpServletRequest request) {
        return gastosServices.findByUsuarioId(request);
    }

    @GetMapping("/categorias")
    public ResponseEntity<List<CategoriasDTO>> findAllCategorias() {
        try {
            return ResponseEntity.ok(gastosServices.findAllCategorias());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/presupuesto")
    public ResponseEntity<PresupuestosDTO> findPresupuesto(HttpServletRequest request) {
        try {
            return ResponseEntity.ok(gastosServices.findPresupuesto(request));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/presupuesto/save")
    public ResponseEntity<PresupuestosDTO> savePresupuesto(@RequestBody PresupuestosDTO presupuestosDTO,HttpServletRequest request) {
        try {
            return ResponseEntity.ok(gastosServices.savePresupuesto(presupuestosDTO,request));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
