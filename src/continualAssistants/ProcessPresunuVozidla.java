package continualAssistants;

import OSPABA.*;
import simulation.*;
import agents.*;
import OSPABA.Process;
import container.SimContainer;
import entity.Linka;

//meta! id="61"
public class ProcessPresunuVozidla extends Process
{
	public ProcessPresunuVozidla(int id, Simulation mySim, CommonAgent myAgent)
	{
		super(id, mySim, myAgent);
	}

	//meta! sender="AgentPresunov", id="62"
	public void processStart(MessageForm message)
	{
		MyMessage mm = (MyMessage) message;
		mm.setCode(Mc.finish);
		hold(mm.getVozidlo().getLinka().dajCasKDalsej(((AgentPresunov)myAgent()).getPoziciuPre(mm.getVozidlo())), mm);
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
		AgentPresunov agent = (AgentPresunov) myAgent();
		agent.setPoziciuPre(mm.getVozidlo(), mm.getVozidlo().getLinka().dajDalsiuZastavku(agent.getPoziciuPre(mm.getVozidlo())));
		mm.setZastavka(mm.getVozidlo().getLinka().getZastavkaId(agent.getPoziciuPre(mm.getVozidlo())));
		assistantFinished(mm);
	}
	
	public SimContainer context() {
		return ((MySimulation) mySim()).getContext();
	}
}