package managers;

import OSPABA.*;
import simulation.*;
import agents.*;
import continualAssistants.*;
import instantAssistants.*;

//meta! id="14"
public class ManagerNastupov extends Manager {
	public ManagerNastupov(int id, Simulation mySim, Agent myAgent) {
		super(id, mySim, myAgent);
	}

	//meta! sender="AgentPrepravy", id="67", type="request"
	public void processNalozZakaznikov(MessageForm message) {
	}

	//meta! sender="ProcessNastupu", id="44"
	public void processFinishProcessNastupu(MessageForm message) {
	}

	//meta! sender="SchedulerCakania", id="59"
	public void processFinishSchedulerCakania(MessageForm message) {
	}

	//meta! sender="AgentPrepravy", id="66", type="notice"
	public void processNovyZakaznik(MessageForm message) {
		MyMessage mm = (MyMessage) message;
		int zastavka = mm.getZastavka();
		myAgent().getFronta(zastavka).enqueue(mm.getPasazier());
		
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
}
