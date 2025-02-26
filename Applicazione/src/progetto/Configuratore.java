package progetto;

public class Configuratore { 
	
	//private String username;
  //  private String password;
    private Autenticazione autenticazione;
    
    public Configuratore(Autenticazione autenticazione) {
        this.autenticazione = autenticazione;
    }
    
    public boolean controllaUsername(String username) {
    	return autenticazione.esisteUsername(username);
    }
    
    public boolean login(String username, String password) {	
        return autenticazione.verificaCredenziali(username, password);
    }
    
    public void ePrimoAccesso(String username, String password) {
    	
    }
	public void effettuaPrimoAccesso(String nuovoUsername, String nuovaPassword) {

		autenticazione.registraNuoveCredenziali(nuovoUsername, nuovaPassword);
		//autenticazione.salvaCredenziali();
		
	//	this.username = nuovoUsername;
		//this.password = nuovaPassword;
    }
}
    /*NELLE ALTRE CLASSI **************************************************
    public void creaGerarchia() {
        // Esempio di creazione della gerarchia
        CategoriaNonFoglia lezioniMusica = new CategoriaNonFoglia("Lezioni di musica", "tipo");
        lezioniMusica.aggiungiSottocategoria("teoria", new CategoriaFoglia("Lezioni di teoria musicale"));
        lezioniMusica.aggiungiSottocategoria("strumento", new CategoriaNonFoglia("Lezioni di strumento", "livello"));

        Gerarchia gerarchiaMusica = new Gerarchia(lezioniMusica);
        gerarchie.add(gerarchiaMusica);
    }
 
 ***************************************************
 */