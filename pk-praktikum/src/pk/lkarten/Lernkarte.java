package pk.lkarten;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import java.util.List;

public abstract class Lernkarte implements Comparable<Lernkarte>, ValidierbareKarte, CsvExportable, Serializable {

	private static final long serialVersionUID = 3L;
	private final int id;
	private String kategorie;
	private String titel;
	private String frage;
	private static int nummer;

	public Lernkarte(String kategorie, String titel, String frage) {
		this.kategorie = kategorie;
		this.titel = titel;
		this.frage = frage;
		this.id = ++nummer;
	}

	public Lernkarte() {
		this.id = ++nummer;
	}

	public void zeigeVorderseite(OutputStream stream) {
		PrintWriter pw = new PrintWriter(stream);
		pw.print("[" + id + "," + kategorie + "]" + titel + ":\n" + frage);
		pw.flush();
	}

	public abstract void zeigeRueckseite(OutputStream stream);

	public abstract void zeigeVorderseite();

	public abstract void zeigeRueckseite();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((frage == null) ? 0 : frage.hashCode());
		result = prime * result + ((kategorie == null) ? 0 : kategorie.hashCode());
		result = prime * result + ((titel == null) ? 0 : titel.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lernkarte other = (Lernkarte) obj;
		if (frage == null) {
			if (other.frage != null)
				return false;
		} else if (!frage.equals(other.frage))
			return false;
		if (kategorie == null) {
			if (other.kategorie != null)
				return false;
		} else if (!kategorie.equals(other.kategorie))
			return false;
		if (titel == null) {
			if (other.titel != null)
				return false;
		} else if (!titel.equals(other.titel))
			return false;
		return true;
	}

	public void druckeKarte() {
		zeigeVorderseite(System.out);
		zeigeRueckseite(System.out);
	}

	public int getId() {
		return this.id;
	}

	public String getKategorie() {
		return this.kategorie;
	}

	public String getTitel() {
		return this.titel;
	}

	public String getFrage() {
		return this.frage;
	}

	public String toString() {
		return "Lernkarte [Id=" + id + ", Kategorie=" + kategorie + ", Titel=" + titel + ", Frage=" + frage;
	}

	public static int getAnzahl() {
		return nummer;
	}

	public static void setAnzahl(int zahl) {
		Lernkarte.nummer = zahl;
	}
	
	public void setTitel(String titl) {
		this.titel=titl;
	}
	
	public void setKategorie(String kat) {
		this.kategorie=kat;
	}
	
	public void setFrage(String frae) {
		this.frage=frae;
	}

	@Override
	public int compareTo(Lernkarte o) {
		if (o == null) {
			return 9999;
		}
		if (this == o) {
			return 0;
		}
		if (this.id < o.id) {
			return 1;
		}
		if (this.id > o.id) {
			return -1;
		}

		return 0;
	}

	public void validiere() throws UngueltigeKarteException {

		List<String> liste = new ArrayList<String>();
		if (kategorie == null || kategorie.isEmpty())
			liste.add("Keine gueltige Kategorie angegeben!");

		if (titel == null || titel.isEmpty()) {
			liste.add("Kein gueltiger Titel angegeben!");
		}
		if (frage == null || frage.isEmpty()) {
			liste.add("Keine gueltige Frage angegeben!");
		}
		if (!liste.isEmpty()) {
			throw new UngueltigeKarteException(liste);
		}

	}

	public String exportiereAlsCsv() {
		return id + "," + kategorie + "," + titel + "," + frage;
	}
}
