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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Unlink
 */
public class SettingsTools {

	private File aFile;
	private JFileChooser aJfc;
	private JComponent aParent;

	public SettingsTools(JComponent paParent) {
		aParent = paParent;
		aFile = new File("rozpis.csv");
		aJfc = new JFileChooser(aFile);
		aJfc.setFileFilter(new FileNameExtensionFilter("CSV subory", "csv"));
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
