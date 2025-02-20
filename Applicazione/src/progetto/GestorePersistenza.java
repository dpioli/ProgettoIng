package progetto;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class GestorePersistenza {

    private static final String FILE_GERARCHIE = "/Applicazione/src/progetto/gerarchie.xml";
    private static final String FILE_COMPRENSORI = "comprensori.xml";
    private static final String FILE_FATTORI = "fattori_di_conversione.xml";
    private static final String CREDENZIALI = "credenziali.xml"; 

    // Metodo per salvare la lista di gerarchie su file in formato XML
    public void salvaGerarchie(List<Gerarchia> gerarchie) {
        try {
            JAXBContext context = JAXBContext.newInstance(GerarchiaWrapper.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            GerarchiaWrapper wrapper = new GerarchiaWrapper(gerarchie);
            wrapper.setGerarchie(gerarchie);

            marshaller.marshal(wrapper, new File(FILE_GERARCHIE));
        } catch (JAXBException e) {
            System.err.println("Errore durante il salvataggio delle gerarchie: " + e.getMessage());
        }
    }

    // Metodo per caricare la lista di gerarchie da file XML
    public List<Gerarchia> caricaGerarchie() {
        try {
            JAXBContext context = JAXBContext.newInstance(GerarchiaWrapper.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            GerarchiaWrapper wrapper = (GerarchiaWrapper) unmarshaller.unmarshal(new File(FILE_GERARCHIE));
            return wrapper.getGerarchie();
        } catch (JAXBException e) {
            System.err.println("Errore durante il caricamento delle gerarchie: " + e.getMessage());
            return List.of();
        }
    }

    // Metodo per salvare i comprensori geografici su file in formato XML
    public void salvaComprensori(ArrayList<Comprensorio> comprensori) {
        try {
            JAXBContext context = JAXBContext.newInstance(ComprensorioWrapper.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            ComprensorioWrapper wrapper = new ComprensorioWrapper(comprensori);
            wrapper.setComprensori(comprensori);

            marshaller.marshal(wrapper, new File(FILE_COMPRENSORI));
        } catch (JAXBException e) {
            System.err.println("Errore durante il salvataggio dei comprensori: " + e.getMessage());
        }
    }

    // Metodo per caricare i comprensori da file XML
    public ArrayList<Comprensorio> caricaComprensori() {
        try {
            JAXBContext context = JAXBContext.newInstance(ComprensorioWrapper.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            ComprensorioWrapper wrapper = (ComprensorioWrapper) unmarshaller.unmarshal(new File(FILE_COMPRENSORI));
            return wrapper.getComprensori();
        } catch (JAXBException e) {
            System.err.println("Errore durante il caricamento dei comprensori: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Metodo per salvare i fattori di conversione su file in formato XML
    public void salvaFattoriConversione(Map<String, Double> fattoriConversione) {
        try {
            JAXBContext context = JAXBContext.newInstance(FattoriDiConversioneWrapper.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            FattoriDiConversioneWrapper wrapper = new FattoriDiConversioneWrapper();
            wrapper.setFattoriDiConversione(fattoriConversione);

            marshaller.marshal(wrapper, new File(FILE_FATTORI));
        } catch (JAXBException e) {
            System.err.println("Errore durante il salvataggio dei fattori di conversione: " + e.getMessage());
        }
    }

    // Metodo per caricare i fattori di conversione da file XML
    public Map<String, Double> caricaFattoriConversione() {
        try {
            JAXBContext context = JAXBContext.newInstance(FattoriDiConversioneWrapper.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            FattoriDiConversioneWrapper wrapper = (FattoriDiConversioneWrapper) unmarshaller.unmarshal(new File(FILE_FATTORI));
            return wrapper.getFattoriDiConversione();
        } catch (JAXBException e) {
            System.err.println("Errore durante il caricamento dei fattori di conversione: " + e.getMessage());
            return new HashMap<>();
        }
    }
    
    //Metodo per caricare le credenziali su file in formato XML
    public void salvaCredenziali(Map<String, String> credenziali) {
    	try {
    		JAXBContext context = JAXBContext.newInstance(Autenticazione.class);
    		Marshaller marshaller = context.createMarshaller();
    		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
    		    		
    		marshaller.marshal(credenziali.toString(), new File(CREDENZIALI));
    	} catch (JAXBException e) {
    		System.err.println("Errore durante il salvataggio dei credenziali: " + e.getMessage());
    	}
    }
    
    // Metodo per caricare i fattori di conversione da file XML
    public HashMap<String, String> caricaCredenziali() {
    	try {
    		JAXBContext context = JAXBContext.newInstance(Autenticazione.class);
    		Unmarshaller unmarshaller = context.createUnmarshaller();
    		return (HashMap<String, String>) unmarshaller.unmarshal(new File(CREDENZIALI));
    	} catch (JAXBException e) {
    		System.err.println("Errore durante il caricamento delle credenziali: " + e.getMessage());
    		return new HashMap<>();
    	}
    }
    
    
  /*  
   * METODO VECCHIO PER SALVARE LE CREDENZIALI
   * 
   * public void salvaCredenziali(Map<String, String> credenziali) {
    	try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(CREDENZIALI)))) {
    		for(Map.Entry<String, String> entry: credenziali.entrySet()) {
    			writer.write(entry.getKey() + ":" + entry.getValue());
    			writer.newLine();
    		}
    		writer.flush();
			System.out.println("Salvataggio dei credenziali completato.");
		} catch (IOException e) {
			System.out.println("Errore durante il salvataggio delle credenziali: " + e.getMessage());
		
	    } 
    }*/
}