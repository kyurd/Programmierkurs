package pk.lkarten;

public interface ValidierbareKarte {

	public abstract void validiere() throws UngueltigeKarteException;
}