package progetto;
public class Gerarchia {
    private Categoria radice;

    public Gerarchia(Categoria radice) {
        this.radice = radice;
    }

    public Categoria getRadice() {
        return radice;
    }
    /*   
    public Categoria trovaCategoria(String nomeCategoria) {
        return trovaCategoriaRicorsiva(this.radice, nomeCategoria); // Iniziamo dalla radice dell'albero
    }
    */
    
    /*
    private Categoria trovaCategoriaRicorsiva(Categoria categoria, String nomeCategoria) {
        // Caso base: se il nome della categoria corrente è quello che cerchiamo
        if (categoria.getNome().equalsIgnoreCase(nomeCategoria)) {
            return categoria;
        }
        
        // Se la categoria corrente non è foglia, cerchiamo nelle sottocategorie
        if (!categoria.isFoglia()) {
            for (Categoria sottocategoria : categoria.getSottocategorie().values()) {
                Categoria trovata = trovaCategoriaRicorsiva(sottocategoria, nomeCategoria);
                if (trovata != null) {
                    return trovata;
                }
            }
        }
 
        
        // Se non troviamo nulla, ritorniamo null
        return null;
    }
    */

	public boolean eUgualeNomeRadice(String nomeGerarchia) {
		return radice.eUguale(nomeGerarchia);
	}
	
	public String toString() {
		return "Gerarchia: " + radice + "Sottocategorie: " + radice.toString();
	}
}
