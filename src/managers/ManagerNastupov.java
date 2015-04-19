package managers;

import OSPABA.*;
import simulation.*;
import agents.*;
import continualAssistants.*;
import entity.Vozidlo;
import entity.Zastavka;
import instantAssistants.*;
import java.util.function.Predicate;

//meta! id="14"
public class ManagerNastupov extends Manager {
	public ManagerNastupov(int id, Simulation mySim, Agent myAgent) {
		super(id, mySim, myAgent);
	}

	//meta! sender="AgentPrepravy", id="67", type="request"
	public void processNalozZakaznikov(MessageForm message) {
		MyMessage mm = (MyMessage) message;
		myAgent().getCakajuceVozidla().remove(mm.getVozidlo());
		int zastavka = mm.getZastavka();
		myAgent().getVozidla(zastavka).put(mm.getVozidlo(), mm);
		nakladajLudi(mm);
	}

	//meta! sender="ProcessNastupu", id="44"
	public void processFinishProcessNastupu(MessageForm message) {
		MyMessage mm = (MyMessage) message;
		nakladajLudi(mm);
		//ak už nikoho som nenaložil tak odchod 
		if (!mm.getVozidlo().nastupujuLudia() && mm.getVozidlo().getTypVozidlo().getCaka() > 0 && !myAgent().getCakajuceVozidla().contains(mm.getVozidlo())) {
			mm.setAddressee(myAgent().findAssistant(Id.schedulerCakania));
			startContinualAssistant(mm);
			myAgent().getCakajuceVozidla().add(mm.getVozidlo());
		}
	}

	//meta! sender="SchedulerCakania", id="59"
	public void processFinishSchedulerCakania(MessageForm message) {
	}

	//meta! sender="AgentPrepravy", id="66", type="notice"
	public void processNovyZakaznik(MessageForm message) {
		MyMessage mm = (MyMessage) message;
		int zastavka = mm.getZastavka();
		myAgent().getFronta(zastavka).enqueue(mm.getPasazier());
		//Ak prišiel na prázdnu zastavku
		if (myAgent().getFronta(zastavka).size() == 1) {
			Vozidlo vozidlo = myAgent().getVozidla(zastavka).keySet().stream().filter((Vozidlo v) -> v.getTypVozidlo().getCaka() == 0 && v.maMiesto()).findFirst().get();
			if (vozidlo != null) {
				mm = (MyMessage) myAgent().getVozidla(zastavka).get(vozidlo).createCopy();
				mm.setAddressee(myAgent().findAssistant(Id.processNastupu));
				startContinualAssistant(mm);
			}
		}
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processOther(MessageForm message) {
		switch (message.code()) {
		}
	}

	@Override
	public AgentNastupov myAgent() {
		return (AgentNastupov) super.myAgent();
	}

	@Override
	public MySimulation mySim() {
		return (MySimulation) super.mySim();
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	@Override
	public void processMessage(MessageForm message) {
		switch (message.code()) {
			case Mc.nalozZakaznikov:
				processNalozZakaznikov(message);
				break;

			case Mc.finish:
				switch (message.sender().id()) {
					case Id.schedulerCakania:
						processFinishSchedulerCakania(message);
						break;

					case Id.processNastupu:
						processFinishProcessNastupu(message);
						break;
				}
				break;

			case Mc.novyZakaznik:
				processNovyZakaznik(message);
				break;

			default:
				processOther(message);
				break;
		}
	}
	//meta! tag="end"
	
	
	private void nakladajLudi(MyMessage paMm) {
		int zastavka = paMm.getZastavka();
		while (paMm.getVozidlo().maMiesto() && paMm.getVozidlo().maVolneDvere() && myAgent().getFronta(zastavka).size() > 0) {
			MyMessage mm2 = (MyMessage) paMm.createCopy();
			mm2.setAddressee(myAgent().findAssistant(Id.processNastupu));
			mm2.setPasazier(myAgent().getFronta(zastavka).dequeue());
			mm2.getVozidlo().obsadDvere();
			mm2.getVozidlo().pridajPasaziera();
			startContinualAssistant(mm2);
		}
	}
}
