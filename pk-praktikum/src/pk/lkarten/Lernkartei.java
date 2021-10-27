package pk.lkarten;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Lernkartei implements Serializable {

	private static final long serialVersionUID = 1L;
	private Random random = new Random();
	private Set<Lernkarte> hash;

	public Lernkartei() {
		hash = new HashSet<Lernkarte>();
	}

	public void hinzufuegen(Lernkarte karte) throws UngueltigeKarteException {
		karte.validiere();
		hash.add(karte);
	}

	public void druckeAlleKarten() {
		List<Lernkarte> liste = new ArrayList<Lernkarte>(hash);
		Collections.sort(liste);
		for (Lernkarte card : liste)
			card.druckeKarte();
	}

	public int gibAnzahlKarten() {
		return hash.size();
	}

	public Lernkarte[] gibKartenZuKategorie(String kategorie) {
		List<Lernkarte> liste = new ArrayList<Lernkarte>(hash);
		int x = 0;
		Lernkarte[] karte = new Lernkarte[liste.size()];
		Iterator<Lernkarte> it = liste.iterator();

		while (it.hasNext()) {
			Lernkarte l = it.next();
			if (l.getKategorie().equals(kategorie))
				karte[x++] = l;
		}
		return karte;
	}

	public Lernkarte[] erzeugeDeck(int anzahlKarte) {
		List<Lernkarte> liste = new ArrayList<Lernkarte>(hash);
		Lernkarte[] zufall = new Lernkarte[anzahlKarte];
		if (hash.isEmpty()) {
			System.out.println("Leere Lernkartei!");
		} else
			for (int i = 0; i < anzahlKarte; i++)
				zufall[i] = liste.get(random.nextInt(hash.size()));

		return zufall;
	}

	public void exportiereEintraegeAlsCsvNio(Path datei) throws IOException {
		StringBuilder b = new StringBuilder();
		List<Lernkarte> Lernkarten = new ArrayList<Lernkarte>(hash);
		for (Lernkarte a : Lernkarten) {
			b.append(a);
		}
		Files.writeString(datei, b.toString());
	}

	public void exportiereEintraegeAlsCsv(File datei) throws IOException {
		StringBuilder b = new StringBuilder();
		List<Lernkarte> Lernkarten = new ArrayList<Lernkarte>(hash);
		for (Lernkarte a : Lernkarten) {
			b.append(a.exportiereAlsCsv() + "\n");
		}
		try (FileOutputStream fos = new FileOutputStream(datei)) {
			PrintWriter pw = new PrintWriter(fos);
			pw.print(b.toString());
			pw.flush();
		}
	}

	public void speichern() {
		try (FileOutputStream fos = new FileOutputStream("/Users/kadir/Desktop/Lernkarte.csv");
				ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(hash);
			oos.writeInt(Lernkarte.getAnzahl());
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void laden() {
		File f = new File("/Users/kadir/Desktop/Lernkarte.csv");
		try (FileInputStream fis = new FileInputStream(f); ObjectInputStream ois = new ObjectInputStream(fis)) {
			this.hash = (Set<Lernkarte>) ois.readObject();
			Lernkarte.setAnzahl(ois.readInt());
			ois.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Iterator<Lernkarte> getIterator() {
		Iterator<Lernkarte> it = hash.iterator();
		
		return it;
	}
}