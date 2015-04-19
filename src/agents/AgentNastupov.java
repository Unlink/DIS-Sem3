package agents;

import OSPABA.*;
import simulation.*;
import managers.*;
import continualAssistants.*;
import instantAssistants.*;

//meta! id="14"
public class AgentNastupov extends Agent
{
	public AgentNastupov(int id, Simulation mySim, Agent parent)
	{
		super(id, mySim, parent);
		init();
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init()
	{
		new ManagerNastupov(Id.managerNastupov, mySim(), this);
		new QueryNastupu(Id.queryNastupu, mySim(), this);
		new ProcessNastupu(Id.processNastupu, mySim(), this);
		new SchedulerCakania(Id.schedulerCakania, mySim(), this);
		addOwnMessage(Mc.novyZakaznik);
		addOwnMessage(Mc.nalozZakaznikov);
	}
	//meta! tag="end"
}