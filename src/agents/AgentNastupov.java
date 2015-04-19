package agents;

import OSPABA.*;
import OSPDataStruct.SimQueue;
import OSPRNG.RNG;
import simulation.*;
import managers.*;
import continualAssistants.*;
import entity.Pasazier;
import entity.Vozidlo;
import entity.Zastavka;
import instantAssistants.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

//meta! id="14"
public class AgentNastupov extends Agent
{
	
	private final RNG<Double>[] aGeneratoryNastupov;
	private final SimQueue<Pasazier>[] aFronta;
	private final HashMap<Vozidlo, MyMessage>[] aVozidla;
	
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
		List<Zastavka> zastavky = ((MySimulation)mySim).getZastavky();
		aFronta = new SimQueue[zastavky.size()];
		aVozidla = new HashMap[zastavky.size()];
		for (Zastavka z: ((MySimulation)mySim).getZastavky()) {
			aFronta[z.getId()] = new SimQueue<>();
			aVozidla[z.getId()] = new HashMap<>();
		}
	}
	
	public SimQueue<Pasazier> getFronta(int paZastavka) {
		return aFronta[paZastavka];
	}
	
	public HashMap<Vozidlo, MyMessage> getVozidla(int paZastavka) {
		return aVozidla[paZastavka];
	}
	
	public RNG<Double> getRNG(int id) {
		return aGeneratoryNastupov[id];
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init()
	{
		new ManagerNastupov(Id.managerNastupov, mySim(), this);
		new SchedulerCakania(Id.schedulerCakania, mySim(), this);
		new ProcessNastupu(Id.processNastupu, mySim(), this);
		addOwnMessage(Mc.nalozZakaznikov);
		addOwnMessage(Mc.novyZakaznik);
	}
	//meta! tag="end"
}