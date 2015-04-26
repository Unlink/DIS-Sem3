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

	private SimulationStatistics aStatis;

	public ReplicationManager(IReplicationListener paCallback) {
		aCallback = paCallback;
		aStopped = false;
		aMs = null;
		aThreadPool = Executors.newFixedThreadPool(1);
	}

	public void run(int paPocetReplikacii, SimContainer paContainer) {
		if (aMs != null) {
			return;
		}
		aContainer = paContainer;
		aReplCount = paPocetReplikacii;
		aStatis = new SimulationStatistics(paContainer.getLinky(), paContainer.getTypyVozidiel());
		synchronized (ReplicationManager.this) {
			reset();
		}
		aThreadPool.execute(() -> {
			aCallback.onStart();
			for (int i = 0; i < paPocetReplikacii; i++) {
				if (aStopped) {
					break;
				}
				aActualRepl = i + 1;
				synchronized (ReplicationManager.this) {
					aMs = new MySimulation(aContainer);
					MyMessage msg = new MyMessage(aMs);
					msg.setAddressee(aMs.agentModelu());
					msg.setCode(Mc.init);
					aMs.agentModelu().manager().notice(msg);
					aMs.setSimSpeed(1, 1);
					aMs.onRefreshUI((s) -> aCallback.onRefresh(aMs));
					aCallback.onReplicationBegin(aMs);
					setSpeed();
				}
				aMs.simulate();
				
				aStatis.onReplicationDone(aMs);
				aCallback.onReplicationDone(aMs);
			}
			aCallback.onEnd();
			aMs = null;
		});
	}

	public int getReplCount() {
		return aReplCount;
	}

	public int getActualRepl() {
		return aActualRepl;
	}

	public SimulationStatistics getStatis() {
		return aStatis;
	}

	public SimContainer getContainer() {
		return aContainer;
	}

	private void reset() {
		aStopped = false;
	}

	private void setSpeed() {
		if (aDelay > 0 && aMs != null) {
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
		synchronized (this) {
			aStopped = true;
			if (aMs != null) {
				aMs.stopSimulation();
			}
		}
	}

	public void pause() {
		synchronized (this) {
			if (aMs != null) {
				if (aMs.isPaused()) {
					aMs.resumeSimulation();
				} 
				else {
					aMs.pauseSimulation();
				}
			}
		}
	}
	
}
