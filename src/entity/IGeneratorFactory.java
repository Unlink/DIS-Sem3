/*
 *  Copyright (c) 2015 Michal Ďuračík
 */
package entity;

import OSPRNG.RNG;

/**
 *
 * @author Unlink
 * @param <T>
 */
public interface IGeneratorFactory<T extends Number> {

	public RNG<T> create();

}
