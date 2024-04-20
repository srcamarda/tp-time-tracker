package com.dev.timetracker.model;

import com.dev.timetracker.utility.TipoCargo;
import com.dev.timetracker.utility.TipoPlano;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class Pessoa {
    private UUID id;
    private String username;
    private String nome;
    private String cpf;
    private TipoPlano plano;
    private TipoCargo cargo;


    private Pessoa(UUID id, String username, String nome, String cpf, TipoPlano plano, TipoCargo cargo) {
        this.id = id;
        this.username = username;
        this.nome = nome;
        this.cpf = cpf;
        this.plano = plano;
        this.cargo = cargo;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public TipoPlano getPlano() {
        return plano;
    }

    public TipoCargo getCargo() {
        return cargo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pessoa pessoa = (Pessoa) o;
        return Objects.equals(id, pessoa.id) ||
                Objects.equals(username, pessoa.username) ||
                Objects.equals(cpf, pessoa.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, cpf);
    }

    @Override
    public String toString() {
        return "Pessoa:\n" +
                "  Username: '" + username + "',\n" +
                "  Nome: '" + nome + "',\n" +
                "  Cargo: " + cargo + "\n\n";
    }

    public static class Builder {
        private UUID id;
        private String username;
        private String nome;
        private String cpf;
        private TipoPlano plano;
        private TipoCargo cargo;


        public Builder id(String id) {
            this.id = UUID.fromString(id);
            return this;
        }

        public Builder username(String username) {
            this.username = username.toLowerCase();
            return this;
        }

        public Builder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public Builder plano(TipoPlano plano) {
            this.plano = plano;
            return this;
        }

        public Builder cargo(TipoCargo cargo) {
            this.cargo = cargo;
            return this;
        }

        public Builder cpf(String cpf) {
            this.cpf = cpf;
            return this;
        }

        public Pessoa build() {
            if (Objects.isNull(id)) {
                id = UUID.randomUUID();
            }

            if (Objects.isNull(username)) {
                Random random = new Random();
                username = nome.replace(" ", "_").toLowerCase() + random.nextInt(999);
            }

            if (Objects.isNull(plano)) {
                plano = TipoPlano.FREE;
            }

            if (Objects.isNull(cargo)) {
                cargo = TipoCargo.SEM_CARGO;
            }

            return new Pessoa(id, username, nome, cpf, plano, cargo);
        }
    }
}


