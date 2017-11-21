package fr.diyfr.sncf.gtfs.model;

import fr.diyfr.sncf.gtfs.exception.FileFormatException;

/**
 * Interface de base pour les éléments GTFS 
 */
public interface GTFSModel {

	public int getNbEntry();
	public void fromStringArray(String[] value) throws FileFormatException;
}
