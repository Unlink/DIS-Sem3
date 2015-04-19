package agents;

import OSPABA.*;
import OSPRNG.RNG;
import simulation.*;
import managers.*;
import continualAssistants.*;
import entity.Vozidlo;
import instantAssistants.*;
import java.util.LinkedList;

//meta! id="14"
public class AgentNastupov extends Agent
{
	
	private final RNG<Double>[] aGeneratoryNastupov;
	
	public AgentNastupov(int id, Simulation mySim, Agent parent)
	{
		super(id, mySim, parent);
		init();
		
		LinkedList<Vozidlo> vsetkyVozidla = new LinkedList<>();
		((MySimulation)mySim).getVozidla().stream().forEach((l) -> l.stream().forEach((v) -> vsetkyVozidla.add(v)));
		aGeneratoryNastupov = new RNG[vsetkyVozidla.size()];
		for (Vozidlo v : vsetkyVozidla) {
			aGeneratoryNastupov[v.getId()] = v.getTypVozidlo().createGeneratorNastupu();
		}
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