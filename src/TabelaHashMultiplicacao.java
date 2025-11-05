public class TabelaHashMultiplicacao extends TabelaHash {

    private static final double A = 0.6180339887;

    public TabelaHashMultiplicacao(int capacidade) {
        super(capacidade);
    }

    @Override
    protected int funcaoHash(String chave) {
        int hashCode = 0;

        // Gera um valor numérico baseado nos caracteres da string
        for (int i = 0; i < chave.length(); i++) {
            hashCode = 31 * hashCode + chave.charAt(i);
        }

        // Deixa o valor positivo
        hashCode = Math.abs(hashCode);

        // Multiplica o valor pelo fator A e pega apenas a parte decimal
        double produto = hashCode * A;
        double fracao = produto - Math.floor(produto);

        // Calcula o índice na tabela (retorna um índice entre 0 e capacidade-1)
        return (int) Math.floor(capacidade * fracao);
    }

    @Override
    public String getNomeFuncao() {
        return "Método da Multiplicação";
    }

}