package pk.lkarten;

import java.util.List;

public class UngueltigeKarteException extends Exception {

	private List<String> fehler;

	public UngueltigeKarteException(List<String> fehler) {
		super();
		this.fehler = fehler;
	}

	public String getFehlerAusgabe() {
		String result = "";
		for (String x : fehler) {
			result += x;
			result += "\n";
		}
		return result;
	}

	public List<String> getFehlerListe() {
		return fehler;
	}
}
