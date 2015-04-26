package agents;

import OSPABA.*;
import container.SimContainer;
import simulation.*;
import managers.*;
import continualAssistants.*;
import entity.Vozidlo;
import instantAssistants.*;
import java.util.List;

//meta! id="20"
public class AgentDepa extends Agent {

	public AgentDepa(int id, Simulation mySim, Agent parent, List<Vozidlo> paVozidla) {
		super(id, mySim, parent);
		init();
		paVozidla.stream().forEach((v) -> v.reset());
		((SchedulerVozidiel) findAssistant(Id.schedulerVozidiel)).inject(paVozidla);
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init() {
		new ManagerDepa(Id.managerDepa, mySim(), this);
		new SchedulerVozidiel(Id.schedulerVozidiel, mySim(), this);
		addOwnMessage(Mc.init);
	}
	//meta! tag="end"
}
