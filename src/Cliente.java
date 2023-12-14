import java.util.ArrayList;

public class Cliente extends Usuario {
    double saldo;
    ArrayList<Aluguel> alu_atuais;
    ArrayList<Aluguel> alu_concluidos;

    public Cliente(String nome, String username, String senha) {
        super(nome, username, senha);
        this.saldo = 0;
        this.alu_atuais = new ArrayList<>();
        this.alu_concluidos = new ArrayList<>();
    }
}
