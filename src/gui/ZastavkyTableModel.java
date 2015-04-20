/*
 *  Copyright (c) 2015 Michal Ďuračík
 */
package gui;

import agents.AgentNastupov;
import container.SimContainer;
import entity.Vozidlo;
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

	private SimContainer aContext;
	private AgentNastupov aAgent;

	private enum Columns {
		ID {
				@Override
				public String getValue(Zastavka paZ) {
					return "" + paZ.getId();
				}
			},
		NAME {
				@Override
				public String getValue(Zastavka paZ) {
					return "" + paZ.getMeno();
				}
			},
		POCET_LUDI {
				@Override
				public String getValue(Zastavka paZ) {
					return "" + agent.getFronta(paZ.getId()).size();
				}
			},
		VOZIDLA {
				@Override
				public String getValue(Zastavka paZ) {
					StringBuilder stringBuilder = new StringBuilder();
					for (Vozidlo vozidla : agent.getVozidla(paZ.getId()).keySet()) {
						stringBuilder.append(vozidla.getTypVozidlo().getMeno() + " (" + vozidla.getId() + "), ");
					}
					if (stringBuilder.length() > 0) {
						stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
					}
					return stringBuilder.toString();
				}
			};

		public abstract String getValue(Zastavka z);

		public static SimContainer context;
		public static AgentNastupov agent;

	}

	public ZastavkyTableModel(List<Zastavka> paZastavky, SimContainer paContext, AgentNastupov paAgent) {
		this.aZastavky = paZastavky;
		this.aContext = paContext;
		this.aAgent = paAgent;
		Columns.agent = aAgent;
		Columns.context = aContext;
	}

	public void setAgent(AgentNastupov paAgent) {
		this.aAgent = paAgent;
		Columns.agent = aAgent;
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
