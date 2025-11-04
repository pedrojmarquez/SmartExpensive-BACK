package com.smartexpensive.backend.domain.services.DeepSeek;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

@Slf4j
@Service
public class DeepSeekServiceImpl {

    // ⚠️ Recomendación: Guarda tu API key en application.properties
    private final String apiKey = "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";

    private final String endpointUrl = "https://openrouter.ai/api/v1/chat/completions";

    public JSONObject analizarTexto(String texto) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        String body = """
                {
                  "model": "deepseek/deepseek-chat-v3.1:free",
                  "messages": [
                    {
                      "role": "user",
                      "content": "Analiza el siguiente texto extraído de un ticket OCR:\\n\\n%s\\n\\nDevuélveme únicamente un JSON válido con los siguientes campos:\\n\\n{\\n  \\"nombre_comercio\\": \\"\\",\\n  \\"total\\": 0.0,\\n  \\"categoria_gasto\\": \\"\\",\\n  \\"descripcion_gasto\\": \\"\\",\\n  \\"fecha_gasto\\": \\"YYYY-MM-DD\\"\\n}\\n\\n📋 Instrucciones:\\n\\n1️⃣ nombre_comercio → identifica el nombre del establecimiento (por ejemplo: Mercadona, Lefties, Repsol, Burger King, etc.). Si no aparece, déjalo vacío.\\n\\n2️⃣ total → identifica el importe que realmente pagó el cliente. Es el importe final que aparece junto a palabras como “total”, “importe total”, “total a pagar”, “pago tarjeta”, “efectivo” o “autorización”. Ignora cualquier subtotal, descuento, o importe intermedio. Si aparecen varios importes, elige el que más claramente representa el pago final.\\n\\n3️⃣ categoria_gasto → dedúcela según el tipo de comercio o los productos: supermercados o alimentación → “supermercado”, tiendas de ropa → “ropa”, gasolineras → “transporte”, restaurantes o comida → “restauración”, electrónica o tecnología → “tecnología”, farmacia o productos de higiene → “salud”, si no es claro → “otros”.\\n\\n4️⃣ descripcion_gasto → genera una breve descripción como “Compra en Mercadona”, “Compra de ropa”, “Cena en restaurante”, “Repostaje de gasolina”.\\n\\n5️⃣ fecha_gasto → convierte cualquier fecha del ticket a formato ISO (YYYY-MM-DD). Si no se encuentra una fecha válida, deja el campo vacío.\\n\\n📌 Importante:\\n- No devuelvas texto adicional ni explicaciones, solo el JSON.\\n- Si hay varios importes posibles, selecciona el que representa el pago final.\\n- Si el OCR tiene errores o faltan datos, deja los campos vacíos o con el valor más probable."
                                                                       }
                  ],
                  "temperature": 0.0
                }
                """.formatted(texto.replace("\"", "\\\""));


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpointUrl))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String rawResponse = response.body();
        try {
            JSONObject jsonResponse = new JSONObject(rawResponse);

            // Acceder al mensaje del modelo
            JSONArray choices = jsonResponse.getJSONArray("choices");
            JSONObject message = choices.getJSONObject(0).getJSONObject("message");

            String content = message.getString("content");

            // Limpiar el contenido del bloque ```json ... ```
            String cleaned = content
                    .replaceAll("```json", "")
                    .replaceAll("```", "")
                    .trim();

            // Convertir el contenido limpio en un objeto JSON
            JSONObject data = new JSONObject(cleaned);

            // Devolver el JSON final procesado y log en consola

            log.info("RESPUESTA DEEPSEEK" + cleaned);
            return data;

        } catch (Exception e) {
            // Si falla el parseo, devolver el error y la respuesta completa
            JSONObject errorJson = new JSONObject();
            errorJson.put("error", "No se pudo parsear la respuesta del modelo");
            errorJson.put("detalle", e.getMessage());
            errorJson.put("respuesta_raw", rawResponse);
            return errorJson;
        }
    }



    public JSONObject analizarTranscripcionAudio(String texto, LocalDate fechaReferencia) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        // Convertir la fecha de referencia a formato ISO
        String fechaIso = fechaReferencia.toString(); // YYYY-MM-DD

        String body = """
        {
          "model": "deepseek/deepseek-chat-v3.1:free",
          "messages": [
            {
              "role": "user",
              "content": "Analiza la siguiente transcripción de voz, que contiene un resumen o descripción hablada de un gasto. Devuélveme únicamente un JSON válido con los siguientes campos:\\n\\n{\\n  \\"nombre_comercio\\": \\"\\",\\n  \\"total\\": 0.0,\\n  \\"categoria_gasto\\": \\"\\",\\n  \\"descripcion_gasto\\": \\"\\",\\n  \\"fecha_gasto\\": \\"YYYY-MM-DD\\"\\n}\\n\\n📋 Instrucciones:\\n\\n1️⃣ nombre_comercio → intenta identificar si la persona menciona un comercio o empresa (por ejemplo: Mercadona, Repsol, Amazon, etc.) si no menciona ninguno pon desconocido.\\n2️⃣ total → busca números que parezcan cantidades de dinero (por ejemplo: 25 euros, 15.50, 30 con tarjeta, etc.).\\n3️⃣ categoria_gasto → dedúcela por el tipo de compra (supermercado, ropa, transporte, restauración, tecnología, salud, otros).\\n4️⃣ descripcion_gasto → genera una breve descripción general, como “Compra de comida”, “Repostaje gasolina”, “Cena en restaurante”, etc.\\n5️⃣ fecha_gasto → si la transcripción menciona una fecha, extráela y conviértela a formato ISO (YYYY-MM-DD). Si menciona 'hoy', 'ayer', 'mañana', 'anteayer', utiliza la fecha de referencia proporcionada: %s.\\n\\n📌 Importante:\\n- El texto proviene de una transcripción de audio, por lo tanto puede tener errores o palabras sin sentido; interpreta lo más probable.\\n- No devuelvas nada que no sea el JSON solicitado.\\n\\nTranscripción:\\n%s"
            }
          ],
          "temperature": 0.0
        }
        """.formatted(fechaIso, texto.replace("\"", "\\\""));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpointUrl))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String rawResponse = response.body();

        try {
            JSONObject jsonResponse = new JSONObject(rawResponse);
            JSONArray choices = jsonResponse.getJSONArray("choices");
            JSONObject message = choices.getJSONObject(0).getJSONObject("message");

            String content = message.getString("content");

            String cleaned = content
                    .replaceAll("```json", "")
                    .replaceAll("```", "")
                    .trim();

            JSONObject data = new JSONObject(cleaned);

            log.info("RESPUESTA DEEPSEEK (AUDIO): " + cleaned);
            return data;

        } catch (Exception e) {
            JSONObject errorJson = new JSONObject();
            errorJson.put("error", "No se pudo parsear la respuesta del modelo");
            errorJson.put("detalle", e.getMessage());
            errorJson.put("respuesta_raw", rawResponse);
            return errorJson;
        }
    }


}
