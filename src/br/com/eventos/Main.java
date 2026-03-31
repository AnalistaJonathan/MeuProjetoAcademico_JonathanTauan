package br.com.eventos;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Evento> eventos = ArquivoEventos.carregar();
        List<Evento> confirmados = new ArrayList<>();

        System.out.println("=== SISTEMA DE EVENTOS ===");

        System.out.print("Digite seu nome: ");
        String nome = scanner.nextLine();

        System.out.print("Digite seu email: ");
        String email = scanner.nextLine();

        System.out.print("Digite sua cidade: ");
        String cidade = scanner.nextLine();

        Usuario usuario = new Usuario(nome, email, cidade);

        while (true) {
            System.out.println("\n=== MENU ===");
            System.out.println("1 - Cadastrar evento");
            System.out.println("2 - Listar todos os eventos ordenados");
            System.out.println("3 - Confirmar participacao");
            System.out.println("4 - Ver meus eventos confirmados");
            System.out.println("5 - Cancelar participacao");
            System.out.println("6 - Ver eventos que ja aconteceram");
            System.out.println("7 - Ver eventos que estao acontecendo agora");
            System.out.println("8 - Salvar e sair");
            System.out.print("Escolha uma opcao: ");

            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    cadastrarEvento(scanner, eventos);
                    break;
                case "2":
                    listarEventos(eventos);
                    break;
                case "3":
                    confirmarParticipacao(scanner, eventos, confirmados);
                    break;
                case "4":
                    listarConfirmados(confirmados);
                    break;
                case "5":
                    cancelarParticipacao(scanner, confirmados);
                    break;
                case "6":
                    listarEventosPassados(eventos);
                    break;
                case "7":
                    listarEventosAtuais(eventos);
                    break;
                case "8":
                    ArquivoEventos.salvar(eventos);
                    System.out.println("Eventos salvos em events.data");
                    System.out.println("Saindo...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opcao invalida.");
            }
        }
    }

    private static void cadastrarEvento(Scanner scanner, List<Evento> eventos) {
        System.out.println("\n=== CADASTRAR EVENTO ===");

        System.out.print("Nome do evento: ");
        String nome = scanner.nextLine();

        System.out.print("Endereco: ");
        String endereco = scanner.nextLine();

        System.out.print("Categoria (Festa, Esporte, Show, Cultura): ");
        String categoria = scanner.nextLine();

        System.out.print("Horario (formato 2026-03-30T20:00): ");
        String horarioTexto = scanner.nextLine();

        System.out.print("Descricao: ");
        String descricao = scanner.nextLine();

        try {
            LocalDateTime horario = LocalDateTime.parse(horarioTexto);
            Evento evento = new Evento(nome, endereco, categoria, horario, descricao);
            eventos.add(evento);
            System.out.println("Evento cadastrado com sucesso!");
        } catch (DateTimeParseException e) {
            System.out.println("Horario invalido. Use o formato 2026-03-30T20:00");
        }
    }

    private static void listarEventos(List<Evento> eventos) {
        System.out.println("\n=== LISTA DE EVENTOS ORDENADOS POR HORARIO ===");
        if (eventos.isEmpty()) {
            System.out.println("Nenhum evento cadastrado.");
            return;
        }

        // Ordena pelos mais próximos primeiro
        eventos.sort((e1, e2) -> e1.getHorario().compareTo(e2.getHorario()));

        for (int i = 0; i < eventos.size(); i++) {
            System.out.println((i + 1) + " - " + eventos.get(i));
        }
    }

    private static void confirmarParticipacao(Scanner scanner, List<Evento> eventos, List<Evento> confirmados) {
        System.out.println("\n=== CONFIRMAR PARTICIPACAO ===");
        if (eventos.isEmpty()) {
            System.out.println("Nao ha eventos cadastrados.");
            return;
        }

        listarEventos(eventos);
        System.out.print("Digite o numero do evento que deseja participar: ");

        try {
            int escolha = Integer.parseInt(scanner.nextLine());
            if (escolha < 1 || escolha > eventos.size()) {
                System.out.println("Numero invalido.");
                return;
            }

            Evento evento = eventos.get(escolha - 1);
            if (!confirmados.contains(evento)) {
                confirmados.add(evento);
                System.out.println("Participacao confirmada!");
            } else {
                System.out.println("Voce ja confirmou esse evento.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Digite um numero valido.");
        }
    }

    private static void listarConfirmados(List<Evento> confirmados) {
        System.out.println("\n=== MEUS EVENTOS CONFIRMADOS ===");
        if (confirmados.isEmpty()) {
            System.out.println("Nenhum evento confirmado.");
            return;
        }

        for (int i = 0; i < confirmados.size(); i++) {
            System.out.println((i + 1) + " - " + confirmados.get(i));
        }
    }

    private static void cancelarParticipacao(Scanner scanner, List<Evento> confirmados) {
        System.out.println("\n=== CANCELAR PARTICIPACAO ===");
        if (confirmados.isEmpty()) {
            System.out.println("Voce nao tem eventos confirmados.");
            return;
        }

        listarConfirmados(confirmados);
        System.out.print("Digite o numero do evento para cancelar: ");

        try {
            int escolha = Integer.parseInt(scanner.nextLine());
            if (escolha < 1 || escolha > confirmados.size()) {
                System.out.println("Numero invalido.");
                return;
            }

            confirmados.remove(escolha - 1);
            System.out.println("Participacao cancelada.");
        } catch (NumberFormatException e) {
            System.out.println("Digite um numero valido.");
        }
    }

    private static void listarEventosPassados(List<Evento> eventos) {
        System.out.println("\n=== EVENTOS PASSADOS ===");
        boolean encontrou = false;

        for (Evento evento : eventos) {
            if (evento.getHorario().isBefore(LocalDateTime.now())) {
                System.out.println("- " + evento);
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Nao ha eventos passados.");
        }
    }

    private static void listarEventosAtuais(List<Evento> eventos) {
        System.out.println("\n=== EVENTOS ACONTECENDO AGORA ===");
        LocalDateTime agora = LocalDateTime.now();
        boolean encontrou = false;

        for (Evento evento : eventos) {
            if (evento.getHorario().getYear() == agora.getYear() &&
                evento.getHorario().getMonthValue() == agora.getMonthValue() &&
                evento.getHorario().getDayOfMonth() == agora.getDayOfMonth() &&
                evento.getHorario().getHour() == agora.getHour()) {
                System.out.println("- " + evento);
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Nenhum evento acontecendo agora.");
        }
    }
}
