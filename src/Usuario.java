import java.util.ArrayList;
import java.util.Scanner;

public abstract class Usuario {
    private String nome;
    private String username;
    private String senha;
    private final String tipo;

    public Usuario(String nome, String username, String senha) {
        this.nome = nome;
        this.username = username;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < senha.length(); i++) {
            char c = senha.charAt(i);
            sb.append((char)(c + 5));
        }
        this.senha = sb.toString();
        this.tipo = String.valueOf(this.getClass());
    }

    public boolean login() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite a senha de sua conta: ");
        String senha = scanner.nextLine();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < senha.length(); i++) {
            char c = senha.charAt(i);
            sb.append((char)(c + 5));
        }
        return this.senha.contentEquals(sb);
    }

    public String getNome() {
        return nome;
    }

    public void setNome() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite seu nome ou deixe em branco para continuar com " + this.nome + ": ");
        String nome = scanner.nextLine().strip();
        if (!nome.isBlank()) {
            this.nome = nome;
        }
    }

    public String getUsername() {
        return username;
    }

    public String getTipo() {
        return tipo;
    }

    public boolean setSenha() {
        if(login()) {
            while (true) {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Digite sua nova senha: ");
                String s1 = scanner.nextLine();
                System.out.print("Confirme sua nova senha: ");
                String s2 = scanner.nextLine();
                if (s1.equals(s2)) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < s1.length(); i++) {
                        char c = s1.charAt(i);
                        sb.append((char)(c + 5));
                    }
                    System.out.println("Página diz: Senha alterada com sucesso.");
                    this.senha = sb.toString();
                    return true;
                }
            }
        } else {
            return false;
        }
    }

    public String toString() {
        return "@" + this.username + " - Nome: " + this.nome;
    }
}
