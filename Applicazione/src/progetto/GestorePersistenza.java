package progetto;

import java.io.*;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GestorePersistenza {

    private static final String FILE_GERARCHIE = "../Applicazione/src/dati/gerarchie.json";
    private static final String FILE_COMPRENSORI = "../Applicazione/src/dati/comprensori.json";
    private static final String FILE_FATTORI = "../Applicazione/src/dati/fattoriDiConversione.json";
    private static final String FILE_CREDENZIALI_CONFIGURATORI = "../Applicazione/src/dati/credenzialiConfiguratori.json"; 
    private Gson gson;
    
    public GestorePersistenza() {
		super();
		this.gson = new GsonBuilder().setPrettyPrinting().create();
	}
    
    // Metodo per salvare la lista di gerarchie su file in formato JSON
    public void salvaGerarchie(List<Gerarchia> gerarchie) {
        try {
            FileWriter fileGerarchie = new FileWriter(FILE_GERARCHIE);
            gson.toJson(gerarchie, fileGerarchie);
			System.out.println("Salvataggio gerarchie in 'gerarchie.json' avvenuto con successo");
        } catch (IOException e) {
            System.err.println("Errore durante il salvataggio delle gerarchie: " + e.getMessage());
        }
    }

    // Metodo per caricare la lista di gerarchie da file JSON
    public GerarchiaWrapper caricaGerarchie() {
    	GerarchiaWrapper gerarchie = new GerarchiaWrapper();
        try {
           FileReader fileGerarchie = new FileReader(FILE_GERARCHIE);
           gerarchie = gson.fromJson(fileGerarchie, GerarchiaWrapper.class);
        } catch (IOException e) {
            System.err.println("Errore durante il caricamento delle gerarchie: " + e.getMessage());
        }
        return gerarchie;
    }

    // Metodo per salvare i comprensori geografici su file in formato JSON
    public void salvaComprensori(ArrayList<Comprensorio> comprensori) {
        try {
            FileWriter fileComprensori = new FileWriter(FILE_COMPRENSORI);
            gson.toJson(comprensori, fileComprensori);
            System.out.println("Salvataggio comprensori in 'comprensori.json' avvenuto con successo");
        } catch (IOException e) {
            System.err.println("Errore durante il salvataggio dei comprensori: " + e.getMessage());
        }
    }

    // Metodo per caricare i comprensori da file JSON
    public ComprensorioWrapper caricaComprensori() {
    	ComprensorioWrapper comprensori = new ComprensorioWrapper();
        try {
            FileReader fileComprensori = new FileReader(FILE_COMPRENSORI);
            comprensori = gson.fromJson(fileComprensori, ComprensorioWrapper.class);
        } catch (IOException e) {
            System.err.println("Errore durante il caricamento dei comprensori: " + e.getMessage());
        }
        return comprensori;
    }

    // Metodo per salvare i fattori di conversione su file in formato JSON
    public void salvaFattoriConversione(Map<String, Double> fattoriConversione) {
        try {
           FileWriter fileFattoriConv = new FileWriter(FILE_FATTORI);
           gson.toJson(fattoriConversione, fileFattoriConv);
        } catch (IOException e) {
            System.err.println("Errore durante il salvataggio dei fattori di conversione: " + e.getMessage());
        }
    }

    // Metodo per caricare i fattori di conversione da file JSON
    public FattoriDiConversioneWrapper caricaFattoriConversione() {
    	FattoriDiConversioneWrapper fattoriConv = new FattoriDiConversioneWrapper();
        try {
           FileReader fileFattConv = new FileReader(FILE_FATTORI);
           fattoriConv = gson.fromJson(fileFattConv, FattoriDiConversioneWrapper.class);
        } catch (IOException e) {
            System.err.println("Errore durante il caricamento dei fattori di conversione: " + e.getMessage());
        }
        return fattoriConv;
    }
    
    //Metodo per caricare le credenziali su file in formato JSON
    public void salvaCredenzialiConfig(HashMap<String, String> credenzialiConfig) {
    	try {
    		FileWriter fileCredenzialiConfig = new FileWriter(FILE_CREDENZIALI_CONFIGURATORI);
    		gson.toJson(credenzialiConfig, fileCredenzialiConfig);
    	} catch (IOException e) {
    		System.err.println("Errore durante il salvataggio dei credenziali: " + e.getMessage());
    	}
    }
    
    // Metodo per caricare i fattori di conversione da file XML
    public HashMap<String, String> caricaCredenzialiConfig() {
    	HashMap<String, String> credenzialiConfig = new HashMap<>();
    	try {
    		FileReader fileCredenzialiConfig = new FileReader(FILE_CREDENZIALI_CONFIGURATORI);
    		credenzialiConfig = gson.fromJson(fileCredenzialiConfig, HashMap.class);
    	} catch (IOException e) {
    		System.err.println("Errore durante il caricamento delle credenziali: " + e.getMessage());
    	}
    	return credenzialiConfig;
    }
    
}