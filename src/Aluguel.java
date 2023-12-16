public class Aluguel {
    int id_aluguel;
    Livro livro;
    Cliente cliente;
    Biblioteca biblioteca;
    Data limite;
    double valor;
    double multa;
    boolean concluido;

    public Aluguel (int id_aluguel, Livro livro, Cliente cliente, Biblioteca biblioteca, Data limite) {
        this.id_aluguel = id_aluguel;
        this.livro = livro;
        this.cliente = cliente;
        this.biblioteca = biblioteca;
        this.limite = limite;
        this.valor = 3 * limite.daysDifference();
        this.multa = 0;
        this.concluido = false;
    }

    public String toString() {
        return "(ID Nº" + this.id_aluguel + ") Aluguel do livro " + this.livro.titulo + " da biblioteca " + biblioteca.getNome() + " pelo cliente @" + this.cliente.getUsername() + " com prazo limite no dia " + this.limite + " e valor final de R$ " + this.valor + "0.";
    }
}
