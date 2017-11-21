package fr.diyfr.sncf.gtfs.model;

import fr.diyfr.sncf.gtfs.exception.FileFormatException;

public class Stops implements GTFSModel {
	private String stop_id;
	private String stop_name;
	private String stop_desc;// nu
	private double stop_lat;
	private double stop_lon;
	private String zone_id;// nu
	private String stop_url;// nu
	private int location_type;
	private String parent_station;

	@Override
	public void fromStringArray(String[] value) throws FileFormatException {
		try {
			this.stop_id = value[0];
			this.stop_name = value[1].replace("\"", "").replace("gare de ", "");
			this.stop_desc = value[2];
			this.stop_lat = Double.parseDouble(value[3]);
			this.stop_lon =  Double.parseDouble(value[4]);
			this.zone_id = value[5];
			this.stop_url = value[6];
			this.location_type = Integer.parseInt(value[7]);
			this.parent_station = value[8];
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new FileFormatException(e.getLocalizedMessage());
		} catch (NumberFormatException n) {
			throw new FileFormatException(n.getLocalizedMessage());
		}
	}

	/**
	 * @return the stop_id
	 */
	public String getStop_id() {
		return stop_id;
	}

	/**
	 * @param stop_id
	 *            the stop_id to set
	 */
	public void setStop_id(String stop_id) {
		this.stop_id = stop_id;
	}

	/**
	 * @return the stop_name
	 */
	public String getStop_name() {
		return stop_name;
	}

	/**
	 * @param stop_name
	 *            the stop_name to set
	 */
	public void setStop_name(String stop_name) {
		this.stop_name = stop_name;
	}

	/**
	 * @return the stop_desc
	 */
	public String getStop_desc() {
		return stop_desc;
	}

	/**
	 * @param stop_desc
	 *            the stop_desc to set
	 */
	public void setStop_desc(String stop_desc) {
		this.stop_desc = stop_desc;
	}

	/**
	 * @return the stop_lat
	 */
	public double getStop_lat() {
		return stop_lat;
	}

	/**
	 * @param stop_lat
	 *            the stop_lat to set
	 */
	public void setStop_lat(double stop_lat) {
		this.stop_lat = stop_lat;
	}

	/**
	 * @return the stop_lon
	 */
	public double getStop_lon() {
		return stop_lon;
	}

	/**
	 * @param stop_lon
	 *            the stop_lon to set
	 */
	public void setStop_lon(double stop_lon) {
		this.stop_lon = stop_lon;
	}

	/**
	 * @return the zone_id
	 */
	public String getZone_id() {
		return zone_id;
	}

	/**
	 * @param zone_id
	 *            the zone_id to set
	 */
	public void setZone_id(String zone_id) {
		this.zone_id = zone_id;
	}

	/**
	 * @return the stop_url
	 */
	public String getStop_url() {
		return stop_url;
	}

	/**
	 * @param stop_url
	 *            the stop_url to set
	 */
	public void setStop_url(String stop_url) {
		this.stop_url = stop_url;
	}

	/**
	 * @return the location_type
	 */
	public int getLocation_type() {
		return location_type;
	}

	/**
	 * @param location_type
	 *            the location_type to set
	 */
	public void setLocation_type(int location_type) {
		this.location_type = location_type;
	}

	/**
	 * @return the parent_station
	 */
	public String getParent_station() {
		return parent_station;
	}

	/**
	 * @param parent_station
	 *            the parent_station to set
	 */
	public void setParent_station(String parent_station) {
		this.parent_station = parent_station;
	}

	@Override
	public int getNbEntry() {
		return 9;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "{" + (stop_id != null ? "\"stop_id\":\"" + stop_id + "\", " : "") + (stop_name != null ? "\"stop_name\":\"" + stop_name + "\", " : "")
				+ (stop_desc != null && !stop_desc.isEmpty() ? "\"stop_desc\":\"" + stop_desc + "\", " : "") + "\"stop_lat\":" + stop_lat + ", \"stop_lon\":" + stop_lon + ", "
				+ (zone_id != null && !zone_id.isEmpty() ? "\"zone_id\":\"" + zone_id + "\", " : "") + (stop_url != null && !stop_url.isEmpty()? "\"stop_url\":\"" + stop_url + "\", " : "")
				+ "\"location_type\":" + location_type + ", " + (parent_station != null ? "\"parent_station\":\"" + parent_station+"\"" : "") + " }";
	}
	
}