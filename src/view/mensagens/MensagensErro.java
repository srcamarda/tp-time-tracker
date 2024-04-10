package view.mensagens;

public enum MensagensErro {
    ERRO_CPF("CPF inválido"),
    ERRO_NOME("Nome inválido"),
    ERRO_USERNAME("Username inválido"),
    ERRO_DATA("Data inválida"),
    ERRO_DATA_FINAL("Data final anterior à data inicial"),
    ERRO_CARGO("Cargo inválido"),
    ERRO_PLANO("Plano inválido"),
    ERRO_UUID("UUID inválido"),
    ERRO_TEXTO("Informação inválida"),
    ERRO_PESSOA("Pessoa não encontrada");

    private final String mensagem;

    MensagensErro(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
}