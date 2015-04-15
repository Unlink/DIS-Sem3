/*
 *  Copyright (c) 2015 Michal Ďuračík
 */
package entity;

/**
 *
 * @author Unlink
 */
public class Vozidlo {
	
	private final String aMeno;
	private final int aKapacita;
	private int aAktObsadenost;
	private final int aPocDveri;
	private int aAktObsadenostDveri;

	public Vozidlo(String paMeno, int paKapacita, int paPocDveri) {
		this.aMeno = paMeno;
		this.aKapacita = paKapacita;
		this.aPocDveri = paPocDveri;
		this.aAktObsadenost = 0;
		this.aAktObsadenostDveri = 0;
	}
	
	public Vozidlo(Vozidlo paVozidlo) {
		aMeno = paVozidlo.aMeno;
		aKapacita = paVozidlo.aKapacita;
		aPocDveri = paVozidlo.aPocDveri;
		aAktObsadenost = 0;
		aAktObsadenostDveri = 0;
	}
	
}
