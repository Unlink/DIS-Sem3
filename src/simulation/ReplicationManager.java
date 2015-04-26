/*
 *  Copyright (c) 2015 Michal Ďuračík
 */
package simulation;

import container.SimContainer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Unlink
 */
public class ReplicationManager {

	private SimContainer aContainer;

	private boolean aStopped;

	private MySimulation aMs;
	
	private ExecutorService aThreadPool;
	
	private double aDelay;
	
	private double aInterval;
	
	private IReplicationListener aCallback;
	
	private int aReplCount;
	
	private int aActualRepl;

	public ReplicationManager(IReplicationListener paCallback) {
		aCallback = paCallback;
		aStopped = false;
		aMs = null;
		aThreadPool = Executors.newFixedThreadPool(1);
	}

	public void run(int paPocetReplikacii, SimContainer paContainer) {
		aReplCount = paPocetReplikacii;
		reset();
		aThreadPool.submit(() -> {
			aCallback.onStart();
			for (int i = 0; i < paPocetReplikacii; i++) {
				if (aStopped) {
					break;
				}
				aActualRepl = i+1;
				aMs = new MySimulation(aContainer);
				aMs.setSimSpeed(1, 1);
				aCallback.onReplicationBegin(aMs);
				setSpeed();
				aMs.simulate();
				aCallback.onReplicationDone(aMs);
			}
			aCallback.onEnd();
		});
	}

	public int getReplCount() {
		return aReplCount;
	}

	public int getActualRepl() {
		return aActualRepl;
	}

	private void reset() {
		aStopped = false;
	}

	private void setSpeed() {
		if (aDelay == 0 && aMs != null) {
			aMs.setSimSpeed(aInterval, aDelay);
		}
		else if (aMs != null) {
			aMs.setMaxSimSpeed();
		}
	}
	
	public void setSimSpeed(double paInterval, double paDelay) {
		aInterval = paInterval;
		aDelay = paDelay;
		setSpeed();
	}
	
	public void stop() {
		aStopped = false;
	}
	
	

}
