/*
 *  Copyright (c) 2015 Michal Ďuračík
 */
package tools;

import container.IVozidlaConf;
import entity.TypVozidlo;
import gui.VozidlaPanel;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import simulation.MySimulationStatistics;

/**
 *
 * @author Unlink
 */
public class SettingsTools {

	private File aFile;
	private JFileChooser aJfc;
	private JComponent aParent;

	private File aSDir;

	private int aLastId;

	private StringBuilder aLastConf;

	private int aLastConfCounts[];

	private int aReplikacii;

	public SettingsTools(JComponent paParent) {
		aParent = paParent;
		aFile = new File("rozpis.csv");
		aJfc = new JFileChooser(aFile);
		aJfc.setFileFilter(new FileNameExtensionFilter("CSV subory", "csv"));
		aSDir = null;
		aLastId = -1;
		aLastConf = new StringBuilder();
	}

	public void choseDir() {
		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (jfc.showOpenDialog(aParent) == JFileChooser.APPROVE_OPTION) {
			setSaveDir(jfc.getSelectedFile());
		}
	}

	public void setSaveDir(File paSaveDir) {
		aSDir = paSaveDir;
		for (File f : aSDir.listFiles()) {
			if (f.isFile() && f.getName().matches("conf-[0-9]+\\.csv")) {
				String name = f.getName().substring(5, f.getName().length() - 4);
				aLastId = Math.max(aLastId, Integer.parseInt(name));
			}
		}
		File stats = new File(aSDir, "results.csv");
		if (!stats.exists()) {
			try {
				stats.createNewFile();
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public void prepareSimulation(List<IVozidlaConf> paLinky, List<TypVozidlo> paTypy, int paReplikacii) {
		if (aSDir != null) {
			aLastConf.delete(0, aLastConf.length());
			aReplikacii = paReplikacii;
			int i = 0;
			for (IVozidlaConf p : paLinky) {
				int closure = i;
				p.forEach((TypVozidlo paVozidlo, double paTime) -> {
					aLastConf.append(closure).append(",").append(paVozidlo.getId()).append(",").append(paTime).append("\n");
				});
				i++;
			}
			aLastConfCounts = new int[paLinky.size() * paTypy.size()];
			for (int j = 0; j < paLinky.size(); j++) {
				int c = j;
				paLinky.get(j).forEach((typ, time) -> aLastConfCounts[c * paTypy.size() + typ.getId()]++);
			}
		}
	}

	public void saveResults(MySimulationStatistics ss) {
		if (aSDir != null) {
			try (
				PrintWriter pw = new PrintWriter(new File(aSDir, "conf-" + (++aLastId) + ".csv"))) {
				pw.print(aLastConf.toString());
			}
			catch (FileNotFoundException ex) {
				ex.printStackTrace();
			}

			try (PrintWriter out = new PrintWriter(new FileWriter(new File(aSDir, "results.csv"), true))) {
				StringBuilder sb = new StringBuilder();
				sb.append(aLastId).append(",");
				for (int q : aLastConfCounts) {
					sb.append(q).append(",");
				}
				sb.append(aReplikacii).append(",");
				sb.append(ss.getCasCakania()).append(",");
				sb.append(ss.getPPPneskoro());
				out.println(sb.toString());
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public void save(List<IVozidlaConf> paLinky) {
		if (aJfc.showSaveDialog(aParent) == JFileChooser.APPROVE_OPTION) {
			aFile = aJfc.getSelectedFile();
			if (!aFile.getName().endsWith(".csv")) {
				aFile = new File(aFile.getAbsolutePath() + ".csv");
			}
			int i = 0;
			try (
				PrintWriter pw = new PrintWriter(aFile);) {
				for (IVozidlaConf p : paLinky) {
					int closure = i;
					p.forEach((TypVozidlo paVozidlo, double paTime) -> {
						pw.println(closure + "," + paVozidlo.getId() + "," + paTime);
					});
					i++;
				}
			}
			catch (FileNotFoundException ex) {
				JOptionPane.showMessageDialog(aParent, "Nepodatilo na nájsť súbor\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public void open(List<IVozidlaConf> paLinky, List<TypVozidlo> paVozidla) {
		if (aJfc.showOpenDialog(aParent) == JFileChooser.APPROVE_OPTION) {
			aFile = aJfc.getSelectedFile();
			paLinky.stream().forEach((IVozidlaConf p) -> p.removeAll());
			try (BufferedReader br = new BufferedReader(new FileReader(aFile));) {
				String line;
				while ((line = br.readLine()) != null) {
					String[] split = line.split(",");
					paLinky.get(Integer.parseInt(split[0])).insertVozidlo(paVozidla.get(Integer.parseInt(split[1])), Double.parseDouble(split[2]));
				}
			}
			catch (IOException ex) {
				JOptionPane.showMessageDialog(aParent, "Nepodatilo na nájsť súbor\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}
