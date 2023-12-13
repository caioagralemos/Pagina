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
            sb.append((char)(c + i));
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
            sb.append((char)(c + i));
        }
        return this.senha.contentEquals(sb);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsername() {
        return username;
    }

    public String getTipo() {
        return tipo;
    }

    public boolean setSenha(String senha) {
        if(login()) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < senha.length(); i++) {
                char c = senha.charAt(i);
                sb.append((char)(c + i));
            }
            this.senha = sb.toString();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "@" + this.username + " - Classe: " + this.tipo;
    }
}
