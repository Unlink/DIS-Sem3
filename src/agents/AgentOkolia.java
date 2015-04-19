package agents;

import OSPABA.*;
import OSPRNG.ExponentialRNG;
import OSPRNG.RNG;
import simulation.*;
import managers.*;
import continualAssistants.*;
import entity.Zastavka;
import instantAssistants.*;

//meta! id="2"
public class AgentOkolia extends Agent
{
	
	private final RNG[] aGeneratoryPrichodov;
	
	public AgentOkolia(int id, Simulation mySim, Agent parent)
	{
		super(id, mySim, parent);
		init();
		aGeneratoryPrichodov = new RNG[((MySimulation)mySim).getZastavky().size()];
		for(Zastavka z:((MySimulation)mySim).getZastavky()) {
			aGeneratoryPrichodov[z.getId()] = new ExponentialRNG(z.getPocLudi()/65d);
		}
	}
	
	public RNG getRngFor(int z) {
		return aGeneratoryPrichodov[z];
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init()
	{
		new ManagerOkolia(Id.managerOkolia, mySim(), this);
	}
	//meta! tag="end"
}
