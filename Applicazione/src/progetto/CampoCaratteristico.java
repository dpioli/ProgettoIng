package progetto;

import java.util.ArrayList;
import java.util.HashMap;

public class CampoCaratteristico {
	public String nomeCampo;
	public HashMap<String, String> valoriCampo;

	public CampoCaratteristico() {
		
	}
	
	public CampoCaratteristico(String nomeCampo) {
		this.nomeCampo = nomeCampo;
		this.valoriCampo = new HashMap<>();
	}

	public void aggiungiValori(ArrayList<String> valori) {
		for(String v : valori) {
			valoriCampo.put(v, null);
		}
	}
	
	public HashMap<String, String > getValori() {
		return valoriCampo;
	}
	
	public void aggiungiDescrizioneSpecifica(String valore, String descrizione) {
		valoriCampo.put(valore, descrizione);
	}
	
	public void aggiungiDescrizioni(String descrizione) {
		for(String v : valoriCampo.keySet()) {
			valoriCampo.put(v, descrizione);
		}
	}
	
	
}