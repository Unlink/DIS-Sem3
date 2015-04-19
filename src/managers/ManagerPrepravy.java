package managers;

import OSPABA.*;
import simulation.*;
import agents.*;
import continualAssistants.*;
import instantAssistants.*;

//meta! id="5"
public class ManagerPrepravy extends Manager
{
	public ManagerPrepravy(int id, Simulation mySim, Agent myAgent)
	{
		super(id, mySim, myAgent);
	}

	//meta! sender="AgentVystupov", id="68", type="response"
	public void processVylozZakaznikov(MessageForm message)
	{
	}

	//meta! sender="AgentNastupov", id="67", type="response"
	public void processNalozZakaznikov(MessageForm message)
	{
	}

	//meta! sender="AgentPresunov", id="32", type="request"
	public void processVybavVozidlo(MessageForm message)
	{
	}

	//meta! sender="AgentDepa", id="30", type="notice"
	public void processNoveVozidlo(MessageForm message)
	{
	}

	//meta! sender="AgentModelu", id="25", type="notice"
	public void processNovyZakaznik(MessageForm message)
	{
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

		case Mc.init:
			processInit(message);
		break;

		case Mc.vylozZakaznikov:
			processVylozZakaznikov(message);
		break;

		case Mc.novyZakaznik:
			processNovyZakaznik(message);
		break;

		case Mc.vybavVozidlo:
			processVybavVozidlo(message);
		break;

		case Mc.nalozZakaznikov:
			processNalozZakaznikov(message);
		break;

		default:
			processOther(message);
		break;
		}
	}
	//meta! tag="end"
	//meta! sender="AgentModelu", id="71", type="notice"
	public void processInit(MessageForm message)
	{
	}

}