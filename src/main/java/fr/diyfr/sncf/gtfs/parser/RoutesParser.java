package fr.diyfr.sncf.gtfs.parser;

import fr.diyfr.sncf.gtfs.model.Routes;

public class RoutesParser extends CSVParser<Routes>{

	public RoutesParser(Class<Routes> clasz) {
		super(clasz);
	}

}
