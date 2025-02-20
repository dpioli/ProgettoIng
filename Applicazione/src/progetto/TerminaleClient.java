package progetto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
//visione del terminale del cliente
public class TerminaleClient {
	//STRINGHE USATE NELLA VISUALIZZAZIONE LATO CLIENT
	private static final String CLIENT_CONNESSO_AL_SERVER = "Client connesso al server.";
	private static final String PROMPT_SERVER = "\n>Prompt server: ";
	private static final String INSERISCI_LA_RISPOSTA = "\n>>Inserisci la risposta: ";
	private static final String DISCONNESSIONE_DAL_SERVER = "Disconnessione dal server.";
	private static final String ERRORE_LATO_CLIENT = "Errore lato client: ";
	
	//STRINGHE DEI TAG INIZIALI E FINALI DA MESSAGGI SERVER
	private static final String TAG_INIZIALE_STATO = "S:";
	private static final String TAG_INIZIALE_PROMPT = "P:";
	private static final String TAG_FINALE_STATO = "::S";
	private static final String TAG_FINALE_PROMPT = "::P";
	//STRINGHE USATE PER MESSAGGI VERSO IL SERVER
	
	private static final String DISCONNESSIONE = "Mi disconnetto.";
	private static final String NEW_LINE = "\n";

	
	public static void main(String[] args) {
	        try (Socket socket = new Socket("localhost", 1234);
	        	 BufferedReader serverIN = new BufferedReader(new InputStreamReader(socket.getInputStream())); //serverinput
	        	 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
  	        	 BufferedReader clienteIN = new BufferedReader(new InputStreamReader(System.in));
	        	) {    
	            System.out.println(CLIENT_CONNESSO_AL_SERVER);
	            String risposta; //richiesta server, risposta user
	            do {
	            	String richi;
	            	StringBuffer lettura = new StringBuffer();
	            	//legge fintanto che il server ha messaggi da inviare e non trova etichette finali (in modo da poterle eliminare)
	            	do {
	            		lettura.append(serverIN.readLine());
	            		lettura.append(NEW_LINE);
	            	} while (serverIN.ready() && !(lettura.toString().contains(TAG_FINALE_PROMPT) || lettura.toString().contains(TAG_FINALE_STATO)));
	            	
	            	richi = lettura.toString();
	            	if(richi.startsWith(TAG_INIZIALE_PROMPT)) { //Se ha ricevuro un prompt che si aspetta una risposta, aspetta input da terminale
	            		
	            		System.out.println(PROMPT_SERVER + eliminaEtichettaPrompt(richi));
	            		
		            	System.out.println(INSERISCI_LA_RISPOSTA);
		            	risposta = clienteIN.readLine();
		            	out.println(risposta);
		            	//mi sa che non serve/da migliorare
		            	if (risposta.equals("6") ) {
			            	System.out.println(DISCONNESSIONE_DAL_SERVER);
			            	out.println(DISCONNESSIONE);
			            	break;
			            }
		            	
	            	}else if(richi.startsWith(TAG_INIZIALE_STATO)) { //Se ha ricevuto un aggiornamento sullo stato di un'operazione, lo stampa semplicemente a video
	            		System.out.println(eliminaEtichettaStato(richi));
	            	}
	            	
	            }while(true);
	            
	            out.close();
	            socket.close();

	        } catch (IOException e) {
	            //e.printStackTrace();
	            System.err.println(ERRORE_LATO_CLIENT + e.getMessage());
	        }
	    }
	
		private static String eliminaEtichettaPrompt(String messaggio) {
			return eliminaEtichetta(messaggio, TAG_INIZIALE_PROMPT, TAG_FINALE_PROMPT);
		}
		
		private static String eliminaEtichettaStato(String messaggio) {
			return eliminaEtichetta(messaggio, TAG_INIZIALE_STATO, TAG_FINALE_STATO);
		}
		
		private static String eliminaEtichetta(String messaggio, String tagIniziale, String tagFinale) {
			int inizio = tagIniziale.length();
			int fine = messaggio.length() - tagFinale.length() - 1;
			return messaggio.substring(inizio, fine); 
		}
}
