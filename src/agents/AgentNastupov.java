package agents;

import OSPABA.*;
import OSPDataStruct.SimQueue;
import OSPRNG.RNG;
import container.SimContainer;
import container.SimVariants;
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
public class AgentNastupov extends Agent {

	private final SimQueue<Pasazier>[] aFronta;
	private final HashMap<Vozidlo, MyMessage>[] aVozidla;

	public AgentNastupov(int id, Simulation mySim, Agent parent, List<Zastavka> paZastavky,
		SimVariants paVariant, List<RNG<Double>> paGeneratoryNastupov) {
		super(id, mySim, parent);
		init();

		aFronta = new SimQueue[paZastavky.size()];
		aVozidla = new HashMap[paZastavky.size()];
		for (Zastavka z : paZastavky) {
			aFronta[z.getId()] = new SimQueue<>();
			aVozidla[z.getId()] = new HashMap<>();
		}

		((ManagerNastupov) manager()).inject(paVariant);
		((ProcessNastupu) findAssistant(Id.processNastupu)).inject(paGeneratoryNastupov);

	}

	public SimQueue<Pasazier> getFronta(int paZastavka) {
		return aFronta[paZastavka];
	}

	public HashMap<Vozidlo, MyMessage> getVozidla(int paZastavka) {
		return aVozidla[paZastavka];
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init() {
		new ManagerNastupov(Id.managerNastupov, mySim(), this);
		new SchedulerCakania(Id.schedulerCakania, mySim(), this);
		new ProcessNastupu(Id.processNastupu, mySim(), this);
		addOwnMessage(Mc.nalozZakaznikov);
		addOwnMessage(Mc.novyZakaznik);
	}
	//meta! tag="end"

}
