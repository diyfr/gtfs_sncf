package fr.diyfr.sncf.gtfs.demo.search.helper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import fr.diyfr.sncf.gtfs.demo.load.model.GTFSData;
import fr.diyfr.sncf.gtfs.demo.search.model.*;
import fr.diyfr.sncf.gtfs.model.Agency;
import fr.diyfr.sncf.gtfs.model.CalendarDates;
import fr.diyfr.sncf.gtfs.model.StopTimes;
import fr.diyfr.sncf.gtfs.model.Stops;
import fr.diyfr.sncf.gtfs.model.Trips;

/**
 * Classe de démonstration permettant d'exploiter les données GTFS
 * 
 */
public class GTFSSearchHelper {

	/**
	 * Fournit les informations GTFS pour un numéro de train
	 * 
	 * @param data non NULL
	 * @param trainNumber non NULL
	 * @return
	 */
	public static GTFSData getGTFSRawDataForTrainNumber(GTFSData data, String trainNumber) {
		GTFSData result = new GTFSData();
		for (Map.Entry<String, Trips> e : data.getTrips().entrySet()) {
			if (e.getValue().getTrip_headsign().equals(trainNumber)) {
				result.getTrips().put(e.getKey(), e.getValue());
				Trips trip = e.getValue();
				result.getRoutes().put(trip.getRoute_id(), data.getRoutes().get(trip.getRoute_id()));

				Agency agency = data.getAgencys().get(data.getRoutes().get(trip.getRoute_id()).getAgency_id());
				if (!result.getAgencys().containsKey(agency.getId())) {
					result.getAgencys().put(agency.getId(), agency);
				}
				if (!result.getCalendar().containsKey(trip.getService_id())) {
					result.getCalendar().put(trip.getService_id(), data.getCalendar().get(trip.getService_id()));
					result.getCalendarDates().put(trip.getService_id(), data.getCalendarDates().get(trip.getService_id()));
				}

				result.getStopTimes().put(trip.getTrip_id(), data.getStopTimes().get(trip.getTrip_id()));

				List<StopTimes> stTimes = data.getStopTimes().get(trip.getTrip_id());
				for (StopTimes stopTime : stTimes) {
					if (!result.getStops().containsKey(stopTime.getStop_id())) {
						result.getStops().put(stopTime.getStop_id(), data.getStops().get(stopTime.getStop_id()));
					}
				}
			}
		}
		return result;
	}

	/**
	 * Fournit les informations GTFS pour une date de circulation. A noter que
	 * si un train effectue un saut de nuit il est pris aussi en compte si son
	 * arrivée concerne la date fournie
	 * 
	 * @param data non NULL
	 * @param dateTrain non NULL
	 * @return
	 */
	public static GTFSData getGTFSRawDataForDate(GTFSData data, Date dateTrain) {
		GTFSData result = new GTFSData();
		// Liste des service pour date=dateTrain
		List<Integer> lstService = getCalendarForThisDate(data, dateTrain);
		for (Map.Entry<String, Trips> e : data.getTrips().entrySet()) {
			// Si service ID existe dans la liste valable pour dateTrain
			if (!lstService.contains(e.getValue().getService_id())) {
				continue;
			}

			result.getTrips().put(e.getKey(), e.getValue());
			Trips trip = e.getValue();
			result.getRoutes().put(trip.getRoute_id(), data.getRoutes().get(trip.getRoute_id()));

			Agency agency = data.getAgencys().get(data.getRoutes().get(trip.getRoute_id()).getAgency_id());
			if (!result.getAgencys().containsKey(agency.getId())) {
				result.getAgencys().put(agency.getId(), agency);
			}
			
			if (!result.getCalendar().containsKey(trip.getService_id())) {
				result.getCalendar().put(trip.getService_id(), data.getCalendar().get(trip.getService_id()));
				result.getCalendarDates().put(trip.getService_id(), data.getCalendarDates().get(trip.getService_id()));
			}

			// On ajoute les dessertes de cette circulation
			result.getStopTimes().put(trip.getTrip_id(), data.getStopTimes().get(trip.getTrip_id()));

			// Pour chaque desserte on ajoute uniquement les gares concernées
			List<StopTimes> stTimes = data.getStopTimes().get(trip.getTrip_id());
			for (StopTimes stopTime : stTimes) {
				if (!result.getStops().containsKey(stopTime.getStop_id())) {
					result.getStops().put(stopTime.getStop_id(), data.getStops().get(stopTime.getStop_id()));
				}
			}
		}

		// Liste des service pour les trains partis la veille
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateTrain);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		List<Integer> lstServiceBefore = getCalendarForThisDate(data, cal.getTime());
		for (Map.Entry<String, Trips> e : data.getTrips().entrySet()) {
			// On s'assure que la circulation n'a pas déjà été ajoutées
			if (!result.getTrips().containsKey(e.getKey())) {
				//Si service ID existe dans la liste valable pour dateTrain -1jour
				if (!lstServiceBefore.contains(e.getValue().getService_id())) {
					continue;
				}
				// On regarde les horaires des desserts si elles sont concernées par un saut de nuit
				List<StopTimes> stTimes = data.getStopTimes().get(e.getValue().getTrip_id());
				boolean startYesterday = false;
				for (StopTimes stopTime : stTimes) {
					if (stopTime.getArrival_time().isMidnightJump() || stopTime.getDeparture_time().isMidnightJump()) {
						// Desserte concernée par un saut de nuit
						startYesterday = true;
						break;
					}
				}
				if (!startYesterday) {
					// non concerné
					continue;
				}
				
				// On ajoute les données de la circulation
				result.getTrips().put(e.getKey(), e.getValue());
				Trips trip = e.getValue();
				result.getRoutes().put(trip.getRoute_id(), data.getRoutes().get(trip.getRoute_id()));

				Agency agency = data.getAgencys().get(data.getRoutes().get(trip.getRoute_id()).getAgency_id());
				if (!result.getAgencys().containsKey(agency.getId())) {
					result.getAgencys().put(agency.getId(), agency);
				}
				if (!result.getCalendar().containsKey(trip.getService_id())) {
					result.getCalendar().put(trip.getService_id(), data.getCalendar().get(trip.getService_id()));
					result.getCalendarDates().put(trip.getService_id(), data.getCalendarDates().get(trip.getService_id()));
				}

				result.getStopTimes().put(trip.getTrip_id(), data.getStopTimes().get(trip.getTrip_id()));

				for (StopTimes stopTime : stTimes) {
					if (!result.getStops().containsKey(stopTime.getStop_id())) {
						result.getStops().put(stopTime.getStop_id(), data.getStops().get(stopTime.getStop_id()));
					}
				}
			}
		}

		return result;
	}

	/**
	 * Fournit les informations GTFS pour un numéro de train et une date de
	 * circulation. A noter que si le train effectue un saut de nuit il est pris
	 * aussi en compte si son arrivée concerne la date fournie
	 * 
	 * @param data
	 * @param trainNumber non NULL
	 * @param dateTrain non NULL
	 * @return
	 */
	public static GTFSData getGTFSRawDataForTrainNumberAndDate(GTFSData data, String trainNumber, Date dateTrain) {
		GTFSData result = new GTFSData();
		List<Integer> lstService = getCalendarForThisDate(data, dateTrain);
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateTrain);
		cal.add(Calendar.DAY_OF_MONTH, -1);

		for (Map.Entry<String, Trips> e : data.getTrips().entrySet()) {
			if (e.getValue().getTrip_headsign().equals(trainNumber)) {
				if (!lstService.contains(e.getValue().getService_id())) {
					continue;
				}

				result.getTrips().put(e.getKey(), e.getValue());
				Trips trip = e.getValue();
				result.getRoutes().put(trip.getRoute_id(), data.getRoutes().get(trip.getRoute_id()));

				Agency agency = data.getAgencys().get(data.getRoutes().get(trip.getRoute_id()).getAgency_id());
				if (!result.getAgencys().containsKey(agency.getId())) {
					result.getAgencys().put(agency.getId(), agency);
				}
				if (!result.getCalendar().containsKey(trip.getService_id())) {
					result.getCalendar().put(trip.getService_id(), data.getCalendar().get(trip.getService_id()));
					result.getCalendarDates().put(trip.getService_id(), data.getCalendarDates().get(trip.getService_id()));
				}

				result.getStopTimes().put(trip.getTrip_id(), data.getStopTimes().get(trip.getTrip_id()));

				List<StopTimes> stTimes = data.getStopTimes().get(trip.getTrip_id());
				for (StopTimes stopTime : stTimes) {
					if (!result.getStops().containsKey(stopTime.getStop_id())) {
						result.getStops().put(stopTime.getStop_id(), data.getStops().get(stopTime.getStop_id()));
					}
				}
			}
		}

		// Saut de nuit
		List<Integer> lstServiceBefore = getCalendarForThisDate(data, cal.getTime());
		for (Map.Entry<String, Trips> e : data.getTrips().entrySet()) {
			if (e.getValue().getTrip_headsign().equals(trainNumber) && !result.getTrips().containsKey(e.getKey())) {

				if (!lstServiceBefore.contains(e.getValue().getService_id())) {
					continue;
				}

				List<StopTimes> stTimes = data.getStopTimes().get(e.getValue().getTrip_id());
				boolean startYesterday = false;
				for (StopTimes stopTime : stTimes) {
					if (stopTime.getArrival_time().isMidnightJump() || stopTime.getDeparture_time().isMidnightJump()) {
						startYesterday = true;
						break;
					}
				}
				if (!startYesterday) {
					continue;
				}
				result.getTrips().put(e.getKey(), e.getValue());
				Trips trip = e.getValue();
				result.getRoutes().put(trip.getRoute_id(), data.getRoutes().get(trip.getRoute_id()));

				Agency agency = data.getAgencys().get(data.getRoutes().get(trip.getRoute_id()).getAgency_id());
				if (!result.getAgencys().containsKey(agency.getId())) {
					result.getAgencys().put(agency.getId(), agency);
				}
				if (!result.getCalendar().containsKey(trip.getService_id())) {
					result.getCalendar().put(trip.getService_id(), data.getCalendar().get(trip.getService_id()));
					result.getCalendarDates().put(trip.getService_id(), data.getCalendarDates().get(trip.getService_id()));
				}

				result.getStopTimes().put(trip.getTrip_id(), data.getStopTimes().get(trip.getTrip_id()));

				for (StopTimes stopTime : stTimes) {
					if (!result.getStops().containsKey(stopTime.getStop_id())) {
						result.getStops().put(stopTime.getStop_id(), data.getStops().get(stopTime.getStop_id()));
					}
				}
			}
		}

		return result;
	}

	/**
	 * Retourne une liste de service_id valable pour la date fournie
	 * 
	 * @param data non NULL
	 * @param dateSearch non NULL
	 * @return
	 */
	private static List<Integer> getCalendarForThisDate(GTFSData data, Date dateSearch) {
		List<Integer> result = new ArrayList<Integer>();
		for (Map.Entry<Integer, fr.diyfr.sncf.gtfs.model.Calendar> e : data.getCalendar().entrySet()) {
			fr.diyfr.sncf.gtfs.model.Calendar cal = e.getValue();
			if (dateSearch.compareTo(cal.getEnd_date()) <= 0 && dateSearch.compareTo(cal.getStart_date()) >= 0) {
				List<CalendarDates> dates = data.getCalendarDates().get(cal.getService_id());
				if (dates != null && !dates.isEmpty()) {
					boolean exclude = false;
					for (CalendarDates oneDate : dates) {
						if (oneDate.getDate().compareTo(dateSearch) == 0) {
							exclude = true;
							break;
						}
					}
					if (exclude) {
						continue;
					}
				}
				Calendar calDate = Calendar.getInstance();
				calDate.setTime(dateSearch);
				int day = calDate.get(Calendar.DAY_OF_WEEK);

				if (!cal.isFriday() && day == Calendar.FRIDAY) {
					continue;
				}
				if (!cal.isMonday() && day == Calendar.MONDAY) {
					continue;
				}
				if (!cal.isSaturday() && day == Calendar.SATURDAY) {
					continue;
				}
				if (!cal.isSunday() && day == Calendar.SUNDAY) {
					continue;
				}
				if (!cal.isThursday() && day == Calendar.THURSDAY) {
					continue;
				}
				if (!cal.isTuesday() && day == Calendar.TUESDAY) {
					continue;
				}
				if (!cal.isWednesday() && day == Calendar.WEDNESDAY) {
					continue;
				}
				result.add(cal.getService_id());
			}
		}
		return result;
	}

	/**
	 * Retourne un objet épuré contenant un ensemble de train et de dessertes
	 * associée en fonction d'un numéro de train et éventuellement une date si
	 * celle-ci est fournie
	 * 
	 * @param global non NULL
	 * @return
	 */
	public static StopList getSimpleFormatStopList(GTFSData global) {
		StopList result = new StopList();
		for (Map.Entry<String, Stops> e : global.getStops().entrySet()) {
			Stop oneStop = new Stop();
			Stops stops = e.getValue();
			oneStop.setLatitude(stops.getStop_lat());
			oneStop.setLongitude(stops.getStop_lon());
			String stopCode = stops.getStop_id().replaceAll("\\D+", "");
			oneStop.setUIC(stopCode);
			oneStop.setName(stops.getStop_name());
			result.getStopList().add(oneStop);
		}
		return result;
	}

	/**
	 * Retourne un objet épuré contenant un ensemble de train et de dessertes
	 * associée en fonction d'un numéro de train et éventuellement une date si
	 * celle-ci est fournie
	 * 
	 * @param global non NULL
	 * @param trainNumber non NULL
	 * @param dateTrain can NULL Value
	 * @return
	 */
	public static TrainList getSimpleFormatTrainDateList(GTFSData global, String trainNumber, Date dateTrain) {
		TrainList result = new TrainList();
		GTFSData filteredData = null;
		if (dateTrain == null) {
			filteredData = getGTFSRawDataForTrainNumber(global, trainNumber);
		} else {
			filteredData = getGTFSRawDataForTrainNumberAndDate(global, trainNumber, dateTrain);
		}

		for (Map.Entry<String, Trips> e : filteredData.getTrips().entrySet()) {
			Train unTrain = new Train();
			unTrain.setExternalCode(e.getValue().getTrip_id());
			unTrain.setNumber(e.getValue().getTrip_headsign());
			String agencyId = filteredData.getRoutes().get(e.getValue().getRoute_id()).getAgency_id();
			unTrain.setAgency(filteredData.getAgencys().get(agencyId).getName());
			unTrain.setCourse(filteredData.getRoutes().get(e.getValue().getRoute_id()).getRoute_long_name());
			List<StopTimes> stopList = filteredData.getStopTimes().get(e.getValue().getTrip_id());
			if (stopList != null && !stopList.isEmpty()) {
				for (StopTimes stimes : stopList) {
					Stop oneStop = new Stop();
					Stops stops = filteredData.getStops().get(stimes.getStop_id());
					oneStop.setLatitude(stops.getStop_lat());
					oneStop.setLongitude(stops.getStop_lon());
					String stopCode = stops.getStop_id().replaceAll("\\D+", "");
					oneStop.setUIC(stopCode);
					oneStop.setName(stops.getStop_name());

					if (stimes.getStop_sequence() != stopList.size() - 1) {
						if (dateTrain != null) {
							Date departure = DateHelper.concatDateTime(dateTrain, stimes.getDeparture_time());
							oneStop.setTheoricOutDateTime(departure);
						} else {
							oneStop.setTheoricOutTime(stimes.getDeparture_time().getTime());
						}
					} else {
						if (dateTrain != null) {
							Date arrival = DateHelper.concatDateTime(dateTrain, stimes.getArrival_time());
							unTrain.setDestinationDateTime(arrival);
						}
						unTrain.setDestinationUIC(stopCode);
						unTrain.setDestination(stops.getStop_name());

					}
					if (stimes.getStop_sequence() != 0) {
						if (dateTrain != null) {
							Date arrival = DateHelper.concatDateTime(dateTrain, stimes.getArrival_time());
							oneStop.setTheoricInDateTime(arrival);
						} else {
							oneStop.setTheoricInTime(stimes.getArrival_time().getTime());
						}
					} else {
						if (dateTrain != null) {
							Date departure = DateHelper.concatDateTime(dateTrain, stimes.getDeparture_time());
							unTrain.setOriginDateTime(departure);
						}
						unTrain.setOriginUIC(stopCode);
						unTrain.setOrigin(stops.getStop_name());
					}
					unTrain.getStopList().add(oneStop);
				}
			}
			unTrain.setTrafficConditions(getTrafficCondition(filteredData, e.getValue().getService_id(), dateTrain));
			result.getTrainList().add(unTrain);
		}

		return result;
	}

	/**
	 * Génération d'une chaine de caractère représentant les conditions de
	 * circulations d'un train
	 * 
	 * @param filteredData non NULL
	 * @param serviceId non NULL
	 * @param dateTrain can NULL Value
	 *            Si elle est prise en compte dans la rédaction du résultat
	 * @return
	 */
	private static String getTrafficCondition(GTFSData filteredData, int serviceId, Date dateTrain) {
		SimpleDateFormat spf = new SimpleDateFormat("dd MMM", Locale.FRANCE);
		SimpleDateFormat spfg = new SimpleDateFormat("dd/MM/YYYY", Locale.FRANCE);
		StringBuilder st = new StringBuilder();
		fr.diyfr.sncf.gtfs.model.Calendar cal = filteredData.getCalendar().get(serviceId);
		if (dateTrain != null) {
			st.append(" Jusqu'au ");
		} else {
			st.append("Circule du ");
			st.append(spfg.format(cal.getStart_date()));
			st.append(" au ");
		}
		st.append(spfg.format(cal.getEnd_date()));

		st.append(" : ");
		st.append(DateHelper.getTrafficDayOfWeekToString(cal));
		st.append(" ");
		// Exceptions
		List<CalendarDates> exce = filteredData.getCalendarDates().get(serviceId);
		int nbExe = 0;
		StringBuilder exception = new StringBuilder();
		if (exce != null && !exce.isEmpty()) {
			for (CalendarDates cald : exce) {
				if (dateTrain == null || cald.getDate().compareTo(dateTrain) >= 0) {
					exception.append(spf.format(cald.getDate()));
					exception.append(", ");
					nbExe++;
				}
			}
			if (nbExe > 0) {
				st.append("sauf le");
			}
			if (nbExe > 1) {
				st.append("s");
			}
			if (nbExe > 0) {
				st.append(" ");
				st.append(exception.toString());
			}
		}
		String result = st.toString().replace(".,", ",");
		if (result.substring(result.length() - 2, result.length()).equals(", ")) {
			result = result.substring(0, result.length() - 2);
		}

		return result;
	}

}
