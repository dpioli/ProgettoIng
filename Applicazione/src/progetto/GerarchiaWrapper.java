package progetto;

import java.util.ArrayList;
import java.util.List;

public class GerarchiaWrapper {

    private List<Gerarchia> gerarchie = new ArrayList<>();
    
    public GerarchiaWrapper(List<Gerarchia> gerarchie) {
    	this.gerarchie = gerarchie;
    }
    
    public GerarchiaWrapper() {
    	
    }

    public List<Gerarchia> getGerarchie() {
    	//if (gerarchie != null)
    		return gerarchie;
    }
    
    public void setGerarchie(List<Gerarchia> gerarchie) {
    	this.gerarchie = gerarchie;
    }
    
    public void aggiungiGerarchia(Gerarchia radice) {
    	if(gerarchie != null)
    		gerarchie.add(radice);
    }	

	public boolean ePresenteGerarchia(String nomeGerarchia) {
		for(Gerarchia g: gerarchie) {
			if(g.eUgualeNomeRadice(nomeGerarchia))
				return true;
		}
		return false;
	}
	
	public Gerarchia estraiGerarchia(String nomeGerarchia) {
		for(Gerarchia g: gerarchie) {
			if (g.eUgualeNomeRadice(nomeGerarchia))
				return g;
		}
		return null;
	}
	   
    public String toString() {
    	StringBuffer b = new StringBuffer();
        for(Gerarchia g: gerarchie) {
        	b.append("\nGerarchia" + g.toString());
        }
        return b.toString();
    }

}
