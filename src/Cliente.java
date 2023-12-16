import java.util.ArrayList;

public class Cliente extends Usuario {
    double saldo;

    public Cliente(String nome, String username, String senha) {
        super(nome, username, senha);
        this.saldo = 0;
    }

    public void setSaldo(double saldo) {
        this.saldo = this.saldo + saldo;
    }
}
