package fr.diyfr.sncf.gtfs.demo.search.model;

import java.util.Date;

import fr.diyfr.sncf.gtfs.demo.search.helper.DateHelper;

/**
 * Desserte d'un train
 *
 */
public class Stop {

	private String UIC;
	private String name;
	private Date theoricInDateTime = null;
	private Date theoricInTime = null;
	private Date theoricOutDateTime = null;
	private Date theoricOutTime = null;
	private double longitude = 0L;
	private double latitude = 0L;

	/**
	 * Code UIC (Union Internationale des Chemins de Fer
	 * 
	 * @return the uIC
	 */
	public String getUIC() {
		return UIC;
	}

	/**
	 * Code UIC (Union Internationale des Chemins de Fer
	 * 
	 * @param uIC
	 *            the uIC to set
	 */
	public void setUIC(String uIC) {
		UIC = uIC;
	}

	/**
	 * Nom de la gare
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Nom de la gare
	 * 
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Date/heure d'arrivée théorique
	 * 
	 * @return the theoricInDateTime
	 */
	public Date getTheoricInDateTime() {
		return theoricInDateTime;
	}

	/**
	 * Heure d'arrivée théorique
	 * 
	 * @return
	 */
	public Date getTheoricInTime() {
		return theoricInTime;
	}

	/**
	 * Heure d'arrivée théorique
	 * 
	 * @param theoricInTime
	 */
	public void setTheoricInTime(Date theoricInTime) {
		this.theoricInTime = theoricInTime;
	}

	/**
	 * Date/heure d'arrivée théorique
	 * 
	 * @param theoricInDateTime
	 *            the theoricInDateTime to set
	 */
	public void setTheoricInDateTime(Date theoricInDateTime) {
		this.theoricInDateTime = theoricInDateTime;
	}

	/**
	 * Date/heure de départ théorique
	 * 
	 * @return the theoricOutDateTime
	 */
	public Date getTheoricOutDateTime() {
		return theoricOutDateTime;
	}

	/**
	 * Date/heure de départ théorique
	 * 
	 * @param theoricOutDateTime
	 *            the theoricOutDateTime to set
	 */
	public void setTheoricOutDateTime(Date theoricOutDateTime) {
		this.theoricOutDateTime = theoricOutDateTime;
	}

	/**
	 * Heure de départ théorique
	 * 
	 * @return
	 */
	public Date getTheoricOutTime() {
		return theoricOutTime;
	}

	/**
	 * Heure de départ théorique
	 * 
	 * @param theoricOutTime
	 */
	public void setTheoricOutTime(Date theoricOutTime) {
		this.theoricOutTime = theoricOutTime;
	}

	/**
	 * Longitude de la gare
	 * 
	 * @return
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * Longitude de la gare
	 * 
	 * @param longitude
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * Latitude de la gare
	 * 
	 * @return
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * Latitude de la gare
	 * @param latitude
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * Surcharge de la méthode toString
	 * Ici les éléments sont fournis au format JSON
	 * 
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{");
		if (UIC != null) {
			builder.append("\"UIC\":\"");
			builder.append(UIC);
			builder.append("\", ");
		}
		if (name != null) {
			builder.append("\"name\":\"");
			builder.append(name);
			builder.append("\", ");
		}

		if (theoricInTime != null) {
			builder.append("\"theoricInTime\":\"");
			builder.append(DateHelper.getJsonFormatTime(theoricInTime));
			builder.append("\", ");
		}
		if (theoricOutTime != null) {
			builder.append("\"theoricOutTime\":\"");
			builder.append(DateHelper.getJsonFormatTime(theoricOutTime));
			builder.append("\", ");
		}

		if (theoricInDateTime != null) {
			builder.append("\"theoricInDateTime\":\"");
			builder.append(DateHelper.getJsonFormatDateTime(theoricInDateTime));
			builder.append("\", ");
		}
		if (theoricOutDateTime != null) {
			builder.append("\"theoricOutDateTime\":\"");
			builder.append(DateHelper.getJsonFormatDateTime(theoricOutDateTime));
			builder.append("\", ");
		}
		builder.append("\"longitude\":");
		builder.append(longitude);
		builder.append(", \"latitude\":");
		builder.append(latitude);
		builder.append(" }");
		return builder.toString();
	}

}
