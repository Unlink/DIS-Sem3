/*
 *  Copyright (c) 2015 Michal Ďuračík
 */
package container;

import OSPRNG.RNG;
import entity.Linka;
import entity.TypVozidlo;
import entity.Vozidlo;
import entity.Zastavka;
import java.util.List;
import simulation.ISimStats;
import simulation.MySimulationStatistics;

/**
 *
 * @author Unlink
 */
public class SimContainer implements IContainer {

	private List<Zastavka> aZastavky;
	private List<Linka> aLinky;
	private List<Vozidlo> aVozidla;
	private SimVariants aVarianta;
	private List<TypVozidlo> aTypyVozidiel;

	private List<RNG<Double>> aGeneratoryPrichodov;

	private List<RNG<Double>> aGeneratoryNastupov;
	private List<RNG<Double>> aGeneratoryVystupov;

	private double aStartZapasu;

	private double aOffset;

	private double aTrvaniePrichodov;
	
	private ISimStats aSimStats;

	public SimContainer(List<Zastavka> paZastavky, List<Linka> paLinky, List<TypVozidlo> paTypyVozidiel, List<Vozidlo> paVozidla,
		SimVariants paVarianta, double paStartZapasu, double paOffset, double paTrvaniePrichodov) {
		this.aZastavky = paZastavky;
		this.aLinky = paLinky;
		this.aTypyVozidiel = paTypyVozidiel;
		this.aVozidla = paVozidla;
		this.aVarianta = paVarianta;
		this.aStartZapasu = paStartZapasu;
		this.aOffset = paOffset;
		this.aTrvaniePrichodov = paTrvaniePrichodov;
		aSimStats = new MySimulationStatistics(paLinky, paTypyVozidiel);
	}

	public void injectGeneratoryPrichodov(List<RNG<Double>> paGeneratoryPrichodov) {
		this.aGeneratoryPrichodov = paGeneratoryPrichodov;
	}

	public void injectGeneratoryNastupov(List<RNG<Double>> paGeneratoryNastupov) {
		this.aGeneratoryNastupov = paGeneratoryNastupov;
	}

	public void injectGeneratoryVystupov(List<RNG<Double>> paGeneratoryVystupov) {
		this.aGeneratoryVystupov = paGeneratoryVystupov;
	}

	public List<Zastavka> getZastavky() {
		return aZastavky;
	}

	public List<Linka> getLinky() {
		return aLinky;
	}

	public List<Vozidlo> getVozidla() {
		return aVozidla;
	}

	public SimVariants getVariant() {
		return aVarianta;
	}

	public double getOffset() {
		return aOffset;
	}

	public double getStartZapasu() {
		return aStartZapasu;
	}

	public List<RNG<Double>> getGeneratoryNastupov() {
		return aGeneratoryNastupov;
	}

	public List<RNG<Double>> getGeneratoryVystupov() {
		return aGeneratoryVystupov;
	}

	public double getTrvaniePrichodov() {
		return aTrvaniePrichodov;
	}

	public List<RNG<Double>> getGeneratoryPrichodov() {
		return aGeneratoryPrichodov;
	}

	public int getPocetTypovVozidiel() {
		return aTypyVozidiel.size();
	}

	public SimVariants getVarianta() {
		return aVarianta;
	}

	public List<TypVozidlo> getTypyVozidiel() {
		return aTypyVozidiel;
	}

	@Override
	public ISimStats getSimStats() {
		return aSimStats;
	}
	
	
}
