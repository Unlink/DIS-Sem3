/*
 *  Copyright (c) 2015 Michal Ďuračík
 */
package container;

import OSPRNG.ExponentialRNG;
import OSPRNG.RNG;
import entity.Linka;
import entity.TypVozidlo;
import entity.Vozidlo;
import entity.Zastavka;
import gui.VozidlaPanel;
import gui.VozidloPanel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;
import tools.ImportTools;

/**
 *
 * @author Unlink
 */
public class ContainerBulider {

	private ImportTools aIt;

	private List<VozidlaPanel> aZoznamLiniek;

	private SimVariants aVarinata;
	
	private int aCounter;
	
	private static final int PRICHOD_MIN = 10;
	private static final int PRICHOD_MAX = 75;

	public ContainerBulider(ImportTools paIt, List<VozidlaPanel> paZoznamLiniek) {
		this.aIt = paIt;
		aZoznamLiniek = paZoznamLiniek;
		aVarinata = SimVariants.A;
	}

	public List<TypVozidlo> getTypyVozidiel() {
		return aIt.getVozidla();
	}

	public String[] getZoznamLiniek() {
		Object[] toArray = aIt.getLinky().stream().map(Linka::getId).toArray();
		return Arrays.copyOf(toArray, toArray.length, String[].class);
	}

	public String[] getZoznamVozidiel() {
		Object[] toArray = aIt.getVozidla().stream().map(TypVozidlo::getMeno).toArray();
		return Arrays.copyOf(toArray, toArray.length, String[].class);
	}
	
	private int getIdForWehicle() {
		return aCounter++;
	}
	
	public SimContainer build() {
		aCounter = 0;
		double offset = 0;
		for (VozidlaPanel a : aZoznamLiniek) {
			for (VozidloPanel v : a.getVozidla()) {
				offset = Math.min(offset, v.getTime());
			}
		}
		offset = offset*(-1);
		double maxD = aIt.getZastavky().stream().map(x -> x.getVzdialenost()).max((d1, d2) -> Double.compare(d1, d2)).get();
		double startZapasu = maxD + PRICHOD_MAX + offset;
		for (Zastavka z : aIt.getZastavky()) {
			z.setZacPrichodov(startZapasu - PRICHOD_MIN - z.getVzdialenost());
		}
		
		List<Vozidlo> vozidla = new ArrayList<>();
		int j = 0;
		for (VozidlaPanel p : aZoznamLiniek) {
			int jx = j;
			p.forEach((TypVozidlo paVozidlo, double paTime) -> {
				Linka l = aIt.getLinky().get(jx);
				Zastavka z = aIt.getZastavky().get(l.getZastavkaId(0));
				vozidla.add(new Vozidlo(paVozidlo, getIdForWehicle(), l, startZapasu - PRICHOD_MIN - z.getVzdialenost() + paTime));
			});
			j++;
		}

		SimContainer simContainer = new SimContainer(aIt.getZastavky(), aIt.getLinky(), vozidla, aVarinata, startZapasu, offset);
		simContainer.injectGeneratoryPrichodov(createGeneratoryPrichodov());
		simContainer.injectGeneratoryNastupov(createGeneratoryNastupov(vozidla));
		simContainer.injectGeneratoryVystupov(createGeneratoryVystupov(vozidla));

		return simContainer;
	}

	public void setVarinata(SimVariants paVarinata) {
		this.aVarinata = paVarinata;
	}

	private List<RNG<Double>> createGeneratoryPrichodov() {
		List<RNG<Double>> g = new ArrayList<>(aIt.getZastavky().size());
		for (Zastavka z : aIt.getZastavky()) {
			g.add(z.getId(), new ExponentialRNG(65 / (double) z.getPocLudi()));
		}
		return g;
	}

	private List<RNG<Double>> createGeneratoryNastupov(List<Vozidlo> paVozidla) {
		List<RNG<Double>> g = new ArrayList<>();
		paVozidla.stream().forEach((v) -> {
			g.add(v.getId(), v.getTypVozidlo().createGeneratorNastupu());
		});
		return g;
	}

	private List<RNG<Double>> createGeneratoryVystupov(List<Vozidlo> paVozidla) {
		List<RNG<Double>> g = new ArrayList<>();
		paVozidla.stream().forEach((v) -> {
			g.add(v.getId(), v.getTypVozidlo().createGeneratorVystupu());
		});
		return g;
	}

}
