import java.util.ArrayList;

public class Biblioteca extends Usuario {
    int livros_disponiveis;
    int livros_alugados;
    public Biblioteca(String nome, String username, String senha) {
        super(nome, username, senha);
        this.livros_disponiveis = 0;
        this.livros_alugados = 0;
    }

    public void disponibilizou() {
        this.livros_disponiveis++;
        this.livros_alugados--;
    }
    public void alugou() {
        this.livros_alugados++;
        this.livros_disponiveis--;
    }

    public double getAlugados() {
        return livros_alugados*3;
    }
}
