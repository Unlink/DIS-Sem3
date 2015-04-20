package continualAssistants;

import OSPABA.*;
import simulation.*;
import agents.*;
import OSPABA.Process;
import container.SimContainer;

//meta! id="43"
public class ProcessNastupu extends Process
{
	public ProcessNastupu(int id, Simulation mySim, CommonAgent myAgent)
	{
		super(id, mySim, myAgent);
	}

	//meta! sender="AgentNastupov", id="44"
	public void processStart(MessageForm message)
	{
		MyMessage mm = (MyMessage) message;
		mm.setCode(Mc.finish);
		hold(context().getGeneratorNastupov(mm.getVozidlo()).sample(), mm);
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processOther(MessageForm message)
	{
		switch (message.code())
		{
			case Mc.finish:
				procesFinnished(message);
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

	private void procesFinnished(MessageForm message) {
		assistantFinished(message);
	}
	
	public SimContainer context() {
		return ((MySimulation) mySim()).getContext();
	}
}