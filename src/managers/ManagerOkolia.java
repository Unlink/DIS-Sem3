package managers;

import OSPABA.*;
import OSPRNG.ExponentialRNG;
import OSPRNG.RNG;
import simulation.*;
import agents.*;
import container.SimContainer;
import continualAssistants.*;
import entity.Zastavka;
import instantAssistants.*;
import java.util.List;

//meta! id="2"
public class ManagerOkolia extends Manager {

	private SchedulerPrichodovZakaznikov[] aPlanovaci;

	private List<Zastavka> aZastavky;

	public ManagerOkolia(int id, Simulation mySim, Agent myAgent) {
		super(id, mySim, myAgent);
	}

	public void inject(List<Zastavka> paZastavky, double paTrvaniePrichodov, List<RNG<Double>> paGeneratoryPrichodov) {
		aZastavky = paZastavky;
		aPlanovaci = new SchedulerPrichodovZakaznikov[aZastavky.size()];
		for (Zastavka z : aZastavky) {
			aPlanovaci[z.getId()] = new SchedulerPrichodovZakaznikov(z.getId(), mySim(), myAgent(),
																	 z.getPocLudi(), z.getZacPrichodov(), z.getZacPrichodov() + paTrvaniePrichodov, paGeneratoryPrichodov.get(z.getId()));
		}
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processOther(MessageForm message) {
		switch (message.code()) {
			case Mc.finish:
				message.setCode(Mc.novyZakaznik);
				message.setAddressee(myAgent().parent());
				notice(message);
				break;
		}
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	@Override
	public void processMessage(MessageForm message) {
		switch (message.code()) {
			case Mc.init:
				processInit(message);
				break;

			default:
				processOther(message);
				break;
		}
	}

	//meta! tag="end"
	//meta! sender="AgentModelu", id="69", type="notice"

	public void processInit(MessageForm message) {
		for (Zastavka z : aZastavky) {
			MyMessage m = (MyMessage) message.createCopy();
			m.setAddressee(aPlanovaci[z.getId()]);
			m.setZastavka(z.getId());
			startContinualAssistant(m);
		}
	}

}
