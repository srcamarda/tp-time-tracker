package model;

import utility.TipoPlano;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Projeto {
    private UUID id;
    private String titulo;
    private String descricao;
    private List<Pessoa> pessoas;
    private TipoPlano plano;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;
    private List<Tag> tags;
    private List<Tarefa> tarefas;

    private Projeto(UUID id, String titulo, String descricao, List<Pessoa> pessoas, TipoPlano plano, LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim, List<Tag> tags, List<Tarefa> tarefas) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.pessoas = pessoas;
        this.plano = plano;
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraFim = dataHoraFim;
        this.tags = tags;
        this.tarefas = tarefas;
    }

    public UUID getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public List<Pessoa> getPessoas() {
        return pessoas;
    }

    public TipoPlano getPlano() {
        return plano;
    }

    public LocalDateTime getDataHoraInicio() {
        return dataHoraInicio;
    }

    public LocalDateTime getDataHoraFim() {
        return dataHoraFim;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Projeto projeto = (Projeto) o;
        return Objects.equals(id, projeto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Projeto{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", pessoas=" + pessoas +
                ", plano=" + plano +
                ", dataHoraInicio=" + dataHoraInicio +
                ", dataHoraFim=" + dataHoraFim +
                ", tags=" + tags +
                ", tarefas=" + tarefas +
                '}';
    }

    public static class Builder {
        private UUID id;
        private String titulo;
        private String descricao;
        private List<Pessoa> pessoas;
        private TipoPlano plano;
        private LocalDateTime dataHoraInicio;
        private LocalDateTime dataHoraFim;
        private List<Tag> tags;
        private List<Tarefa> tarefas;

        public Builder id(String id) {
            this.id = UUID.fromString(id);
            return this;
        }

        public Builder titulo(String titulo) {
            this.titulo = titulo;
            return this;
        }

        public Builder descricao(String descricao) {
            this.descricao = descricao;
            return this;
        }

        public Builder pessoas(List<Pessoa> pessoas) {
            this.pessoas = pessoas;
            return this;
        }

        public Builder plano(TipoPlano plano) {
            this.plano = plano;
            return this;
        }

        public Builder dataHoraInicio(LocalDateTime dataHoraInicio) {
            this.dataHoraInicio = dataHoraInicio;
            return this;
        }

        public Builder dataHoraFim(LocalDateTime dataHoraFim) {
            this.dataHoraFim = dataHoraFim;
            return this;
        }

        public Builder tags(List<Tag> tags) {
            this.tags = tags;
            return this;
        }

        public Builder tarefas(List<Tarefa> tarefas) {
            this.tarefas = tarefas;
            return this;
        }

        public Projeto build() {
            if (Objects.isNull(id)) {
                this.id = UUID.randomUUID();
            }

            return new Projeto(id, titulo, descricao, pessoas, plano, dataHoraInicio, dataHoraFim, tags, tarefas);
        }
    }
}