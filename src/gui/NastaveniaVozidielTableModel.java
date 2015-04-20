/*
 *  Copyright (c) 2015 Michal Ďuračík
 */
package gui;

import container.ContainerBulider;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author Unlink
 */
public class NastaveniaVozidielTableModel implements TableModel {

	private String[] aVozidla;
	private String[] aLinky;
	private int[][] aData;

	public NastaveniaVozidielTableModel(String[] paVozidla, String[] paLinky) {
		this.aVozidla = paVozidla;
		this.aLinky = paLinky;
		aData = new int[aLinky.length][];
		for (int i = 0; i < aLinky.length; i++) {
			aData[i] = new int[aVozidla.length];
			for (int j = 0; j < aVozidla.length; j++) {
				aData[i][j] = 1;
			}
		}
	}

	@Override
	public int getRowCount() {
		return aData.length;
	}

	@Override
	public int getColumnCount() {
		return aVozidla.length + 1;
	}

	@Override
	public String getColumnName(int paColumnIndex) {
		if (paColumnIndex == 0) {
			return "Linka";
		}
		else {
			return aVozidla[paColumnIndex - 1];
		}
	}

	@Override
	public Class<?> getColumnClass(int paColumnIndex) {
		if (paColumnIndex == 0) {
			return String.class;
		}
		else {
			return int.class;
		}
	}

	@Override
	public boolean isCellEditable(int paRowIndex, int paColumnIndex) {
		return (paColumnIndex > 0);
	}

	@Override
	public Object getValueAt(int paRowIndex, int paColumnIndex) {
		if (paColumnIndex == 0) {
			return aLinky[paRowIndex];
		}
		else {
			return aData[paRowIndex][paColumnIndex - 1];
		}
	}

	@Override
	public void setValueAt(Object paaValue, int paRowIndex, int paColumnIndex) {
		aData[paRowIndex][paColumnIndex + 1] = Integer.parseInt(paaValue + "");
	}

	@Override
	public void addTableModelListener(TableModelListener paL) {
	}

	@Override
	public void removeTableModelListener(TableModelListener paL) {
	}

	public void setData(ContainerBulider cb) {
		for (int i = 0; i < aData.length; i++) {
			for (int j = 0; j < aData[i].length; j++) {
				cb.setCount(i, j, aData[i][j]);
			}
		}
	}

}
