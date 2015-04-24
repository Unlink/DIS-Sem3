package continualAssistants;

import OSPABA.*;
import simulation.*;
import agents.*;
import container.SimContainer;
import entity.Vozidlo;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

//meta! id="54"
public class SchedulerVozidiel extends Scheduler {
	private final Queue<Vozidlo> aVozidla;

	public SchedulerVozidiel(int id, Simulation mySim, CommonAgent myAgent) {
		super(id, mySim, myAgent);
		aVozidla = new PriorityQueue<>((v1, v2) -> Double.compare(v1.getCasPrichodu(), v2.getCasPrichodu()));
	}

	//meta! sender="AgentDepa", id="55"
	public void processStart(MessageForm message) {
		aVozidla.addAll(context().getVozidla());

		MyMessage mm = (MyMessage) message.createCopy();
		planujVozidlo(mm);
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processOther(MessageForm message) {
		switch (message.code()) {
			case Mc.finish:
				processFinished(message);
				break;
		}
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	@Override
	public void processMessage(MessageForm message)
	{
		switch (message.code())
		{
		case Mc.start:
			processStart(message);
		break;

		default:
			processOther(message);
		break;
		}
	}
	//meta! tag="end"

	private void processFinished(MessageForm message) {
		MyMessage mm = (MyMessage) message.createCopy();
		mm.getVozidlo().setStav(Vozidlo.VozidloState.InRide);
		assistantFinished(mm);
		if (aVozidla.size() > 0) {
			MyMessage mm2 = (MyMessage) message;
			planujVozidlo(mm2);
		}
	}

	private void planujVozidlo(MyMessage mm) {
		mm.setVozidlo(aVozidla.remove());
		mm.setLinka(mm.getVozidlo().getLinka());
		mm.setCode(Mc.finish);
		if (mm.getVozidlo().getCasPrichodu() <= mySim().currentTime()) {
			processFinished(mm);
		}
		else {
			hold(mm.getVozidlo().getCasPrichodu() - mySim().currentTime(), mm);
		}
	}

	public SimContainer context() {
		return ((MySimulation) mySim()).getContext();
	}
}