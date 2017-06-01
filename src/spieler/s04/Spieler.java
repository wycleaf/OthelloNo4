package spieler.s04;

import java.util.ArrayList;

import spieler.Farbe;
import spieler.OthelloSpieler;
import spieler.Zug;
import spieler.ZugException;

public class Spieler implements OthelloSpieler 
{
	private Farbe unsereFarbe;
	private Farbe gegnerFarbe;
	private Brett brett;
	private int suchtiefe;
	
	Spieler ()
	{
		this.suchtiefe = 6;
	}
	
	Spieler (int suchtiefe)

	{
		this.suchtiefe = suchtiefe;
	}
	
	public Zug berechneZug(Zug vorherigerZug, long bedenkZeitWeiss, long bedenktZeitSchwarz) throws ZugException 
	{
		if(vorherigerZug != null)
		{
			brett = setzeStein(vorherigerZug, gegnerFarbe, brett);
		}
		
		BewerteterZug unserZug = bewerteZug(unsereFarbe, suchtiefe, brett);
		brett = setzeStein(unserZug.getZug(), unsereFarbe, brett);
		return unserZug.getZug();
	}

	public String meinName() 
	{
		return "S04";
	}

	public void neuesSpiel(Farbe farbe, int bedenktZeitInSekunden) 
	{
		unsereFarbe = farbe;
		gegnerFarbe = Farbe.WEISS;
		if(farbe == Farbe.WEISS)
			gegnerFarbe = Farbe.SCHWARZ;
		
		brett = new Brett();
		brett.brettAufbau();
	}

	public ArrayList<Zug> dreheSteine(Zug zug, Farbe spieler)
	{
		Farbe gegner = Farbe.SCHWARZ;
		if(spieler == Farbe.SCHWARZ)
			gegner = Farbe.WEISS;
		
		ArrayList<Zug> drehsteine = new ArrayList<Zug>();
//		if(brett.getSpielfeld()[zug.getZeile()][zug.getSpalte()] == Farbe.LEER)
//		{
			for(int z = -1; z<2; z++)
				for(int s = -1; s<2; s++)
				{	if(z==0 && s == 0) continue;
					Zug nachbar = new Zug(zug.getZeile()+z, zug.getSpalte()+s);
				
					if(imFeld(nachbar))
					{				
						ArrayList<Zug> moeglicheSteine = new ArrayList<Zug>();
						if(brett.getSpielfeld()[nachbar.getZeile()][nachbar.getSpalte()] == gegner)
						{
							moeglicheSteine.add(nachbar);
							nachbar = new Zug (nachbar.getZeile()+z, nachbar.getSpalte()+s);
						
							while(imFeld(nachbar))
							{
								if(brett.getSpielfeld()[nachbar.getZeile()][nachbar.getSpalte()] == gegner)
								{
									moeglicheSteine.add(nachbar);
									nachbar = new Zug (nachbar.getZeile()+z, nachbar.getSpalte()+s);
									continue;
								}
							
								if(brett.getSpielfeld()[nachbar.getZeile()][nachbar.getSpalte()] == spieler)
								{
									drehsteine.addAll(moeglicheSteine);
								}
								break;
							}
						}
					}
				}
		
//			}
		return drehsteine;
	}
	public BewerteterZug bewerteZug (Farbe spieler, int suchtiefe, Brett brett)
	{
		ArrayList<Zug> zuege = moeglicheZuege(spieler);
		
		BewerteterZug bestZug = new BewerteterZug();
		
		bestZug.setBewertung(10000);
		if (spieler == Farbe.SCHWARZ)
			bestZug.setBewertung(-10000);
		boolean passen = true;
		
		for(int i = 0; i<zuege.size(); i++)
		{
			Zug zug = zuege.get(i);
			passen = false;
			Brett kopie = brett.kopiereBrett();
			kopie = setzeStein(zug, spieler, kopie);
			BewerteterZug rekZug;
			
			if (suchtiefe > 1) 
			{
				Farbe rekSpieler = Farbe.WEISS;
				if (spieler == Farbe.WEISS)
					rekSpieler = Farbe.SCHWARZ;
				rekZug = bewerteZug(rekSpieler, suchtiefe - 1, kopie);
				rekZug.setZug(zug);
			}
			else
			{
				rekZug = new BewerteterZug();
				rekZug.setZug(zug);
				rekZug.setBewertung(kopie.bewerte());
			}
			if (spieler == Farbe.SCHWARZ && rekZug.getBewertung() > bestZug.getBewertung())
				bestZug = rekZug;
			if (spieler == Farbe.WEISS && rekZug.getBewertung() < bestZug.getBewertung())
				bestZug = rekZug;
		}
		
		if (passen) 
		{
			bestZug = new BewerteterZug();
			bestZug.setZug(new Zug(-1, -1));
			bestZug.setBewertung(brett.bewerte());
		}
		return bestZug;
	
	}
	
	public ArrayList<Zug> moeglicheZuege (Farbe spieler)
	{	ArrayList<Zug> zuege = new ArrayList<Zug>();
		for(int i = 0; i<8; i++)
			for(int k = 0 ; k<8; k++)
			{
				if(brett.getSpielfeld()[i][k] == Farbe.LEER)
				{
					Zug moeglicherZug = new Zug(i,k);
					ArrayList<Zug> drehsteine = dreheSteine(moeglicherZug, spieler);
					if(drehsteine.size() > 0)
						zuege.add(moeglicherZug);
				}
			}
	
		return zuege;
	}
	
	public Brett setzeStein(Zug zug, Farbe spieler, Brett ausgangsbrett)
	{
		ausgangsbrett.getSpielfeld()[zug.getZeile()][zug.getSpalte()] = spieler;
		ArrayList<Zug> drehsteine = dreheSteine(zug, spieler);
		for(int i = 0; i<drehsteine.size(); i++)
			ausgangsbrett.getSpielfeld()[drehsteine.get(i).getZeile()][drehsteine.get(i).getSpalte()] = spieler;
		return ausgangsbrett;
	}
	
	public boolean imFeld(Zug zug)
	{
		if(zug.getZeile() > 7 || zug.getZeile() < 0 || zug.getSpalte() > 7 || zug.getSpalte() < 0)
			return false;
		
		return true;
	}
	
}
