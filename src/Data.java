import java.util.Calendar;
import java.util.GregorianCalendar;

public class Data {
    int dia;
    int mes;
    int ano;
    int hora;

    public Data() {
        Calendar cal = GregorianCalendar.getInstance();
        this.ano = cal.get(Calendar.YEAR);
        this.mes = cal.get(Calendar.MONTH) + 1;
        this.dia = cal.get(Calendar.DATE);
        int daytime = cal.get(Calendar.AM_PM);
        if (daytime == 0) {
            this.hora = cal.get(Calendar.HOUR);
        } else {
            this.hora = cal.get(Calendar.HOUR) + 12;
        }
    }

    public Data(int dia, int mes, int ano) {
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

    public Data(int dia, int mes, int ano, int hora) {
        Calendar cal = GregorianCalendar.getInstance();
        if (hora >= 0 && hora < 24) {
            this.hora = hora;
        }
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
            if (this.mes == 1 || this.mes == 3 || this.mes == 5 || this.mes == 7 || this.mes == 8 || this.mes == 10 || this.mes == 12) {
                if (this.dia < 31) {
                    this.dia++;
                } else {
                    this.dia = 1;
                    this.mes++;
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

    public int daysDifference() {
        Data hoje = new Data();
        Data limite = this;
        int contador = 0;
        if (limite.compare(hoje)) {
            while (!limite.hoje(hoje)) {
                limite.nextDay();
                contador++;
            }
            return contador;
        } else {
            return -1;
        }
    }

    public void setHora(int hora) {
        if (hora == 8 || hora == 9 || hora == 10 || hora == 11 || hora == 14 || hora == 15 || hora == 16 || hora == 17) {
            this.hora = hora;
        } else {
            throw new Error();
        }
    }

    public String toString() {
        return this.dia + "/" + this.mes + "/" + this.ano + " " + this.hora + ":00";
    }
}