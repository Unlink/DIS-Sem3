/*
 *  Copyright (c) 2015 Michal Ďuračík
 */
package container;

import OSPRNG.RNG;
import entity.Linka;
import entity.Vozidlo;
import entity.Zastavka;
import java.util.List;

/**
 *
 * @author Unlink
 */
public class SimContainer {
	
	private List<Zastavka> aZastavky;
	private List<Linka> aLinky;
	private List<List<Vozidlo>> aVozidla;
	private SimVariants aVarianta;
	
	private List<RNG<Double>> aGeneratoryPrichodov;
	
	private List<RNG<Double>> aGeneratoryNastupov;
	private List<RNG<Double>> aGeneratoryVystupov;
	

	public SimContainer(List<Zastavka> paZastavky, List<Linka> paLinky, List<List<Vozidlo>> paVozidla, SimVariants paVarianta) {
		this.aZastavky = paZastavky;
		this.aLinky = paLinky;
		this.aVozidla = paVozidla;
		this.aVarianta = paVarianta;
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
	
	
	
	
}
