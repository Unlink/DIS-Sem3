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
		hold(getLinka(message).dajCasKDalsej(mm.getPomNum()), mm);
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
	
	public Linka getLinka(MessageForm mm) {
		return context().getLinky().get(((MyMessage)mm).getLinka());
	}

	private void processFinished(MessageForm message) {
		MyMessage mm = (MyMessage) message;
		mm.setPomNum(getLinka(message).dajDalsiuZastavku(mm.getPomNum()));
		mm.setZastavka(getLinka(message).getZastavkaId(mm.getPomNum()));
		assistantFinished(message);
	}
	
	public SimContainer context() {
		return ((MySimulation) mySim()).getContext();
	}
}