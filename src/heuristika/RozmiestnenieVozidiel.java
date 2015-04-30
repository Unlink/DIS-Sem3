/*
 *  Copyright (c) 2015 Michal Ďuračík
 */
package heuristika;

import container.IVozidlaConf;
import container.IVozidloConf;
import entity.TypVozidlo;
import java.io.File;
import java.util.List;

/**
 *
 * @author Unlink
 */
public class RozmiestnenieVozidiel extends Base {
	
	private static int[] CENY = {17_780_000, 6_450_000};
	
	public static void main(String[] args) {
		RozmiestnenieVozidiel rozmiestnenieVozidiel = new RozmiestnenieVozidiel(new File("E:\\diss\\1"));
		rozmiestnenieVozidiel.run();
	}

	public RozmiestnenieVozidiel(File paResultsDir) {
		super(paResultsDir);
	}

	private void run() {
		//Defaultná konfiguracia
		pripravKonfiguraciu(20,20);
		long cena = spocitajCenu();
		boolean zmena;
		int linka = 0;
		do {
			zmena = false;
			List<IVozidlaConf> savedState = saveState();
			List<IVozidlaConf> best = savedState;
			for (int i=-2; i<=2; i++) {
				for (int j = -2; j <= 2; j++) {
					if (j != 0 && i != 0) {
						restoreState(savedState);
						
					}
				}
			}
			
			
			linka = (linka + 1)%aLinkyConf.size();
		} while (zmena);
	}

	private void pripravKonfiguraciu(int a, int b) {
		//Defaultná konfiguracia
		List<TypVozidlo> typyVozidiel = aBuilder.getTypyVozidiel();
		for (int i = 0; i < aBuilder.getLinky().size(); i++) {
			for (int j = 0; j < a; j++) {
				aLinkyConf.get(i).insertVozidlo(typyVozidiel.get(0), 0);
			}
			for (int j = 0; j < b; j++) {
				aLinkyConf.get(i).insertVozidlo(typyVozidiel.get(1), 0);
			}
			prepocitajRovnomerne(i, 0);
		}
		
	}
	
	private long spocitajCenu() {
		long cena = 0;
		for (IVozidlaConf linka : aLinkyConf) {
			for (IVozidloConf vozidla : linka.getVozidla()) {
				cena += CENY[vozidla.getTyp().getId()];
			}
		}
		return cena;
	}
	
}
