package fr.diyfr.sncf.gtfs.demo.search.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Classe représentant une Circulation
 */
public class Train {

	private String course;
	private String number;
	private String externalCode;
	private String agency;
	private String originUIC;
	private String origin;
	private Date originDateTime;
	private String destinationUIC;
	private String destination;
	private Date destinationDateTime;
	private String trafficConditions;
	private List<Stop> stopList;

	public Train() {
		this.stopList = new LinkedList<Stop>();
	}

	/**
	 * Parcours du train
	 * 
	 * @return
	 */
	public String getCourse() {
		return course;
	}

	/**
	 * Parcours du train
	 * 
	 * @param course
	 */
	public void setCourse(String course) {
		this.course = course;
	}

	/**
	 * Numéro du train
	 * 
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * Numéro du train
	 * 
	 * @param number
	 *            the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * Code GTFS (trips)
	 * 
	 * @return the externalCode
	 */
	public String getExternalCode() {
		return externalCode;
	}

	/**
	 * Code GTFS (trips)
	 * 
	 * @param externalCode
	 *            the externalCode to set
	 */
	public void setExternalCode(String externalCode) {
		this.externalCode = externalCode;
	}

	/**
	 * Transporteur
	 * 
	 * @return the agency
	 */
	public String getAgency() {
		return agency;
	}

	/**
	 * Transporteur
	 * 
	 * @param agency
	 *            the agency to set
	 */
	public void setAgency(String agency) {
		this.agency = agency;
	}

	/**
	 * Code UIC de la gare d'origine
	 * 
	 * @return the originUIC
	 */
	public String getOriginUIC() {
		return originUIC;
	}

	/**
	 * Code UIC de la gare d'origine
	 * 
	 * @param originUIC
	 *            the originUIC to set
	 */
	public void setOriginUIC(String originUIC) {
		this.originUIC = originUIC;
	}

	/**
	 * Nom de la gare d'origine
	 * 
	 * @return the origin
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * Nom de la gare d'origine
	 * 
	 * @param origin
	 *            the origin to set
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	/**
	 * Date/heure départ à l'origine
	 * 
	 * @return the originDateTime
	 */
	public Date getOriginDateTime() {
		return originDateTime;
	}

	/**
	 * Date/heure départ à l'origine
	 * 
	 * @param originDateTime
	 *            the originDateTime to set
	 */
	public void setOriginDateTime(Date originDateTime) {
		this.originDateTime = originDateTime;
	}

	/**
	 * Code UIC de la gare de destination
	 * 
	 * @return the destinationUIC
	 */
	public String getDestinationUIC() {
		return destinationUIC;
	}

	/**
	 * Code UIC de la gare de destination
	 * 
	 * @param destinationUIC
	 *            the destinationUIC to set
	 */
	public void setDestinationUIC(String destinationUIC) {
		this.destinationUIC = destinationUIC;
	}

	/**
	 * Nom de la gare de destination
	 * 
	 * @return the destination
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * Nom de la gare de destination
	 * 
	 * @param destination
	 *            the destination to set
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}

	/**
	 * Date/heure d'arrivée à destination
	 * 
	 * @return the destinationDateTime
	 */
	public Date getDestinationDateTime() {
		return destinationDateTime;
	}

	/**
	 * Date/heure d'arrivée à destination
	 * 
	 * @param destinationDateTime
	 *            the destinationDateTime to set
	 */
	public void setDestinationDateTime(Date destinationDateTime) {
		this.destinationDateTime = destinationDateTime;
	}

	/**
	 * Liste des dessertes
	 * 
	 * @return the stopList
	 */
	public List<Stop> getStopList() {
		return stopList;
	}

	/**
	 * Liste des dessertes
	 * 
	 * @param stopList
	 *            the stopList to set
	 */
	public void setStopList(List<Stop> stopList) {
		this.stopList = stopList;
	}

	/**
	 * Conditions de circulation
	 * 
	 * @return
	 */
	public String getTrafficConditions() {
		return trafficConditions;
	}

	/**
	 * Conditions de circulation
	 * 
	 * @param trafficConditions
	 */
	public void setTrafficConditions(String trafficConditions) {
		this.trafficConditions = trafficConditions;
	}

	/**
	 * Surcharge de la méthode toString Ici les éléments sont fournis au format
	 * JSON
	 * 
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{");
		if (course != null) {
			builder.append("\"course\":\"");
			builder.append(course);
			builder.append("\", ");
		}
		if (number != null) {
			builder.append("\"number\":\"");
			builder.append(number);
			builder.append("\", ");
		}
		if (externalCode != null) {
			builder.append("\"externalCode\":\"");
			builder.append(externalCode);
			builder.append("\", ");
		}
		if (agency != null) {
			builder.append("\"agency\":\"");
			builder.append(agency);
			builder.append("\", ");
		}
		if (originUIC != null) {
			builder.append("\"originUIC\":\"");
			builder.append(originUIC);
			builder.append("\", ");
		}
		if (origin != null) {
			builder.append("\"origin\":\"");
			builder.append(origin);
			builder.append("\", ");
		}
		if (originDateTime != null) {
			builder.append("\"originDateTime\":\"");
			builder.append(originDateTime);
			builder.append("\", ");
		}
		if (destinationUIC != null) {
			builder.append("\"destinationUIC\":\"");
			builder.append(destinationUIC);
			builder.append("\", ");
		}
		if (destination != null) {
			builder.append("\"destination\":\"");
			builder.append(destination);
			builder.append("\", ");
		}
		if (destinationDateTime != null) {
			builder.append("\"destinationDateTime\":\"");
			builder.append(destinationDateTime);
			builder.append("\", ");
		}
		if (trafficConditions != null) {
			builder.append("\"trafficConditions\":\"");
			builder.append(trafficConditions);
			builder.append("\", ");
		}
		if (stopList != null) {
			builder.append("\"stopList\":[");
			builder.append(stopList);
			builder.append("]");
		}
		builder.append(" }");
		return builder.toString();
	}
}
