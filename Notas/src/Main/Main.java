package Main;

import Base.Dados;
import Base.Nota;
import Injecao.Gson;
import Injecao.Injecao;
import Injecao.Persiste;
import Injecao.Postgres;
import Injecao.Xml;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author eduardo
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Injecao injecao;

        while (true) {
            exibirMenu();

            int escolha = scanner.nextInt();
            scanner.nextLine();

            switch (escolha) {
                case 1:
                    injecao = new Xml();
                    executarOperacoes(scanner, injecao);
                    break;
                case 2:
                    injecao = new Gson();
                    executarOperacoes(scanner, injecao);
                    break;
                case 3:
                    injecao = new Postgres();
                    executarOperacoes(scanner, injecao);
                    break;
                case 4:
                    System.out.println("Saindo do programa. Até mais!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Escolha inválida. Tente novamente.");
            }
        }
    }

    private static void exibirMenu() {
        System.out.println("\n### MENU ###");
        System.out.println("1. Operações com XML");
        System.out.println("2. Operações com Gson");
        System.out.println("3. Operações com Postgres");
        System.out.println("4. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void executarOperacoes(Scanner scanner, Injecao injecao) {
        System.out.print("Informe o nome do arquivo para leitura/gravação: ");
        String nomeArquivo = scanner.nextLine();

        Dados dados = new Dados();
        Persiste persiste = new Persiste(injecao);

        while (true) {
            exibirSubMenu();

            int escolha = scanner.nextInt();
            scanner.nextLine();

            switch (escolha) {
                case 1:
                    adicionarNota(scanner, dados);
                    persiste.gravar(nomeArquivo, dados);
                    exibirNotas(dados);
                    break;
                case 2:
                    exibirNotas(dados);
                    removerNota(scanner, dados);
                    persiste.gravar(nomeArquivo, dados);
                    break;
                case 3:
                    editarNota(scanner, dados);
                    persiste.gravar(nomeArquivo, dados);
                    exibirNotas(dados);
                    break;
                case 4:
                    Dados dadosLidos = persiste.ler(nomeArquivo);
                    if (dadosLidos != null) {
                        exibirNotas(dadosLidos);
                    } else {
                        System.out.println("Falha ao ler os dados.");
                    }
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Escolha inválida. Tente novamente.");
            }
        }
    }

    private static void exibirSubMenu() {
        System.out.println("\n### SUBMENU ###");
        System.out.println("1. Adicionar Nota");
        System.out.println("2. Remover Nota");
        System.out.println("3. Editar Nota");
        System.out.println("4. Ler Dados");
        System.out.println("5. Voltar ao Menu Principal");
        System.out.print("Escolha uma opção: ");
    }

    private static void adicionarNota(Scanner scanner, Dados dados) {
        Nota novaNota = new Nota();

        System.out.print("Informe o título da nota: ");
        novaNota.setTitulo(scanner.nextLine());

        System.out.print("Informe o texto da nota: ");
        novaNota.setTexto(scanner.nextLine());

        novaNota.setDataCriacao(new Date());

        dados.addNota(novaNota);
        System.out.println("Nota adicionada com sucesso!");
    }

    private static void removerNota(Scanner scanner, Dados dados) {
        System.out.print("Informe o título da nota a ser removida: ");
        String titulo = scanner.nextLine();

        List<Nota> notas = dados.getNotas();
        for (Nota nota : notas) {
            if (nota.getTitulo().equalsIgnoreCase(titulo)) {
                dados.removerNota(nota);
                System.out.println("Nota removida com sucesso!");
                return;
            }
        }

        System.out.println("Nota não encontrada.");
    }

    private static void editarNota(Scanner scanner, Dados dados) {
        System.out.print("Informe o título da nota a ser editada: ");
        String titulo = scanner.nextLine();

        List<Nota> notas = dados.getNotas();
        for (Nota nota : notas) {
            if (nota.getTitulo().equalsIgnoreCase(titulo)) {
                System.out.print("Informe o novo título da nota: ");
                nota.setTitulo(scanner.nextLine());

                System.out.print("Informe o novo texto da nota: ");
                nota.setTexto(scanner.nextLine());

                System.out.println("Nota editada com sucesso!");
                return;
            }
        }

        System.out.println("Nota não encontrada.");
    }

    private static void exibirNotas(Dados dados) {
        System.out.println("\n### NOTAS ###");
        for (Nota nota : dados.getNotas()) {
            System.out.println(nota);
        }
    }
}

