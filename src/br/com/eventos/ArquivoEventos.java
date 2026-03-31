package br.com.eventos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ArquivoEventos {
    private static final String ARQUIVO = "events.data";

    public static void salvar(List<Evento> eventos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (Evento evento : eventos) {
                writer.write(evento.getNome() + ";" +
                             evento.getEndereco() + ";" +
                             evento.getCategoria() + ";" +
                             evento.getHorario().toString() + ";" +
                             evento.getDescricao());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar eventos: " + e.getMessage());
        }
    }

    public static List<Evento> carregar() {
        List<Evento> eventos = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;

            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length == 5) {
                    Evento evento = new Evento(
                            partes[0],
                            partes[1],
                            partes[2],
                            LocalDateTime.parse(partes[3]),
                            partes[4]
                    );
                    eventos.add(evento);
                }
            }
        } catch (IOException e) {
        }

        return eventos;
    }
}
