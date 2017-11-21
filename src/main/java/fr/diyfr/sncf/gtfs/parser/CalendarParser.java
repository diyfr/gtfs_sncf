package fr.diyfr.sncf.gtfs.parser;

import fr.diyfr.sncf.gtfs.model.Calendar;

public class CalendarParser extends CSVParser<Calendar>{

	public CalendarParser(Class<Calendar> clasz) {
		super(clasz);
	}

}
