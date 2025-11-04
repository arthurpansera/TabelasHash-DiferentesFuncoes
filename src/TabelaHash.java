import java.util.ArrayList;
import java.util.List;

public abstract class TabelaHash {

    protected List<String>[] tabela;
    protected int capacidade;
    protected int totalColisoes;
    protected int[] colisoesporPosicao;
    protected int totalElementos;

    public TabelaHash(int capacidade) {
        this.capacidade = capacidade;
        this.tabela = new ArrayList[capacidade];
        this.colisoesporPosicao = new int[capacidade];
        this.totalColisoes = 0;
        this.totalElementos = 0;

        for (int i = 0; i < capacidade; i++) {
            tabela[i] = new ArrayList<>();
        }
    }

    protected abstract int funcaoHash(String chave);

    public void inserir(String chave) {
        int indice = funcaoHash(chave);

        if (!tabela[indice].isEmpty()) {
            totalColisoes++;
            colisoesporPosicao[indice]++;
        }

        tabela[indice].add(chave);
        totalElementos++;
    }

    public boolean buscar(String chave) {
        int indice = funcaoHash(chave);
        return tabela[indice].contains(chave);
    }

    public int getTotalColisoes() {
        return totalColisoes;
    }

    public int[] getColisoesporPosicao() {
        return colisoesporPosicao;
    }

    public int[] getDistribuicao() {
        int[] distribuicao = new int[capacidade];
        for (int i = 0; i < capacidade; i++) {
            distribuicao[i] = tabela[i].size();
        }
        return distribuicao;
    }

    public int getTotalElementos() {
        return totalElementos;
    }

    public double getFatorCarga() {
        return (double) totalElementos / capacidade;
    }

    public void limpar() {
        for (int i = 0; i < capacidade; i++) {
            tabela[i].clear();
            colisoesporPosicao[i] = 0;
        }
        totalColisoes = 0;
        totalElementos = 0;
    }

    public List<String>[] getTabela() {
        return tabela;
    }

    public void setTabela(List<String>[] tabela) {
        this.tabela = tabela;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public void setTotalColisoes(int totalColisoes) {
        this.totalColisoes = totalColisoes;
    }

    public void setColisoesporPosicao(int[] colisoesporPosicao) {
        this.colisoesporPosicao = colisoesporPosicao;
    }

    public void setTotalElementos(int totalElementos) {
        this.totalElementos = totalElementos;
    }

}