package progetto;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "fattoriDiConversione")
public class FattoriDiConversioneWrapper {

    private Map<String, Double> fattoriDiConversione = new HashMap<>();

    @XmlElement(name = "fattore")
    public Map<String, Double> getFattoriDiConversione() {
        return fattoriDiConversione;
    }

    public void setFattoriDiConversione(Map<String, Double> fattoriDiConversione) {
        this.fattoriDiConversione = fattoriDiConversione;
    }
}
