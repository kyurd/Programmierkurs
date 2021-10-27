package pk.lkarten;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import pk.lkarten.ui.util.DialogUtil;

public class EinzelantwortKarte extends Lernkarte implements Serializable {

	private static final long serialVersionUID = 2L;
	private String antwort;

	public EinzelantwortKarte(String kategorie, String titel, String frage, String antwort) {
		super(kategorie, titel, frage);
		this.antwort = antwort;
	}

	public EinzelantwortKarte() {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((antwort == null) ? 0 : antwort.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		EinzelantwortKarte other = (EinzelantwortKarte) obj;
		if (antwort == null) {
			if (other.antwort != null)
				return false;
		} else if (!antwort.equals(other.antwort))
			return false;
		return true;
	}

	public void zeigeRueckseite(OutputStream stream) {
		PrintWriter pw = new PrintWriter(stream);
		pw.print("    " + antwort + "!");
		pw.flush();
	}

	public void zeigeRueckseite() {
		DialogUtil.showTextDialog(super.getKategorie(), super.getTitel(), antwort, "Nächste Karte zeigen");
	}

	public void zeigeVorderseite() {
		DialogUtil.showTextDialog(super.getKategorie(),super.getTitel(), super.getFrage(), "Rückseite zeigen");
	}

	public String getAntwort() {
		return antwort;
	}
	
	public void setAntwort(String antw) {
		this.antwort=antw;
	}

	public void validiere() throws UngueltigeKarteException {
		List<String> listee = new ArrayList<String>();
		try {
			super.validiere();
		} catch (UngueltigeKarteException e) {
			if (!e.getFehlerListe().isEmpty())
				listee = e.getFehlerListe();
		}

		if (antwort==null || antwort.isEmpty()) {
			listee.add("Keine gültige Antwort gegeben!");
		}
		
		if (!listee.isEmpty()) {
			throw new UngueltigeKarteException(listee);
		}
	}

	@Override
	public String exportiereAlsCsv() {
		return super.exportiereAlsCsv() + "," + antwort;
	}

	@Override
	public String toString() {
		return super.toString() + ", Antwort=" + antwort + "]";
	}
}