package continualAssistants;

import OSPABA.*;
import simulation.*;
import agents.*;
import OSPABA.Process;
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
		hold(getLinka(message).dajCasKDalsej(mm.getZastavka()), mm);
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
	
	private Linka getLinka(MessageForm mm) {
		return ((MySimulation)mySim()).getLinky().get(((MyMessage)mm).getLinka());
	}

	private void processFinished(MessageForm message) {
		MyMessage mm = (MyMessage) message;
		mm.setZastavka(getLinka(message).dajDalsiuZastavku(mm.getZastavka()));
		assistantFinished(message);
	}
}