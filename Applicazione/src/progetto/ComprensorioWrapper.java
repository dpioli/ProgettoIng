package progetto;

import java.util.ArrayList;
import java.util.List;

public class ComprensorioWrapper {

    private ArrayList<Comprensorio> comprensori = new ArrayList<>();
    
    public ComprensorioWrapper(ArrayList<Comprensorio> comprensori) {
    	this.comprensori = comprensori;
	}
	
    public ComprensorioWrapper() {
	}

	public ArrayList<Comprensorio> getComprensori() {
	    return comprensori;
    }

    public void setComprensori(ArrayList<Comprensorio> comprensori) {
        this.comprensori = comprensori;
    }
    
    public void aggiungiNuovoComprensorio(String nomeComprensorio, List<String> comuni) {
    	if(comprensori != null)
    		comprensori.add(new Comprensorio(nomeComprensorio, comuni));
    }
    
    public boolean ePresenteComprensorio(String nome) {
    	if(comprensori != null)
	    	for(Comprensorio c: comprensori) {
	    		if(c.ePresente(nome))
	    			return true;
	    	}
    	return false;
    }
    
    public String toString() {
    	StringBuffer b = new StringBuffer();
        for(Comprensorio c: comprensori) {
        	b.append("\nComprensorio" + c.toString());
        }
        return b.toString();
    }
}
