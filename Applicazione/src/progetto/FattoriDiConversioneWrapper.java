package progetto;

import java.util.HashMap;
import java.util.Map;

public class FattoriDiConversioneWrapper {

    private Map<String, Double> fattoriDiConversione = new HashMap<>();

   
    public Map<String, Double> getFattoriDiConversione() {
        return fattoriDiConversione;
    }

    public void setFattoriDiConversione(Map<String, Double> fattoriDiConversione) {
        this.fattoriDiConversione = fattoriDiConversione;
    }
}
