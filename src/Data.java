import java.util.Calendar;
import java.util.GregorianCalendar;

public class Data {
    int dia;
    int mes;
    int ano;

    public Data(int dia, int mes, int ano) {
        this.ano = ano;
        this.mes = mes;
        this.dia = dia;
    }

    public Data() {
        Calendar cal = GregorianCalendar.getInstance();
        this.ano = cal.get(Calendar.YEAR);
        this.mes = cal.get(Calendar.MONTH) + 1;
        this.dia = cal.get(Calendar.DATE);
    }

    public Data(int dia, int mes, int ano, boolean check) {
        Calendar cal = GregorianCalendar.getInstance();
        if (ano > cal.get(Calendar.YEAR)) {
            this.ano = ano;
            this.mes = mes;
            this.dia = dia;
        } else if (ano == cal.get(Calendar.YEAR)) {
            if (mes == (cal.get(Calendar.MONTH) + 1)) {
                if (mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12) {
                    if (dia >= cal.get(Calendar.DATE) && dia <= 31) {
                        this.ano = ano;
                        this.mes = mes;
                        this.dia = dia;
                    } else {
                        throw new Error();
                    }
                } else if (mes == 4 || mes == 6 || mes == 9 || mes == 11) {
                    if (dia >= cal.get(Calendar.DATE) && dia <= 30) {
                        this.ano = ano;
                        this.mes = mes;
                        this.dia = dia;
                    } else {
                        throw new Error();
                    }
                } else {
                    if (dia >= cal.get(Calendar.DATE) && dia <= 29) {
                        this.ano = ano;
                        this.mes = mes;
                        this.dia = dia;
                    } else {
                        throw new Error();
                    }
                }
            } else if (mes > (cal.get(Calendar.MONTH) + 1) && mes <= 12) {
                this.ano = ano;
                this.mes = mes;
                this.dia = dia;
            } else {
                throw new Error();
            }
        } else {
            throw new Error();
        }
    }

    public boolean hoje(Data data2) {
        return this.ano == data2.ano && this.mes == data2.mes && this.dia == data2.dia;
    }

    public String adh() {
        Data d = new Data();
        if (this.ano > d.ano) {
            return "depois";
        } else if (this.ano == d.ano) {
            if (this.mes > d.mes) {
                return "depois";
            } else if (this.mes == d.mes) {
                if (this.dia == d.dia) {
                    return "hoje";
                } else if (this.dia > d.dia) {
                    return "depois";
                } else {
                    return "antes";
                }
            } else {
                return "antes";
            }
        } else {
            return "antes";
        }
    }

    public boolean compare(Data data2) {
        if (this.ano > data2.ano) {
            return true;
        } else if (this.ano == data2.ano) {
            if (this.mes > data2.mes) {
                return true;
            } else if (this.mes == data2.mes) {
                return this.dia >= data2.dia;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void nextDay() {
        if (this.dia >= 1 && this.dia < 28) {
            this.dia++;
        } else {
            if (this.mes == 1 || this.mes == 3 || this.mes == 5 || this.mes == 7 || this.mes == 8 || this.mes == 10) {
                if (this.dia < 31) {
                    this.dia++;
                } else {
                    this.dia = 1;
                    this.mes++;
                }
            } else if (this.mes == 12) {
                if (this.dia < 31) {
                    this.dia++;
                } else {
                    this.dia = 1;
                    this.mes = 1;
                    this.ano++;
                }
            } else if (this.mes == 2) {
                if (this.ano % 4 == 0 && this.ano % 100 != 0) {
                    this.dia = 1;
                    this.mes++;
                } else {
                    if (this.dia < 29) {
                        this.dia++;
                    } else {
                        this.dia = 1;
                        this.mes++;
                    }
                }
            } else {
                if (this.dia < 30) {
                    this.dia++;
                } else {
                    this.dia = 1;
                    this.mes++;
                }
            }
        }
    }

    public int daysDifferenceF() {
        Data hoje = new Data();
        Data limite = this;
        int contador = 0;
        if (limite.compare(hoje)) {
            while (!limite.hoje(hoje)) {
                contador++;
                hoje.nextDay();
            }
            return contador;
        } else {
            return -1;
        }
    }

    public int daysDifferenceP() {
        Data limite = new Data();
        Data hoje = new Data(this.dia, this.mes, this.ano);
        int contador = 0;
        if (limite.compare(hoje)) {
            while (!limite.hoje(hoje)) {
                contador++;
                hoje.nextDay();
            }
            return contador;
        } else {
            return -1;
        }
    }

    public String toString() {
        return this.dia + "/" + this.mes + "/" + this.ano;
    }
}