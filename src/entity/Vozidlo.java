/*
 *  Copyright (c) 2015 Michal Ďuračík
 */
package entity;

/**
 *
 * @author Unlink
 */
public class Vozidlo {

	private static int aCounter = 1;

	private final int aId;
	private final TypVozidlo aTypVozidlo;
	private int aAktObsadenost;
	private int aAktObsadenostDveri;

	public Vozidlo(TypVozidlo paTypVozidlo) {
		this.aTypVozidlo = paTypVozidlo;
		this.aAktObsadenost = 0;
		this.aAktObsadenostDveri = 0;
		this.aId = aCounter++;
	}

	public int getId() {
		return aId;
	}

	public TypVozidlo getTypVozidlo() {
		return aTypVozidlo;
	}

	public int getAktObsadenost() {
		return aAktObsadenost;
	}

	public int getAktObsadenostDveri() {
		return aAktObsadenostDveri;
	}

	public Vozidlo pridajPasaziera() {
		aAktObsadenost++;
		return this;
	}
	
	public Vozidlo obsadDvere() {
		aAktObsadenostDveri++;
		return this;
	}
	
	public Vozidlo uvoliDvere() {
		aAktObsadenostDveri--;
		return this;
	}
	
	public boolean maMiesto() {
		return aAktObsadenost < aTypVozidlo.getKapacita();
	}
	
	public boolean maVolneDvere() {
		return aAktObsadenostDveri < aTypVozidlo.getPocDveri();
	}
	
	public boolean nastupujuLudia() {
		return aAktObsadenostDveri != 0;
	}

	public void vyprazdniVozidlo() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}
