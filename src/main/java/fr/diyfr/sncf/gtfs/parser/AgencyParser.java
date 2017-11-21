package fr.diyfr.sncf.gtfs.parser;

import fr.diyfr.sncf.gtfs.model.Agency;

public class AgencyParser  extends CSVParser<Agency>{

	public AgencyParser(Class<Agency> clasz) {
		super(clasz);
	}
}
