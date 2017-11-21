package fr.diyfr.sncf.gtfs.parser;

import fr.diyfr.sncf.gtfs.model.Stops;

public class StopsParser extends CSVParser<Stops>{

	public StopsParser(Class<Stops> clasz) {
		super(clasz);
	}

}
