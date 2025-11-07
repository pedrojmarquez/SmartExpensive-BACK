package com.smartexpensive.backend.web;

import com.smartexpensive.backend.domain.services.DeepSeek.DeepSeekServiceImpl;
import com.smartexpensive.backend.domain.services.Whisper.WhisperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("/api/transcripcion")
@CrossOrigin(origins = "http://localhost:8100")
public class TranscripcionController {

    @Autowired
    private WhisperServiceImpl whisperService;

    @Autowired
    private DeepSeekServiceImpl deepSeekService;

    @PostMapping("/audio")
    public String transcribirAudio(@RequestParam("file") MultipartFile file) {
        try {
            // Guardar temporalmente el archivo en disco
            File tempFile = File.createTempFile("audio_", ".mp3");
            file.transferTo(tempFile);

            // Transcribir usando Whisper
            String texto = whisperService.transcribirAudio(tempFile.getAbsolutePath());

            // Borrar el archivo temporal
            tempFile.delete();

            // Devolver el texto transcrito
            log.info("Texto transcrito: " + texto);
            return texto;

        } catch (IOException e) {
            e.printStackTrace();
            return "Error al guardar el archivo: " + e.getMessage();
        }
    }



    @PostMapping("/audio-gasto")
    public ResponseEntity<?> procesarAudioGasto(@RequestParam("audio") MultipartFile audio) {
        try {
            // 1️⃣ Guardamos el archivo temporalmente
            File tempFile = File.createTempFile("audio-", ".mp3");
            audio.transferTo(tempFile);

            // 2️⃣ Transcribimos el audio
            String textoTranscrito = whisperService.transcribirAudio(tempFile.getAbsolutePath());

            // 3️⃣ Pasamos el texto a DeepSeek
            JSONObject resultado = deepSeekService.analizarTranscripcionAudio(textoTranscrito, LocalDate.now());

            return ResponseEntity.ok(resultado.toMap());

        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
