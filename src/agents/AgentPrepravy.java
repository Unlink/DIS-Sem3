package agents;

import OSPABA.*;
import simulation.*;
import managers.*;
import continualAssistants.*;
import instantAssistants.*;

//meta! id="5"
public class AgentPrepravy extends Agent
{
	public AgentPrepravy(int id, Simulation mySim, Agent parent)
	{
		super(id, mySim, parent);
		init();
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init()
	{
		new ManagerPrepravy(Id.managerPrepravy, mySim(), this);
		addOwnMessage(Mc.noveVozidlo);
		addOwnMessage(Mc.vybavVozidlo);
		addOwnMessage(Mc.prepravenyZakaznik);
		addOwnMessage(Mc.nalozZakaznikov);
		addOwnMessage(Mc.init);
		addOwnMessage(Mc.novyZakaznik);
		addOwnMessage(Mc.vylozZakaznikov);
	}
	//meta! tag="end"
}