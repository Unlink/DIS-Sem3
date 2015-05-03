/*
 *  Copyright (c) 2015 Michal Ďuračík
 */
package simulation;

import OSPABA.Simulation;
import OSPStat.Stat;
import entity.Linka;
import entity.TypVozidlo;
import entity.Vozidlo;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

/**
 *
 * @author Unlink
 */
public class MySimulationStatistics implements ISimStats {
	
	private HashMap<Linka, Stat> aVytazenostLiniek;
	
	private List<TypVozidlo> aVozidlaTyp;
	
	private Stat[] aPrepravenych;
	
	private HashMap<Linka, Stat> aCakanieNaLinke;
	
	private HashMap<Linka, Stat> aNeskoroNaLinke;
	
	private Stat aDobaCakania;
	
	private Stat aPocetNeskoro;
	
	private Stat[] aVytazenostVozidla;
	
	private List<Vozidlo> aVozidla;
		
	public MySimulationStatistics(List<Linka> paLinky, List<TypVozidlo> paVozidlaTyp, List<Vozidlo> paVozidla) {
		this.aVozidlaTyp = paVozidlaTyp;
		this.aPrepravenych = new Stat[aVozidlaTyp.size()];
		for (int i = 0; i < aPrepravenych.length; i++) {
			aPrepravenych[i] = new Stat();
		}
		
		this.aDobaCakania = new Stat();
		this.aPocetNeskoro = new Stat();
		
		this.aVozidla = paVozidla;
		this.aVytazenostVozidla = new Stat[paVozidla.size()];
		for (int i = 0; i < aVytazenostVozidla.length; i++) {
			aVytazenostVozidla[i] = new Stat();
		}
		
		this.aVytazenostLiniek = new HashMap();
		this.aCakanieNaLinke = new HashMap<>();
		this.aNeskoroNaLinke = new HashMap<>();
		for (Linka linka : paLinky) {
			aVytazenostLiniek.put(linka, new Stat());
			aCakanieNaLinke.put(linka, new Stat());
			aNeskoroNaLinke.put(linka, new Stat());
		}
	}
	
	@Override
	public void onReplicationDone(Simulation paSim) {
		MySimulation ms = (MySimulation) paSim;
		aDobaCakania.addSample(ms.agentNastupov().getDobaCakania().mean());
		aPocetNeskoro.addSample((ms.agentPrepravy().getObsluzenychNeskoro().val() / (double)ms.agentPrepravy().getObsluzenych().val())*100);
		
		for (Entry<Linka, Stat> s : ms.agentVystupov().getVytazenieLiniek().entrySet()) {
			aVytazenostLiniek.get(s.getKey()).addSample(s.getValue().mean());
		}
		
		for (Entry<Linka, Stat> s : ms.agentNastupov().getDobaCakaniaNaLinke().entrySet()) {
			aCakanieNaLinke.get(s.getKey()).addSample(s.getValue().mean());
		}
		
		for (Entry<Linka, SimCounter[]> s : ms.agentPrepravy().getNeskoroNaLinke().entrySet()) {
			aNeskoroNaLinke.get(s.getKey()).addSample((s.getValue()[1].val()/(double)s.getValue()[0].val())*100);
		}
		
		for (int i = 0; i < aPrepravenych.length; i++) {
			aPrepravenych[i].addSample(ms.agentVystupov().getPrepravenych()[i]);
		}
		
		for (int i = 0; i < aVytazenostVozidla.length; i++) {
			aVytazenostVozidla[i].addSample(ms.agentVystupov().getVytazenieVozidiel()[i]);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Replikacia: ").append(aDobaCakania.sampleSize()).append("\n");
		sb.append("Priemerná doba čakania: ").append(aDobaCakania).append("\n");
		sb.append("Percent oneskorených: ").append(aPocetNeskoro).append("\n");
		sb.append("\nVyťaženosť vozidiel na linke:\n");
		aVytazenostLiniek.entrySet().stream().forEach((s) -> {
			sb.append("Linka ").append(s.getKey().getId()).append(": ").append(s.getValue().toString()).append("\n");
		});
		sb.append("\nDoba čakania na linke:\n");
		aCakanieNaLinke.entrySet().stream().forEach((s) -> {
			sb.append("Linka ").append(s.getKey().getId()).append(": ").append(s.getValue().toString()).append("\n");
		});
		sb.append("\n% Oneskorených na linke:\n");
		aNeskoroNaLinke.entrySet().stream().forEach((s) -> {
			sb.append("Linka ").append(s.getKey().getId()).append(": ").append(s.getValue().toString()).append("\n");
		});
		sb.append("\nPrepravených vodidlom jednotlivého typu:\n");
		for (TypVozidlo v : aVozidlaTyp) {
			sb.append("Typ - ").append(v.getMeno()).append(": ").append(aPrepravenych[v.getId()].toString()).append("\n");
		}
		sb.append("\nVytaženie vozidiel:\n");
		for (Vozidlo v : aVozidla) {
			sb.append("Linka: ").append(v.getLinka().getId()).append(" vozidlo - ").append(v.getId()).append(": ").append(aVytazenostVozidla[v.getId()].mean()).append("\n");
		}
		return sb.toString();
	}
	
	public double getCasCakania() {
		return aDobaCakania.mean()/60;
	}
	
	public double getPPPneskoro() {
		return aPocetNeskoro.mean();
	}

	public HashMap<Linka, Stat> getVytazenostLiniek() {
		return aVytazenostLiniek;
	}

	public HashMap<Linka, Stat> getCakanieNaLinke() {
		return aCakanieNaLinke;
	}

	public HashMap<Linka, Stat> getNeskoroNaLinke() {
		return aNeskoroNaLinke;
	}
}
