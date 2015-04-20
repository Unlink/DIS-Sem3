package agents;

import OSPABA.*;
import container.SimContainer;
import simulation.*;
import managers.*;
import continualAssistants.*;
import entity.Vozidlo;
import instantAssistants.*;

//meta! id="8"
public class AgentPresunov extends Agent
{
	
	private int[] aPozicie;
	
	public AgentPresunov(int id, Simulation mySim, Agent parent)
	{
		super(id, mySim, parent);
		init();
		aPozicie = new int[context().getVozidla().size()];
	}
	
	public int getPoziciuPre(int paVozidlo) {
		return aPozicie[paVozidlo];
	}
	
	public int getPoziciuPre(Vozidlo paVozidlo) {
		return aPozicie[paVozidlo.getId()];
	}
	
	public void setPoziciuPre(int paVozidlo, int paPos) {
		aPozicie[paVozidlo] = paPos;
	}
	
	public void setPoziciuPre(Vozidlo paVozidlo, int paPos) {
		aPozicie[paVozidlo.getId()] = paPos;
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init()
	{
		new ManagerPresunov(Id.managerPresunov, mySim(), this);
		new ProcessPresunuVozidla(Id.processPresunuVozidla, mySim(), this);
		addOwnMessage(Mc.noveVozidlo);
		addOwnMessage(Mc.vybavVozidlo);
	}
	//meta! tag="end"
	
	public SimContainer context() {
		return ((MySimulation) mySim()).getContext();
	}
}