package managers;

import OSPABA.*;
import OSPRNG.ExponentialRNG;
import OSPRNG.RNG;
import simulation.*;
import agents.*;
import continualAssistants.*;
import entity.Zastavka;
import instantAssistants.*;

//meta! id="2"
public class ManagerOkolia extends Manager
{
	private SchedulerPrichodovZakaznikov[] aPlanovaci;
	
	public ManagerOkolia(int id, Simulation mySim, Agent myAgent)
	{
		super(id, mySim, myAgent);
		aPlanovaci = new SchedulerPrichodovZakaznikov[((MySimulation)mySim).getZastavky().size()];
		for(Zastavka z:((MySimulation)mySim).getZastavky()) {
			aPlanovaci[z.getId()] = new SchedulerPrichodovZakaznikov(z.getId(), mySim(), myAgent(), z.getPocLudi(), z.getZacPrichodov());
		}
		
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processOther(MessageForm message)
	{
		switch (message.code())
		{
			case Mc.novyZakaznik:
				message.setAddressee(myAgent().parent());
				notice(message);
			break;
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

		default:
			processOther(message);
		break;
		}
	}
	//meta! tag="end"
	//meta! sender="AgentModelu", id="69", type="notice"
	public void processInit(MessageForm message)
	{
		for (Zastavka z : ((MySimulation)mySim()).getZastavky()) {
			MyMessage m = (MyMessage) message.createCopy();
			m.setAddressee(aPlanovaci[z.getId()]);
			m.setZastavka(z.getId());
			startContinualAssistant(m);
		}
	}

}