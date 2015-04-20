package simulation;

import OSPABA.*;
import entity.Linka;
import entity.Pasazier;
import entity.Vozidlo;

public class MyMessage extends MessageForm
{
	private Pasazier aPasazier;
	
	private Vozidlo aVozidlo;
	
	private int aZastavka;
	
	private Linka aLinka;
	
	public MyMessage(MyMessage original)
	{
		super(original);
		// copy() is called in superclass
	}

	public MyMessage(Simulation paMySim) {
		super(paMySim);
	}

	@Override
	public MessageForm createCopy()
	{
		return new MyMessage(this);
	}

	public Pasazier getPasazier() {
		return aPasazier;
	}

	public void setPasazier(Pasazier paPasazier) {
		this.aPasazier = paPasazier;
	}

	public Vozidlo getVozidlo() {
		return aVozidlo;
	}

	public void setVozidlo(Vozidlo paVozidlo) {
		this.aVozidlo = paVozidlo;
	}

	public int getZastavka() {
		return aZastavka;
	}

	public void setZastavka(int paZastavka) {
		this.aZastavka = paZastavka;
	}

	public Linka getLinka() {
		return aLinka;
	}

	public void setLinka(Linka paLinka) {
		this.aLinka = paLinka;
	}

	@Override
	protected void copy(MessageForm message)
	{
		super.copy(message);
		MyMessage original = (MyMessage)message;
		// Copy attributes
		aLinka = original.aLinka;
		aPasazier = original.aPasazier;
		aVozidlo = original.aVozidlo;
		aZastavka = original.aZastavka;
	}
	
	
}