package com.smartexpensive.backend.web;

import com.smartexpensive.backend.domain.converters.GastosMapper;
import com.smartexpensive.backend.domain.dto.GastosDTO;
import com.smartexpensive.backend.domain.models.entity.Gasto;
import com.smartexpensive.backend.domain.services.Gastos.IGastosServices;
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
    public ResponseEntity<GastosDTO> save(@RequestBody GastosDTO gastosDTO) {
        try {
            return ResponseEntity.ok(gastosServices.save(gastosDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
