package com.alura.literalura.Services;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

public class ConsultarGemini {

    public static String obtenerTraduccion(String texto){
        String modelo = "gemini-2.5-flash";
        String prompt = "Traduce el siguiente texto al español: " + texto+ ", OJO: Cuando Traduzca simplemente dame el texto plano no incluyas palabras como esta es la traducción.";
        Client cliente = new Client.Builder().apiKey("AIzaSyBDw1sGKS70TgHtrvTP8-v4NAy2uXC_OQk").build();
        try{
            GenerateContentResponse respuesta = cliente.models.generateContent(modelo,prompt,null);
            if (!respuesta.text().isEmpty()){
                return  respuesta.text();
            }
        }catch (Exception e){
            System.err.println("Error al llamar a la API de Gemini para traducción");
        }
        return null;
    }
}
