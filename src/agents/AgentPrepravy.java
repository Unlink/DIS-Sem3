package agents;

import OSPABA.*;
import OSPStat.Stat;
import simulation.*;
import managers.*;
import continualAssistants.*;
import entity.Linka;
import instantAssistants.*;
import java.util.HashMap;
import java.util.List;

//meta! id="5"
public class AgentPrepravy extends Agent {

	private SimCounter aVygenerovanych;
	private SimCounter aObsluzenych;
	private SimCounter aObsluzenychNeskoro;
	private HashMap<Linka, SimCounter[]> aNeskoroNaLinke;

	public AgentPrepravy(int id, Simulation mySim, Agent parent, double paStartZapasu, List<Linka> paLinky) {
		super(id, mySim, parent);
		init();
		aVygenerovanych = new SimCounter();
		aObsluzenych = new SimCounter();
		aObsluzenychNeskoro = new SimCounter();
		aNeskoroNaLinke = new HashMap<>();
		for (Linka linka : paLinky) {
			aNeskoroNaLinke.put(linka, new SimCounter[]{new SimCounter(), new SimCounter()});
		}
		((ManagerPrepravy) manager()).inject(paStartZapasu);

	}

	public SimCounter getVygenerovanych() {
		return aVygenerovanych;
	}

	public SimCounter getObsluzenych() {
		return aObsluzenych;
	}
	
	public SimCounter getObsluzenychNeskoro() {
		return aObsluzenychNeskoro;
	}

	public HashMap<Linka, SimCounter[]> getNeskoroNaLinke() {
		return aNeskoroNaLinke;
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
