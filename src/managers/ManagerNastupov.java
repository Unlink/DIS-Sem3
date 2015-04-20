package managers;

import OSPABA.*;
import simulation.*;
import agents.*;
import continualAssistants.*;
import entity.Pasazier;
import entity.Vozidlo;
import entity.Zastavka;
import instantAssistants.*;
import java.util.Optional;
import java.util.function.Predicate;

//meta! id="14"
public class ManagerNastupov extends Manager {

	public ManagerNastupov(int id, Simulation mySim, Agent myAgent) {
		super(id, mySim, myAgent);
	}

	//meta! sender="AgentPrepravy", id="67", type="request"
	public void processNalozZakaznikov(MessageForm message) {
		MyMessage mm = (MyMessage) message;
		mm.getVozidlo().setStav(Vozidlo.VozidloState.NotWaiting);
		int zastavka = mm.getZastavka();
		myAgent().getVozidla(zastavka).put(mm.getVozidlo(), mm);
		nakladajLudi(mm);
	}

	//meta! sender="ProcessNastupu", id="44"
	public void processFinishProcessNastupu(MessageForm message) {
		MyMessage mm = (MyMessage) message;
		mm.getVozidlo().uvoliDvere();
		nakladajLudi(mm);
	}

	//meta! sender="SchedulerCakania", id="59"
	public void processFinishSchedulerCakania(MessageForm message) {
		MyMessage mm = (MyMessage) message;
		if (!mm.getVozidlo().nastupujuLudia()) {
			ukonciObsluhuVozidla(mm);
		}
		else {
			mm.getVozidlo().setStav(Vozidlo.VozidloState.WaitingEnded);
		}
	}

	private void ukonciObsluhuVozidla(MyMessage mm) {
		myAgent().getVozidla(mm.getZastavka()).remove(mm.getVozidlo());
		mm.getVozidlo().setStav(Vozidlo.VozidloState.InRide);
		mm.setCode(Mc.nalozZakaznikov);
		response(mm);
	}

	//meta! sender="AgentPrepravy", id="66", type="notice"
	public void processNovyZakaznik(MessageForm message) {
		MyMessage mm = (MyMessage) message;
		int zastavka = mm.getZastavka();
		myAgent().getFronta(zastavka).enqueue(mm.getPasazier());
		//Ak prišiel na prázdnu zastavku
		if (myAgent().getFronta(zastavka).size() == 1) {
			Optional<Vozidlo> findFirst = myAgent().getVozidla(zastavka).keySet().stream().filter((Vozidlo v) -> v.getTypVozidlo().getMinCasZakaznika() == 0 && v.maMiesto()).findFirst();
			if (findFirst.isPresent()) {
				mm.setPasazier(myAgent().getFronta(zastavka).dequeue());
				mm = (MyMessage) myAgent().getVozidla(zastavka).get(findFirst.get()).createCopy();
				mm.getVozidlo().obsadDvere().pridajPasaziera();
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

	private void nakladajLudi(MyMessage mm) {
		int zastavka = mm.getZastavka();
		while (mm.getVozidlo().maMiesto() && mm.getVozidlo().maVolneDvere() && myAgent().getFronta(zastavka).size() > 0) {
			//Ak je zakaznik ochotný nastupiť do vozidla
			if (myAgent().getFronta(mm.getZastavka()).peek().timeInSystem() >= mm.getVozidlo().getTypVozidlo().getMinCasZakaznika()) {
				//Prišlo vozidlo do ktorého niesu zakaznici ochotný nastupiť hneď ale na zastavke je aj iné, do ktorého sú => nenastupujú
				if (mm.getVozidlo().getTypVozidlo().getMinCasZakaznika() == 0 || myAgent().getVozidla(mm.getZastavka()).keySet().stream().filter((Vozidlo v) -> v.getTypVozidlo().getMinCasZakaznika() == 0).count() == 0) {
					MyMessage mm2 = (MyMessage) mm.createCopy();
					mm2.setAddressee(myAgent().findAssistant(Id.processNastupu));
					mm2.setPasazier(myAgent().getFronta(zastavka).dequeue());
					mm2.getVozidlo().obsadDvere().pridajPasaziera();
					startContinualAssistant(mm2);
				}
				else {
					break;
				}
			}
			else {
				break;
			}
		}
		//ak už nikoho som nenaložil tak
		if (!mm.getVozidlo().nastupujuLudia()) {
			if (mm.getVozidlo().getTypVozidlo().getCaka() > 0 && mm.getVozidlo().getStav() == Vozidlo.VozidloState.NotWaiting) {
				mm.setAddressee(myAgent().findAssistant(Id.schedulerCakania));
				startContinualAssistant(mm);
				mm.getVozidlo().setStav(Vozidlo.VozidloState.Waiting);
			}
			else if (mm.getVozidlo().getStav() != Vozidlo.VozidloState.Waiting) {
				ukonciObsluhuVozidla(mm);
			}
		}
	}
}
