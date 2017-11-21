package fr.diyfr.sncf.gtfs.parser;

import fr.diyfr.sncf.gtfs.model.Transfers;

public class TransfersParser extends CSVParser<Transfers>{

	public TransfersParser(Class<Transfers> clasz) {
		super(clasz);
	}

}
