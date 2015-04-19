package simulation;

import OSPABA.*;
import entity.Pasazier;
import entity.Vozidlo;

public class MyMessage extends MessageForm
{
	private Pasazier aPasazier;
	
	private Vozidlo aVozidlo;
	
	private int aZastavka;
	
	private int aLinka;
	
	private int aPomNum;
	
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

	public int getLinka() {
		return aLinka;
	}

	public void setLinka(int paLinka) {
		this.aLinka = paLinka;
	}

	public int getPomNum() {
		return aPomNum;
	}

	public void setPomNum(int paPomNum) {
		this.aPomNum = paPomNum;
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
		aPomNum = original.aPomNum;
	}
	
	
}