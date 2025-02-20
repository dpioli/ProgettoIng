package progetto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Categoria {

	private String nome;  // Nome della categoria
    private boolean foglia;
    private List<Categoria> sottocategorie;
    // private ArrayList<String> campoCaratteristico = new ArrayList<>();  // Descrizione opzionale
    //private HashMap<String, String> campoCaratteristico;
    private CampoCaratteristico campoCaratteristico;
    private Boolean completo;
    private Integer dominio;
    
    public Categoria(String nome, boolean foglia, Integer dominio) {
        this.nome = nome;
        this.foglia = foglia;
        this.dominio = dominio;
    }
    
    //VERSIONE DIEGO
    public Categoria(String nome, CampoCaratteristico campoCaratteristico, Boolean completo, Integer dominio) {
    	this.nome = nome;
    	this.campoCaratteristico = new CampoCaratteristico();
    	this.completo = completo;
    	this.dominio = dominio;
    	this.sottocategorie = new ArrayList<Categoria>();
    }
    
    /*
     * VERSIONE ERA
     * 
    public void creaCategoriaNonFoglia(String nome, ArrayList<String> campo, Integer dominio) {
    	Categoria nonFoglia = new Categoria(nome, false, dominio);
    	for(int i = 0; i < dominio; i++) {
	    	for(String c: campo) {
	    		nonFoglia.campoCaratteristico.put(c, null);
	    	}
    	}  	
    }
    */
    
    /*
    public void aggiungiSottocategoria(String daAggiungere) {
    	if(dominio == null || sottocategorie.size() < dominio) {
    		if(this.foglia == true) {
    			foglia = false;
    		}
	        sottocategorie.add(new Categoria(daAggiungere, true, dominio));
	        campoCaratteristico.put(daAggiungere, daAggiungere);
	        dominio++;
        }else {
        	System.out.println("Ecceduto valore dominio.");
        }
    }
    */
    
    public HashMap<String, String> getValoriCampo(){
    	return campoCaratteristico.getValori();
    }
    
    public Boolean getCompleto() {
    	return completo;
    }
    
    
    public String getNome() {
        return nome;
    }

    public List<Categoria> getSottocategorie() {
        return sottocategorie;
    }
    

    public boolean isFoglia() {
    	return foglia;
    }
    
    public void cambiaInNonFoglia() {
    	foglia = false;
    	dominio++;
    	
    }
    
	public boolean eUguale(String nomeGerarchia) {
		return nome.equalsIgnoreCase(nomeGerarchia);
	}
	
	public boolean ePresenteGerarchia(String nomeCategoria) {
		for(Categoria c: sottocategorie) {
			if(c.eUguale(nomeCategoria))
				return true;
		}
		return false;
	}
	
	public String toString() {
		return sottocategorie.toString();
	}


}

