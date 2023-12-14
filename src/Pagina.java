import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Pagina {
    ArrayList<Admin> admins = new ArrayList<>();
    ArrayList<Biblioteca> bibliotecas = new ArrayList<>();
    ArrayList<Cliente> clientes = new ArrayList<>();
    ArrayList<Livro> livros = new ArrayList<>();
    ArrayList<Aluguel> alugueis = new ArrayList<>();
    ArrayList<Aluguel> concluidos = new ArrayList<>();
    Gson gson = new Gson();
    Scanner scanner = new Scanner(System.in);
    Usuario usuario;

    public Pagina() {
        try {
            Path admin_path = Paths.get("./data/admins.json");
            byte[] admin_jsonData = Files.readAllBytes(admin_path);
            String admin_json = new String(admin_jsonData);
            admins = gson.fromJson(admin_json, new TypeToken<ArrayList<Admin>>(){}.getType());
        } catch (Exception ignored) {}

        try {
            Path bibliotecas_path = Paths.get("./data/bibliotecas.json");
            byte[] bibliotecas_jsonData = Files.readAllBytes(bibliotecas_path);
            String bibliotecas_json = new String(bibliotecas_jsonData);
            bibliotecas = gson.fromJson(bibliotecas_json, new TypeToken<ArrayList<Biblioteca>>(){}.getType());
        } catch (Exception ignored) {}

        try {
            Path clientes_path = Paths.get("./data/clientes.json");
            byte[] clientes_jsonData = Files.readAllBytes(clientes_path);
            String clientes_json = new String(clientes_jsonData);
            clientes = gson.fromJson(clientes_json, new TypeToken<ArrayList<Cliente>>(){}.getType());
        } catch (Exception ignored) {}


        try {
            Path livros_path = Paths.get("./data/livros.json");
            byte[] livros_jsonData = Files.readAllBytes(livros_path);
            String livros_json = new String(livros_jsonData);
            livros = gson.fromJson(livros_json, new TypeToken<ArrayList<Livro>>(){}.getType());
        } catch (Exception ignored) {}

        try {
            Path alugueis_path = Paths.get("./data/alugueis.json");
            byte[] alugueis_jsonData = Files.readAllBytes(alugueis_path);
            String alugueis_json = new String(alugueis_jsonData);
            alugueis = gson.fromJson(alugueis_json, new TypeToken<ArrayList<Aluguel>>(){}.getType());
        } catch (Exception ignored) {}

        try {
            Path concluidos_path = Paths.get("./data/concluidos.json");
            byte[] concluidos_jsonData = Files.readAllBytes(concluidos_path);
            String concluidos_json = new String(concluidos_jsonData);
            concluidos = gson.fromJson(concluidos_json, new TypeToken<ArrayList<Aluguel>>(){}.getType());
        } catch (Exception ignored) {}

        System.out.println("\nBem vindo(a) ao Página.");
        System.out.println("Página é o seu novo serviço de aluguel de livros.");
        System.out.println("Utilize-o tanto como um serviço para sua locadora, quando para alugar livros de outras locadoras.\n");

        System.out.print("Digite 1 para entrar como cliente, 2 como biblioteca, ou 3 como admin: ");
        String escolha = scanner.nextLine();
        while (!escolha.equals("1") && !escolha.equals("2") && !escolha.equals("3")) {
            output("Algo deu errado.\n");
            System.out.print("Digite 1 para entrar como cliente, 2 como biblioteca, ou 3 como admin: ");
            escolha = scanner.nextLine();
        }

        if (escolha.equals("1")) {
            while (true) {
                boolean off = false;
                boolean acc_found = false;
                System.out.print("Digite o nome de usuário: ");
                String username = scanner.nextLine().strip().toLowerCase();

                if (!clientes.isEmpty()) {
                    for(Cliente c: clientes) {
                        if (c.getUsername().equals(username)) {
                            if (c.login()) {
                                usuario = c;
                                off = true;
                                acc_found = true;
                                clientInterface();
                            } else {
                                output("Senha incorreta. Tente novamente\n");
                            }
                        }
                    }
                } else {
                    output("Não foi encontrada uma conta com esse nome. Criando nova conta");

                    System.out.print("Digite seu nome: ");
                    String nome = scanner.nextLine();

                    System.out.print("Digite sua nova senha: ");
                    String password = scanner.nextLine();

                    try {
                        Cliente novo_user = new Cliente(nome, username, password);
                        clientes.add(novo_user);
                        usuario = novo_user;
                        off = true;
                        clientInterface();
                    } catch (Exception e) {
                        output("Algo deu errado. Tente novamente");
                    }
                }
                if (!acc_found) {
                    output("Não foi encontrada uma conta com esse nome. Criando nova conta");

                    System.out.print("Digite seu nome: ");
                    String nome = scanner.nextLine();

                    System.out.print("Digite sua nova senha: ");
                    String password = scanner.nextLine();

                    try {
                        Cliente novo_user = new Cliente(nome, username, password);
                        clientes.add(novo_user);
                        usuario = novo_user;
                        off = true;
                        clientInterface();
                    } catch (Exception e) {
                        output("Algo deu errado. Tente novamente");
                    }
                }
                if (off) { clientInterface(); }
            }
        } else if (escolha.equals("2")) {
            while (true) {
                boolean off = false;
                boolean acc_found = false;
                System.out.print("Digite o nome de usuário: ");
                String username = scanner.nextLine().strip().toLowerCase();

                if (!bibliotecas.isEmpty()) {
                    for(Biblioteca c: bibliotecas) {
                        if (c.getUsername().equals(username)) {
                            if (c.login()) {
                                usuario = c;
                                off = true;
                                acc_found = true;
                                libInterface();
                            } else {
                                output("Senha incorreta. Tente novamente\n");
                            }
                        }
                    }
                } else {
                    output("Não foi encontrada uma conta com esse nome. Criando nova conta");

                    System.out.print("Digite seu nome: ");
                    String nome = scanner.nextLine();

                    System.out.print("Digite sua nova senha: ");
                    String password = scanner.nextLine();

                    try {
                        Biblioteca novo_user = new Biblioteca(nome, username, password);
                        bibliotecas.add(novo_user);
                        usuario = novo_user;
                        off = true;
                        libInterface();
                    } catch (Exception e) {
                        output("Algo deu errado. Tente novamente");
                    }
                }
                if (!acc_found) {
                    output("Não foi encontrada uma conta com esse nome. Criando nova conta");

                    System.out.print("Digite seu nome: ");
                    String nome = scanner.nextLine();

                    System.out.print("Digite sua nova senha: ");
                    String password = scanner.nextLine();

                    try {
                        Biblioteca novo_user = new Biblioteca(nome, username, password);
                        bibliotecas.add(novo_user);
                        usuario = novo_user;
                        off = true;
                        break;
                    } catch (Exception e) {
                        output("Algo deu errado. Tente novamente");
                    }
                }
                if (off) { libInterface(); }
            }
        } else {
            while (true) {
                boolean off = false;
                boolean acc_found = false;
                System.out.print("Digite o nome de usuário: ");
                String username = scanner.nextLine().strip().toLowerCase();

                if (!admins.isEmpty()) {
                    for(Admin c: admins) {
                        if (c.getUsername().equals(username)) {
                            acc_found = true;
                            if (c.login()) {
                                usuario = c;
                                off = true;
                                adminInterface();
                            } else {
                                output("Senha incorreta. Tente novamente\n");
                            }
                        }
                    }
                    if (!acc_found) {
                        output("Não foi encontrado admin com esse username.\n");
                    }
                } else {
                    output("Não há admins registrados.\n");
                }
                if (off) { adminInterface(); }
            }
        }

    }

    private void output(String texto) { System.out.println("Página diz: " + texto); }

    private Livro pesquisarLivro() {
        if (livros.isEmpty()) {
            output("Não há livros cadastrados.");
            return null;
        } else {
            return null;
        }
    }

    private void alugarLivroC() {
        if(usuario.getTipo().equals("class Cliente")) {
            output("é cliente");
        }
    }

    private void gerenciarAlugueisC() {
        if(usuario.getTipo().equals("class Cliente")) {
            output("é cliente");
        }
    }

    private void gerenciarContaC() {
        if(usuario.getTipo().equals("class Cliente")) {
            output("é cliente");
        }
    }

    private void clientInterface() {
        while (true) {
            System.out.println();
            output("Olá, " + this.usuario.getNome() + "! Você pode me controlar usando os seguintes comandos: ");

            System.out.println("1 - Alugar Livro");
            System.out.println("2 - Pesquisar Livro");
            System.out.println("3 - Meus Aluguéis");
            System.out.println("4 - Gerenciar Conta");
            System.out.println("S - Fechar e Salvar Alterações\n");

            System.out.print("Digite aqui: ");
            String escolha = scanner.nextLine();

            if (escolha.equals("1")) {
                alugarLivroC();
            } else if (escolha.equals("2")) {
                pesquisarLivro();
            } else if (escolha.equals("3")) {
                gerenciarAlugueisC();
            } else if (escolha.equals("4")) {
                gerenciarContaC();
            } else {
                System.out.print("Digite S para confirmar sua saída do programa: ");
                String escolha2 = scanner.nextLine().strip().toUpperCase();
                if (escolha2.equals("S")) {
                    sair();
                }
            }
        }
    }

    private void gerenciarUsuariosA() {
        if(usuario.getTipo().equals("class Admin")) {
            output("é admin");
        }
    }

    private void verAlugueisA() {
        if(usuario.getTipo().equals("class Admin")) {
            output("é admin");
        }
    }

    private void adicionarAdminA() {
        if(usuario.getTipo().equals("class Admin")) {
            output("é admin");
        }
    }

    private void adicionarBibliotecaA() {
        if(usuario.getTipo().equals("class Admin")) {
            output("é admin");
        }
    }

    private void adminInterface() {
        while (true) {
            System.out.println();
            output("Olá, " + this.usuario.getNome() + "! Você pode me controlar usando os seguintes comandos: ");

            System.out.println("1 - Gerenciar Usuários");
            System.out.println("2 - Gerenciar Alugueis");
            System.out.println("3 - Adicionar Admin");
            System.out.println("4 - Adicionar Biblioteca");
            System.out.println("5 - Pesquisar Livro");
            System.out.println("S - Fechar e Salvar Alterações\n");

            System.out.print("Digite aqui: ");
            String escolha = scanner.nextLine();

            if (escolha.equals("1")) {
                gerenciarUsuariosA();
            } else if (escolha.equals("2")) {
                verAlugueisA();
            } else if (escolha.equals("3")) {
                adicionarAdminA();
            } else if (escolha.equals("4")) {
                adicionarBibliotecaA();
            } else if (escolha.equals("5")) {
                pesquisarLivro();
            } else {
                System.out.print("Digite S para confirmar sua saída do programa: ");
                String escolha2 = scanner.nextLine().strip().toUpperCase();
                if (escolha2.equals("S")) {
                    sair();
                }
            }
        }
    }

    private void gerenciarAlugueisB() {
        if(usuario.getTipo().equals("class Biblioteca")) {
            output("é biblioteca");
        }
    }

    private void adicionarLivroB() {
        if(usuario.getTipo().equals("class Biblioteca")) {
            output("é biblioteca");
        }
    }

    private void editarLivroB() {
        if(usuario.getTipo().equals("class Biblioteca")) {
            output("é biblioteca");
        }
    }

    private void gerenciarFinancasB() {
        if(usuario.getTipo().equals("class Biblioteca")) {
            output("é biblioteca");
        }
    }

    private void libInterface() {
        while (true) {
            System.out.println();
            output("Olá, " + this.usuario.getNome() + "! Você pode me controlar usando os seguintes comandos: ");

            System.out.println("1 - Gerenciar Alugueis");
            System.out.println("2 - Adicionar Livro");
            System.out.println("3 - Editar Livro");
            System.out.println("4 - Gerenciar Finanças");
            System.out.println("5 - Pesquisar Livro");
            System.out.println("S - Fechar e Salvar Alterações\n");

            System.out.print("Digite aqui: ");
            String escolha = scanner.nextLine();
            
            if (escolha.equals("1")) {
                gerenciarAlugueisB();
            } else if (escolha.equals("2")) {
                adicionarLivroB();
            } else if (escolha.equals("3")) {
                editarLivroB();
            } else if (escolha.equals("4")) {
                gerenciarFinancasB();
            } else if (escolha.equals("5")) {
                pesquisarLivro();
            } else {
                System.out.print("Digite S para confirmar sua saída do programa: ");
                String escolha2 = scanner.nextLine().strip().toUpperCase();
                if (escolha2.equals("S")) {
                    sair();
                }
            }
        }
    }

    private void sair() {
        output("Tentando salvar os seus dados...");

        File dataFolder = new File("./data");
        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }


        if(!admins.isEmpty()) {
            String json_admins = gson.toJson(admins);
            try (FileWriter fileWriter = new FileWriter("./data/admins.json")) {
                fileWriter.write(json_admins);
            } catch (IOException e) {
                output("Não foi possível salvar os admins.");
            }
        }

        if(!bibliotecas.isEmpty()) {
            String json_bibliotecas = gson.toJson(bibliotecas);
            try (FileWriter fileWriter = new FileWriter("./data/bibliotecas.json")) {
                fileWriter.write(json_bibliotecas);
            } catch (IOException e) {
                output("Não foi possível salvar as bibliotecas.");
            }
        }

        if(!clientes.isEmpty()) {
            String json_clientes = gson.toJson(clientes);
            try (FileWriter fileWriter = new FileWriter("./data/clientes.json")) {
                fileWriter.write(json_clientes);
            } catch (IOException e) {
                output("Não foi possível salvar os clientes.");
            }
        }

        if (!livros.isEmpty()) {
            String json_livros = gson.toJson(livros);
            try (FileWriter fileWriter = new FileWriter("./data/livros.json")) {
                fileWriter.write(json_livros);
            } catch (IOException e) {
                output("Não foi possível salvar os livros.");
            }
        }

        if (!alugueis.isEmpty()) {
            String json_alugueis = gson.toJson(alugueis);
            try (FileWriter fileWriter = new FileWriter("./data/alugueis.json")) {
                fileWriter.write(json_alugueis);
            } catch (IOException e) {
                output("Não foi possível salvar os aluguéis.");
            }
        }


        if (!concluidos.isEmpty()) {
            String json_concluidos = gson.toJson(concluidos);
            try (FileWriter fileWriter = new FileWriter("./data/concluidos.json")) {
                fileWriter.write(json_concluidos);
            } catch (IOException e) {
                output("Não foi possível salvar os aluguéis concluidos.");
            }
        }


        output("Obrigado por utilizar o Página.");
        System.exit(0);
    }
}
