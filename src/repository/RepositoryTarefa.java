package repository;

import dto.PessoaDTO;
import model.Tag;
import model.Tarefa;
import utility.Conversores;
import utility.Entradas;
import utility.Validadores;
import utility.singleton.PessoaSingleton;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RepositoryTarefa {
    ArquivoUtil arquivo;
    List<Tarefa> tarefas;

    public RepositoryTarefa(ArquivoPaths path) {
        arquivo = new ArquivoUtil(path);
        tarefas = carregarTarefas();
    }

    public List<Tarefa> carregarTarefas() {
        List<String> tarefasStr = arquivo.lerArquivo();
        List<Tarefa> tarefas = new ArrayList<>();
        tarefasStr.stream().skip(1)
                .map((this::tarefaParser))
                .filter(Objects::nonNull)
                .forEach(tarefas::add);

        return tarefas;
    }

    public void salvarTarefas(List<Tarefa> tarefas) {
        tarefas.forEach(this::salvarTarefa);
    }

    public void salvarTarefa(Tarefa Tarefa) {
        String tarefaStr = Tarefa.getId() + ";"
                + Tarefa.getTitulo() + ";"
                + Tarefa.getDescricao() + ";"
                + Tarefa.getTag() + ";"
                + Tarefa.getPessoaDTO() + ";"
                + Tarefa.getDataHoraInicio() + ";"
                + Tarefa.getDataHoraFim();

        arquivo.escreverArquivo(tarefaStr);
    }

    public Tarefa tarefaParser(String linha) {
        String[] valores = linha.split(";");

        try {
            PessoaDTO pessoaDTO = Conversores.converterParaDTO(
                    PessoaSingleton
                            .INSTANCE
                            .getRepositoryPessoa()
                            .buscarPessoa(valores[4]));

            String id = Entradas.obterUUIDValidado(valores[0]);
            String titulo = Entradas.obterTextoValidado(valores[1]);
            String descricao = Entradas.obterTextoValidado(valores[2]);
            Tag tag = Entradas.obterTagValidado(valores[3]);
            LocalDateTime dataHoraInicio = Entradas.obterDataValidada(valores[5]);
            LocalDateTime dataHoraFim = Entradas.obterDataValidada(valores[6]);

            if (!Validadores.validaDataFinal(dataHoraInicio, dataHoraFim))
                return null;

            return new Tarefa.Builder()
                    .id(id)
                    .titulo(titulo)
                    .descricao(descricao)
                    .tag(tag)
                    .pessoaDTO(pessoaDTO)
                    .dataHoraInicio(dataHoraInicio)
                    .dataHoraFim(dataHoraFim)
                    .build();
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public Tarefa buscarTarefa(String id) {
        return tarefas.stream()
                .filter(tarefa -> tarefa.getId().toString().equals(id))
                .findFirst().orElse(null);
    }

    public List<Tarefa> buscarTarefasComTitulo(String titulo) {
        return tarefas.stream()
                .filter(tarefa -> tarefa.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                .collect(Collectors.toList());
    }
}