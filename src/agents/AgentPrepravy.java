package agents;

import OSPABA.*;
import simulation.*;
import managers.*;
import continualAssistants.*;
import instantAssistants.*;

//meta! id="5"
public class AgentPrepravy extends Agent {

	private SimCounter aVygenerovanych;
	private SimCounter aObsluzenych;

	public AgentPrepravy(int id, Simulation mySim, Agent parent, double paStartZapasu) {
		super(id, mySim, parent);
		init();
		aVygenerovanych = new SimCounter();
		aObsluzenych = new SimCounter();
		((ManagerPrepravy) manager()).inject(paStartZapasu);

	}

	public SimCounter getVygenerovanych() {
		return aVygenerovanych;
	}

	public SimCounter getObsluzenych() {
		return aObsluzenych;
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init() {
		new ManagerPrepravy(Id.managerPrepravy, mySim(), this);
		addOwnMessage(Mc.nalozZakaznikov);
		addOwnMessage(Mc.vybavVozidlo);
		addOwnMessage(Mc.prepravenyZakaznik);
		addOwnMessage(Mc.novyZakaznik);
		addOwnMessage(Mc.noveVozidlo);
		addOwnMessage(Mc.init);
		addOwnMessage(Mc.vylozZakaznikov);
	}
	//meta! tag="end"
}
