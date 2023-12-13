import java.util.ArrayList;

public class Biblioteca extends Usuario {
    int total_livros;
    int total_alugados;
    private ArrayList<Livro> livros;
    private ArrayList<Aluguel> alugueis;
    private ArrayList<Aluguel> concluidos;
    public Biblioteca(String nome, String username, String senha) {
        super(nome, username, senha);
        this.livros = new ArrayList<>();
        this.alugueis = new ArrayList<>();
        this.concluidos = new ArrayList<>();
    }
}
