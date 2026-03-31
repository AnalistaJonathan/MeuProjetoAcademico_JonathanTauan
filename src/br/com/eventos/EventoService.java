package br.com.eventos;

import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class EventoService {
    public static List<Evento> ordenarPorHorario(List<Evento> eventos) {
        return eventos.stream()
                .sorted(Comparator.comparing(Evento::getHorario))
                .collect(Collectors.toList());
    }

    public static List<Evento> eventosFuturos(List<Evento> eventos) {
        return eventos.stream()
                .filter(Evento::isFuturo)
                .sorted(Comparator.comparing(Evento::getHorario))
                .collect(Collectors.toList());
    }

    public static List<Evento> eventosPassados(List<Evento> eventos) {
        return eventos.stream()
                .filter(Evento::isPassado)
                .sorted(Comparator.comparing(Evento::getHorario))
                .collect(Collectors.toList());
    }

    public static List<Evento> eventosAtuais(List<Evento> eventos) {
        return eventos.stream()
                .filter(Evento::isAtual)
                .collect(Collectors.toList());
    }
}
