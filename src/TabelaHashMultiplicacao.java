public class TabelaHashMultiplicacao extends TabelaHash {

    private static final double A = 0.6180339887;

    public TabelaHashMultiplicacao(int capacidade) {
        super(capacidade);
    }

    @Override
    protected int funcaoHash(String chave) {
        int hashCode = 0;

        for (int i = 0; i < chave.length(); i++) {
            hashCode = 31 * hashCode + chave.charAt(i);
        }

        hashCode = Math.abs(hashCode);

        double produto = hashCode * A;
        double fracao = produto - Math.floor(produto);

        return (int) Math.floor(capacidade * fracao);
    }

    public String getNomeFuncao() {
        return "Método da Multiplicação";
    }

}