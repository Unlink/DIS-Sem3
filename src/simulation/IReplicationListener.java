/*
 *  Copyright (c) 2015 Michal Ďuračík
 */
package simulation;

/**
 *
 * @author Unlink
 */
public interface IReplicationListener {
	
	public void onStart();
	
	public void onEnd();
	
	public void onReplicationDone(MySimulation simulation);
	
	public void onReplicationBegin(MySimulation simulation);
	
}
