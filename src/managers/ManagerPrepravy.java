package managers;

import OSPABA.*;
import simulation.*;
import agents.*;
import container.SimContainer;
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
		MyMessage mm = (MyMessage) message;
		message.setCode(Mc.vybavVozidlo);
		response(message);
	}

	//meta! sender="AgentNastupov", id="67", type="response"
	public void processNalozZakaznikov(MessageForm message)
	{
		MyMessage mm = (MyMessage) message;
		message.setCode(Mc.vybavVozidlo);
		response(message);
	}

	//meta! sender="AgentPresunov", id="32", type="request"
	public void processVybavVozidlo(MessageForm message)
	{
		MyMessage mm = (MyMessage) message;
		if (mm.getZastavka() < 0) {
			mm.setAddressee(((MySimulation)mySim()).agentVystupov());
			mm.setCode(Mc.vylozZakaznikov);
		}
		else {
			mm.setAddressee(((MySimulation)mySim()).agentNastupov());
			mm.setCode(Mc.nalozZakaznikov);
		}
		request(mm);
	}

	//meta! sender="AgentDepa", id="30", type="notice"
	public void processNoveVozidlo(MessageForm message)
	{
		MyMessage mm = (MyMessage) message;
		message.setAddressee(((MySimulation)mySim()).agentPresunov());
		notice(message);
	}

	//meta! sender="AgentModelu", id="25", type="notice"
	public void processNovyZakaznik(MessageForm message)
	{
		((AgentPrepravy)myAgent()).getVygenerovanych().inc();
		MyMessage mm = (MyMessage) message;
		message.setAddressee(((MySimulation)mySim()).agentNastupov());
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
		case Mc.noveVozidlo:
			processNoveVozidlo(message);
		break;

		case Mc.vybavVozidlo:
			processVybavVozidlo(message);
		break;

		case Mc.prepravenyZakaznik:
			processPrepravenyZakaznik(message);
		break;

		case Mc.nalozZakaznikov:
			processNalozZakaznikov(message);
		break;

		case Mc.init:
			processInit(message);
		break;

		case Mc.novyZakaznik:
			processNovyZakaznik(message);
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
	//meta! sender="AgentModelu", id="71", type="notice"
	public void processInit(MessageForm message)
	{
		message.setAddressee(((MySimulation)mySim()).agentDepa());
		notice(message);
	}

	//meta! sender="AgentVystupov", id="74", type="notice"
	public void processPrepravenyZakaznik(MessageForm message)
	{
		((AgentPrepravy)myAgent()).getObsluzenych().inc();
		if (mySim().currentTime() >= context().getStartZapasu()) {
			if (((AgentPrepravy)myAgent()).getObsluzenych().val() == ((AgentPrepravy)myAgent()).getVygenerovanych().val()) {
				mySim().stopSimulation();
			}
		}
	}
	
	public SimContainer context() {
		return ((MySimulation) mySim()).getContext();
	}

}