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
	
	private final String aMeno;
	private final int aKapacita;
	private final int aPocDveri;
	private final IGeneratorFactory aGenNastupu;
	private final IGeneratorFactory aGenVystupu;

	public TypVozidlo(String paMeno, int paKapacita, int paPocDveri, IGeneratorFactory paGenNastupu, IGeneratorFactory paGenVystupu) {
		this.aMeno = paMeno;
		this.aKapacita = paKapacita;
		this.aPocDveri = paPocDveri;
		this.aGenNastupu = paGenNastupu;
		this.aGenVystupu = paGenVystupu;
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
	
	public RNG createGeneratorNastupu() {
		return aGenNastupu.create();
	}
	
	public RNG createGeneratorVystupu() {
		return aGenVystupu.create();
	}
	
}
