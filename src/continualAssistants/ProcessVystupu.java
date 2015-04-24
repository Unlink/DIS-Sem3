package continualAssistants;

import OSPABA.*;
import simulation.*;
import agents.*;
import OSPABA.Process;
import container.SimContainer;
import entity.Vozidlo;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

//meta! id="38"
public class ProcessVystupu extends Process
{	
	public ProcessVystupu(int id, Simulation mySim, CommonAgent myAgent)
	{
		super(id, mySim, myAgent);
	}

	//meta! sender="AgentVystupov", id="39"
	public void processStart(MessageForm message)
	{
		MyMessage mm = (MyMessage) message;
		mm.setCode(Mc.finish);
		hold(context().getGeneratorVystupov(mm.getVozidlo()).sample(), mm);
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processOther(MessageForm message)
	{
		switch (message.code())
		{
			case Mc.finish:
				provessFinish(message);
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

	private void provessFinish(MessageForm message) {
		MyMessage mm = (MyMessage) message;
		assistantFinished(mm);
	}
	
	public SimContainer context() {
		return ((MySimulation) mySim()).getContext();
	}
}