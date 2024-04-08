package repository;

import dto.PessoaDTO;
import model.Pessoa;
import model.Tag;
import model.Tarefa;
import utility.Conversores;
import utility.Entradas;
import utility.Mensagens;
import utility.Validadores;
import utility.singleton.PessoaSingleton;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
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
                + Tarefa.getPessoaDTO().id() + ";"
                + Tarefa.getDataHoraInicio() + ";"
                + Tarefa.getDataHoraFim();

        arquivo.escreverArquivo(tarefaStr);
        tarefas.add(Tarefa);
    }

    public Tarefa tarefaParser(String linha) {
        String[] valores = linha.split(";");

        try {
            String id = Entradas.obterUUIDValidado(valores[0]);
            String titulo = Entradas.obterTextoValidado(valores[1]);
            String descricao = Entradas.obterTextoValidado(valores[2]);
            Tag tag = Entradas.obterTagValidado(valores[3]);
            String id_pessoa = Entradas.obterUUIDValidado(valores[4]);

            Pessoa pessoa = PessoaSingleton.INSTANCE.getRepositoryPessoa().buscarPessoa(id_pessoa);

            if (Objects.isNull(pessoa)) {
                System.out.println(Mensagens.ERRO_PESSOA.getMensagem());
                return null;
            }

            PessoaDTO pessoaDTO = Conversores.converterParaDTO(pessoa);

            //Caso não tenha data de início, utiliza a atual
            LocalDateTime dataHoraInicio;
            if (!valores[5].isEmpty() && !valores[5].equals("null"))
                dataHoraInicio = Entradas.obterDataValidada(valores[5]);
            else
                dataHoraInicio = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

            //Permite carregar projetos em andamento (sem data de término)
            if (valores.length > 6 && !valores[6].isEmpty() && !valores[6].equals("null")) {
                LocalDateTime dataHoraFim = Entradas.obterDataValidada(valores[6]);

                if (!Validadores.validaDataFinal(dataHoraInicio, dataHoraFim)) {
                    System.out.println(Mensagens.ERRO_DATA_FINAL.getMensagem());
                    return null;
                }

                return new Tarefa.Builder()
                        .id(id)
                        .titulo(titulo)
                        .descricao(descricao)
                        .tag(tag)
                        .pessoaDTO(pessoaDTO)
                        .dataHoraInicio(dataHoraInicio)
                        .dataHoraFim(dataHoraFim)
                        .build();
            }

            return new Tarefa.Builder()
                    .id(id)
                    .titulo(titulo)
                    .descricao(descricao)
                    .tag(tag)
                    .pessoaDTO(pessoaDTO)
                    .dataHoraInicio(dataHoraInicio)
                    .build();

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Tarefa buscarTarefa(String id) {
        return tarefas.stream()
                .filter(tarefa -> tarefa.getId().toString().equals(id))
                .findFirst().orElse(null);
    }

    public List<Tarefa> buscarTarefas(String titulo) {
        return tarefas.stream()
                .filter(tarefa -> tarefa.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Tarefa> buscarTarefas(UUID pessoaId) {
        return tarefas.stream()
                .filter(tarefa -> tarefa.getPessoaDTO().id().equals(pessoaId))
                .collect(Collectors.toList());
    }

    public List<Tarefa> buscarTarefas(PessoaDTO pessoaDTO) {
        return tarefas.stream()
                .filter(tarefa -> tarefa.getPessoaDTO().nome().equalsIgnoreCase(pessoaDTO.nome()))
                .collect(Collectors.toList());
    }
}