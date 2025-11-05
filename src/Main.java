import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    private static final int CAPACIDADE_TABELA = 32;
    private static final String ARQUIVO_NOMES = "female_names.txt";

    public static void main(String[] args) {
        System.out.println("\n===========================");
        System.out.println("COMPARAÇÃO DE TABELAS HASH");
        System.out.println("===========================\n");

        // Lista para armazenar os nomes do arquivo
        List<String> nomes = lerArquivo(ARQUIVO_NOMES);

        if (nomes.isEmpty()) {
            System.out.println("Erro: Nenhum nome encontrado.");
            return;
        }

        System.out.println("Total de nomes lidos: " + nomes.size());

        // Criação das duas tabelas hash com capacidade máxima de 32 posições
        TabelaHashDivisao tabelaDivisao = new TabelaHashDivisao(CAPACIDADE_TABELA);
        TabelaHashMultiplicacao tabelaMultiplicacao = new TabelaHashMultiplicacao(CAPACIDADE_TABELA);

        System.out.println("\n---------------------------");
        System.out.println("\nTABELA 1 (" + tabelaDivisao.getNomeFuncao() + ")");
        testarTabelaHash(tabelaDivisao, nomes);

        System.out.println("\n---------------------------");
        System.out.println("\nTABELA 2 (" + tabelaMultiplicacao.getNomeFuncao() + ")");
        testarTabelaHash(tabelaMultiplicacao, nomes);

        System.out.println("\n---------------------------");
        System.out.println("\nCOMPARAÇÃO FINAL");
        compararTabelas(tabelaDivisao, tabelaMultiplicacao);
    }

    // Lê o arquivo e retorna uma lista com todos os nomes
    private static List<String> lerArquivo(String nomeArquivo) {
        List<String> nomes = new ArrayList<>();

        // Leitura do arquivo com 5000 nomes
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linha = linha.trim();
                if (!linha.isEmpty()) {
                    nomes.add(linha);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
        }

        return nomes;
    }

    // Testa desempenho da tabela hash: inserção, busca e colisões
    private static void testarTabelaHash(TabelaHash tabela, List<String> nomes) {
        
        // Mede tempo de inserção
        long inicioInsercao = System.nanoTime();
        for (String nome : nomes) {
            tabela.inserir(nome);
        }
        long fimInsercao = System.nanoTime();
        double tempoInsercao = (fimInsercao - inicioInsercao) / 1_000_000.0; // em ms

        // Mede tempo de 1000 buscas
        Random random = new Random(42);
        int numBuscas = 1000;
        long inicioBusca = System.nanoTime();
        int encontrados = 0;

        for (int i = 0; i < numBuscas; i++) {
            String nomeBuscar = nomes.get(random.nextInt(nomes.size()));
            if (tabela.buscar(nomeBuscar)) {
                encontrados++;
            }
        }

        long fimBusca = System.nanoTime();
        double tempoBusca = (fimBusca - inicioBusca) / 1_000_000.0; // em ms

        // Exibe resultados de performance
        System.out.println("\nMétricas de Desempenho:");
        System.out.printf("- Tempo de inserção: %.3f ms\n", tempoInsercao);
        System.out.printf("- Tempo de busca (%d buscas): %.3f ms\n", numBuscas, tempoBusca);
        System.out.printf("- Taxa de sucesso nas buscas: %.1f%%\n", (encontrados * 100.0 / numBuscas));

        // Dados sobre colisões
        System.out.println("\nAnálise de Colisões:");
        System.out.println("- Total de colisões: " + tabela.getTotalColisoes());
        System.out.printf("- Fator de carga: %.2f\n", tabela.getFatorCarga());

        // Mostra ocupação de cada posição da tabela
        System.out.println("\nDistribuição das Chaves:");
        int[] distribuicao = tabela.getDistribuicao();
        int[] colisoesPos = tabela.getColisoesporPosicao();

        System.out.println("Posição | Quantidade | Colisões");
        System.out.println("--------|------------|----------");

        for (int i = 0; i < distribuicao.length; i++) {
            if (distribuicao[i] > 0) {
                System.out.printf("%7d | %10d | %8d\n", i, distribuicao[i], colisoesPos[i]);
            }
        }

        // Calcula estatísticas sobre distribuição dos elementos
        int posicoesVazias = 0;
        int maxElementos = 0;
        int minElementos = Integer.MAX_VALUE;

        for (int qtd : distribuicao) {
            if (qtd == 0) {
                posicoesVazias++;
            } else {
                maxElementos = Math.max(maxElementos, qtd);
                minElementos = Math.min(minElementos, qtd);
            }
        }

        System.out.println("\nEstatísticas de Distribuição:");
        System.out.println("- Posições vazias: " + posicoesVazias + " de " + CAPACIDADE_TABELA);
        System.out.println("- Posições ocupadas: " + (CAPACIDADE_TABELA - posicoesVazias) + " de " + CAPACIDADE_TABELA);

        if (minElementos != Integer.MAX_VALUE) {
            System.out.println("- Mínimo de elementos em uma posição: " + minElementos);
        }

        System.out.println("- Máximo de elementos em uma posição: " + maxElementos);
        System.out.printf("- Média de elementos por posição ocupada: %.2f\n", (double) tabela.getTotalElementos() / (CAPACIDADE_TABELA - posicoesVazias));
    }

    // Compara qual tabela hash teve melhor desempenho
    private static void compararTabelas(TabelaHashDivisao tabela1, TabelaHashMultiplicacao tabela2) {
        System.out.println("\nComparação de Colisões:");
        System.out.println("- " + tabela1.getNomeFuncao() + ": " + tabela1.getTotalColisoes() + " colisões");
        System.out.println("- " + tabela2.getNomeFuncao() + ": " + tabela2.getTotalColisoes() + " colisões");

        int diferenca = Math.abs(tabela1.getTotalColisoes() - tabela2.getTotalColisoes());
        String melhor = tabela1.getTotalColisoes() < tabela2.getTotalColisoes() ? "Método da Divisão" : "Método da Multiplicação";

        System.out.println("\n- Melhor desempenho: " + melhor);
        System.out.println("- Diferença: " + diferenca + " colisões");

        // Compara quantidade de posições vazias
        int[] dist1 = tabela1.getDistribuicao();
        int[] dist2 = tabela2.getDistribuicao();

        int vazias1 = 0, vazias2 = 0;

        for (int i = 0; i < CAPACIDADE_TABELA; i++) {
            if (dist1[i] == 0) vazias1++;
            if (dist2[i] == 0) vazias2++;
        }

        System.out.println("\nComparação de Distribuição:");
        System.out.println("- Método da Divisão: " + vazias1 + " posições vazias");
        System.out.println("- Método da Multiplicação: " + vazias2 + " posições vazias");

        String melhorDist = vazias1 < vazias2 ? "Método da Divisão" : "Método da Multiplicação";
        System.out.println("\n- Melhor distribuição: " + melhorDist + " (menos posições vazias)\n");
    }

}