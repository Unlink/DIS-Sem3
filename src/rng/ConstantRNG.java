/*
 *  Copyright (c) 2015 Michal Ďuračík
 */
package rng;

import OSPRNG.RNG;

/**
 *
 * @author Unlink
 */
public class ConstantRNG extends RNG {

	private final double aConst;
	
	public ConstantRNG(double paConst) {
		aConst = paConst;
	}

	@Override
	public Number sample() {
		return aConst;
	}
}
