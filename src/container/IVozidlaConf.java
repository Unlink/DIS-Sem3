/*
 *  Copyright (c) 2015 Michal Ďuračík
 */
package container;

import entity.Linka;
import entity.TypVozidlo;
import gui.VozidlaPanel;
import java.util.List;

/**
 *
 * @author Unlink
 */
public interface IVozidlaConf {
	
	public Linka getLinka();
	
	public void insertVozidlo(TypVozidlo paTyp, double paTime);
	
	public void forEach(Command l);
	
	public List<IVozidloConf> getVozidla();
	
	public void removeAll();
	
	public interface Command {
		public void process(TypVozidlo paVozidlo, double paTime);
	}
	
}
