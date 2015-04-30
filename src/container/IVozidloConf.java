/*
 *  Copyright (c) 2015 Michal Ďuračík
 */
package container;

import entity.TypVozidlo;

/**
 *
 * @author Unlink
 */
public interface IVozidloConf {

	public TypVozidlo getTyp();

	public double getTime();

	public void setTime(double paTime);

}
