package progetto;

import java.util.ArrayList;
import java.util.List;

public class ComprensorioWrapper {

    private ArrayList<Comprensorio> comprensori;
    
    public ComprensorioWrapper(ArrayList<Comprensorio> comprensori) {
    	this.comprensori = comprensori;
	}

	
    public ComprensorioWrapper() {
	}


	public ArrayList<Comprensorio> getComprensori() {
		if (comprensori == null) {
	        comprensori = new ArrayList<>();
	    }
	    return comprensori;
    }

    public void setComprensori(ArrayList<Comprensorio> comprensori) {
        this.comprensori = comprensori;
    }
    
    public void aggiungiNuovoComprensorio(String nomeComprensorio, List<String> comuni) {
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
    
    /* 
     * IN CLASSE GESTIONEPERSISTENZE
     * 
    public static void salvaConfigurazione(ComprensorioWrapper configurazione, String filename) {
        try {
            JAXBContext context = JAXBContext.newInstance(ComprensorioWrapper.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(configurazione, new File(filename));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
   	public static ComprensorioWrapper caricaConfigurazione(String filename) {
		try {
			JAXBContext context = JAXBContext.newInstance(ComprensorioWrapper.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			return (ComprensorioWrapper) unmarshaller.unmarshal(new File(filename));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}*/
}
