package spieler.s04;

import spieler.Zug;

public class BewerteterZug 
{
	private Zug zug;
	private int bewertung;
		
	public Zug getZug() {
		return zug;
	}
	
	public void setZug(Zug zug) {
		this.zug = zug;
	}
	
	public int getBewertung() {
		return bewertung;
	}
	public void setBewertung(int bewertung) {
		this.bewertung = bewertung;
	}
	
	public BewerteterZug ()
	{
		
	}
	
	public BewerteterZug (int zeile, int spalte)
	{
		this.zug = new Zug(zeile, spalte);
	}
	
}
