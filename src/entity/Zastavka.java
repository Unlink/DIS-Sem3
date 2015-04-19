/*
 *  Copyright (c) 2015 Michal Ďuračík
 */
package entity;

import OSPDataStruct.SimQueue;
import java.util.HashSet;

/**
 *
 * @author Unlink
 */
public class Zastavka {
	
	private String aMeno;
	private int aPocLudi;
	private HashSet<TypVozidlo> aVozidla;
	private SimQueue<Pasazier> aPasazieri;
	private double aVzdialenost;

	public Zastavka(String paMeno, int paPocLudi) {
		this.aMeno = paMeno;
		this.aPocLudi = paPocLudi;
		this.aPasazieri = new SimQueue<>();
		this.aVozidla = new HashSet<>();
		this.aVzdialenost = -1;
	}

	public String getMeno() {
		return aMeno;
	}

	public int getPocLudi() {
		return aPocLudi;
	}

	public HashSet<TypVozidlo> getVozidla() {
		return aVozidla;
	}

	public SimQueue<Pasazier> getPasazieri() {
		return aPasazieri;
	}

	public double getVzdialenost() {
		return aVzdialenost;
	}

	public void setVzdialenost(double paVzdialenost) {
		this.aVzdialenost = paVzdialenost;
	}
}
