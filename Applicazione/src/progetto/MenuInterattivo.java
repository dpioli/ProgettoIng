package progetto;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
//
public class MenuInterattivo {

	//ETICHETTE MESSAGGI
	private static final String TAG_FINALE_STATO = "::S";
	private static final String TAG_INIZIALE_STATO = "S:";
	private static final String TAG_FINALE_PROMPT = "::P";
	private static final String TAG_INIZIALE_PROMPT = "P:";
	
	//STRINGHE USATE PER MESSAGGI VERSO CLIENT
	//menù configuratore
	private static final String MENU_CONFIGURATORE = "\n--- Menu Configuratore ---\n1. Introdurre nuovo comprensorio geografico\n2. Creare nuova gerarchia di categorie";
	private static final String VISUALIZZARE_COMPRENSORI_GEOGRAFICI = "3. Visualizzare comprensori geografici";
	private static final String VISUALIZZARE_GERARCHIE = "4. Visualizzare gerarchie";
	private static final String AGGIUNTA_A_CATEGORIA_ESISTENTE = "5. Aggiunta a categoria esistente";
	private static final String USCIRE = "6. Uscire";
	private static final String SCEGLIERE = "\nScelta: ";

	private static final String USCITA = "		Uscita...";
	private static final String INSERIMENTO_NON_VALIDO = "\nInserimento non valido. Inserisci un valore da 1 a 6.\n";
	//creazione comprensorio
	private static final String NOME_COMPRENSORIO = "Inserisci nome del nuovo comprensorio: ";
	private static final String NOMI_COMUNI = "Inserisci comuni del comprensorio (separati da virgola): ";
	//creazione gerarchia
	private static final String NOME_GERARCHIA = "Inserisci nome della gerarchia: ";
	private static final String RICHIESTA_AGGIUNTA_SOTTOCATEGORIE = "Vuoi completare la gerarchia aggiungendo le sottocategorie? ";
	private static final String NOME_CATEGORIA = "Inserisci nome della categoria: ";
	private static final String NOME_CAMPO_CARATTERISTICO = "Inserisci il nome del campo caratteristico della categoria %s, "
			+ "se non è presente inserisci 'nessuno': \n";
	private static final String VALORI_CAMPO = "Inserisci i valori del %s (separati da virgola): \n";
	
	private static final String RICHIESTA_ESTENSIBILITA = "Il dominio del campo caratteristico sarà estensibile in futuro?   "
			+ "sì: è estensibile. "
			+ "no: è completo.";
	private static final String GERARCHIA_PADRE = "Inserisci il nome della gerarchia a cui vuoi aggiungere una sottocategoria: ";
	private static final String CATEGORIA_PADRE = "Inserisci il nome della categoria a cui vuoi aggiungere una sottocategoria: ";

	private static final String GERARCHIA_CREATA = "Hai creato una nuova gerarchia: ";
	//extra
	private static final String SI = "si";
	private static final String NO = "no";
	private static final String NESSUNO = "nessuno";
	private static final String VIRGOLA = ",";
	private static final String PNT_ESCL = " ! ";
	
	//STRINGHE USATE PER TENER TRACCIA DELLE SCELTE DEL CLIENT
	private static final String PRESENTANDO_AL_CONFIGURATORE_IL_MENU = "\nPresentando al configuratore il menu...";
	private static final String RICHIESTA_USCITA = "Il client richiede l'uscita.";
	
	private static final String VALORE_NON_VALIDO_ATTESA = "\nIl configuratore ha inserito un valore non valido. \nIn attesa di input valido...\n";

	private static final String CREANDO_COMPRENSORIO = "Richiedendo al configuratore i dettagli del nuovo comprensorio.";
	
	private static final String CREANDO_GERARCHIA = "Richiedendo al configuratore i dettagli della nuova gerarchia.";
	private static final String AGGIUNGENDO_SOTTOCATEGORIE = "Il configuratore sta aggiungendo sottocategorie.";
	private static final String SOTTOCATEGORIA_A_CATEGORIA_ESISTENTE = "\nIl configuratore vuole aggiungere una sottocategoria a una categoria già esistente.";
	
	private Integer opzione;
    private Scanner scanner;
    private ClientHandler clientHandler;
    PrintWriter out;

    public MenuInterattivo(Integer opzione, ClientHandler clientHandler) {
        this.opzione = opzione;
        this.clientHandler = clientHandler;
        this.scanner = clientHandler.in;
        out = clientHandler.out;
    }

    // Metodo principale per far partire l'interfaccia
    public void start() {
    	if(opzione == 1) {
    		mostraMenuConfiguratore();
    	}
    }
    // Mostra il menu per il configuratore
    private void mostraMenuConfiguratore() {
        int scelta = 0;
        do {
            System.out.println(PRESENTANDO_AL_CONFIGURATORE_IL_MENU);
            inviaStatoAlClient(MENU_CONFIGURATORE);
            inviaStatoAlClient(VISUALIZZARE_COMPRENSORI_GEOGRAFICI);
            inviaStatoAlClient(VISUALIZZARE_GERARCHIE);
            inviaStatoAlClient(AGGIUNTA_A_CATEGORIA_ESISTENTE);
            inviaStatoAlClient(USCIRE);
            inviaPromptAlClient(SCEGLIERE);
            if(scanner.hasNextInt()) {
	            scelta = scanner.nextInt();
	            scanner.nextLine(); // Consuma il newline
	
	            switch (scelta) {
	                case 1:
	                    aggiungiComprensorio();
	                    break;
	                case 2:
	                    creaNuovaGerarchia();
	                    break;
	                case 3:
	                    visualizzaComprensori();
	                    break;
	                case 4:
	                    visualizzaGerarchie();
	                    break;
	                case 5:
	                	aggiuntaACategoriaEsistente();
	                	break;
	                case 6:
	                	inviaStatoAlClient(USCITA);
	                    System.out.println(RICHIESTA_USCITA);
	                    return;
	                default:
	                    System.out.println(VALORE_NON_VALIDO_ATTESA);
	                    inviaStatoAlClient(INSERIMENTO_NON_VALIDO);
	            }
            } else {
            	System.out.println(VALORE_NON_VALIDO_ATTESA);
            	inviaStatoAlClient(INSERIMENTO_NON_VALIDO);
            }
        } while (scelta != 6);
        return;
    }
    private void inviaPromptAlClient(String messaggio) {  
    	String daInviare = aggiungiEtichetta(messaggio, TAG_INIZIALE_PROMPT,TAG_FINALE_PROMPT);
    	out.println(daInviare); ///PROMPT
    }
    //RIGUARDA
    private void inviaPromptAlClient(String messaggio, String aggiunta) {  
    	String daInviare = aggiungiEtichetta(messaggio, TAG_INIZIALE_PROMPT, TAG_FINALE_PROMPT);
    	out.printf(daInviare, aggiunta); ///PROMPT
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
    // --- Funzionalità per il configuratore ---

	private void aggiungiComprensorio() {
		System.out.println(CREANDO_COMPRENSORIO);
		inviaPromptAlClient(NOME_COMPRENSORIO);
    	String nomeComprensorio = scanner.nextLine();
    	inviaPromptAlClient(NOMI_COMUNI);
    	//String comuni = scanner.nextLine();
    	List<String> nomiComuni = new ArrayList<String>();
    	if(scanner.hasNextLine()) {
    		String input = scanner.nextLine();
    		String[] daAggiungere = input.split(VIRGOLA);
    		for(String nuovo : daAggiungere) {
    			nomiComuni.add(nuovo.trim());
    		}
    	}
    	clientHandler.gestisciCreazioneComprensorio(nomeComprensorio, nomiComuni);
    	return;
    }
    
    //versione Diego
    private void creaNuovaGerarchia() {
		System.out.println(CREANDO_GERARCHIA);
		inviaPromptAlClient(NOME_GERARCHIA);
        String nomeGerarchia = scanner.nextLine();
        
        inviaPromptAlClient(NOME_CAMPO_CARATTERISTICO, nomeGerarchia);
    	String nomeCampo = scanner.nextLine();
    	
    	inviaPromptAlClient(VALORI_CAMPO , nomeCampo);
    	
    	ArrayList<String> valoriCampo = new ArrayList<>();
    	if(scanner.hasNextLine()) {
    		String input = scanner.nextLine();
    		String[] daAggiungere = input.split(VIRGOLA);
    		for(String nuovo : daAggiungere) {
    			valoriCampo.add(nuovo.trim());
    		}
    	}
    	
    	inviaPromptAlClient(RICHIESTA_ESTENSIBILITA);
    	String estensibile = scanner.nextLine();
    	
    	boolean completo = (NO.equalsIgnoreCase(estensibile) ? true : false);
    	int dimensioneDominio = valoriCampo.size();	
    	
    	Gerarchia nuovaGerarchia = clientHandler.gestisciCreazioneGerarchia(nomeGerarchia, nomeCampo, valoriCampo, completo, dimensioneDominio);
    	
    	inviaStatoAlClient(GERARCHIA_CREATA + nomeGerarchia + PNT_ESCL);
    	
    	inviaPromptAlClient(RICHIESTA_AGGIUNTA_SOTTOCATEGORIE
    			+ SI 
    			+ NO);
    	String scelta = scanner.nextLine();
    	if(scelta == SI) {
    		aggiungiSottocategorie(nuovaGerarchia.getRadice());
    	} else {
    		//continue;
    	}
      
    }
    
    
    public void aggiungiSottocategorie(Categoria categoria) {
    	System.out.println(AGGIUNGENDO_SOTTOCATEGORIE);
    	//VERIFICARE CONTROLLO SULL'ESTENSIBILITA
    	if(categoria.getCompleto() == true) {
    		for(String v: categoria.getValoriCampo().keySet()) {
    			inviaPromptAlClient(NOME_CATEGORIA);
    			String nomeCategoria = scanner.nextLine();
    			
    			inviaPromptAlClient(NOME_CAMPO_CARATTERISTICO, nomeCategoria);
    			String nomeCampo = scanner.nextLine();
    			
    			if(!nomeCampo.equalsIgnoreCase(NESSUNO)) {
    				inviaPromptAlClient(VALORI_CAMPO, nomeCampo);
    				
    				ArrayList<String> valoriCampo = new ArrayList<>();
    				if(scanner.hasNextLine()) {
    					String input = scanner.nextLine();
    					String[] daAggiungere = input.split(VIRGOLA);
    					for(String nuovo : daAggiungere) {
    						valoriCampo.add(nuovo.trim());
    					}
    				}
    				
    				inviaPromptAlClient(RICHIESTA_ESTENSIBILITA);
    				String estensibile = scanner.nextLine();
    				
    				boolean completo = (NO.equalsIgnoreCase(estensibile) ? true : false);
    				int dimensioneDominio = valoriCampo.size();	
    				Categoria nuovaCategoria = clientHandler.gestisciCreazioneCategoria(nomeCategoria, nomeCampo, valoriCampo, completo, dimensioneDominio);
    				aggiungiSottocategorie(nuovaCategoria);
    				//categoria.getSottocategorie().add(nuovaCategoria); AGGIUNGERE LA CATEGORIA ALLA LISTA DELLE SOTTOCATEGORIE 
    			} else {
    				CategoriaFoglia nuovaFoglia = new CategoriaFoglia(nomeCategoria);
    				categoria.getSottocategorie().add(nuovaFoglia);
    				continue;
    			}
    		}
    		
    		
    	}
   
    }
    
    public void aggiuntaACategoriaEsistente() {
    	System.out.println(SOTTOCATEGORIA_A_CATEGORIA_ESISTENTE);
    	inviaPromptAlClient(GERARCHIA_PADRE);
    	String nomeGerarch = scanner.nextLine();
    	
    	inviaPromptAlClient(CATEGORIA_PADRE);
    	String nomeCateg = scanner.nextLine();
    	
    	clientHandler.ricercaSottocategorie(nomeGerarch, nomeCateg);
    	
    	
    	
    }
    

	public void visualizzaComprensori() {
        clientHandler.comprensori.toString();
    }

    public void visualizzaGerarchie() {
        System.out.println("Gerarchie presenti:");
    }
    
    /*
     * METODO SE VOGLIAMO ESTRARRE LA RICHIESTA DEI DATI
     * 
    public Object[] richiediDati() {
    	 System.out.print("Inserisci nome della gerarchia: ");
         String nomeGerarchia = scanner.nextLine();
         
         System.out.printf("Inserisci il nome del campo caratteristico della categoria %s: \n", nomeGerarchia);
     	String nomeCampo = scanner.nextLine();
     	
     	System.out.printf("Inserisci i valori del %s (separati da virgola: \n", nomeCampo);
     	
     	ArrayList<String> valoriCampo = new ArrayList<>();
     	if(scanner.hasNextLine()) {
     		String input = scanner.nextLine();
     		String[] daAggiungere = input.split(",");
     		for(String nuovo : daAggiungere) {
     			valoriCampo.add(nuovo.trim());
     		}
     	}
     	
     	System.out.println("Il dominio del campo caratteristico sarà estensibile in futuro?   "
     			+ "sì: è estensibile. "
     			+ "no: è completo.");
     	String estensibile = scanner.nextLine();
     	
     	boolean completo = ("no".equalsIgnoreCase(estensibile) ? true : false);
     	int dimensioneDominio = valoriCampo.size();	
    	
    	System.out.println("Vuoi completare la gerarchia aggiungendo le sottocategorie? "
    			+ " si " 
    			+ " no ");
    	String scelta = scanner.nextLine();
    	
    	return new Object[] {nomeGerarchia, nomeCampo, valoriCampo, estensibile, completo, dimensioneDominio, scelta};
    }
    */

    // --- Funzionalità per il fruitore ---
/*	QUESTI SONO DEL FRUITORE (VERSIONE 2)
    private void visualizzaCategorieDisponibili() {
        System.out.println("Categorie disponibili per scambi:");
    }
    private void proponiScambio() {
        System.out.print("Inserisci categoria di partenza: ");
        String categoria1 = scanner.nextLine();
        System.out.print("Inserisci categoria desiderata: ");
        String categoria2 = scanner.nextLine();
        System.out.print("Inserisci ore da scambiare: ");
        int ore = scanner.nextInt();
        scanner.nextLine(); // Consuma il newline
        // Proponi scambio al server
        System.out.println("Scambio proposto.");
    }

    private void visualizzaScambiEffettuati() {
        System.out.println("Scambi effettuati:");
    }*/
}
