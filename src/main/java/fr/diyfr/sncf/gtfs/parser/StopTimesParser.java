package fr.diyfr.sncf.gtfs.parser;

import fr.diyfr.sncf.gtfs.model.StopTimes;

public class StopTimesParser extends CSVParser<StopTimes>{

	public StopTimesParser(Class<StopTimes> clasz) {
		super(clasz);
	}

}
