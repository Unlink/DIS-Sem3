package continualAssistants;

import OSPABA.*;
import simulation.*;
import agents.*;
import entity.Vozidlo;

//meta! id="54"
public class SchedulerVozidiel extends Scheduler
{
	public SchedulerVozidiel(int id, Simulation mySim, CommonAgent myAgent)
	{
		super(id, mySim, myAgent);
	}

	//meta! sender="AgentDepa", id="55"
	public void processStart(MessageForm message)
	{
		message.setCode(Id.finish);
		hold(0, message);
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
		MyMessage mm = (MyMessage) message;
		mm.setVozidlo(new Vozidlo(((MySimulation)mySim()).getVozidlaTypy().get(0)));
		assistantFinished(mm);
	}
}