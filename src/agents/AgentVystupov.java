package agents;

import OSPABA.*;
import OSPRNG.RNG;
import simulation.*;
import managers.*;
import continualAssistants.*;
import entity.Vozidlo;
import instantAssistants.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

//meta! id="15"
public class AgentVystupov extends Agent
{
	
	private final RNG<Double>[] aGeneratoryVystupov;
	
	public AgentVystupov(int id, Simulation mySim, Agent parent)
	{
		super(id, mySim, parent);
		init();
		LinkedList<Vozidlo> vsetkyVozidla = new LinkedList<>();
		((MySimulation)mySim).getVozidla().stream().forEach((l) -> l.stream().forEach((v) -> vsetkyVozidla.add(v)));
		aGeneratoryVystupov = new RNG[vsetkyVozidla.size()];
		for (Vozidlo v : vsetkyVozidla) {
			aGeneratoryVystupov[v.getId()] = v.getTypVozidlo().createGeneratorVystupu();
		}
	}
	
	public RNG<Double> getRNG(int id) {
		return aGeneratoryVystupov[id];
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init()
	{
		new ManagerVystupov(Id.managerVystupov, mySim(), this);
		new ProcessVystupu(Id.processVystupu, mySim(), this);
		addOwnMessage(Mc.vylozZakaznikov);
	}
	//meta! tag="end"
}