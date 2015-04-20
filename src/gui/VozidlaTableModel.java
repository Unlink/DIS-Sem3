/*
 *  Copyright (c) 2015 Michal Ďuračík
 */
package gui;

import entity.Vozidlo;
import entity.Zastavka;
import java.util.List;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author Unlink
 */
public class VozidlaTableModel implements TableModel {

	private final List<Vozidlo> aVozidla;
	
	private enum Columns {
		ID {
			@Override
			public String getValue(Vozidlo paZ) {
				return ""+paZ.getId();
			}
		}, 
		NAME {
			@Override
			public String getValue(Vozidlo paZ) {
				return ""+paZ.getTypVozidlo().getMeno();
			}
		}, 
		POCET_LUDI {
			@Override
			public String getValue(Vozidlo paZ) {
				return ""+paZ.getAktObsadenost()+"/"+paZ.getTypVozidlo().getKapacita();
			}
		},
		POCET_OBSADENYCH_DVERI {
			@Override
			public String getValue(Vozidlo paZ) {
				return ""+paZ.getAktObsadenostDveri()+"/"+paZ.getTypVozidlo().getPocDveri();
			}
		},
		POZICIA {
			@Override
			public String getValue(Vozidlo paZ) {
				return "";//paZ.getPozicia();
			}
		},
		STAV {
			@Override
			public String getValue(Vozidlo paZ) {
				if (paZ.getStav() == Vozidlo.VozidloState.InRide) {
					return "v pohybe";
				}
				else if (paZ.getStav() == Vozidlo.VozidloState.NotCreated) {
					return "v depe";
				}
				else if (paZ.nastupujuLudia()) {
					return "nastup/vystup";
				}
				else {
					return "caka";
				}
			}
		};
		
		
		public abstract String getValue(Vozidlo v);
		
	}

	public VozidlaTableModel(List<Vozidlo> paVozidla) {
		this.aVozidla = paVozidla;
	}
	
	@Override
	public int getRowCount() {
		return aVozidla.size();
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
		return Columns.values()[paColumnIndex].getValue(aVozidla.get(paRowIndex));
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
