package pk.lkarten;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws UngueltigeKarteException {

		Lernkartei kartei1 = new Lernkartei();

//		Lernkarte karte1 = new MehrfachantwortKarte(
//				"Allgemeinwissen", "Jahr", "Die Monate mit 31 Tagen?", new String[] { "Januar", "Feb", "März", "April",
//						"Mai", "Juni", "Julie", "August", "September", "Oktober", "November", "Dezember" },
//				new int[] { 0, 2, 4, 6, 9, 11 });
//		Lernkarte karte4 = new MehrfachantwortKarte("Mathe", "Zahlen", "Zahlen, durch die 12 teilbar ist",
//				new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9" }, new int[] { 1, 2, 3, 5 });
//		EinzelantwortKarte karte2 = new EinzelantwortKarte("Mathe", "Addition", "1+2=3?", "ja");
//		Lernkarte karte3 = new EinzelantwortKarte("Englisch", "Vokabel", "be=laufen?", "nein");
//
//		kartei1.hinzufuegen(karte1);
//		kartei1.hinzufuegen(karte2);
//		kartei1.hinzufuegen(karte3);
//		kartei1.hinzufuegen(karte4);

		/*
		 * System.out.println("Anzahl der Karten: " + kartei1.gibAnzahlKarten());
		 * Lernkarte[] kate=kartei1.gibKartenZuKategorie("Mathe"); for(Lernkarte karte :
		 * kate) if(karte!=null) karte.druckeKarte(); kartei1.druckeAlleKarten();
		 * Lernkarte[]zufall=kartei1.erzeugeDeck(2); for(Lernkarte karte : zufall)
		 * if(karte!=null; karte.druckeKarte();
		 */
		Menu m = new Menu(kartei1);
		m.zeigeMenu();
	}
}
