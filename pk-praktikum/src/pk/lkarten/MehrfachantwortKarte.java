package pk.lkarten;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pk.lkarten.ui.util.DialogUtil;

public class MehrfachantwortKarte extends Lernkarte implements Serializable {

	private static final long serialVersionUID = 4L;
	private String[] moeglicheAntworten;
	private int[] richtigeAntworten;

	public MehrfachantwortKarte(String kategorie, String titel, String frage, String[] moeglicheAntworten,
			int[] richtigeAntworten) {
		super(kategorie, titel, frage);
		this.moeglicheAntworten = moeglicheAntworten;
		this.richtigeAntworten = richtigeAntworten;
	}

	public MehrfachantwortKarte() {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(moeglicheAntworten);
		result = prime * result + Arrays.hashCode(richtigeAntworten);
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
		MehrfachantwortKarte other = (MehrfachantwortKarte) obj;
		if (!Arrays.equals(moeglicheAntworten, other.moeglicheAntworten))
			return false;
		if (!Arrays.equals(richtigeAntworten, other.richtigeAntworten))
			return false;
		return true;
	}

	public void zeigeRueckseite(OutputStream stream) {
		PrintWriter pw = new PrintWriter(stream);
		for (int i = 0; i < richtigeAntworten.length; i++) {
			pw.print((i + 1) + ": " + moeglicheAntworten[richtigeAntworten[i]] + ", ");
		}
		pw.flush();
	}

	@Override
	public void zeigeVorderseite(OutputStream stream) {
		PrintWriter pw = new PrintWriter(stream);
		pw.print("[" + super.getId() + "," + super.getKategorie() + "]" + super.getTitel() + ":\n" + super.getFrage() + moeglicheAntworten);
		pw.flush();
	}

	public void zeigeRueckseite() {
		String antw="Die Richtige Antworten sind:\n\n";
		for (int i = 0; i < richtigeAntworten.length; i++) {
			antw+=(i+1)+": "+moeglicheAntworten[richtigeAntworten[i]] + "\n";
		}
		DialogUtil.showTextDialog(super.getKategorie(), super.getTitel(), antw, "Nächste Karte Zeigen");
	}

	public void zeigeVorderseite() {
		String antw=super.getFrage()+"?\n\n";
		for(int i=0;i<moeglicheAntworten.length;i++) {
			antw+=(i+1)+": " + moeglicheAntworten[i]+"\n";
		}
		DialogUtil.showTextDialog(super.getKategorie(), super.getTitel(), antw, "Rückseite zeigen");
	}

	public String[] getMoeglicheAntworten() {
		return moeglicheAntworten;
	}

	public int[] getRichtigeAntworten() {
		return richtigeAntworten;
	}
	
	public void setMoeglicheAntworte(String[] mant) {
		this.moeglicheAntworten=mant;
	}
	
	public void setRichtigeAntworte(int[] rant) {
		this.richtigeAntworten=rant;
	}

	public void validiere() throws UngueltigeKarteException {
		List<String> liste = new ArrayList<>();
		try {
			super.validiere();
		} catch (UngueltigeKarteException e) {
			if (!e.getFehlerListe().isEmpty()) {
				liste = e.getFehlerListe();
			}
		}
		if (moeglicheAntworten.length < 2) {
			liste.add("Es sind weniger als 2 Anworten gegeben, nutzen Sie die Einzelantwortkarte!");
		} 
//		else {
//			for (String st : moeglicheAntworten) {
//				if (st.isEmpty() || st.equals(null)) {
//					liste.add("Die mögliche Antwort darf nicht leer sein!");
//				}
//			}
//		}
		if (!liste.isEmpty()) {
			throw new UngueltigeKarteException(liste);
		}
	}

	@Override
	public String exportiereAlsCsv() {
		return super.exportiereAlsCsv() + "," + Arrays.toString(moeglicheAntworten) + ","
				+ Arrays.toString(richtigeAntworten);
	}

	@Override
	public String toString() {
		return super.toString() + ", Mögliche Antworte=" + Arrays.toString(moeglicheAntworten) + ", Richtige Antworten=" + Arrays.toString(richtigeAntworten) + "]";
	}
}
