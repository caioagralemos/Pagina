import java.util.ArrayList;

public class Livro {
    Biblioteca biblioteca;
    ArrayList<String> categorias;
    int idLivro;
    String titulo;
    String autor;
    int paginas;
    int qtd_disponivel;
    int qtd_alugados;

    public Livro(Biblioteca biblioteca, ArrayList<String> categorias, int idLivro, String titulo, String autor, int paginas, int qtd_disponivel) {
        this.biblioteca = biblioteca;
        this.categorias = categorias;
        this.idLivro = idLivro;
        this.titulo = titulo;
        this.autor = autor;
        this.paginas = paginas;
        this.qtd_disponivel = qtd_disponivel;
        this.qtd_alugados = 0;
    }

    public String toString() {
        return this.titulo + ", de " + this.autor + " (" +this.qtd_disponivel + " disponíveis).";
    }

    public String descricao() {
        return this.titulo + "\n    de " + this.autor + "\n    " + this.paginas + " páginas" + "\n    disponibilizado por " + this.biblioteca.getNome() + "\n    Categorias: " + this.categorias + "\n    " + this.qtd_disponivel + " livros disponíveis pra aluguel";
    }
}
