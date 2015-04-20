/*
 *  Copyright (c) 2015 Michal Ďuračík
 */
package simulation;

/**
 *
 * @author Unlink
 */
public class SimCounter {
	
	private int aCount;

	public SimCounter() {
		aCount = 0;
	}
	
	public void inc(int paVal) {
		aCount += paVal;
	}
	
	public void inc() {
		inc(1);
	}
	
	public int val() {
		return aCount;
	}
	
	public void set(int paVal) {
		aCount = paVal;
	}
	
	public void clear() {
		set(0);
	}
}
