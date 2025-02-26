package progetto;

import java.util.HashMap;


public class Autenticazione {
	GestorePersistenza gp;
    private HashMap<String, String> credenziali = new HashMap<>(); // Mappa username -> password
    private static final String USERNAME_DEFAULT = "configuratore";
    private static final String PASSWORD_DEFAULT = "password";
   // private static final String CREDENZIALI = "../Applicazione/src/dati/credenzialiConfiguratori.json"; //boh spe

    public Autenticazione(GestorePersistenza gp) {
        //credenziali.put(USERNAME_DEFAULT, PASSWORD_DEFAULT);
        this.gp = gp;
        this.credenziali = gp.caricaCredenzialiConfig();
    }

    public boolean primoAccesso(String username, String password) {
        // Verifica delle credenziali predefinite per il primo accesso
        return USERNAME_DEFAULT.equals(username) && PASSWORD_DEFAULT.equals(password);
    }

    public boolean verificaCredenziali(String username, String password) {
        // Verifica se le credenziali sono già state impostate
    	if(credenziali.isEmpty())
    		return false;
    	return credenziali.containsKey(username) && credenziali.get(username).equals(password);
    }

    public void registraNuoveCredenziali(String nuovoUsername, String nuovaPassword) {
        // Rimuove le credenziali predefinite e imposta quelle nuove
        //credenziali.remove(USERNAME_DEFAULT);
        credenziali.put(nuovoUsername, nuovaPassword);
        salvaCredenziali();
    }

    public boolean esisteUsername(String username) {
    	 if (credenziali == null) {
    	        System.err.println("Credenziali non inizializzate.");
    	        return false; // Or throw an exception
    	    }
    	    return credenziali.containsKey(username);
    	//if(credenziali != null)
    	//	return false;
    }

    public void salvaCredenziali() {
    	gp.salvaCredenzialiConfig(credenziali);
    	/*try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(CREDENZIALI)))) {
    		for(Map.Entry<String, String> entry: credenziali.entrySet()) {
    			writer.write(entry.getKey() + ":" + entry.getValue());
    			writer.newLine();
    		}
    		writer.flush();
			System.out.println("Salvataggio dei credenziali completato.");
		} catch (IOException e) {
			System.out.println("Errore durante il salvataggio delle credenziali: " + e.getMessage());
		
	    } */
    }
}