package managers;

import OSPABA.*;
import simulation.*;
import agents.*;
import continualAssistants.*;
import instantAssistants.*;

//meta! id="1"
public class ManagerModelu extends Manager
{
	public ManagerModelu(int id, Simulation mySim, Agent myAgent)
	{
		super(id, mySim, myAgent);
	}

	//meta! sender="AgentOkolia", id="18", type="notice"
	public void processNovyZakaznik(MessageForm message)
	{
		message.setAddressee(((MySimulation)mySim()).agentPrepravy());
		notice(message);
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processOther(MessageForm message)
	{
		switch (message.code())
		{
			case Mc.init:
				MyMessage mm = (MyMessage) message.createCopy();
				mm.setAddressee(((MySimulation)mySim()).agentOkolia());
				notice(mm);
				mm = (MyMessage) message.createCopy();
				mm.setAddressee(((MySimulation)mySim()).agentPrepravy());
				notice(mm);
			break;
		}
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	@Override
	public void processMessage(MessageForm message)
	{
		switch (message.code())
		{
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