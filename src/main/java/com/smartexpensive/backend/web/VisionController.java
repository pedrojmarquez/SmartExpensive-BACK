package com.smartexpensive.backend.web;


import com.smartexpensive.backend.domain.services.DeepSeek.DeepSeekServiceImpl;
import com.smartexpensive.backend.domain.services.Vision.VisionServiceImpl;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/api/vision")
public class VisionController {

    @Autowired
    private VisionServiceImpl visionService;
    @Autowired
    private DeepSeekServiceImpl deepSeekService;


    @PostMapping("/analizar")
    public String analizarImagen(@RequestParam("file") MultipartFile file) throws Exception {
        File tempFile = File.createTempFile("ticket-", ".jpg");
        file.transferTo(tempFile);
        String resultado = visionService.detectarTexto(tempFile);
        tempFile.delete();
        return resultado;
    }

    @PostMapping("/procesar-ticket")
    public ResponseEntity<?> processTicket(@RequestParam("file") MultipartFile file) throws Exception {
        // 1. Convierte MultipartFile a File
        File temp = File.createTempFile("ticket-", ".jpg");
        file.transferTo(temp);

        // 2. OCR
        String texto = visionService.detectarTexto(temp);

        // 3. Usa DeepSeek para estructurar
        JSONObject resultadoJson = deepSeekService.analizarTexto(texto);

        // 4. Devuelve el resultado JSON directamente
        temp.delete();
        return ResponseEntity.ok(resultadoJson.toString(2)); // pretty print con 2 espacios
    }
}

