package progetto;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class FattoreConversione {
	private Map<String, Map<String, Double>> fattori = new HashMap<>();

    public void aggiungiFattore(String categoria1, String categoria2, double valore) {
        fattori.putIfAbsent(categoria1, new HashMap<>());
        fattori.putIfAbsent(categoria2, new HashMap<>());

        // Assegna il valore al fattore di conversione tra categoria1 e categoria2
        fattori.get(categoria1).put(categoria2, valore);

        // Assegna il valore inverso al fattore di conversione tra categoria2 e categoria1
        fattori.get(categoria2).put(categoria1, 1.0 / valore);

        // Propagazione dei valori derivabili
        aggiornaFattoriDerivabili(categoria1, categoria2);
    }

    private void aggiornaFattoriDerivabili(String categoria1, String categoria2) {
        Map<String, Double> correlatiC1 = fattori.get(categoria1);
        Map<String, Double> correlatiC2 = fattori.get(categoria2);

        for (String c3 : correlatiC2.keySet()) {
            if (!fattori.get(categoria1).containsKey(c3)) {
                double nuovoFattore = correlatiC1.get(categoria2) * correlatiC2.get(c3);
                fattori.get(categoria1).put(c3, nuovoFattore);
                fattori.get(c3).put(categoria1, 1.0 / nuovoFattore);
            }
        }
    }

    public double getFattore(String categoria1, String categoria2) {
        return fattori.getOrDefault(categoria1, new HashMap<>()).getOrDefault(categoria2, -1.0);
    }
    
    public Map<Categoria, Map<Categoria, Double>> getFattori() {
        Map<Categoria, Map<Categoria, Double>> fattoriDiConversione = new HashMap<>();
        
        try {
            // Creazione della connessione al server
            Socket socket = new Socket("localhost", 12345); // L'IP e la porta del server
            
            // Creazione di uno stream di input per ricevere i fattori di conversione dal server
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            
            // Ricezione dei fattori di conversione (mappa serializzata) dal server
            fattoriDiConversione = (Map<Categoria, Map<Categoria, Double>>) in.readObject();
            
            // Chiusura dello stream e del socket
            in.close();
            socket.close();
            
            System.out.println("Fattori di conversione ricevuti dal server.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Errore durante la ricezione dei fattori di conversione: " + e.getMessage());
        }
        
        return fattoriDiConversione;
    }
}

