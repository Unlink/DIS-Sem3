/*
 *  Copyright (c) 2015 Michal Ďuračík
 */
package entity;

/**
 *
 * @author Unlink
 */
public class Linka {
	
	private char aId;
	private Zastavka[] aZastavky;
	private double[] aPresuny;

	public Linka(char paId, Zastavka[] paZastavky, double[] paPresuny) {
		this.aId = paId;
		this.aZastavky = paZastavky;
		this.aPresuny = paPresuny;
	}
	
}
