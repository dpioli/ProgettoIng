package progetto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "gerarchie")
public class GerarchiaWrapper {

    private List<Gerarchia> gerarchie;
    
    public GerarchiaWrapper(List<Gerarchia> gerarchie) {
    	this.gerarchie = gerarchie;
    }

    @XmlElement(name = "gerarchia")
    public List<Gerarchia> getGerarchie() {
    	if (gerarchie == null) {
    		gerarchie = new ArrayList<>();
        }
        return gerarchie;
    }
    
    public void aggiungiGerarchia(Gerarchia radice) {
    	gerarchie.add(radice);
    }

    public void setGerarchie(List<Gerarchia> gerarchie) {
        this.gerarchie = gerarchie;
    }

	public boolean ePresenteGerarchia(String nomeGerarchia) {
		for(Gerarchia g: gerarchie) {
			if(g.eUgualeNomeRadice(nomeGerarchia))
				return true;
		}
		return false;
	}
	   
    public String toString() {
    	StringBuffer b = new StringBuffer();
        for(Gerarchia g: gerarchie) {
        	b.append("\nGerarchia" + g.toString());
        }
        return b.toString();
    }

}
