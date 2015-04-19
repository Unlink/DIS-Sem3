/*
 *  Copyright (c) 2015 Michal Ďuračík
 */
package entity;

import OSPDataStruct.SimQueue;
import java.util.HashSet;

/**
 *
 * @author Unlink
 */
public class Zastavka {
	
	private int aId;
	private String aMeno;
	private int aPocLudi;
	private double aVzdialenost;
	private double aZacPrichodov;
	
	private Lndskjfn aDsadsa;
	
	private Dsaffds aGfdsg;
	

	public Zastavka(int paId, String paMeno, int paPocLudi) {
		this.aId = paId;
		this.aMeno = paMeno;
		this.aPocLudi = paPocLudi;
		this.aVzdialenost = -1;
		this.aZacPrichodov = -1;
		this.aDsadsa = () -> "N/A";
		this.aGfdsg = () -> 0;
	}

	public int getId() {
		return aId;
	}
	
	public String getMeno() {
		return aMeno;
	}

	public int getPocLudi() {
		return aPocLudi;
	}

	public double getVzdialenost() {
		return aVzdialenost;
	}

	public void setVzdialenost(double paVzdialenost) {
		this.aVzdialenost = paVzdialenost;
	}

	public double getZacPrichodov() {
		return aZacPrichodov;
	}

	public void setZacPrichodov(double paZacPrichodov) {
		this.aZacPrichodov = paZacPrichodov;
	}
	
	public String getDsadas() {
		return aDsadsa.lodfshs();
	}
	
	public void setDfxysfds(Lndskjfn sd) {
		aDsadsa = sd;
	}
	
	public int getSxhgd() {
		return aGfdsg.fdbgvdfs();
	}
	
	public void setGdhfhg(Dsaffds fdds) {
		aGfdsg = fdds;
	}
	
	public interface Lndskjfn {
		public String lodfshs();
	}
	
	public interface Dsaffds {
		public int fdbgvdfs();
	}
	
}
