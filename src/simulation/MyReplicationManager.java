/*
 *  Copyright (c) 2015 Michal Ďuračík
 */
package simulation;

import OSPABA.Simulation;
import container.IContainer;
import container.SimContainer;

/**
 *
 * @author Unlink
 */
public class MyReplicationManager extends ReplicationManager {

	public MyReplicationManager(IReplicationListener paCallback) {
		super(paCallback);
	}

	@Override
	public SimContainer getContainer() {
		return (SimContainer) super.getContainer();
	}

	@Override
	protected Simulation createSimulation(IContainer paContainer) {
		MySimulation ms = new MySimulation((SimContainer)paContainer);
		MyMessage msg = new MyMessage(ms);
		msg.setAddressee(ms.agentModelu());
		msg.setCode(Mc.init);
		ms.agentModelu().manager().notice(msg);
		return ms;
	}
	
	
	
}
