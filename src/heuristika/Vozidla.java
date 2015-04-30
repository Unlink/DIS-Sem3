/*
 *  Copyright (c) 2015 Michal Ďuračík
 */
package heuristika;

import container.IVozidlaConf;
import container.IVozidloConf;
import entity.Linka;
import entity.TypVozidlo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Unlink
 */
public class Vozidla implements IVozidlaConf {

	private Linka aLinka;

	private List<IVozidloConf> aVozidla;

	public Vozidla(Linka paLinka) {
		this.aLinka = paLinka;
		this.aVozidla = new ArrayList<>();
	}

	@Override
	public Linka getLinka() {
		return aLinka;
	}

	@Override
	public void insertVozidlo(TypVozidlo paTyp, double paTime) {
		aVozidla.add(new Vozidlo(paTyp, paTime));
	}

	@Override
	public void forEach(Command paL) {
		aVozidla.stream().forEach((v) -> paL.process(v.getTyp(), v.getTime()));
	}

	@Override
	public List<IVozidloConf> getVozidla() {
		return aVozidla;
	}

	@Override
	public void removeAll() {
		aVozidla.clear();
	}

}
