package agents;

import OSPABA.*;
import OSPRNG.RNG;
import container.SimContainer;
import simulation.*;
import managers.*;
import continualAssistants.*;
import entity.Vozidlo;
import instantAssistants.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

//meta! id="15"
public class AgentVystupov extends Agent {

	public AgentVystupov(int id, Simulation mySim, Agent parent, List<RNG<Double>> paGeneratoryVystupov) {
		super(id, mySim, parent);
		init();
		((ProcessVystupu) findAssistant(Id.processVystupu)).inject(paGeneratoryVystupov);
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init() {
		new ManagerVystupov(Id.managerVystupov, mySim(), this);
		new ProcessVystupu(Id.processVystupu, mySim(), this);
		addOwnMessage(Mc.vylozZakaznikov);
	}
	//meta! tag="end"

}
