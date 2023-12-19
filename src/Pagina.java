import com.google.gson.*;
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

        ArrayList<String> devedores = new ArrayList<>();
        for (Aluguel a: alugueis) {
            if (a.limite.adh().equals("antes") && !a.concluido) {
                a.multa = a.limite.daysDifferenceP() * 5;
                devedores.add(a.cliente.getUsername());
            }
        }
        for (Cliente c: clientes) {
            if (devedores.contains(c.getUsername())) {
                c.bloqueado = true;
            }
        }

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

    private void atualizarDevolucao(Livro livro, Biblioteca biblioteca) {
        Livro atualizar = null;
        for (Livro l: livros) {
            if (l.idLivro == livro.idLivro) {
                atualizar = l;
                break;
            }
        }
        assert atualizar != null;
        atualizar.qtd_disponivel++;
        atualizar.qtd_alugados--;

        Biblioteca atualizar2 = null;
        for (Biblioteca b: bibliotecas) {
            if (b.getUsername().equals(biblioteca.getUsername())) {
                atualizar2 = b;
                break;
            }
        }
        assert atualizar2 != null;
        atualizar2.disponibilizou();
    }

    private String getString(String descricao, String titulo) {
        System.out.print("Digite " + descricao + ": ");
        String resposta = scanner.nextLine().strip();
        while (resposta.isBlank() || resposta.equals("/menu")) {
            if (resposta.equals("/menu")) {
                return null;
            }
            output(titulo + " não pode ficar em branco!");
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
                    output(titulo + " não pode ficar em branco!");
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

        String e = getString("1 para pesquisar por nome ou 2 por categoria", "Sua escolha");
        while (!e.equals("1") && !e.equals("2")) {
            if (e == null) {
                return null;
            }
            output("Algo deu errado! Tente novamente");
            e = getString("1 para pesquisar por nome ou 2 por categoria", "Sua escolha");
        }

        Livro retorno = null;

        if (e.equals("1")) {
            System.out.print("Digite o nome do livro: ");
            String nome_pesquisar = scanner.nextLine().strip();
            ArrayList<Livro> livros_achados = new ArrayList<>();
            for(Livro l: livros) {
                if(l.titulo.toUpperCase().contains(nome_pesquisar.toUpperCase()) && l.qtd_disponivel > 0) {
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
                } catch (Exception ignored) {
                    output("Algo deu errado. Verifique o indice e tente novamente.");
                }
            }
        } else {
            ArrayList<String> categorias = new ArrayList<>();
            for (Livro l: livros) {
                for (String c: l.categorias) {
                    if (!categorias.contains(c)) {
                        categorias.add(c);
                    }
                }
            }

            System.out.println("Categorias disponíveis: ");
            int ctd = 1;
            for (String c: categorias) {
                System.out.println(ctd + " - " + c);
                ctd++;
            }

            int indice = getInt("o indice da categoria", "O indice");
            while (indice < 0 || indice > categorias.size()) {
                output("Algo deu errado. Tente novamente");
                indice = getInt("o indice da categoria", "O indice");
            }

            String categoria = categorias.get(indice-1);
            ArrayList<Livro> livros_achados = new ArrayList<>();

            for (Livro l: livros) {
                if (l.categorias.contains(categoria) && l.qtd_disponivel > 0) {
                    livros_achados.add(l);
                }
            }

            if (livros_achados.isEmpty()) {
                output("Não foi encontrado nenhum livro com essa categoria.");
                return null;
            } else {
                ctd = 1;
                for (Livro l: livros_achados) {
                    System.out.println(ctd + " - " + l);
                    ctd++;
                }
            }

            while(true) {
                System.out.print("Escolha o índice do livro: ");
                try {
                    int escolha = Integer.parseInt(scanner.nextLine());
                    retorno = livros_achados.get(escolha-1);
                    break;
                } catch (Exception ignored) {
                    output("Algo deu errado. Verifique o indice e tente novamente.");
                }
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
            Cliente cliente = (Cliente) this.usuario;

            boolean block = false;
            for (Aluguel a: alugueis) {
                if (a.cliente.getUsername().equals(cliente.getUsername()) && !a.concluido && a.multa > 0) {
                    block = true;
                    break;
                }
            }
            cliente.bloqueado = block;

            if (cliente.bloqueado) {
                output("Você deve pagar suas multas para alugar novos livros.");
                return;
            }

            int id = alugueis.size()+1;

            Livro livro = pesquisarLivro(true);
            if (livro == null) {
                return;
            }

            Biblioteca biblioteca = null;
            for (Biblioteca b: bibliotecas) {
                if (b.getUsername().equals(livro.biblioteca.getUsername())) {
                    biblioteca = b;
                }
            }
            if (biblioteca == null) {
                output("Algo deu errado. Tente novamente mais tarde.");
                return;
            }

            Data data_limite;
            while (true) {
                try {
                    output("Digite o dia do final do aluguel: ");
                    int dia = Integer.parseInt(scanner.nextLine());

                    output("Digite o mês do final do aluguel: ");
                    int mes = Integer.parseInt(scanner.nextLine());

                    output("Digite o ano do final do aluguel: ");
                    int ano = Integer.parseInt(scanner.nextLine());

                    data_limite = new Data(dia, mes, ano, true);
                    System.out.println();
                    break;
                } catch (Exception | Error ignored) {
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
                    cliente.saldo = cliente.saldo - novo.valor;
                    livro.qtd_alugados++;
                    livro.qtd_disponivel--;
                    biblioteca.livros_alugados++;
                    biblioteca.livros_disponiveis--;
                    alugueis.add(novo);
                    output("Aluguel feito com sucesso.");
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
                if (a.cliente.getUsername().equals(usuario.getUsername()) && !a.concluido) {
                    alugueis_cliente.add(a);
                }
            }

            if (alugueis_cliente.isEmpty()) {
                output("Você ainda não tem aluguéis registrados.");
                return;
            }

            String escolha = getString("1 para ver aluguéis, 2 para alugar livro ou 3 para devolver livro", "Sua escolha");
            if (escolha == null) {
                return;
            }
            while (!escolha.equals("1") && !escolha.equals("2") && !escolha.equals("3")) {
                if (escolha == null) {
                    return;
                }
                output("Algo deu errado. Tente novamente");
                escolha = getString("1 para ver alugueis, 2 para alugar livro ou 3 para devolver livro", "Sua escolha");
            }

            if (escolha.equals("2")) {
                alugarLivroC();
            } else {
                if (alugueis_cliente.isEmpty()) {
                    output("Você ainda não tem aluguéis registrados.");
                } else {
                    if (escolha.equals("1")) {
                        System.out.println("Seus aluguéis: ");
                        int ctd = 1;
                        for (Aluguel a: alugueis_cliente) {
                            System.out.println(ctd + " - " + a);
                            ctd++;
                        }
                    } else {
                        System.out.println("Seus aluguéis: ");
                        int ctd = 1;
                        for (Aluguel a : alugueis_cliente) {
                            System.out.println(ctd + " - " + a);
                            ctd++;
                        }
                        int indice = getInt("o índice do aluguel que você quer devolver", "O índice") - 1;
                        while (indice < 0 || indice >= alugueis_cliente.size()) {
                            output("Algo deu errado. Verifique sua resposta e tente novamente");
                            indice = getInt("o índice do aluguel que você quer devolver", "O índice") - 1;
                        }
                        Aluguel aluguel = alugueis_cliente.get(indice);
                        String situacao = aluguel.limite.adh();
                        Cliente c = (Cliente) this.usuario;
                        switch (situacao) {
                            case "antes":
                                output("Seu prazo limite era " + aluguel.limite + " e por isso você vai precisar pagar uma multa de R$ " + aluguel.multa + "0");
                                System.out.print("Digite S para confirmar a devolução e pagar a multa: ");
                                if (scanner.nextLine().strip().equalsIgnoreCase("s")) {
                                    if (c.saldo >= aluguel.multa) {
                                        c.saldo -= aluguel.multa;
                                        aluguel.valor += aluguel.multa;
                                        aluguel.concluido = true;
                                        atualizarDevolucao(aluguel.livro, aluguel.biblioteca);
                                        output("Multa paga e devolução efetuada com sucesso.\n");
                                    }
                                }
                                break;
                            case "depois":
                                double valor_devolucao = aluguel.limite.daysDifferenceF() * 3;
                                output("Seu prazo limite é " + aluguel.limite + " e por devolver antes você vai receber de volta R$ " + valor_devolucao + "0");
                                System.out.print("Digite S para confirmar a devolução: ");
                                if (scanner.nextLine().strip().equalsIgnoreCase("s")) {
                                    c.saldo += valor_devolucao;
                                    aluguel.valor -= valor_devolucao;
                                    aluguel.limite = new Data();
                                    aluguel.concluido = true;
                                    atualizarDevolucao(aluguel.livro, aluguel.biblioteca);
                                    output("Devolução efetuada com sucesso.");
                                }
                                break;
                            default:
                                output("Seu prazo limite para devolver esse livro é hoje!");
                                System.out.print("Digite S para confirmar a devolução: ");
                                if (scanner.nextLine().strip().equalsIgnoreCase("s")) {
                                    aluguel.concluido = true;
                                    atualizarDevolucao(aluguel.livro, aluguel.biblioteca);
                                    output("Devolução efetuada com sucesso.");
                                }
                        }
                    }
                }
            }
        }
    }

    private void gerenciarContaC() {
        if(usuario.getTipo().equals("class Cliente")) {
            Cliente c = (Cliente) usuario;
            String escolha = getString("1 para alterar nome, 2 para alterar senha, 3 para consultar saldo, 4 pra adicionar saldo ou 5 para ver aluguéis concluídos", "Sua escolha");
            if (escolha == null) {
                return;
            }
            while (!escolha.equals("1") && !escolha.equals("2") && !escolha.equals("3") && !escolha.equals("4") && !escolha.equals("5")) {
                output("Sua escolha precisa ser entre 1, 2, 3 e 4, ou /menu pra sair. Tente novamente");
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
                    break;
                case "5":
                    ArrayList<Aluguel> alugueis_concluidos = new ArrayList<>();
                    for (Aluguel a: alugueis) {
                        if (a.cliente.getUsername().equals(c.getUsername()) && a.concluido) {
                            alugueis_concluidos.add(a);
                        }
                    }
                    if (!alugueis_concluidos.isEmpty()) {
                        System.out.println("Seus aluguéis concluídos: ");
                        int ctd = 1;
                        for (Aluguel a: alugueis_concluidos) {
                            System.out.println(ctd + " - " + a);
                            ctd++;
                        }
                    } else {
                        output("Você ainda não tem aluguéis concluídos.");
                    }
                    break;
            }
        }
    }

    private void clientInterface() {
        while (true) {
            ArrayList<String> pendentes = new ArrayList<>();
            int ctd = 1;
            for (Aluguel a: alugueis) {
                if (a.cliente.getUsername().equals(usuario.getUsername()) && (a.limite.adh().equals("antes") || a.limite.adh().equals("hoje")) && !a.concluido) {
                    pendentes.add(ctd + " - " + a);
                    ctd++;
                }
            }

            if (!pendentes.isEmpty()) {
                System.out.println();
                output("Você tem devoluções pendentes!");
                for (String s: pendentes) {
                    System.out.println(s);
                }
            }

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
                    if (clientes.isEmpty()) {
                        output("Não há clientes cadastrados.");
                        return;
                    }
                    for (Cliente c: clientes) {
                        System.out.println(c);
                    }
                    break;
                case "2":
                    if (bibliotecas.isEmpty()) {
                        output("Não há bibliotecas cadastradas.");
                        return;
                    }
                    for (Biblioteca b: bibliotecas) {
                        System.out.println(b);
                    }
                    break;
                default:
                    if (admins.isEmpty()) {
                        output("Não há admins cadastrados.");
                        return;
                    }
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
                if (a.biblioteca.getUsername().equals(usuario.getUsername())) {
                    alugueis_lib.add(a);
                }
            }

            if (!alugueis_lib.isEmpty()) {
                for (Aluguel a: alugueis_lib) {
                    System.out.println(a);
                }
            } else {
                output("Seus livros ainda não foram alugados :/ Que tal publicar um novo livro?");
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
                ((Biblioteca) usuario).livros_disponiveis = ((Biblioteca) usuario).livros_disponiveis + qtd;
                livros.add(l);
                output("Livro " + nome + " adicionado com sucesso!");
            } catch (Exception e) {
                output("Algo deu errado. Tente novamente");
            }
        }
    }

    private void editarLivroB() {
        if(usuario.getTipo().equals("class Biblioteca")) {
            ArrayList<Livro> livros_lib = new ArrayList<>();
            int ctd = 1;
            for (Livro l: livros) {
                if(l.biblioteca.getUsername().equals(usuario.getUsername())) {
                    if (ctd == 1) {
                        output("Seus livros: ");
                    }
                    livros_lib.add(l);
                    System.out.println(ctd + " - " + l);
                    ctd++;

                }
            }
            if (livros_lib.isEmpty()) {
                output("Sua biblioteca ainda não anunciou nenhum livro.");
                return;
            }

            int indice = getInt("o índice do livro que você quer editar", "O índice")-1;
            while (indice < 0 || indice >= livros_lib.size()) {
                output("Algo deu errado. Verifique sua resposta e tente novamente");
                indice = getInt("o índice do livro que você quer editar", "O índice")-1;
            }
            Livro livro = livros_lib.get(indice);

            String e = getString("1 para editar categorias ou 2 para editar quantidade", "Sua escolha");
            if (e == null) {
                return;
            }
            while (!e.equals("1") && !e.equals("2")) {
                output("Escolha inválida. Digite /menu pra voltar ao menu.");
                e = getString("1 para editar categorias ou 2 para editar quantidade", "Sua escolha");
                if (e == null) {
                    return;
                }
            }

            if (e.equals("1")) {
                System.out.print("Escolha 1 para adicionar categorias ou 2 para remover categorias: ");
                String escolha = scanner.nextLine();

                while (!escolha.equals("1") && !escolha.equals("2")) {
                    output("Escolha inválida. Digite /menu pra voltar ao menu.");
                    System.out.print("Escolha 1 para adicionar categorias ou 2 para remover categorias: ");
                    escolha = scanner.nextLine();

                    if(escolha.equals("/menu")) {
                        return;
                    }
                }

                if(escolha.equals("1")) {
                    output("Digite as categorias do livro e digite -1 quando tiver concluído: ");
                    int contador = livro.categorias.size()+1;
                    while (true) {
                        System.out.print("Digite aqui (categoria nº" + contador + "): ");
                        String categoria = scanner.nextLine().strip().toUpperCase();

                        if (categoria.equals("-1")) {
                            break;
                        }

                        if (!categoria.isBlank()) {
                            if (!livro.categorias.contains(categoria)) {
                                contador++;
                                livro.categorias.add(categoria);
                            } else {
                                output("O livro já possui essa categoria.");
                            }
                        }
                    }
                } else {
                    if (livro.categorias.isEmpty()) {
                        output("Livro não tem nenhuma categoria.");
                        return;
                    } else {
                        output("Escolha a categoria do livro a ser removida: ");
                        int c = 0;
                        for (String d : livro.categorias) {
                            System.out.println((c + 1) + " - " + d);
                            c++;
                        }
                        int escolha2;
                        while (true) {
                            try {
                                System.out.print("Digite aqui: ");
                                escolha2 = Integer.parseInt(scanner.nextLine().strip());
                                if (escolha2 <= (livro.categorias.size() + 1) && escolha2 > 0) {
                                    livro.categorias.remove(escolha2 - 1);
                                    output("Categoria removida com sucesso.");
                                    break;
                                } else {
                                    throw new Exception();
                                }
                            } catch (Exception ignored) {
                                output("Algo deu errado.\n");
                                output("Escolha a categoria do livro a ser removida: ");
                            }
                        }
                    }
                }
            } else {
                output("Editando quantidade de livros disponíveis");
                int atual = livro.qtd_disponivel;
                livro.qtd_disponivel = getInt("a nova quantidade", "A quantidade de livros");
                Biblioteca lib = null;
                for (Biblioteca b: bibliotecas) {
                    if (b.getUsername().equals(livro.biblioteca.getUsername())) {
                        lib = b;
                    }
                }
                if (lib == null) {
                    output("Algo deu errado. Tente novamente");
                    return;
                }

                while (livro.qtd_disponivel < 0) {
                    output("A quantidade de livros disponíveis não pode ser menor que 0.");
                    livro.qtd_disponivel = getInt("a nova quantidade", "A quantidade de livros");
                }
                lib.livros_disponiveis = lib.livros_disponiveis - atual + livro.qtd_disponivel;
                output("Quantidade de livros atualizada com sucesso.");
            }
        }
    }

    private void gerenciarFinancasB() {
        if(usuario.getTipo().equals("class Biblioteca")) {
            Biblioteca b = (Biblioteca) usuario;
            double previsto = 0;
            for (Aluguel a: alugueis) {
                if (a.biblioteca.getUsername().equals(usuario.getUsername()) && !a.concluido) {
                    previsto = previsto + a.valor + a.multa;
                }
            }
            double historico = 0;
            for (Aluguel a: alugueis) {
                if (a.biblioteca.getUsername().equals(usuario.getUsername()) && a.concluido) {
                    historico = historico + a.valor + a.multa;
                }
            }

            System.out.println();
            System.out.println("Relatório de Lucros de " + b.getNome()  + ": ");
            System.out.println("    Lucros de hoje: R$ " + b.getAlugados() + "0");
            System.out.println("    Lucro total previsto: R$ " + previsto + "0");
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

        output("Obrigado por utilizar o Página.");
        System.exit(0);
    }
}
