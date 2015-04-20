package continualAssistants;

import OSPABA.*;
import simulation.*;
import agents.*;
import OSPABA.Process;
import container.SimContainer;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

//meta! id="38"
public class ProcessVystupu extends Process
{	
	public ProcessVystupu(int id, Simulation mySim, CommonAgent myAgent)
	{
		super(id, mySim, myAgent);
	}

	//meta! sender="AgentVystupov", id="39"
	public void processStart(MessageForm message)
	{
		MyMessage mm = (MyMessage) message;
		PriorityQueue<Double> counter = new PriorityQueue<>();
		for (int i = 0; i < mm.getVozidlo().getTypVozidlo().getPocDveri(); i++) {
			counter.add(0d);
		}
		
		for (int i = 0; i < mm.getVozidlo().getAktObsadenost(); i++) {
			Double min = counter.poll();
			min+=context().getGeneratorVystupov(mm.getVozidlo()).sample();
			counter.add(min);
		}
		
		double trvanie = counter.stream().max((Double d1, Double d2) -> Double.compare(d1, d2)).get();
		message.setCode(Mc.finish);
		hold(trvanie, message);
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processOther(MessageForm message)
	{
		switch (message.code())
		{
			case Mc.finish:
				provessFinish(message);
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

	private void provessFinish(MessageForm message) {
		MyMessage mm = (MyMessage) message;
		mm.getVozidlo().vyprazdniVozidlo();
		assistantFinished(message);
	}
	
	public SimContainer context() {
		return ((MySimulation) mySim()).getContext();
	}
}