package continualAssistants;

import OSPABA.*;
import simulation.*;
import agents.*;
import entity.Vozidlo;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

//meta! id="54"
public class SchedulerVozidiel extends Scheduler
{
	private final List<Queue<Vozidlo>> aVozidla;
	
	public SchedulerVozidiel(int id, Simulation mySim, CommonAgent myAgent)
	{
		super(id, mySim, myAgent);
		aVozidla = new ArrayList<>();
	}

	//meta! sender="AgentDepa", id="55"
	public void processStart(MessageForm message)
	{
		for (List<Vozidlo> vList: ((MySimulation) mySim()).getVozidla()) {
			Queue<Vozidlo> q = new LinkedList<>();
			for (Vozidlo v : vList) {
				q.add(v);
			}
			aVozidla.add(q);
		}
		
		for (int i = 0; i < aVozidla.size(); i++) {
			MyMessage mm = (MyMessage) message.createCopy();
			mm.setVozidlo(aVozidla.get(i).remove());
			mm.getVozidlo().setStav(Vozidlo.VozidloState.InRide);
			mm.setLinka(i);
			mm.setPomNum(0);
			mm.setCode(Mc.finish);
			processFinished(mm);
		}
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processOther(MessageForm message)
	{
		switch (message.code())
		{
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
		assistantFinished(mm);
		if (aVozidla.get(mm.getLinka()).size() > 0) {
			((MyMessage)message).setVozidlo(aVozidla.get(mm.getLinka()).remove());
			hold(5*60, message);
		}
	}
}