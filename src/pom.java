
import simulation.Mc;
import simulation.MyMessage;
import simulation.MySimulation;
import tools.ImportTools;

/*
 *  Copyright (c) 2015 Michal Ďuračík
 */

/**
 *
 * @author Unlink
 */
public class pom {
	public static void main(String[] args) {		
		MySimulation ms = new MySimulation();
		MyMessage msg = new MyMessage(ms);
		msg.setAddressee(ms.agentModelu());
		msg.setCode(Mc.init);
		ms.agentModelu().manager().notice(msg);
		
		ms.simulate(60*60*100d);
		System.out.println(ms.agentOkolia().getVygenerovanych());
		System.out.println(ms.agentVystupov().getVylozenych());
	}
}
