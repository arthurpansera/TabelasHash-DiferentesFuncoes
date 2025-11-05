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

    public int getTotalColisoes() {
        return totalColisoes;
    }

    public void setTotalColisoes(int totalColisoes) {
        this.totalColisoes = totalColisoes;
    }

    public int[] getColisoesporPosicao() {
        return colisoesporPosicao;
    }

    public void setColisoesporPosicao(int[] colisoesporPosicao) {
        this.colisoesporPosicao = colisoesporPosicao;
    }

    public int getTotalElementos() {
        return totalElementos;
    }

    public void setTotalElementos(int totalElementos) {
        this.totalElementos = totalElementos;
    }

    // Função hash implementada pelas subclasses
    protected abstract int funcaoHash(String chave);

    // Retorna o nome da função
    public abstract String getNomeFuncao();

    // Insere uma chave na tabela
    public void inserir(String chave) {
        int indice = funcaoHash(chave);

        if (!tabela[indice].isEmpty()) { // houve colisão
            totalColisoes++;
            colisoesporPosicao[indice]++;
        }

        tabela[indice].add(chave);
        totalElementos++;
    }

    // Busca a chave na tabela
    public boolean buscar(String chave) {
        int indice = funcaoHash(chave);
        return tabela[indice].contains(chave);
    }

    // Quantidade de elementos em cada posição
    public int[] getDistribuicao() {
        int[] distribuicao = new int[capacidade];

        for (int i = 0; i < capacidade; i++) {
            distribuicao[i] = tabela[i].size();
        }

        return distribuicao;
    }

    // Fator de carga = elementos / capacidade
    public double getFatorCarga() {
        return (double) totalElementos / capacidade;
    }

    // Limpa a tabela
    public void limpar() {
        for (int i = 0; i < capacidade; i++) {
            tabela[i].clear();
            colisoesporPosicao[i] = 0;
        }
        
        totalColisoes = 0;
        totalElementos = 0;
    }

}