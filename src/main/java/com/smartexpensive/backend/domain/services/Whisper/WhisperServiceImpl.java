package com.smartexpensive.backend.domain.services.Whisper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;


@Service
@Slf4j
public class WhisperServiceImpl {

    public String transcribirAudio(String rutaAudio) {
        try {
            // Comando para ejecutar el script
            ProcessBuilder builder = new ProcessBuilder(
                    "C:\\Users\\Pedro J\\AppData\\Local\\Programs\\Python\\Python313\\python.exe",
                    "-W", "ignore",
                    "src/main/resources/Whisper/transcribir.py",
                    rutaAudio
            );

            builder.redirectErrorStream(true);
            Process process = builder.start();

            // Leer la salida del script
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                return output.toString().trim();
            } else {
                System.err.println("Error al ejecutar Whisper: " + output);
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
