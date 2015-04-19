package agents;

import OSPABA.*;
import simulation.*;
import managers.*;
import continualAssistants.*;
import instantAssistants.*;

//meta! id="8"
public class AgentPresunov extends Agent
{
	public AgentPresunov(int id, Simulation mySim, Agent parent)
	{
		super(id, mySim, parent);
		init();
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init()
	{
		new ManagerPresunov(Id.managerPresunov, mySim(), this);
		new ProcessPresunuVozidla(Id.processPresunuVozidla, mySim(), this);
		addOwnMessage(Mc.vybavVozidlo);
		addOwnMessage(Mc.noveVozidlo);
	}
	//meta! tag="end"
}
