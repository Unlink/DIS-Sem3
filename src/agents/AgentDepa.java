package agents;

import OSPABA.*;
import simulation.*;
import managers.*;
import continualAssistants.*;
import instantAssistants.*;

//meta! id="20"
public class AgentDepa extends Agent
{
	public AgentDepa(int id, Simulation mySim, Agent parent)
	{
		super(id, mySim, parent);
		init();
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init()
	{
		new ManagerDepa(Id.managerDepa, mySim(), this);
		new SchedulerVozidiel(Id.schedulerVozidiel, mySim(), this);
		addOwnMessage(Mc.init);
	}
	//meta! tag="end"
}