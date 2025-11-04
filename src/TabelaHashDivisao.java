public class TabelaHashDivisao extends TabelaHash {

    public TabelaHashDivisao(int capacidade) {
        super(capacidade);
    }

    @Override
    protected int funcaoHash(String chave) {
        int hashCode = 0;

        for (int i = 0; i < chave.length(); i++) {
            hashCode += chave.charAt(i);
        }

        return Math.abs(hashCode) % capacidade;
    }

    public String getNomeFuncao() {
        return "Método da Divisão";
    }

}