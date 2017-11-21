package fr.diyfr.sncf.gtfs.demo.load.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.diyfr.sncf.gtfs.model.*;

/**
 * Représentation des données GTFS déserialisées
 *
 */
public class GTFSData {

	private Map<String, Agency> agency;
	private Map<Integer, Calendar> calendar;
	private Map<Integer, List<CalendarDates>> calendarDates;
	private Map<String, Routes> routes;
	private Map<String, Stops> stops;
	private Map<String, List<StopTimes>> stopTimes;
	private List<Transfers> transfers;
	private Map<String, Trips> trips;

	public GTFSData() {
		this.agency = new HashMap<String, Agency>();
		this.calendar = new HashMap<Integer, Calendar>();
		this.calendarDates = new HashMap<Integer, List<CalendarDates>>();
		this.routes = new HashMap<String, Routes>();
		this.stops = new HashMap<String, Stops>();
		this.stopTimes = new HashMap<String, List<StopTimes>>();
		this.transfers = new ArrayList<Transfers>();
		this.trips = new HashMap<String, Trips>();
	}

	/**
	 * @return the agency
	 */
	public Map<String, Agency> getAgencys() {
		return agency;
	}

	/**
	 * @param agency
	 *            the agency to set
	 */
	public void setAgencys(Map<String, Agency> agency) {
		this.agency = agency;
	}

	/**
	 * @return the calendar
	 */
	public Map<Integer, Calendar> getCalendar() {
		return calendar;
	}

	/**
	 * @param calendar
	 *            the calendar to set
	 */
	public void setCalendar(Map<Integer, Calendar> calendar) {
		this.calendar = calendar;
	}

	/**
	 * @return the calendarDates
	 */
	public Map<Integer, List<CalendarDates>> getCalendarDates() {
		return calendarDates;
	}

	/**
	 * @param calendarDates
	 *            the calendarDates to set
	 */
	public void setCalendarDates(Map<Integer, List<CalendarDates>> calendarDates) {
		this.calendarDates = calendarDates;
	}

	/**
	 * @return the routes
	 */
	public Map<String, Routes> getRoutes() {
		return routes;
	}

	/**
	 * @param routes
	 *            the routes to set
	 */
	public void setRoutes(Map<String, Routes> routes) {
		this.routes = routes;
	}

	/**
	 * @return the stops
	 */
	public Map<String, Stops> getStops() {
		return stops;
	}

	/**
	 * @param stops
	 *            the stops to set
	 */
	public void setStops(Map<String, Stops> stops) {
		this.stops = stops;
	}

	/**
	 * @return the stopTimes
	 */
	public Map<String, List<StopTimes>> getStopTimes() {
		return stopTimes;
	}

	/**
	 * @param stopTimes
	 *            the stopTimes to set
	 */
	public void setStopTimes(Map<String, List<StopTimes>> stopTimes) {
		this.stopTimes = stopTimes;
	}

	/**
	 * @return the transfers
	 */
	public List<Transfers> getTransfers() {
		return transfers;
	}

	/**
	 * @param transfers
	 *            the transfers to set
	 */
	public void setTransfers(List<Transfers> transfers) {
		this.transfers = transfers;
	}

	/**
	 * @return the trips
	 */
	public Map<String, Trips> getTrips() {
		return trips;
	}

	/**
	 * @param trips
	 *            the trips to set
	 */
	public void setTrips(Map<String, Trips> trips) {
		this.trips = trips;
	}

	/**
	 * Surcharge de la méthode toString Ici les éléments sont fournis au format
	 * JSON
	 * 
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{\"data\":{ ");
		if (agency != null && agency.size() > 0) {
			builder.append("\"agencies\":[");
			for (Agency ag : agency.values()) {
				builder.append(ag);
				builder.append(",");
			}
			builder.append("], ");
		}
		if (calendar != null && calendar.size() > 0) {
			builder.append("\"calendars\":[");
			for (Calendar cal : calendar.values()) {
				builder.append(cal);
				builder.append(",");
			}
			builder.append("], ");
		}
		if (calendarDates != null && calendarDates.size() > 0) {
			builder.append("\"calendarDates\":[");
			for (List<CalendarDates> listdate : calendarDates.values()) {
				if (listdate != null && !listdate.isEmpty()) {
					for (CalendarDates cdate : listdate) {
						builder.append(cdate);
						builder.append(",");
					}
				}
			}
			builder.append("], ");
		}
		if (routes != null && routes.size() > 0) {

			builder.append("\"routes\":[");
			for (Routes route : routes.values()) {
				builder.append(route);
				builder.append(",");
			}
			builder.append("], ");
		}
		if (stops != null && stops.size() > 0) {
			builder.append("\"stops\":[");
			for (Stops stop : stops.values()) {
				builder.append(stop);
				builder.append(",");
			}
			builder.append("], ");
		}
		if (stopTimes != null && stopTimes.size() > 0) {
			builder.append("\"stopTimes\":[");
			for (List<StopTimes> liststoptimes : stopTimes.values()) {
				if (liststoptimes != null && !liststoptimes.isEmpty()) {
					for (StopTimes stop : liststoptimes) {
						builder.append(stop);
						builder.append(",");
					}
				}
			}
			builder.append("], ");
		}
		if (transfers != null && transfers.size() > 0) {
			builder.append("\"transfers\":[");
			for (Transfers transfert : transfers) {
				builder.append(transfert);
				builder.append(",");
			}
			builder.append("], ");
		}
		if (trips != null && trips.size() > 0) {
			builder.append("\"trips\":[");
			for (Trips trip : trips.values()) {
				builder.append(trip);
				builder.append(",");
			}
			builder.append("]");
		}
		builder.append(" }}");
		return builder.toString().replace(",]", "]").replace(", }", "}");
	}

}
