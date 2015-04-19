/*
 *  Copyright (c) 2015 Michal Ďuračík
 */
package tools;

import OSPRNG.ExponentialRNG;
import OSPRNG.RNG;
import OSPRNG.TriangularRNG;
import OSPRNG.UniformContinuousRNG;
import entity.IGeneratorFactory;
import entity.Linka;
import entity.TypVozidlo;
import entity.Zastavka;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import rng.ConstantRNG;

/**
 *
 * @author Unlink
 */
public class ImportTools {

	private HashMap<String, TypVozidlo> aVozidla;
	private HashMap<String, Zastavka> aZastavky;
	private HashMap<String, Linka> aLinky;
	private ArrayList<IGeneratorFactory> aGeneratory;
	private HashMap<String, List[]> aPom;
	private HashMap<String, Double> aCakania;

	public static ImportTools importData() {
		return new ImportTools();
	}

	private ImportTools() {
		aVozidla = new HashMap<>();
		aZastavky = new HashMap<>();
		aLinky = new HashMap<>();
		aGeneratory = new ArrayList<>();
		aPom = new HashMap<>();
		aCakania = new HashMap<>();
		String[] subory = new String[]{"zastavky", "linky", "generatory", "dopravneProstriedky"};
		for (String subor : subory) {
			String f = "/data/" + subor + ".csv";
			try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(f)))) {
				String line;
				while ((line = br.readLine()) != null) {
					String split[] = line.split(",");
					for (int i = 0; i < split.length; i++) {
						split[i] = split[i].trim();
					}
					System.out.println(String.join(",", split));
					switch (subor) {
						case "zastavky":
							insertZastavka(split);
							break;
						case "linky":
							insertLinka(split);
							break;
						case "generatory":
							insertGenerator(split);
							break;
						case "dopravneProstriedky":
							insertVozidlo(split);
							break;
					}
				}
			}
			catch (IOException ex) {
				throw new RuntimeException("Nepodarilo sa načítať konfiguráciu", ex);
			}
		}
		spocitajLinky();
	}

	private void spocitajLinky() {
		for (Map.Entry<String, List[]> entrySet : aPom.entrySet()) {
			double[] d = new double[entrySet.getValue()[1].size()];
			Zastavka[] z = new Zastavka[d.length];
			double summator = 0;
			for (int i = d.length - 1; i >= 0; i--) {
				d[i] = (double) entrySet.getValue()[1].get(i);
				z[i] = (Zastavka) entrySet.getValue()[0].get(i);
				if (i < d.length - 1) {
					summator += d[i];
					z[i].setVzdialenost(summator);
				}
			}
			aLinky.put(entrySet.getKey(), new Linka(entrySet.getKey(), z, d));
		}
	}

	private void insertZastavka(String[] paSplit) {
		Zastavka z = new Zastavka(paSplit[0], parseInt(paSplit[1]));
		aZastavky.put(z.getMeno(), z);
	}

	private void insertLinka(String[] paSplit) {
		Zastavka z = aZastavky.get(paSplit[1]);
		String linkaId = paSplit[0];
		if (!aPom.containsKey(linkaId)) {
			aPom.put(linkaId, new List[]{new LinkedList<>(), new LinkedList<>()});
		}
		List[] l = aPom.get(linkaId);
		l[0].add(z);
		l[1].add(parseDouble(paSplit[2]));
	}

	private void insertGenerator(String[] paSplit) {
		int id = parseInt(paSplit[0]);
		switch (paSplit[1]) {
			case "tria":
				aGeneratory.add(id, (IGeneratorFactory) () -> new TriangularRNG(parseDouble(paSplit[2]), parseDouble(paSplit[4]), parseDouble(paSplit[3])));
				break;
			case "unif":
				aGeneratory.add(id, (IGeneratorFactory) () ->  new UniformContinuousRNG(parseDouble(paSplit[2]), parseDouble(paSplit[3])));
				break;
			case "exp":
				aGeneratory.add(id, (IGeneratorFactory) () -> new ExponentialRNG(parseDouble(paSplit[2])));
				break;
		}
	}

	private void insertVozidlo(String[] paSplit) {
		String name = paSplit[0];
		aVozidla.put(name, new TypVozidlo(name, parseInt(paSplit[1]), parseInt(paSplit[2]), aGeneratory.get(parseInt(paSplit[4])), aGeneratory.get(parseInt(paSplit[5]))));
		aCakania.put(name, parseDouble(paSplit[3]));
	}

}
