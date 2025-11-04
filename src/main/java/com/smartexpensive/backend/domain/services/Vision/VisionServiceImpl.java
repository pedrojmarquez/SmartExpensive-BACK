package com.smartexpensive.backend.domain.services.Vision;

import org.springframework.stereotype.Service;
import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.vision.v1.ImageAnnotatorSettings;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;

import java.io.File;


@Service
public class VisionServiceImpl {

    private final ImageAnnotatorClient client;

    public VisionServiceImpl() throws IOException {
        // Carga el JSON de src/main/resources/credential/vision-key.json
        InputStream credentialsStream = getClass().getClassLoader()
                .getResourceAsStream("credentials/vision-key.json");

        if (credentialsStream == null) {
            throw new RuntimeException("No se encontró el archivo de credenciales");
        }

        GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream);
        ImageAnnotatorSettings settings = ImageAnnotatorSettings.newBuilder()
                .setCredentialsProvider(() -> credentials)
                .build();

        client = ImageAnnotatorClient.create(settings);
    }


    /* VISION*/
//    public String detectarTexto(Path imagePath) throws IOException {
//        List<AnnotateImageRequest> requests = new ArrayList<>();
//
//        ByteString imgBytes = ByteString.readFrom(Files.newInputStream(imagePath));
//
//        Image img = Image.newBuilder().setContent(imgBytes).build();
//        Feature feat = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
//        AnnotateImageRequest request =
//                AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
//        requests.add(request);
//
//        BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
//        List<AnnotateImageResponse> responses = response.getResponsesList();
//
//        StringBuilder textBuilder = new StringBuilder();
//        for (AnnotateImageResponse res : responses) {
//            if (res.hasError()) {
//                return "Error: " + res.getError().getMessage();
//            }
//            textBuilder.append(res.getFullTextAnnotation().getText());
//        }
//
//        return textBuilder.toString();
//    }



    /* TESSERACT*/
    public String detectarTexto(File imageFile) {
        ITesseract tesseract = new Tesseract();

        // Ruta a los datos de Tesseract dentro de resources
        tesseract.setDatapath("src/main/resources/tessdata");
        tesseract.setLanguage("spa"); // español

        try {
            // Ejecuta OCR en la imagen
            return tesseract.doOCR(imageFile);
//            String texto = tesseract.doOCR(imageFile);
//            String textoLimpio = texto
//                    .replaceAll("[^\\x00-\\x7F]", " ") // eliminar símbolos raros
//                    .replaceAll("\\s+", " ")           // colapsar espacios
//                    .trim();
//
//            return textoLimpio;
        } catch (TesseractException e) {
            e.printStackTrace();
            return "Error al leer la imagen: " + e.getMessage();
        }
    }
}
