package progetto;

import java.util.ArrayList;
import java.util.List;

public class Comprensorio {
    private String nome;
    private List<String> comuni = new ArrayList<>();

    public Comprensorio(String nome, List<String> comuni) {
        this.nome = nome;
        this.comuni = comuni;
    }

    public String getNome() {
        return nome;
    }

    public void aggiungiComune(String comune) {
        comuni.add(comune);
    }
    
    public boolean ePresente(String cerca) {
    	return nome.equalsIgnoreCase(cerca);
    }

    public List<String> getComuni() {
        return comuni;
    }

    public String toString() {
        return "Comprensorio: " + nome + ", Comuni: " + comuni.toString();
    }
}
