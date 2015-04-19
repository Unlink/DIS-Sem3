/*
 *  Copyright (c) 2015 Michal Ďuračík
 */
package entity;

/**
 *
 * @author Unlink
 */
public class Vozidlo {
	
	public static enum VozidloState {
		NotCreated,
		InRide,
		NotWaiting,
		Waiting, 
		WaitingEnded;
	}


	private static int aCounter = 1;

	private final int aId;
	private final TypVozidlo aTypVozidlo;
	private int aAktObsadenost;
	private int aAktObsadenostDveri;
	
	private VozidloState aStav;
	
	private String aPozicia;

	public Vozidlo(TypVozidlo paTypVozidlo) {
		this.aTypVozidlo = paTypVozidlo;
		this.aAktObsadenost = 0;
		this.aAktObsadenostDveri = 0;
		this.aId = aCounter++;
		this.aStav = VozidloState.NotCreated;
		this.aPozicia = "depo";
	}

	public static void resetCounter() {
		aCounter = 1;
	}
	
	public int getId() {
		return aId;
	}

	public TypVozidlo getTypVozidlo() {
		return aTypVozidlo;
	}

	public int getAktObsadenost() {
		return aAktObsadenost;
	}

	public int getAktObsadenostDveri() {
		return aAktObsadenostDveri;
	}

	public Vozidlo pridajPasaziera() {
		aAktObsadenost++;
		return this;
	}

	public Vozidlo obsadDvere() {
		aAktObsadenostDveri++;
		return this;
	}

	public Vozidlo uvoliDvere() {
		aAktObsadenostDveri--;
		return this;
	}

	public boolean maMiesto() {
		return aAktObsadenost < aTypVozidlo.getKapacita();
	}

	public boolean maVolneDvere() {
		return aAktObsadenostDveri < aTypVozidlo.getPocDveri();
	}

	public boolean nastupujuLudia() {
		return aAktObsadenostDveri != 0;
	}

	public void vyprazdniVozidlo() {
		aAktObsadenost = 0;
		aAktObsadenostDveri = 0;
	}
	
	public VozidloState getStav() {
		return aStav;
	}

	public void setStav(VozidloState paStav) {
		this.aStav = paStav;
	}

	public String getPozicia() {
		return aPozicia;
	}

	public void setPozicia(String paPozicia) {
		this.aPozicia = paPozicia;
	}

	@Override
	public int hashCode() {
		return this.aId;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Vozidlo other = (Vozidlo) obj;
		if (this.aId != other.aId) {
			return false;
		}
		return true;
	}
	
	

}
