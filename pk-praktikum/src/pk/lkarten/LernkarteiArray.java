package pk.lkarten;

import java.util.Random;

public class LernkarteiArray {

	private int anzahlKarten;
	private Lernkarte[] dieKarte;
	private Random random;

	public LernkarteiArray(int groesse) {
		dieKarte = new Lernkarte[groesse];
		random = new Random();
	}

	public void hinzufuegen(Lernkarte karte) {
		if (this.anzahlKarten < dieKarte.length && karte != null)
			dieKarte[anzahlKarten++] = karte;

		else
			System.out.println("Die Lernkarte ist voll!");
	}

	public void druckeAlleKarten() {
		for (int i = 0; i < dieKarte.length; i++)
			dieKarte[i].druckeKarte();
	}

	public int gibAnzahlKarten() {
		return anzahlKarten;
	}

	public Lernkarte[] gibKartenZuKategorie(String kategorie) {
		int x = 0;
		Lernkarte[] karte = new Lernkarte[dieKarte.length];
		for (int i = 0; i < anzahlKarten; i++)
			if (dieKarte[i].getKategorie().equals(kategorie))
				karte[x++] = dieKarte[i];

		return karte;
	}

	public Lernkarte[] erzeugeDeck(int anzahlKarte) {
		Lernkarte[] zufall = new Lernkarte[anzahlKarte];
		for (int i = 0; i < anzahlKarte; i++)
			zufall[i] = dieKarte[random.nextInt(anzahlKarten)];

		return zufall;
	}
}
