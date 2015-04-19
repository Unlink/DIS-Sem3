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

}
