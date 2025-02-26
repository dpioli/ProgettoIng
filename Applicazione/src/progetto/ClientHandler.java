package progetto;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

//Gestore di client individuale eseguito su thread separato
/**
 * Qui mettiamo tutto ciò che fa il Server, quindi questo è il protocollo che il Server segue per presentare al client le sue opzioni e per eseguire i comandi che ci sono
 * Qui devo implementare quasi tutti i metodi che sono presenti in Configuratore attualmente.
 * @author Erjona
 *
 */

public class ClientHandler implements Runnable {
	//ETICHETTE MESSAGGI
	private static final String TAG_FINALE_STATO = "::S";
	private static final String TAG_INIZIALE_STATO = "S:";
	private static final String TAG_FINALE_PROMPT = "::P";
	private static final String TAG_INIZIALE_PROMPT = "P:";
	
	//STRINGHE USATE PER TENER TRACCIA DEI MOVIMENTI DEL CLIENT
	//scelta ruolo
	private static final String SCELTA_RUOLO = "Seleziona il tuo ruolo:\n1. Configuratore\n2. Fruitore";
	private static final String RICHIEDENDO_RUOLO = "Richiedendo al client il suo ruolo.";
	private static final String EFFETTUATO_ACCESSO_FRUITORE = "\nIl client ha effettuato l'accesso in qualità di fruitore.";
	private static final String EFFETTUATO_ACCESO_CONFIGURATORE = "\nIl client ha effettuato l'acceso in qualità di configuratore.";
	//accesso
	private static final String IN_ATTESA_CREDENZIALI = "In attesa che il configuratore inserisca le credenziali...";
	//primo accesso
	private static final String RILEVATO_PRIMO_ACCESSO = "\nE' stato rilevato primo accesso.";
	private static final String RICHIESTA_NUOVA_PASSWORD = "\nRichiesta nuova password";
	private static final String RICHIESTA_NUOVO_USERNAME = "\nRichiesta nuovo username";
	
	private static final String ACCESSO_CON_SUCCESSO = "\nIl configuratore ha compiuto l'accesso con successo.";
	private static final String ANNULLATO_ACCESSO = "\nIl configuratore ha annullato l'accesso.";
	private static final String VALORE_NON_INTERO = "\nIl client non ha inserito un valore intero, viene riproposto il comando.";
	private static final String VALORE_NON_VALIDO = "\nIl client non ha inserito un valore valido, viene riproposto il comando.";
	
	//STRINGHE USATE PER MESSAGGI VERSO CLIENT
	private static final String SCELTA_NON_VALIDA = "\nScelta non valida. \nInserisci il valore 1 [Configuratore] oppure il valore 2 [Fruitore].";
	private static final String SCELTA_RUOLO_NON_VALIDA = "\nScelta non valida, non sono ammesse stringhe! \nInserisci il valore 1 [Configuratore] oppure il valore 2 [Fruitore].";
	private static final String ACCESSO = "\nAccesso effettuato con successo.";
	private static final String BENVENUTO = "\nBenvenuto ";
	private static final String FALLIMENTO_ACCESSO_CONFIGURATORE = "Accesso come configuratore fallito.";
	//accesso
	private static final String RICHIESTA_CREDENZIALI = "\nInserisci le credenziali del Configuratore:\n		[Inserire ESC per tornare indietro]";
	private static final String PASSWORD_ = "Password: ";
	private static final String USERNAME_ = "Username: ";
	private static final String CREDENZIALI_ERRATE = "\nCredenziali errate!";
	private static final String USERNAME_ESISTENTE = "Username già esistente. Per favore inserire un nuovo username.";
	private static final String MINIMO_PSW = "\nPer piacere inserire una password valida. (Una stringa non vuota e di lunghezza maggiore di 6)";
	//primo accesso
	private static final String PRIMO_ACCESSO_RILEVATO = "\nPrimo accesso rilevato. Si prega di impostare nuove credenziali.";
	private static final String INSERISCI_NUOVO_USERNAME = "Inserisci nuovo username: ";
	private static final String INSERISCI_NUOVA_PASSWORD = "Inserisci nuova password: ";
	private static final String SALVATO_PRIMO_ACCESSO = "Le nuove credenziali sono state salvate. Primo accesso avvenuto con successo.";
	//comprensorio geografico
	private static final String COMPRENSORIO_AGGIUNTO = "Comprensorio aggiunto con successo.";
	private static final String COMPRENSORIO_ESISTENTE = "Nome comprensorio inserito già presente!";
	//categoria
	private static final String SUCCESSO_GERARCHIA_CREAZIONE = "Gerarchia creata con successo.";
	private static final String GERARCHIA_ESISTENTE = "Esiste già una gerarchia con questo nome!";
	private static final String CATEGORIA_ESISTENTE = "Esiste già una Categoria con questo nome!";
	//extra
	private static final String PNT_ESCL = "!";
	private static final String ESC = "ESC";
	private static final String TORNANDO_MENÙ = "\nStai tornando al menù principale.";
	
	
	private Socket clientSocket;
    private GerarchiaWrapper gerarchie;
    private FattoriDiConversioneWrapper fattoriConversione;
    public ComprensorioWrapper comprensori;
    private GestorePersistenza gp;
    private Autenticazione autenticazione;
    public Scanner in;
    public PrintWriter out;
    
    public ClientHandler(Socket socket, GerarchiaWrapper gerarchie, FattoriDiConversioneWrapper fattoriConversione, ComprensorioWrapper comprensori, Map<String, String> credenziali, GestorePersistenza gp) {
        this.clientSocket = socket;
        this.gerarchie = gerarchie;
        this.fattoriConversione = fattoriConversione;
        this.comprensori = comprensori;
        this.autenticazione = new Autenticazione();
        this.gp = gp;
    }

    @Override
    public void run() {
        try {
            // Stream per ricevere dati dal client
            in = new Scanner(clientSocket.getInputStream()); //input client

            // Stream per inviare dati al client
            out = new PrintWriter(clientSocket.getOutputStream(), true);
           
            int scelta = 0;
            boolean accesso = false;
            MenuInterattivo menu ;
            do{
            	System.out.println(RICHIEDENDO_RUOLO);
            	inviaPromptAlClient(SCELTA_RUOLO);
            	//se è im intero accetta l'input del client e fa oartire l'accesso
            	//blocca finché il client non manda un input e poi lo processa
            	if(in.hasNextInt()) {
    	        	scelta = in.nextInt();
    	        	in.nextLine(); // Consuma il newline, rimettendosi in attesa del nuovo input
    	        
    	        	if (scelta == 1) {
    	        		accesso = gestisciAccessoConfiguratore();
    	        		if(accesso) {
    	        			//menu = new MenuInterattivo(scanner, 1, this);
    	        			menu = new MenuInterattivo(1, this);
    	        			System.out.println(EFFETTUATO_ACCESO_CONFIGURATORE);
    	        			menu.start();
    	        			break;
    	        		} else {
    	        			scelta = 0; // se l'accesso ritorna falso torno al menu di scelta
    	        			inviaStatoAlClient(FALLIMENTO_ACCESSO_CONFIGURATORE);
    	        		}
    	        	} else if (scelta == 2) {
    	        		//gestisciAccessoFruitore();
    	        		System.out.println(EFFETTUATO_ACCESSO_FRUITORE);
    	        		inviaStatoAlClient("Opzione ancora non disponibile.");
    	        		scelta = 0;
    	        	} else {
    	        		inviaStatoAlClient(SCELTA_NON_VALIDA);
    	        		System.out.println(VALORE_NON_VALIDO);
    	        	}
            	}else {
            		inviaStatoAlClient(SCELTA_RUOLO_NON_VALIDA);
            		System.out.println(VALORE_NON_INTERO);	
            		in.nextLine();
            		continue;
            	}
            } while((scelta != 1 || scelta != 2) && accesso == false);
          
           // out.write("DISCONNESSIONE");
            // Chiude le connessioni
            in.close();
            out.close();
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void inviaPromptAlClient(String messaggio) {  
    	String daInviare = aggiungiEtichetta(messaggio, TAG_INIZIALE_PROMPT, TAG_FINALE_PROMPT);
    	out.println(daInviare); ///PROMPT
    }
    private void inviaStatoAlClient(String messaggio) {
    	String daInviare = aggiungiEtichetta(messaggio, TAG_INIZIALE_STATO, TAG_FINALE_STATO);
    	out.println(daInviare); ///STATO
    }
    private String aggiungiEtichetta(String messaggio, String etichettaInizio, String etichettaFine) {
    	StringBuffer s = new StringBuffer(etichettaInizio);
    	s.append(messaggio);
    	s.append(etichettaFine);
    	return s.toString();
    }
    private boolean gestisciAccessoConfiguratore() {
    	Configuratore conf = new Configuratore(autenticazione);
    	inviaStatoAlClient(RICHIESTA_CREDENZIALI);
    	System.out.println(IN_ATTESA_CREDENZIALI);
    	String username = null;
        String password = null;
        
        do {  
        	inviaPromptAlClient(USERNAME_);
        	username = in.next();
        	in.nextLine();
        	
        	if(username.equalsIgnoreCase(ESC)) {
        		System.out.println(ANNULLATO_ACCESSO);
        		return false; //l'accesso è stato annullato
        	}
        	//BOH NON USATO DAVVERO? In teoria rimane in attessa finchè no ha un input perchè ha uno scanner  => dovrei chiedere se \n?
        	if(username.isEmpty()) {
        		inviaStatoAlClient("\nPer piacere inserire un username valido.");
        		continue;
        	}
        	
        	inviaPromptAlClient(PASSWORD_);
        	password = in.nextLine();
        	
        	if(password.equalsIgnoreCase(ESC)) {
        		System.out.println(ANNULLATO_ACCESSO);
        		inviaStatoAlClient(TORNANDO_MENÙ);
        		return false;
        	}
        	if(password.isEmpty() || password.length() < 7) {
        		inviaStatoAlClient(MINIMO_PSW);
        		continue;
        	}
  
        	if(loginConfiguratore(conf, username, password)) {
        		inviaStatoAlClient(ACCESSO_CON_SUCCESSO);
        		break;
        	}else {
        		inviaStatoAlClient(CREDENZIALI_ERRATE);
        	}
        	
        } while(true);
        //gp.salvaCredenziali(credenziali);
        System.out.println(ACCESSO_CON_SUCCESSO);
        inviaStatoAlClient(ACCESSO
        		+ BENVENUTO + username +PNT_ESCL);
        //mostraMenuConfiguratore();
        return true; //se arriva qui significa che l'accesso ha avuto successo, quindi mostra il menu
    }
    public boolean loginConfiguratore(Configuratore conf, String username, String password) {
    	if (autenticazione.primoAccesso(username, password)) {
            effettuaPrimoAccessoConfiguratore(conf);
            return true;       
    	} else if (conf.login(username, password)) {
            	return true;
        } else if (autenticazione.verificaCredenziali(username, password)) {
            return true; 
        } else {
            System.out.println(CREDENZIALI_ERRATE);
            return false;
        }
    }
    public void effettuaPrimoAccessoConfiguratore(Configuratore conf) {
    	HashMap<String, String> credenzialiConfig = new HashMap<>();
    	System.out.println(RILEVATO_PRIMO_ACCESSO);
    	inviaStatoAlClient(PRIMO_ACCESSO_RILEVATO);
        
    	System.out.println(RICHIESTA_NUOVO_USERNAME);
		String nuovoUsername;
		do{
			inviaPromptAlClient(INSERISCI_NUOVO_USERNAME);
			nuovoUsername = in.next();
			in.nextLine();
			if(conf.controllaUsername(nuovoUsername)) {
				inviaStatoAlClient(USERNAME_ESISTENTE);
			} else break;
		}while(true);
		
        System.out.println(RICHIESTA_NUOVA_PASSWORD);
        String nuovaPassword;
        do {
			inviaPromptAlClient(INSERISCI_NUOVA_PASSWORD);
			nuovaPassword = in.next();
			in.nextLine();
			if(nuovaPassword.length() < 7)
				inviaStatoAlClient(MINIMO_PSW);
        }while(nuovaPassword.length() < 7);
        
		conf.effettuaPrimoAccesso(nuovoUsername, nuovaPassword);
		credenzialiConfig.put(nuovoUsername, nuovaPassword);
		gp.salvaCredenzialiConfig(credenzialiConfig);
		inviaStatoAlClient(SALVATO_PRIMO_ACCESSO);
        return ;
    }
    
    public void gestisciCreazioneComprensorio(String nomeComprensorio, List<String> nomiComuni) {
 
    	if(comprensori.ePresenteComprensorio(nomeComprensorio)) {
    		inviaStatoAlClient(COMPRENSORIO_ESISTENTE);
    	} else {
    		comprensori.aggiungiNuovoComprensorio(nomeComprensorio, nomiComuni);
    		inviaStatoAlClient(COMPRENSORIO_AGGIUNTO);
    	}
    	gp.salvaComprensori(comprensori.getComprensori());
    	return;
    }
   
    public Gerarchia gestisciCreazioneGerarchia(String nomeGerarchia, String nomeCampo, ArrayList<String> valoriCampo, Boolean completo, Integer dimensioneDominio) {
    	
    	if(gerarchie.ePresenteGerarchia(nomeGerarchia)) {
    		inviaStatoAlClient(GERARCHIA_ESISTENTE);
    		//bisogna decidere cosa fare!!
    		gp.salvaGerarchie(gerarchie.getGerarchie());
    		return null;
    	} else {
    		CampoCaratteristico campoCaratteristico = new CampoCaratteristico(nomeCampo);
    		campoCaratteristico.aggiungiValori(valoriCampo);
    		CategoriaNonFoglia radice = new CategoriaNonFoglia(nomeGerarchia, campoCaratteristico, completo, dimensioneDominio);
    		Gerarchia nuovaGerarchia = new Gerarchia(radice);
    		gerarchie.aggiungiGerarchia(nuovaGerarchia);
    		gp.salvaGerarchie(gerarchie.getGerarchie());
    		inviaStatoAlClient(SUCCESSO_GERARCHIA_CREAZIONE);
    		return nuovaGerarchia;
    	}
    	
    }
    
    public Categoria gestisciCreazioneCategoria(String nomeCategoria, String nomeCampo, ArrayList<String> valoriCampo, Boolean completo, Integer dimDominio) {
    	
    	if(gerarchie.ePresenteGerarchia(nomeCategoria)) {
    		inviaStatoAlClient(CATEGORIA_ESISTENTE);
    		//bisogna decidere cosa fare!!
    		return null;
    	} else {
    		CampoCaratteristico campoCaratteristico = new CampoCaratteristico(nomeCampo);
    		campoCaratteristico.aggiungiValori(valoriCampo);
    		CategoriaNonFoglia nuovaCategoria = new CategoriaNonFoglia(nomeCategoria, campoCaratteristico, completo, dimDominio);
    		return nuovaCategoria;
    	}
    	
    }


	public void ricercaSottocategorie(String nomeGerarch, String nomeCateg) {
		// TODO Auto-generated method stub
		
	}
   
}
