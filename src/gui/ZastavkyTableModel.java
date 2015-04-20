/*
 *  Copyright (c) 2015 Michal Ďuračík
 */
package gui;

import entity.Zastavka;
import java.util.List;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author Unlink
 */
public class ZastavkyTableModel implements TableModel {

	private final List<Zastavka> aZastavky;
	
	private enum Columns {
		ID {
			@Override
			public String getValue(Zastavka paZ) {
				return ""+paZ.getId();
			}
		}, 
		NAME {
			@Override
			public String getValue(Zastavka paZ) {
				return ""+paZ.getMeno();
			}
		}, 
		POCET_LUDI {
			@Override
			public String getValue(Zastavka paZ) {
				return "";//+paZ.getSxhgd();
			}
		}, 
		VOZIDLA {
			@Override
			public String getValue(Zastavka paZ) {
				return "";//paZ.getDsadas();
			}
		};
		
		public abstract String getValue(Zastavka z);
		
	}

	public ZastavkyTableModel(List<Zastavka> paZastavky) {
		this.aZastavky = paZastavky;
	}
	
	@Override
	public int getRowCount() {
		return aZastavky.size();
	}

	@Override
	public int getColumnCount() {
		return Columns.values().length;
	}

	@Override
	public String getColumnName(int paColumnIndex) {
		return Columns.values()[paColumnIndex].toString();
	}

	@Override
	public Class<?> getColumnClass(int paColumnIndex) {
		return String.class;
	}

	@Override
	public boolean isCellEditable(int paRowIndex, int paColumnIndex) {
		return false;
	}

	@Override
	public Object getValueAt(int paRowIndex, int paColumnIndex) {
		return Columns.values()[paColumnIndex].getValue(aZastavky.get(paRowIndex));
	}

	@Override
	public void setValueAt(Object paaValue, int paRowIndex, int paColumnIndex) {
	}

	@Override
	public void addTableModelListener(TableModelListener paL) {
	}

	@Override
	public void removeTableModelListener(TableModelListener paL) {
	}
	
}
