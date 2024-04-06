package mockup;

import dto.ProjetoDTO;
import dto.TarefaDTO;
import dto.PessoaDTO;
import model.Pessoa;
import model.Projeto;
import model.Tag;
import model.Tarefa;
import utility.TipoCargo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Mockup {
    public static List<PessoaDTO> pessoaDTOList = new ArrayList<>();
    public static List<TarefaDTO> tarefaDTOList = new ArrayList<>();
    public static List<ProjetoDTO> projetoDTOList = new ArrayList<>();

    public static void init() {
        criarPessoas();
        criarTarefas();
        criarTarefas();
    }

    private static void criarPessoas() {
        Pessoa pessoa = new Pessoa.Builder().nome("Paulo").cargo(TipoCargo.JUNIOR).build();
        Pessoa pessoa1 = new Pessoa.Builder().nome("Pedro").cargo(TipoCargo.JUNIOR).build();

        pessoaDTOList.add(new dto.PessoaDTO(pessoa.getId(), pessoa.getUsername(), pessoa.getNome(), pessoa.getPlano(), pessoa.getCargo()));
        pessoaDTOList.add(new dto.PessoaDTO(pessoa1.getId(), pessoa1.getUsername(), pessoa1.getNome(), pessoa1.getPlano(), pessoa1.getCargo()));
    }

    private static void criarTarefas() {
        Tarefa tarefa = new Tarefa.Builder().titulo("Estruturar projeto").pessoaDTO(pessoaDTOList.get(0)).build();
        Tarefa tarefa1 = new Tarefa.Builder().titulo("Testar classes").pessoaDTO(pessoaDTOList.get(1)).build();

        tarefaDTOList.add(new TarefaDTO(tarefa.getId(), tarefa.getTitulo(), tarefa.getDescricao(), tarefa.getPessoaDTO(), tarefa.getDataHoraInicio(), tarefa.getDataHoraFim(), tarefa.getTag()));
        tarefaDTOList.add(new TarefaDTO(tarefa1.getId(), tarefa1.getTitulo(), tarefa1.getDescricao(), tarefa1.getPessoaDTO(), tarefa1.getDataHoraInicio(), tarefa1.getDataHoraFim(), tarefa1.getTag()));
    }

    private static void criarProjetos() {
        Projeto projeto = new Projeto.Builder()
                .titulo("TP Time Tracker")
                .descricao("Projeto Ada")
                .tags(List.of(Tag.IMPORTANTE))
                .build();
        ProjetoDTO projetoDTO = new ProjetoDTO(
                projeto.getId(),
                projeto.getTitulo(),
                projeto.getDescricao(),
                projeto.getPessoasDTO(),
                projeto.getDataHoraInicio(),
                projeto.getDataHoraFim(),
                projeto.getTags(),
                projeto.getTarefasDTO());

        Projeto projeto1 = new Projeto.Builder()
                .titulo("Projeto final")
                .descricao("Projeto Final Ada")
                .tags(List.of(Tag.URGENTE))
                .tarefasDTO(List.of(tarefaDTOList.get(0), tarefaDTOList.get(1)))
                .dataHoraInicio(LocalDateTime.of(2024, 4, 1, 12, 30))
                .pessoasDTO(List.of(pessoaDTOList.get(0), pessoaDTOList.get(1)))
                .build();
        ProjetoDTO projetoDTO1 = new ProjetoDTO(
                projeto1.getId(),
                projeto1.getTitulo(),
                projeto1.getDescricao(),
                projeto1.getPessoasDTO(),
                projeto1.getDataHoraInicio(),
                projeto1.getDataHoraFim(),
                projeto1.getTags(),
                projeto1.getTarefasDTO());

        projetoDTOList.add(projetoDTO);
        projetoDTOList.add(projetoDTO1);
    }
}
