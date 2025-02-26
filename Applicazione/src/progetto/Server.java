package progetto;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server { /*IL SERVER CONTIENE TUTTE LE MAP E LIST E GESTISCE TUTTA LA LOGICA
	qui possiamo lasciare la visualizzazione e gestione degli stream
	ClientHandler sarà il protocollo che il Server implementa per fornire un servizio al Client
*/
	private GerarchiaWrapper gerarchie;
	private FattoriDiConversioneWrapper fattoriConversione;
	private ComprensorioWrapper comprensori; // Mappa dei comprensori
	private Map<String, String> credenzialiConfig = new HashMap<>(); // Mappa username -> password
	private GestorePersistenza gp = new GestorePersistenza();

	
    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
    
    public void start() {
    	try(ServerSocket serverSocket = new ServerSocket(1234)) {
            // Crea un ServerSocket che ascolta sulla porta 1234
            System.out.println("Server in attesa di connessioni...");
            // Il server continua ad accettare nuovi client
            while (true) {
                // Accetta un nuovo client
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nuovo client connesso!");

                // Crea un nuovo thread per gestire il client
                //CARICA DATI
               caricaDati();
              // System.out.println(System.getProperty("java.class.path"));
               
               ClientHandler clientHandler = new ClientHandler(clientSocket, gerarchie, fattoriConversione, comprensori, credenzialiConfig, gp);
               Thread clientThread =  new Thread(clientHandler);
               clientThread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void caricaDati() {
    	comprensori = gp.caricaComprensori();    	
    	gerarchie = gp.caricaGerarchie();
    	fattoriConversione = gp.caricaFattoriConversione();
    	credenzialiConfig = gp.caricaCredenzialiConfig();
    }
}

