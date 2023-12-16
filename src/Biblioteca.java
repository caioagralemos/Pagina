import java.util.ArrayList;

public class Biblioteca extends Usuario {
    private int livros_disponiveis;
    private int livros_alugados;
    ArrayList<Livro> livros;
    ArrayList<Aluguel> alugueis;
    ArrayList<Aluguel> concluidos;
    public Biblioteca(String nome, String username, String senha) {
        super(nome, username, senha);
        this.livros_disponiveis = 0;
        this.livros_alugados = 0;
        this.livros = new ArrayList<>();
        this.alugueis = new ArrayList<>();
        this.concluidos = new ArrayList<>();
    }

    public void disponibilizou() {
        this.livros_disponiveis++;
        this.livros_alugados--;
    }
    public void alugou() {
        this.livros_alugados++;
        this.livros_disponiveis--;
    }

    public int getAlugados() {
        return livros_alugados;
    }
}
