/*
 *  Copyright (c) 2015 Michal Ďuračík
 */
package heuristika;

import container.IVozidloConf;
import entity.TypVozidlo;

/**
 *
 * @author Unlink
 */
public class Vozidlo implements IVozidloConf {

	private TypVozidlo aTyp;
	
	private double aTime;

	public Vozidlo(TypVozidlo paTyp, double paTime) {
		this.aTyp = paTyp;
		this.aTime = paTime;
	}
	
	@Override
	public TypVozidlo getTyp() {
		return aTyp;
	}

	@Override
	public double getTime() {
		return aTime;
	}

	@Override
	public void setTime(double paTime) {
		aTime = paTime;
	}
	
}
