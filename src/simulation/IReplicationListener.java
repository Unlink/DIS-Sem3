/*
 *  Copyright (c) 2015 Michal Ďuračík
 */
package simulation;

import OSPABA.Simulation;

/**
 *
 * @author Unlink
 */
public interface IReplicationListener {
	
	public void onStart();
	
	public void onEnd();
	
	public void onReplicationDone(Simulation simulation);
	
	public void onReplicationBegin(Simulation simulation);
	
	public void onRefresh(Simulation simulation);
	
}
