/*
 *  Copyright (c) 2015 Michal Ďuračík
 */
package entity;

import OSPABA.Entity;
import OSPABA.Simulation;

/**
 *
 * @author Unlink
 */
public class Pasazier extends Entity {

	private double aCreationTime;

	public Pasazier(Simulation paMySim) {
		super(paMySim);
		aCreationTime = paMySim.currentTime();
	}

	public double timeInSystem() {
		return mySim().currentTime() - aCreationTime;
	}

}
