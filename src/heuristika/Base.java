/*
 *  Copyright (c) 2015 Michal Ďuračík
 */
package heuristika;

import container.ContainerBulider;
import container.IVozidlaConf;
import entity.Linka;
import entity.TypVozidlo;
import gui.VozidloPanel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import simulation.ReplicationManager;
import tools.ImportTools;

/**
 *
 * @author Unlink
 */
public abstract class Base {

	protected ContainerBulider aBuilder;

	protected List<IVozidlaConf> aLinkyConf;

	protected File aResultsDir;

	protected int aCounter;

	public Base(File paResultsDir) {
		aResultsDir = paResultsDir;
		aLinkyConf = new ArrayList<>();
		aBuilder = new ContainerBulider(ImportTools.importData(), aLinkyConf);
		for (Linka linka : aBuilder.getLinky()) {
			aLinkyConf.add(new Vozidla(linka));
		}
		for (File f : aResultsDir.listFiles()) {
			if (f.isFile() && f.getName().matches("/conf-[0-9]+\\.csv/")) {
				String name = f.getName().substring(5, f.getName().length() - 4);
				aCounter = Math.max(aCounter, Integer.parseInt(name));
			}
		}
	}

	protected void prepocitajRovnomerne(int linka, int od) {
		double firstTime = aLinkyConf.get(linka).getVozidla().get(od).getTime();
		double length = aLinkyConf.get(linka).getLinka().getLength(true);

		int prvkov = aLinkyConf.get(linka).getVozidla().size() - od - 1;
		double step = (length - firstTime) / prvkov;
		for (int i = od + 1; i < aLinkyConf.get(linka).getVozidla().size(); i++) {
			aLinkyConf.get(linka).getVozidla().get(i).setTime(firstTime + (i - od) * step);
		}
	}

	protected List<IVozidlaConf> saveState() {
		List<IVozidlaConf> item = new ArrayList<>(aLinkyConf.size());
		for (IVozidlaConf aconf : aLinkyConf) {
			Vozidla v = new Vozidla(aconf.getLinka());
			aconf.forEach((type, time) -> v.insertVozidlo(type, time));
			item.add(v);
		}
		return item;
	}

	protected void restoreState(List<IVozidlaConf> state) {
		List<IVozidlaConf> pop = state;
		aLinkyConf.clear();
		for (IVozidlaConf conf : pop) {
			aLinkyConf.add(conf);
		}
	}

	protected void savaConfWithResults(ReplicationManager rm) {
		int i = 0;
		try (
			PrintWriter pw = new PrintWriter(new File(aResultsDir, "conf-" + (++aCounter) + ".csv"))) {
			for (IVozidlaConf p : aLinkyConf) {
				int closure = i;
				p.forEach((TypVozidlo paVozidlo, double paTime) -> {
					pw.println(closure + "," + paVozidlo.getId() + "," + paTime);
				});
				i++;
			}
		}
		catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
		int pocty[] = new int[aBuilder.getTypyVozidiel().size()];
		for (IVozidlaConf a : aLinkyConf) {
			a.forEach((typ, time) -> pocty[typ.getId()]++);
		}
		
		try (PrintWriter out = new PrintWriter(new FileWriter(new File(aResultsDir, "results.csv"), true))) {
			StringBuilder sb = new StringBuilder();
			sb.append(aCounter).append(",");
			for (int q : pocty) {
				sb.append(q).append(",");
			}
			sb.append(rm.getStatis().getCasCakania()).append(",");
			sb.append(rm.getStatis().getPPPneskoro());
			out.println(sb.toString());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
