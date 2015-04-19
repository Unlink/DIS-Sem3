package managers;

import OSPABA.*;
import simulation.*;
import agents.*;
import continualAssistants.*;
import instantAssistants.*;

//meta! id="20"
public class ManagerDepa extends Manager
{
	public ManagerDepa(int id, Simulation mySim, Agent myAgent)
	{
		super(id, mySim, myAgent);
	}

	//meta! sender="SchedulerVozidiel", id="55"
	public void processFinish(MessageForm message)
	{
		message.setAddressee(myAgent().parent());
		message.setCode(Mc.noveVozidlo);
		notice(message);
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
		case Mc.init:
			processInit(message);
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
	//meta! sender="AgentPrepravy", id="73", type="notice"
	public void processInit(MessageForm message)
	{
		//@TOTO príchod autobusov
		for (int i=0; i<3; i++) {
			MyMessage mm = (MyMessage) message.createCopy();
			mm.setLinka(i);
			mm.setZastavka(0);
			mm.setAddressee(myAgent().findAssistant(Id.schedulerVozidiel));
			startContinualAssistant(mm);
		}
	}

}