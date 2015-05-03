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

	private double[] aVytazenieVozidiel;

	public AgentVystupov(int id, Simulation mySim, Agent parent, List<RNG<Double>> paGeneratoryVystupov, List<Linka> paLinky, int pocetTypovVozidiel, int pocVozidiel) {
		super(id, mySim, parent);
		init();
		((ProcessVystupu) findAssistant(Id.processVystupu)).inject(paGeneratoryVystupov);
		aVytazenieLiniek = new HashMap<>();
		for (int i = 0; i < paLinky.size(); i++) {
			aVytazenieLiniek.put(paLinky.get(i), new Stat());
		}
		aPrepravenych = new int[pocetTypovVozidiel];

		aVytazenieVozidiel = new double[pocVozidiel];
	}

	public void insertVytazenostSample(Vozidlo vozidlo, double paValue) {
		aVytazenieLiniek.get(vozidlo.getLinka()).addSample(paValue);
		insertVytazenostVozidla(vozidlo.getId(), paValue);
	}

	public void insertVytazenost(int id, int pocet) {
		aPrepravenych[id] += pocet;
	}

	public void insertVytazenostVozidla(int id, double vytazenost) {
		//Zaujima ma len prvá vytazenosť
		if (aVytazenieVozidiel[id] == 0) {
			aVytazenieVozidiel[id] = vytazenost;
		}
	}

	public HashMap<Linka, Stat> getVytazenieLiniek() {
		return aVytazenieLiniek;
	}

	public int[] getPrepravenych() {
		return aPrepravenych;
	}

	public double[] getVytazenieVozidiel() {
		return aVytazenieVozidiel;
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init() {
		new ManagerVystupov(Id.managerVystupov, mySim(), this);
		new ProcessVystupu(Id.processVystupu, mySim(), this);
		addOwnMessage(Mc.vylozZakaznikov);
	}
	//meta! tag="end"

}
