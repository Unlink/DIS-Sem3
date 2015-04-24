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
public class AgentOkolia extends Agent {

	private SimCounter aPocitadlo;
	
	public AgentOkolia(int id, Simulation mySim, Agent parent) {
		super(id, mySim, parent);
		aPocitadlo = new SimCounter();
		init();
		addOwnMessage(Mc.novyZakaznik);
	}

	public SimCounter getPocitadlo() {
		return aPocitadlo;
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init()
	{
		new ManagerOkolia(Id.managerOkolia, mySim(), this);
		addOwnMessage(Mc.init);
	}
	//meta! tag="end"
}