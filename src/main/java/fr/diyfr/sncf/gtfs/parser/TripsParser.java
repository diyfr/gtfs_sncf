package fr.diyfr.sncf.gtfs.parser;
import fr.diyfr.sncf.gtfs.model.Trips;

public class TripsParser extends CSVParser<Trips>{

	public TripsParser(Class<Trips> clasz) {
		super(clasz);
	}

}
