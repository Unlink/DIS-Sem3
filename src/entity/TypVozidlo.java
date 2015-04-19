/*
 *  Copyright (c) 2015 Michal Ďuračík
 */
package entity;

import OSPRNG.RNG;

/**
 *
 * @author Unlink
 */
public class TypVozidlo {
	
	private final int aId;
	private final String aMeno;
	private final int aKapacita;
	private final int aPocDveri;
	private final double aCaka;
	private final IGeneratorFactory aGenNastupu;
	private final IGeneratorFactory aGenVystupu;

	public TypVozidlo(int paId, String paMeno, int paKapacita, int paPocDveri, double paCaka, IGeneratorFactory paGenNastupu, IGeneratorFactory paGenVystupu) {
		this.aId = paId;
		this.aMeno = paMeno;
		this.aKapacita = paKapacita;
		this.aPocDveri = paPocDveri;
		this.aCaka = paCaka;
		this.aGenNastupu = paGenNastupu;
		this.aGenVystupu = paGenVystupu;
	}

	public int getId() {
		return aId;
	}
	
	public String getMeno() {
		return aMeno;
	}

	public int getKapacita() {
		return aKapacita;
	}

	public int getPocDveri() {
		return aPocDveri;
	}
	
	public double getCaka() {
		return aCaka;
	}
	
	public RNG createGeneratorNastupu() {
		return aGenNastupu.create();
	}
	
	public RNG createGeneratorVystupu() {
		return aGenVystupu.create();
	}
	
}
