package spieler.s04;

import spieler.Farbe;

public class Brett 
{
	private   Farbe[][] spielfeld;
	private static int [][] punkteuebersicht = 
			
			{{50, -1,  5, 2, 2, 5,  -1, 50},
			{-1, -10, 1, 1, 1, 1, -10, -1},
			{5,    1, 1, 1, 1, 1,   1,  5},
			{2,    1, 1, 1, 1, 1,   1,  2},
			{2,    1, 1, 1, 1, 1,   1,  2},
			{5,    1, 1, 1, 1, 1,   1,  5},
			{-1, -10, 1, 1, 1, 1, -10, -1},
			{50,  -1, 5, 2, 2, 5,  -1, 50}
			};
	
	

	public Farbe[][] getSpielfeld() {
		return spielfeld;
	}

	public void setSpielfeld(Farbe[][] spielfeld) {
		this.spielfeld = spielfeld;
	}


	public static int[][] getPunkteuebersicht() {
		return punkteuebersicht;
	}

	public static void setPunkteuebersicht(int[][] punkteuebersicht) {
		Brett.punkteuebersicht = punkteuebersicht;
	}

	public Brett ()
	{
		spielfeld = new Farbe [8][8];
	}	
	
	public void brettAufbau ()
	{
		/*komplettes Feld mit Farbe.Leer befüllen. Die Ausgangsposition danach korrigieren*/
		
		for(int zeile = 0; zeile<=7; zeile++)
			for(int spalte=0; spalte<=7; spalte++)
			{
				spielfeld[zeile][spalte] = Farbe.LEER;
			} 
		
		spielfeld[3][3] = Farbe.WEISS;
		spielfeld[3][4] = Farbe.SCHWARZ;
		spielfeld[4][3] = Farbe.SCHWARZ;
		spielfeld[4][4] = Farbe.WEISS;
	}

	public Brett kopiereBrett()
	{
		Brett kopie = new Brett();
		
		for(int  i = 0;  i<8; i++)
			for(int k = 0; k<8; k++)
			{
				kopie.spielfeld[i][k] = this.spielfeld[i][k];
			}
			
		return kopie;
	}

	public int rating() 
	{
		int bewertung = 0;
		for (int zeile = 0; zeile < 8; zeile++) {
			for (int spalte = 0; spalte < 8; spalte++) {
				Farbe tile = spielfeld[zeile][spalte];
				if (tile != Farbe.LEER)
					if (tile == Farbe.SCHWARZ)
						bewertung += punkteuebersicht[zeile][spalte];
					else
						bewertung -= punkteuebersicht[zeile][spalte];
			}

		}

		return bewertung;
	}
	
}