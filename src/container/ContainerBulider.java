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
import java.util.Random;
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

	private Random aGenNasad;

	private static final int PRICHOD_MIN = 10 * 60;
	private static final int PRICHOD_MAX = 75 * 60;

	public ContainerBulider(ImportTools paIt, List<VozidlaPanel> paZoznamLiniek) {
		aGenNasad = new Random();
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

	public List<Linka> getLinky() {
		return aIt.getLinky();
	}

	private int getIdForWehicle() {
		return aCounter++;
	}

	public SimContainer build() {
		aCounter = 0;
		double maxD = aIt.getZastavky().stream().map(x -> x.getVzdialenost()).max((d1, d2) -> Double.compare(d1, d2)).get();
		double offset = 0;
		for (VozidlaPanel a : aZoznamLiniek) {
			for (VozidloPanel v : a.getVozidla()) {
				offset = Math.min(offset, v.getTime() + maxD - a.getLinka().getLength(false));
			}
		}
		offset = offset * (-1);
		double startZapasu = maxD + PRICHOD_MAX + offset;
		for (Zastavka z : aIt.getZastavky()) {
			z.setZacPrichodov(startZapasu - PRICHOD_MAX - z.getVzdialenost());
		}

		List<Vozidlo> vozidla = new ArrayList<>();
		int j = 0;
		for (VozidlaPanel p : aZoznamLiniek) {
			int jx = j;
			p.forEach((TypVozidlo paVozidlo, double paTime) -> {
				Linka l = p.getLinka();
				Zastavka z = aIt.getZastavky().get(l.getZastavkaId(0));
				vozidla.add(new Vozidlo(paVozidlo, getIdForWehicle(), l, startZapasu - PRICHOD_MAX - z.getVzdialenost() + paTime));
			});
			j++;
		}

		SimContainer simContainer = new SimContainer(aIt.getZastavky(), aIt.getLinky(), vozidla, aVarinata, startZapasu, offset, PRICHOD_MAX - PRICHOD_MIN);
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
			g.add(z.getId(), new ExponentialRNG((PRICHOD_MAX - PRICHOD_MIN) / (double) z.getPocLudi(), aGenNasad));
		}
		return g;
	}

	private List<RNG<Double>> createGeneratoryNastupov(List<Vozidlo> paVozidla) {
		List<RNG<Double>> g = new ArrayList<>();
		paVozidla.stream().forEach((v) -> {
			g.add(v.getId(), v.getTypVozidlo().createGeneratorNastupu(aGenNasad));
		});
		return g;
	}

	private List<RNG<Double>> createGeneratoryVystupov(List<Vozidlo> paVozidla) {
		List<RNG<Double>> g = new ArrayList<>();
		paVozidla.stream().forEach((v) -> {
			g.add(v.getId(), v.getTypVozidlo().createGeneratorVystupu(aGenNasad));
		});
		return g;
	}

}
