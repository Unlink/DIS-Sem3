/*
 *  Copyright (c) 2015 Michal Ďuračík
 */
package continualAssistants;

import OSPABA.CommonAgent;
import OSPABA.MessageForm;
import OSPABA.Scheduler;
import OSPABA.Simulation;
import OSPRNG.ExponentialRNG;
import agents.AgentOkolia;
import entity.Pasazier;
import simulation.Mc;
import simulation.MyMessage;

/**
 *
 * @author Unlink
 */
public class SchedulerPrichodovZakaznikov extends Scheduler {

	private int remainingGenerations;
	private double startTime;
	private ExponentialRNG aGenerator;

	public SchedulerPrichodovZakaznikov(int paId, Simulation paMySim, CommonAgent paMyAgent, int maxGenerated, double maxTime) {
		super(paId, paMySim, paMyAgent);
		remainingGenerations = maxGenerated;
		this.startTime = maxTime;
		aGenerator = new ExponentialRNG(maxGenerated / 65d);
	}

	@Override
	public void processMessage(MessageForm message) {
		switch (message.code()) {
			case Mc.start:
				processStart(message);
				break;
			case Mc.novyZakaznik:
				processNovyZakaznik(message);
				break;
			default:
				processOther(message);
				break;
		}
	}

	private void processStart(MessageForm paMessage) {
		paMessage.setCode(Mc.novyZakaznik);
		hold(startTime, paMessage);
	}

	private void processOther(MessageForm paMessage) {

	}

	private void processNovyZakaznik(MessageForm paMessage) {
		MyMessage mm = (MyMessage) paMessage.createCopy();
		//Planovanie ďalšieho
		remainingGenerations--;
		double next = aGenerator.sample();
		if (remainingGenerations > 0 && (startTime + 65 * 60) > (mySim().currentTime() + next)) {
			hold(next, paMessage);
		}

		//Oznamenie o vygenerovani
		mm.setCode(Mc.novyZakaznik);
		mm.setPasazier(new Pasazier(mySim()));
		assistantFinished(mm);
	}

}
