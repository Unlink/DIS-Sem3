package agents;

import OSPABA.*;
import OSPRNG.RNG;
import OSPStat.Stat;
import container.SimContainer;
import simulation.*;
import managers.*;
import continualAssistants.*;
import entity.Linka;
import entity.Vozidlo;
import instantAssistants.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

//meta! id="15"
public class AgentVystupov extends Agent {

	private HashMap<Linka, Stat> aVytazenieLiniek;
	
	private int[] aPrepravenych;
	
	public AgentVystupov(int id, Simulation mySim, Agent parent, List<RNG<Double>> paGeneratoryVystupov, List<Linka> paLinky, int pocetTypovVozidiel) {
		super(id, mySim, parent);
		init();
		((ProcessVystupu) findAssistant(Id.processVystupu)).inject(paGeneratoryVystupov);
		aVytazenieLiniek = new HashMap<>();
		for (int i = 0; i < paLinky.size(); i++) {
			aVytazenieLiniek.put(paLinky.get(i), new Stat());
		}
		aPrepravenych = new int[pocetTypovVozidiel];
	}
	
	public void insertVytazenostSample(Linka paLinka, double paValue) {
		aVytazenieLiniek.get(paLinka).addSample(paValue);
	}
	
	public void insertVytazenost(int id, int pocet) {
		aPrepravenych[id] += pocet;
	}

	public HashMap<Linka, Stat> getVytazenieLiniek() {
		return aVytazenieLiniek;
	}

	public int[] getPrepravenych() {
		return aPrepravenych;
	}
	
	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init() {
		new ManagerVystupov(Id.managerVystupov, mySim(), this);
		new ProcessVystupu(Id.processVystupu, mySim(), this);
		addOwnMessage(Mc.vylozZakaznikov);
	}
	//meta! tag="end"

}
