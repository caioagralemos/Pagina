import java.util.ArrayList;

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

    public boolean login(String senha) {
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

    public boolean setSenha(String senha_atual, String senha) {
        if(login(senha_atual)) {
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
}
