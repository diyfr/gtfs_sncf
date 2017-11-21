package fr.diyfr.sncf.gtfs.demo.load.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.diyfr.sncf.gtfs.parser.*;
import fr.diyfr.sncf.gtfs.demo.load.model.GTFSData;
import fr.diyfr.sncf.gtfs.exception.FileAccessException;
import fr.diyfr.sncf.gtfs.model.*;

/**
 * Helper pour charger les données GTFS
 * A optimiser ca
 * 
 */
public class GTFSLoaderHelper {
	private static String COMMA_SEP = ",";
	private static String AGENCY_FILENAME = "agency.txt";
	private static String CALENDAR_FILENAME = "calendar.txt";
	private static String CALENDAR_DATES_FILENAME = "calendar_dates.txt";
	private static String ROUTES_FILENAME = "routes.txt";
	private static String STOPTIMES_FILENAME = "stop_times.txt";
	private static String STOPS_FILENAME = "stops.txt";
	private static String TRANSFERS_FILENAME = "transfers.txt";
	private static String TRIPS_FILENAME = "trips.txt";

	/**
	 * Charge toutes les données en fonction du chemin fourni en paramètre
	 * 
	 * @param directory
	 *            ex "D:\\MONDOSSIER\\"
	 * @return GTFSData All data in one Object
	 */
	public static GTFSData readGTFS(String directory) throws FileAccessException {
		GTFSData result = new GTFSData();

		AgencyParser agencyParser = new AgencyParser(Agency.class);
		List<Agency> agencys = agencyParser.getAll(directory + AGENCY_FILENAME, COMMA_SEP);
		Map<String, Agency> mapAgencys = new HashMap<String, Agency>();
		for (Agency ag : agencys) {
			mapAgencys.put(ag.getId(), ag);
		}
		result.setAgencys(mapAgencys);
		agencys.clear();
		agencys = null;

		CalendarDatesParser calDatesParser = new CalendarDatesParser(CalendarDates.class);
		List<CalendarDates> calDates = calDatesParser.getAll(directory + CALENDAR_DATES_FILENAME, COMMA_SEP);
		Map<Integer, List<CalendarDates>> mapCalendarDates = new HashMap<Integer, List<CalendarDates>>();
		for (CalendarDates cal : calDates) {
			if (mapCalendarDates.containsKey(cal.getService_id())) {
				mapCalendarDates.get(cal.getService_id()).add(cal);
			} else {
				List<CalendarDates> lst = new ArrayList<CalendarDates>();
				lst.add(cal);
				mapCalendarDates.put(cal.getService_id(), lst);
			}
		}
		result.setCalendarDates(mapCalendarDates);
		calDates.clear();
		calDates = null;

		CalendarParser calParser = new CalendarParser(Calendar.class);
		List<Calendar> calendars = calParser.getAll(directory + CALENDAR_FILENAME, COMMA_SEP);
		Map<Integer, Calendar> mapCalendars = new HashMap<Integer, Calendar>();
		for (Calendar c : calendars) {
			mapCalendars.put(c.getService_id(), c);
		}
		result.setCalendar(mapCalendars);
		calendars.clear();
		calendars = null;

		RoutesParser routesParser = new RoutesParser(Routes.class);
		Map<String, Routes> mapRoutes = new HashMap<String, Routes>();
		List<Routes> routes = routesParser.getAll(directory + ROUTES_FILENAME, COMMA_SEP);
		for (Routes route : routes) {
			mapRoutes.put(route.getRoute_id(), route);
		}
		result.setRoutes(mapRoutes);
		routes.clear();
		routes = null;

		StopsParser stopsParser = new StopsParser(Stops.class);
		Map<String, Stops> mapStops = new HashMap<String, Stops>();
		List<Stops> stops = stopsParser.getAll(directory + STOPS_FILENAME, COMMA_SEP);
		for (Stops stop : stops) {
			mapStops.put(stop.getStop_id(), stop);
		}
		result.setStops(mapStops);
		stops.clear();
		stops = null;

		TripsParser tripsParser = new TripsParser(Trips.class);
		List<Trips> trips = tripsParser.getAll(directory + TRIPS_FILENAME, COMMA_SEP);
		Map<String, Trips> mapTrips = new HashMap<String, Trips>();
		for (Trips trip : trips) {
			mapTrips.put(trip.getTrip_id(), trip);
		}
		result.setTrips(mapTrips);
		trips.clear();
		trips = null;

		StopTimesParser stopTimesParser = new StopTimesParser(StopTimes.class);
		List<StopTimes> stopTimes = stopTimesParser.getAll(directory + STOPTIMES_FILENAME, COMMA_SEP);
		Map<String, List<StopTimes>> mapStopTimes = new HashMap<String, List<StopTimes>>();
		for (StopTimes stop : stopTimes) {
			if (mapStopTimes.containsKey(stop.getTrip_id())) {
				mapStopTimes.get(stop.getTrip_id()).add(stop);
			} else {
				List<StopTimes> lst = new ArrayList<StopTimes>();
				lst.add(stop);
				mapStopTimes.put(stop.getTrip_id(), lst);
			}
		}
		result.setStopTimes(mapStopTimes);
		stopTimes.clear();
		stopTimes = null;

		TransfersParser transfersParser = new TransfersParser(Transfers.class);
		result.setTransfers(transfersParser.getAll(directory + TRANSFERS_FILENAME, COMMA_SEP));
		return result;
	}
}
