package managers;

import OSPABA.*;
import simulation.*;
import agents.*;
import continualAssistants.*;
import instantAssistants.*;

//meta! id="15"
public class ManagerVystupov extends Manager
{
	public ManagerVystupov(int id, Simulation mySim, Agent myAgent)
	{
		super(id, mySim, myAgent);
	}

	//meta! sender="AgentPrepravy", id="68", type="request"
	public void processVylozZakaznikov(MessageForm message)
	{
		MyMessage mm = (MyMessage) message;
		((AgentVystupov)myAgent()).incVylozenych(mm.getVozidlo().getAktObsadenost());
		mm.setAddressee(myAgent().findAssistant(Id.processVystupu));
		startContinualAssistant(mm);
	}

	//meta! sender="ProcessVystupu", id="39"
	public void processFinish(MessageForm message)
	{
		MyMessage mm = (MyMessage) message;
		mm.setCode(Mc.vylozZakaznikov);
		response(mm);
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
		case Mc.finish:
			processFinish(message);
		break;

		case Mc.vylozZakaznikov:
			processVylozZakaznikov(message);
		break;

		default:
			processOther(message);
		break;
		}
	}
	//meta! tag="end"
}