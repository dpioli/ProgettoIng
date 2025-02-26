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
/*
    public void visualizzaFattoriConversione(FattoreConversione fattoriConversione) {
        System.out.println("Fattori di conversione disponibili:");
        for (Categoria cat1 : fattoriConversione.getFattori().keySet()) {
            for (Entry<Categoria, Double> entry : fattoriConversione.getFattori().get(cat1).entrySet()) {
                Categoria cat2 = entry.getKey();
                double valore = entry.getValue();
                System.out.println("Fattore tra " + cat1 + " e " + cat2 + ": " + valore);
            }
        }
    }
*/
  /*NON QUI  
    public void salvaDati() {
        GestorePersistenza gestore = new GestorePersistenza();
        gestore.salvaGerarchie(gerarchie);
        gestore.salvaComprensori(comprensorio);
        gestore.salvaFattoriConversione((Map<String, Double>) fattoriConversione);  // Salva i fattori di conversione
    }
    
    public void caricaDati() {
        GestorePersistenza gestore = new GestorePersistenza();
        gerarchie = gestore.caricaGerarchie();
        comprensorio = gestore.caricaComprensori();
        fattoriConversione = (FattoreConversione) gestore.caricaFattoriConversione();  // Carica i fattori di conversione
    }
*/
}

/*   public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 1234);
        	 BufferedReader serverIN = new BufferedReader(new InputStreamReader(socket.getInputStream())); //serverinput
        	 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        	 BufferedReader clienteIN = new BufferedReader(new InputStreamReader(System.in))
        	) {    
            System.out.println("Configuratore connesso al server.");

            String richiesta, risposta; //richiesta server, risposta user
            while(true) {
            	// Legge il messaggio del server e lo presenta al client
	            richiesta = serverIN.readLine();
	            System.out.println("\nS: " + richiesta);
	            // il client risponde e la risposta viene mandata al server
	            risposta = clienteIN.readLine();
	            out.println(risposta);
	            if (risposta.equals("DISCONNESSIONE") || richiesta.equals("DISCONNESSIONE")) {
	            	System.out.println("Disconnessione dal server.");
	            	break;
	            }
            };
            
            out.close();
            socket.close();

        } catch (IOException e) {
            //e.printStackTrace();
            System.err.println("Errore lato client: " + e.getMessage());
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
    
    public void creaGerarchia(String nomeGerarchia, Map<String, String> categorieNonFoglia) {
        Categoria radice = new Categoria(nomeGerarchia, false);
        for (Map.Entry<String, String> entry : categorieNonFoglia.entrySet()) {
            Categoria nonFoglia = new Categoria(entry.getKey(), false);
            // Aggiungi sottocategorie o gestisci il dominio
            radice.aggiungiSottocategoria(entry.getValue(), nonFoglia);
        }
        Gerarchia nuovaGerarchia = new Gerarchia(radice);
        inviaGerarchiaAlServer(nuovaGerarchia);
    }
 ***************************************************
    /*
    public void creaNuovaGerarchia(String nomeRadice, Map<String, List<String>> struttura) {
        Categoria radice = new Categoria(nomeRadice, false); // La radice non è una foglia
        
        // Creazione della struttura gerarchica
        for (Map.Entry<String, List<String>> entry : struttura.entrySet()) {
            String nomeCategoria = entry.getKey();
            List<String> sottocategorie = entry.getValue();
            
            Categoria categoria = new Categoria(nomeCategoria, false); // Categoria non foglia
            for (String nomeSottocategoria : sottocategorie) {
                Categoria foglia = new Categoria(nomeSottocategoria, true); // Categoria foglia
                categoria.aggiungiSottocategoria(nomeSottocategoria, foglia);
            }
            radice.aggiungiSottocategoria(nomeCategoria, categoria);
        }

        Gerarchia nuovaGerarchia = new Gerarchia(radice);
        inviaGerarchiaAlServer(nuovaGerarchia);
        System.out.println("Gerarchia " + nomeRadice + " creata e inviata al server.");
    }
 */
/*
    public void creaNuovaGerarchia() {
        Scanner scanner = new Scanner(System.in);
        
        // Creazione della gerarchia
        System.out.println("Inserisci il nome della gerarchia:");
        String nomeGerarchia = scanner.nextLine();
        
        System.out.println("Inserisci la descrizione della gerarchia:");
        String descrizioneGerarchia = scanner.nextLine();
        
        Gerarchia nuovaGerarchia = new Gerarchia(nomeGerarchia);
        
        // Creazione della radice
        System.out.println("Inserisci il nome della categoria radice:");
        String nomeRadice = scanner.nextLine();
        
        System.out.println("La categoria radice ha un campo caratteristico? (sì/no)");
        String haCampoCaratteristico = scanner.nextLine();
        
        Categoria radice;
        
        if (haCampoCaratteristico.equalsIgnoreCase("sì")) {
            // Aggiunta del campo caratteristico alla categoria radice
            System.out.println("Inserisci il nome del campo caratteristico:");
            String nomeCampo = scanner.nextLine();
            
            System.out.println("Inserisci il dominio dei valori del campo caratteristico (separa i valori con una virgola):");
            String[] valoriDominio = scanner.nextLine().split(",");
            List<String> dominioCampo = Arrays.asList(valoriDominio);
            
            radice = new CategoriaNonFoglia(nomeRadice, nomeCampo);
        } else {
            radice = new CategoriaFoglia(nomeRadice);
        }
        
        // Aggiunta della radice alla gerarchia
        nuovaGerarchia.setRadice(radice);
        
        // Aggiunta di sottocategorie ricorsive
        aggiungiSottocategorie(scanner, radice);
        
        // Invio della gerarchia al server
        inviaGerarchiaAlServer(nuovaGerarchia);
        
        System.out.println("La nuova gerarchia è stata creata e inviata al server.");
    }

    private void aggiungiSottocategorie(Scanner scanner, Categoria categoria) {
        System.out.println("Vuoi aggiungere delle sottocategorie a " + categoria.getNome() + "? (sì/no)");
        String risposta = scanner.nextLine();
        
        if (risposta.equalsIgnoreCase("sì")) {
            while (true) {
                System.out.println("Inserisci il nome della sottocategoria:");
                String nomeSottocategoria = scanner.nextLine();
                
                System.out.println("La sottocategoria ha un campo caratteristico? (sì/no)");
                String haCampoCaratteristico = scanner.nextLine();
                
                Categoria sottocategoria;
                
                if (haCampoCaratteristico.equalsIgnoreCase("sì")) {
                    System.out.println("Inserisci il nome del campo caratteristico:");
                    String nomeCampo = scanner.nextLine();
                    
                    System.out.println("Inserisci il dominio dei valori del campo caratteristico (separa i valori con una virgola):");
                    String[] valoriDominio = scanner.nextLine().split(",");
                    List<String> dominioCampo = Arrays.asList(valoriDominio);
                    
                    sottocategoria = new CategoriaNonFoglia(nomeSottocategoria, nomeCampo);
                } else {
                    sottocategoria = new CategoriaFoglia(nomeSottocategoria);
                }
                
                // Aggiunta della sottocategoria
                categoria.aggiungiSottocategoria(sottocategoria);
                
                System.out.println("Vuoi aggiungere altre sottocategorie a " + categoria.getNome() + "? (sì/no)");
                String continua = scanner.nextLine();
                
                if (continua.equalsIgnoreCase("no")) {
                    break;
                }
            }
            
            // Aggiunta ricorsiva per ogni sottocategoria
            for (Categoria sottocategoria : categoria.getSottocategorie().values()) {
                aggiungiSottocategorie(scanner, sottocategoria);
            }
        }
    }
 */

/*
    public void aggiornaGerarchia(Gerarchia gerarchia, String nomeCategoria, List<String> nuoveSottocategorie) {
        Categoria categoria = gerarchia.trovaCategoria(nomeCategoria);
        
        if (categoria != null && !categoria.isFoglia()) {
            for (String nomeSottocategoria : nuoveSottocategorie) {
                Categoria nuovaFoglia = new Categoria(nomeSottocategoria, true);
                categoria.aggiungiSottocategoria(nomeSottocategoria, nuovaFoglia);
            }
            inviaGerarchiaAlServer(gerarchia); // Aggiornamento inviato al server
            System.out.println("Gerarchia aggiornata con nuove sottocategorie.");
        } else {
            System.out.println("Errore: la categoria non è modificabile o non è stata trovata.");
        }
    }
 */
/*  
    public void assegnaFattoreConversione(FattoreConversione fattoriConversione, String cat1, String cat2, double valore) {
        if (valore >= 0.5 && valore <= 2.0) {
            fattoriConversione.aggiungiFattore(cat1, cat2, valore);
            System.out.println("Fattore di conversione assegnato tra " + cat1 + " e " + cat2 + ": " + valore);
        } else {
            System.out.println("Errore: il valore del fattore di conversione deve essere compreso tra 0.5 e 2.0.");
        }
    }
   
    public void inviaConfigurazioniAlServer() {
        try {
            Socket socket = new Socket("localhost", 1234);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            // Invia le gerarchie al server
          //  out.writeObject(gerarchie);

            // Invia i fattori di conversione
          // out.writeObject(fattoriConversione);

            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void inviaGerarchiaAlServer(Socket socket, Gerarchia gerarchia) {
        try {
                
            // Creazione di uno stream di output per inviare l'oggetto Gerarchia al server
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            
            // Invio della gerarchia serializzata al server
            out.writeObject(gerarchia);
            out.flush();
            
            // Chiusura dello stream e del socket
            out.close();
            socket.close();
            
            System.out.println("Gerarchia inviata con successo al server.");
        } catch (IOException e) {
            System.err.println("Errore durante l'invio della gerarchia al server: " + e.getMessage());
        }
    }
    
    /* è uguale a quello sotto
    public void definisciComprensorio(String nomeComprensorio, List<String> comuni) {
        Comprensorio comprensorio = new Comprensorio(nomeComprensorio);
        for (String comune : comuni) {
            comprensorio.aggiungiComune(comune);
        }
        
        inviaComprensoriAlServer(comprensorio);
    }
 */


/*
    public void inviaComprensoriAlServer(Socket socket, Comprensorio comprensorio) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            // Invia il comprensorio al server
            out.writeObject(comprensorio);

            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 */