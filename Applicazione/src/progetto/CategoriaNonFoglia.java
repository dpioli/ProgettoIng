package progetto;
import java.util.HashMap;

public class CategoriaNonFoglia extends Categoria {

    //private ArrayList<String> campoCaratteristico = new ArrayList<>(); // Desc()rizione opzionale
	private HashMap<String, String> campoCaratteristico = new HashMap<>();
	
	/*
	 * VERSIONE ERA
	 * 
    public CategoriaNonFoglia(String nome, String campo, Integer dominio) {
        super(nome, false, dominio);
        campoCaratteristico.add(campo);
    }
	*/
    
    //VERSIONE DIEGO
    public CategoriaNonFoglia(String nome, CampoCaratteristico campoCaratteristico, Boolean completo, Integer dominio) {
    	super(nome, campoCaratteristico, completo, dominio);
    }

    public HashMap<String, String> getDescrizione() {
        return campoCaratteristico;
    }

    public void setDescrizione(String descrizione) {
    	for(String c: campoCaratteristico.keySet())
        this.campoCaratteristico.put(c, descrizione);
    }
    
    @Override
    public boolean isFoglia() {
        return false;
    }
}
