package br.com.eventos;

import java.time.LocalDateTime;

public class Evento {
    private String nome;
    private String endereco;
    private String categoria;
    private LocalDateTime horario;
    private String descricao;

    public Evento(String nome, String endereco, String categoria, LocalDateTime horario, String descricao) {
        this.nome = nome;
        this.endereco = endereco;
        this.categoria = categoria;
        this.horario = horario;
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getCategoria() {
        return categoria;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setHorario(LocalDateTime horario) {
        this.horario = horario;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "Nome: " + nome +
               " | Endereco: " + endereco +
               " | Categoria: " + categoria +
               " | Horario: " + horario +
               " | Descricao: " + descricao;
    }
    
    public boolean isPassado() {
        return this.getHorario().isBefore(LocalDateTime.now());
    }

    public boolean isAtual() {
        LocalDateTime agora = LocalDateTime.now();
        return this.getHorario().getYear() == agora.getYear() &&
               this.getHorario().getMonthValue() == agora.getMonthValue() &&
               this.getHorario().getDayOfMonth() == agora.getDayOfMonth() &&
               this.getHorario().getHour() == agora.getHour();
    }

    public boolean isFuturo() {
        return this.getHorario().isAfter(LocalDateTime.now());
    }

    
}
