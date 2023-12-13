import java.util.ArrayList;

public class Cliente extends Usuario {
    ArrayList<Aluguel> alu_atuais;
    ArrayList<Aluguel> alu_concluidos;

    public Cliente(String nome, String username, String senha) {
        super(nome, username, senha);
        this.alu_atuais = new ArrayList<>();
        this.alu_concluidos = new ArrayList<>();
    }
}
