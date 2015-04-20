/*
 *  Copyright (c) 2015 Michal Ďuračík
 */
package container;

/**
 *
 * @author Unlink
 */
public enum SimVariants {
	A(0), B(1);

	private int aNasobic;

	private SimVariants(int paNasobic) {
		this.aNasobic = paNasobic;
	}

	public int getNasobic() {
		return aNasobic;
	}

}
