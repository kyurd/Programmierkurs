package pk.lkarten;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.InputMismatchException;
import java.util.Scanner;
import pk.lkarten.ui.util.DialogUtil;

public class Menu {
	
	private Lernkartei k;
	boolean i = true;

	public Menu(Lernkartei k) {
		this.k = k;
	}

	public void zeigeMenu() {
		while (i) {
			System.out.println("Lernkarten-App");
			System.out.println("1. Lernen!");
			System.out.println("2. Einzelantwortkarte hinzufügen");
			System.out.println("3. Drucke alle Karten");
			System.out.println("4. Drucke Karten zu Kategorie");
			System.out.println("5. CSV-Export");
			System.out.println("6. Laden aus Datei");
			System.out.println("7. Speichern in Datei");
			System.out.println("8. Beenden");
			System.out.println("Bitte Aktion wählen:");

			Scanner sc = new Scanner(System.in);

			try {
				int zahl1 = sc.nextInt();

				switch (zahl1) {
				case 1:
					click(k);
					break;
				case 2:
					String eingabe1 = DialogUtil.showInputDialog(null, "Bitte die Kategorie eingeben:");
					String eingabe2 = DialogUtil.showInputDialog(null, "Bitte den Titel eingeben:");
					String eingabe3 = DialogUtil.showInputDialog(null, "Bitte die Frage eingeben:");
					String eingabe4 = DialogUtil.showInputDialog(null, "Bitte die Antwort eingeben:");

					EinzelantwortKarte k1 = new EinzelantwortKarte(eingabe1, eingabe2, eingabe3, eingabe4);
					try {
						k.hinzufuegen(k1);
					} catch (UngueltigeKarteException e) {
						DialogUtil.showMessageDialog(null, e.getFehlerAusgabe());
					}
					break;
				case 3:
					k.druckeAlleKarten();
					break;
				case 4:
					String eingabe = DialogUtil.showInputDialog(null, "Die gesuchte Kategorie eingeben:");
					k.gibKartenZuKategorie(eingabe);
					break;
				case 5:
					runcsv();
					break;
				case 6:
					k.laden();
					break;
				case 7:
					k.speichern();
					break;
				case 8:
					i = false;
					break;
				default:
					System.err.println("Bitte eine der gegebenen Menüpunkte auswählen!");
					break;
				}
			} catch (InputMismatchException e) {
				System.err.println("Falsche Eingabe");
				zeigeMenu();
				sc.nextLine();
			}
		}
	}

	public void click(Lernkartei k) {
		if (k == null || k.gibAnzahlKarten() == 0) {
			System.out.println("Die Lernkarte ist Leer");
		} else {
			Lernkarte[] karten = k.erzeugeDeck(5);
			for (Lernkarte card : karten) {
				card.zeigeVorderseite(System.out);
				warteAufEnter();
				card.zeigeRueckseite(System.out);
				warteAufEnter();
			}
		}
	}

	private void warteAufEnter() {
		try {
			System.in.read();
		} catch (IOException e) {
			System.err.println("Fehler: " + e.getMessage());
		}
	}

	public void runcsv() {
		try {
			String eingabe = "";
			boolean b = true;
			while (b) {
				eingabe = DialogUtil.showInputDialog(null, "Bitte einen Dateinamen eingeben");
				if (eingabe == null || eingabe.isEmpty()) {
					DialogUtil.showMessageDialog(null, "Dateiname leer! Bitte erneut eingeben.");
					b = true;
					continue;
				} else
					b = false;

				Path path = Path.of(eingabe + ".csv");

				if (Files.exists(path)) {
					boolean button = DialogUtil.showConfirmDialog
							("Soll die bereits existierende Datei überschrieben werden?", "Name bereits vergeben!"
							);

					if (button) {
						Files.delete(path);
//						Files.createFile(path);
						b = false;
					}
					else {
						b = true;
						continue;
					}
				}
				k.exportiereEintraegeAlsCsv(path.toFile());
			}
		} catch (IOException e) {
			DialogUtil.showMessageDialog(null, e.getMessage());
		}
	}
}