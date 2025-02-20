package progetto;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Autenticazione {
    private Map<String, String> credenziali = new HashMap<>(); // Mappa username -> password
    private static final String USERNAME_DEFAULT = "configuratore";
    private static final String PASSWORD_DEFAULT = "password";
    private static final String CREDENZIALI = "credenziali.xml"; //boh spe

    public Autenticazione() {
        // Credenziali predefinite per il primo accesso
        credenziali.put(USERNAME_DEFAULT, PASSWORD_DEFAULT);
    }

    public boolean primoAccesso(String username, String password) {
        // Verifica delle credenziali predefinite per il primo accesso
        return USERNAME_DEFAULT.equals(username) && PASSWORD_DEFAULT.equals(password);
    }

    public boolean verificaCredenziali(String username, String password) {
        // Verifica se le credenziali sono già state impostate
        return credenziali.containsKey(username) && credenziali.get(username).equals(password);
    }

    public void registraNuoveCredenziali(String nuovoUsername, String nuovaPassword) {
        // Rimuove le credenziali predefinite e imposta quelle nuove
        credenziali.remove(USERNAME_DEFAULT);
        credenziali.put(nuovoUsername, nuovaPassword);
    }

    public boolean esisteUsername(String username) {
        return credenziali.containsKey(username);
    }

    public void salvaCredenziali() {
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
    }
}