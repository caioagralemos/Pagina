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

        System.out.print("Digite 1 para entrar como cliente ou 2 como biblioteca: ");
        String escolha = scanner.nextLine();
        while (!escolha.equals("1") && !escolha.equals("2") && !escolha.equals("3")) {
            output("Algo deu errado.\n");
            System.out.print("Digite 1 para entrar como cliente ou 2 como biblioteca: ");
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
                            acc_found = true;
                            if (c.login()) {
                                usuario = c;
                                off = true;
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
            if (bibliotecas.isEmpty()) {
                output("Não há bibliotecas disponíveis. Se quiser registrar a sua, entre em contato com um administrador.");
            } else {
                while (true) {
                    boolean off = false;
                    boolean acc_found = false;
                    System.out.print("Digite o nome de usuário: ");
                    String username = scanner.nextLine().strip().toLowerCase();

                    for (Biblioteca c : bibliotecas) {
                        if (c.getUsername().equals(username)) {
                            acc_found = true;
                            if (c.login()) {
                                usuario = c;
                                off = true;
                                libInterface();
                            } else {
                                output("Senha incorreta. Tente novamente\n");
                            }
                        }
                    }
                    if (!acc_found) { output("Não foi encontrada uma biblioteca com esse nome de usuário. Tente novamente\n"); }
                    if (off) { libInterface(); }
                }
            }
        } else  {
            while (true) {
                boolean off = false;
                boolean acc_found = false;
                System.out.print("Digite o nome de usuário: ");
                String username = scanner.nextLine().strip().toLowerCase();

                if (username.equals("admin")) {
                    System.out.print("Digite a senha de sua conta: ");
                    String password = scanner.nextLine();
                    if (password.equals("password")) {
                        usuario = new Admin("Administrador", username, password);
                        off = true;
                        adminInterface();
                    } else {
                        output("Senha incorreta. Tente novamente\n");
                    }
                } else if (!admins.isEmpty()) {
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

    private String getString(String descricao, String titulo) {
        System.out.print("Digite " + descricao + ": ");
        String resposta = scanner.nextLine().strip();
        while (resposta.isBlank() || resposta.equals("/menu")) {
            if (resposta.equals("/menu")) {
                return null;
            }
            output(titulo + "não pode ficar em branco!");
            System.out.print("Digite " + descricao + " ou /menu pra voltar ao menu: ");
            resposta = scanner.nextLine().strip();
        }
        return resposta;
    }

    private int getInt(String descricao, String titulo) {
        String resposta;
        int resultado;
        while (true) {
            try {
                System.out.print("Digite " + descricao + ": ");
                resposta = scanner.nextLine().strip();
                while (resposta.isBlank() || resposta.equals("/menu")) {
                    if (resposta.equals("/menu")) {
                        return -1;
                    }
                    output(titulo + "não pode ficar em branco!");
                    System.out.print("Digite " + descricao + " ou /menu pra voltar ao menu: ");
                    resposta = scanner.nextLine().strip();
                }
                resultado = Integer.parseInt(resposta);
                break;
            } catch (Exception e) {
                output("Algo deu errado, tente novamente.");
            }
        }
        return resultado;
    }

    private Livro pesquisarLivro(boolean retorna) {
        if (livros.isEmpty()) {
            output("Não há livros cadastrados.");
            return null;
        }

        Livro retorno;

        System.out.print("Digite o nome do livro: ");
        String nome_pesquisar = scanner.nextLine().strip();
        ArrayList<Livro> livros_achados = new ArrayList<>();
        for(Livro l: livros) {
            if(l.titulo.toUpperCase().contains(nome_pesquisar.toUpperCase())) {
                livros_achados.add(l);
            }
        }

        if(livros_achados.isEmpty()) {
            output("Não foram encontrados livros com esse nome.");
            return null;
        }

        int contador = 1;
        for (Livro l: livros_achados) {
            System.out.println(contador + " - " + l);
            contador++;
        }

        while(true) {
            System.out.print("Escolha o índice do livro: ");
            try {
                int escolha = Integer.parseInt(scanner.nextLine());
                retorno = livros_achados.get(escolha-1);
                break;
            } catch (Exception e) {
                output("Algo deu errado. Verifique o indice e tente novamente.");
            }
        }

        if (retorna) {
            return retorno;
        } else {
            System.out.println();
            output("Seu livro:");
            System.out.println(retorno.descricao());
            return null;
        }
    }

    private void alugarLivroC() {
        if(usuario.getTipo().equals("class Cliente")) {
            int id = alugueis.size()+1;

            Livro livro = pesquisarLivro(true);
            if (livro == null) {
                return;
            }

            Cliente cliente = (Cliente) this.usuario;

            Biblioteca biblioteca = livro.biblioteca;

            Data data_limite;
            while (true) {
                try {
                    output("Digite o dia do final do aluguel: ");
                    int dia = Integer.parseInt(scanner.nextLine());

                    output("Digite o mês do final do aluguel: ");
                    int mes = Integer.parseInt(scanner.nextLine());

                    output("Digite o ano do final do aluguel: ");
                    int ano = Integer.parseInt(scanner.nextLine());

                    data_limite = new Data(dia, mes, ano);
                    System.out.println();
                    break;
                } catch (Error e) {
                    output("Algo deu errado. Tente novamente");
                }
            }

            Aluguel novo = new Aluguel(id, livro, cliente, biblioteca, data_limite);
            output("Seu novo aluguel: ");
            System.out.println(novo);
            System.out.print("Digite S para confirmar seu aluguel: ");
            String escolha = scanner.nextLine().strip().toUpperCase();
            if (escolha.equals("S")) {
                if (cliente.saldo >= novo.valor && livro.qtd_disponivel > 1)  {
                    alugueis.add(novo);
                    cliente.alu_atuais.add(novo);
                    biblioteca.alugueis.add(novo);
                    biblioteca.alugou();
                    output("Aluguel feito com sucesso.");
                    cliente.saldo = cliente.saldo - novo.valor;
                    livro.qtd_alugados++;
                    livro.qtd_disponivel--;
                } else if (livro.qtd_disponivel < 1) {
                    output("Esse livro não tem estoque suficiente.");
                } else {
                    output("Você não tem saldo suficiente para isso.");
                }
            }
        }
    }

    private void gerenciarAlugueisC() {
        if(usuario.getTipo().equals("class Cliente")) {
            ArrayList<Aluguel> alugueis_cliente = new ArrayList<>();

            for (Aluguel a: alugueis) {
                if (a.cliente == usuario) {
                    alugueis_cliente.add(a);
                }
            }

            if (!alugueis_cliente.isEmpty()) {
                for (Aluguel a: alugueis_cliente) {
                    System.out.println(a);
                }
            } else {
                output("Você ainda não tem alugueis em curso!");
            }
        }
    }

    private void gerenciarContaC() {
        if(usuario.getTipo().equals("class Cliente")) {
            Cliente c = (Cliente) usuario;
            String escolha = getString("1 para alterar nome, 2 para alterar senha, 3 para consultar saldo ou 4 pra adicionar saldo", "Sua escolha");
            if (escolha == null) {
                return;
            }
            while (!escolha.equals("1") && !escolha.equals("2") && !escolha.equals("3") && !escolha.equals("4")) {
                output("Sua escolha precisa ser entre 1, 2, 3 e 4. Tente novamente");
                escolha = getString("1 para alterar nome, 2 para alterar senha, 3 para consultar saldo ou 4 pra adicionar saldo", "Sua escolha");
                if (escolha == null) {
                    return;
                }
            }

            switch (escolha) {
                case "1":
                    c.setNome();
                    break;
                case "2":
                    c.setSenha();
                    break;
                case "3":
                    output("Seu saldo atual é de R$ " + c.saldo + "0");
                    break;
                case "4":
                    int valor = getInt("o valor que deseja adicionar", "O valor");
                    c.setSaldo(valor);
                    output("Seu novo saldo é de R$ " + c.saldo + "0");
            }
        }
    }

    private void clientInterface() {
        while (true) {
            System.out.println();
            output("Olá, " + this.usuario.getNome() + "! Você pode me controlar usando os seguintes comandos:\n");

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
                pesquisarLivro(false);
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
            String escolha = getString("1 para ver os clientes, 2 para ver as bibliotecas ou 3 pra ver os Admins", "Sua escolha");
            if (escolha == null) {
                return;
            }
            while (!escolha.equals("1") && !escolha.equals("2") && !escolha.equals("3")) {
                output("Sua escolha precisa ser entre 1, 2 e 3. Tente novamente");
                escolha = getString("1 para ver os clientes, 2 para ver as bibliotecas ou 3 pra ver os Admins", "Sua escolha");
                if (escolha == null) {
                    return;
                }
            }

            switch (escolha) {
                case "1":
                    for (Cliente c: clientes) {
                        System.out.println(c);
                    }
                    break;
                case "2":
                    for (Biblioteca b: bibliotecas) {
                        System.out.println(b);
                    }
                    break;
                default:
                    for (Admin a: admins) {
                        System.out.println(a);
                    }
                    break;
            }
        }
    }

    private void verAlugueisA() {
        if(usuario.getTipo().equals("class Admin")) {
            for (Aluguel a: alugueis) {
                System.out.println(a);
            }
        }
    }

    private void adicionarAdminA() {
        if(usuario.getTipo().equals("class Admin")) {
            String username;
            while (true) {
                System.out.print("Digite o nome de usuário da nova conta ou /menu pra sair: ");
                username = scanner.nextLine().strip().toLowerCase();

                if (username.equals("/menu")) {
                    return;
                }

                boolean found = false;
                for (Admin a: admins) {
                    if (a.getUsername().equals(username)) {
                        output("Esse username já está sendo utilizado.");
                        found = true;
                    }
                }

                if (!found) {
                    break;
                }
            }

            System.out.print("Digite seu nome: ");
            String nome = scanner.nextLine();

            while (nome.isBlank()) {
                System.out.print("Nome inválido. Digite seu nome ou /menu pra sair: ");
                nome = scanner.nextLine();
                if (nome.equals("/menu")) {
                    return;
                }
            }

            System.out.print("Digite sua senha: ");
            String senha = scanner.nextLine();
            System.out.print("Confirme sua senha: ");
            String senha_2 = scanner.nextLine();
            while (senha.isBlank() || (!senha.equals(senha_2))) {
                System.out.print("Senha inválida. Digite uma senha ou /menu pra sair: ");
                senha = scanner.nextLine();
                if (senha.equals("/menu")) {
                    return;
                }
                System.out.print("Confirme sua senha: ");
                senha_2 = scanner.nextLine();
            }

            try {
                Admin novo_admin = new Admin(nome, username, senha);
                admins.add(novo_admin);
                output("Admin @" + username + " adicionada com sucesso.");
            } catch (Exception e) {
                output("Algo deu errado. Tente novamente mais tarde.");
                return;
            }
        }
    }

    private void adicionarBibliotecaA() {
        if(usuario.getTipo().equals("class Admin")) {
            String username;
            while (true) {
                System.out.print("Digite o nome de usuário da nova conta ou /menu pra sair: ");
                username = scanner.nextLine().strip().toLowerCase();

                if (username.equals("/menu")) {
                    return;
                }

                boolean found = false;
                for (Biblioteca b: bibliotecas) {
                    if (b.getUsername().equals(username)) {
                        output("Esse username já está sendo utilizado.");
                        found = true;
                    }
                }

                if (!found) {
                    break;
                }
            }

            System.out.print("Digite o nome de sua biblioteca: ");
            String nome = scanner.nextLine();

            while (nome.isBlank()) {
                System.out.print("Nome inválido. Digite o nome de sua biblioteca ou /menu pra sair: ");
                nome = scanner.nextLine();
                if (nome.equals("/menu")) {
                    return;
                }
            }

            System.out.print("Digite sua senha: ");
            String senha = scanner.nextLine();
            System.out.print("Confirme sua senha: ");
            String senha_2 = scanner.nextLine();
            while (senha.isBlank() || (!senha.equals(senha_2))) {
                System.out.print("Senha inválida. Digite uma senha ou /menu pra sair: ");
                senha = scanner.nextLine();
                if (senha.equals("/menu")) {
                    return;
                }
                System.out.print("Confirme sua senha: ");
                senha_2 = scanner.nextLine();
            }

            try {
                Biblioteca nova_lib = new Biblioteca(nome, username, senha);
                bibliotecas.add(nova_lib);
                output("Biblioteca @" + username + " adicionada com sucesso.");
            } catch (Exception e) {
                output("Algo deu errado. Tente novamente mais tarde.");
                return;
            }
        }
    }

    private void adminInterface() {
        while (true) {
            System.out.println();
            output("Olá, " + this.usuario.getNome() + "! Você pode me controlar usando os seguintes comandos:\n");

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
                pesquisarLivro(false);
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
            ArrayList<Aluguel> alugueis_lib = new ArrayList<>();

            for (Aluguel a: alugueis) {
                if (a.biblioteca == usuario) {
                    alugueis_lib.add(a);
                }
            }

            for (Aluguel a: alugueis_lib) {
                System.out.println(a);
            }
        }
    }

    private void adicionarLivroB() {
        if(usuario.getTipo().equals("class Biblioteca")) {
            ArrayList<String> titulos = new ArrayList<>();
            if (!livros.isEmpty()) {
                for (Livro l: livros) {
                    titulos.add(l.titulo);
                }
            }
            System.out.print("Digite o título do seu novo livro: ");
            String nome = scanner.nextLine().strip();
            while (nome.isBlank() || nome.equals("/menu") || titulos.contains(nome)) {
                if (nome.equals("/menu")) {
                    return;
                }
                if (titulos.contains(nome)) {
                    output("Esse livro já está disponível em nosso app.");
                    return;
                }
                output("O titulo do livro não pode ficar em branco!");
                System.out.print("Digite o título do seu novo livro ou /menu pra voltar ao menu: ");
                nome = scanner.nextLine().strip();
            }

            ArrayList<String> categorias = new ArrayList<>();
            output("Digite as categorias do livro e digite -1 quando tiver concluído: ");
            int contador = 1;
            while (true) {
                System.out.print("Digite aqui (Especialidade nº" + contador + "): ");
                String cat = scanner.nextLine().strip().toUpperCase();

                if(cat.equals("-1")) {
                    break;
                }

                if (!cat.isBlank()) {
                    if(!categorias.contains(cat)) {
                        contador++;
                        categorias.add(cat);
                    } else {
                        output("Categoria já registrada.");
                    }
                }
            }

            String autor = getString("o nome do autor", "O nome do autor");
            if (autor == null) {
                return;
            }

            int paginas = getInt("o número de páginas", "O número de páginas");
            if (paginas == -1) {
                return;
            }

            int qtd = getInt("a quantidade de livros disponiveis", "A quantidade de livros");
            if (qtd == -1) {
                return;
            }

            Livro l;
            try {
                l = new Livro((Biblioteca) usuario, categorias, livros.size()+1, nome, autor, paginas, qtd);
                livros.add(l);
                output("Livro " + nome + " adicionado com sucesso!");
            } catch (Exception e) {
                output("Algo deu errado. Tente novamente");
            }
        }
    }

    private void editarLivroB() {
        if(usuario.getTipo().equals("class Biblioteca")) {
            output("é biblioteca");
        }
    }

    private void gerenciarFinancasB() {
        if(usuario.getTipo().equals("class Biblioteca")) {
            Biblioteca b = (Biblioteca) usuario;
            double previsto = 0;
            for (Aluguel a: b.alugueis) {
                previsto = previsto + a.valor + a.multa;
            }
            double historico = 0;
            for (Aluguel a: b.concluidos) {
                historico = historico + a.valor + a.multa;
            }

            output("Relatório de Lucros de " + b.getNome()  + ": ");
            System.out.println("    Lucros de hoje: R$ " + (b.getAlugados()*3) + "0");
            System.out.println("    Lucros totais previsto: R$ " + previsto + "0");
            System.out.println("    Lucros totais já recebidos: R$ " + historico + "0");
        }
    }

    private void libInterface() {
        while (true) {
            System.out.println();
            output("Olá, " + this.usuario.getNome() + "! Você pode me controlar usando os seguintes comandos:\n");

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
                pesquisarLivro(false);
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
