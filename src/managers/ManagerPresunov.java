package managers;

import OSPABA.*;
import simulation.*;
import agents.*;
import continualAssistants.*;
import entity.Linka;
import instantAssistants.*;

//meta! id="8"
public class ManagerPresunov extends Manager
{
	public ManagerPresunov(int id, Simulation mySim, Agent myAgent)
	{
		super(id, mySim, myAgent);
	}

	//meta! sender="ProcessPresunuVozidla", id="62"
	public void processFinish(MessageForm message)
	{
		message.setCode(Mc.vybavVozidlo);
		message.setAddressee(((MySimulation)mySim()).agentPrepravy());
		request(message);
	}

	//meta! sender="AgentPrepravy", id="32", type="response"
	public void processVybavVozidlo(MessageForm message)
	{
		message.setAddressee(myAgent().findAssistant(Id.processPresunuVozidla));
		startContinualAssistant(message);
	}

	//meta! sender="AgentPrepravy", id="31", type="notice"
	public void processNoveVozidlo(MessageForm message)
	{
		message.setCode(Mc.vybavVozidlo);
		message.setAddressee(((MySimulation)mySim()).agentPrepravy());
		request(message);
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processOther(MessageForm message)
	{
		switch (message.code())
		{
		}
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	@Override
	public void processMessage(MessageForm message)
	{
		switch (message.code())
		{
		case Mc.noveVozidlo:
			processNoveVozidlo(message);
		break;

		case Mc.vybavVozidlo:
			processVybavVozidlo(message);
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