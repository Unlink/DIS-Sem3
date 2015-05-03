package managers;

import OSPABA.*;
import OSPStat.Stat;
import simulation.*;
import agents.*;
import continualAssistants.*;
import entity.Linka;
import entity.Vozidlo;
import instantAssistants.*;
import java.util.HashMap;
import java.util.List;

//meta! id="15"
public class ManagerVystupov extends Manager {

	public ManagerVystupov(int id, Simulation mySim, Agent myAgent) {
		super(id, mySim, myAgent);
	}

	//meta! sender="AgentPrepravy", id="68", type="request"
	public void processVylozZakaznikov(MessageForm message) {
		MyMessage mm = (MyMessage) message;
		if (mm.getVozidlo().getAktObsadenost()== 0) {
			mm.setCode(Mc.vylozZakaznikov);
			response(mm);
		}
		else {
			mm.getVozidlo().setStav(Vozidlo.VozidloState.Vystup);
			((AgentVystupov) myAgent()).insertVytazenostSample(mm.getVozidlo(), mm.getVozidlo().getAktObsadenost() / (double) mm.getVozidlo().getTypVozidlo().getKapacita());
			((AgentVystupov) myAgent()).insertVytazenost(mm.getVozidlo().getTypVozidlo().getId(), mm.getVozidlo().getAktObsadenost());
			vystupLudi(message);
		}
	}

	private void vystupLudi(MessageForm paMessage) {
		MyMessage mm = (MyMessage) paMessage;
		while (mm.getVozidlo().getAktObsadenost() > 0 && mm.getVozidlo().maVolneDvere()) {
			mm.getVozidlo().obsadDvere().odoberPasaziera();
			MyMessage mm2 = (MyMessage) mm.createCopy();
			mm2.setAddressee(myAgent().findAssistant(Id.processVystupu));
			startContinualAssistant(mm2);
		}
		if (mm.getVozidlo().getAktObsadenost() == 0 && !mm.getVozidlo().nastupujuLudia()) {
			mm.setCode(Mc.vylozZakaznikov);
			mm.getVozidlo().setStav(Vozidlo.VozidloState.InRide);
			response(mm);
		}
	}

	//meta! sender="ProcessVystupu", id="39"
	public void processFinish(MessageForm message) {
		MyMessage mm = (MyMessage) message.createCopy();
		mm.getVozidlo().uvoliDvere();
		mm.setAddressee(myAgent().parent());
		mm.setCode(Mc.prepravenyZakaznik);
		notice(mm);
		vystupLudi(message);
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processOther(MessageForm message) {
		switch (message.code()) {
		}
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	@Override
	public void processMessage(MessageForm message) {
		switch (message.code()) {
			case Mc.vylozZakaznikov:
				processVylozZakaznikov(message);
				break;

			case Mc.finish:
				processFinish(message);
				break;

			default:
				processOther(message);
				break;
		}
	}
	//meta! tag="end"
}
