public class Aluguel {
    int id_aluguel;
    Livro livro;
    Cliente cliente;
    Biblioteca biblioteca;
    Data limite;
    boolean expirado;
    boolean concluido;
    double valor_total;
    double multa;

    public String toString() {
        return "Aluguel do livro " + this.livro.titulo + " da biblioteca " + biblioteca.getNome() + " pelo cliente @" + this.cliente.getUsername() + " com prazo limite no dia " + this.limite + ".";
    }
}
