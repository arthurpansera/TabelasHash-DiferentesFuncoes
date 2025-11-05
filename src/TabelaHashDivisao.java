public class TabelaHashDivisao extends TabelaHash {

    public TabelaHashDivisao(int capacidade) {
        super(capacidade);
    }

    @Override
    protected int funcaoHash(String chave) {
        int hashCode = 0;

        // Gera um valor numérico baseado nos caracteres da string
        for (int i = 0; i < chave.length(); i++) {
            hashCode = 31 * hashCode + chave.charAt(i);
        }

        // Deixa o valor positivo e o reduz para caber dentro da tabela (retorna um índice entre 0 e capacidade-1)
        return Math.abs(hashCode) % capacidade;
    }

    @Override
    public String getNomeFuncao() {
        return "Método da Divisão";
    }

}