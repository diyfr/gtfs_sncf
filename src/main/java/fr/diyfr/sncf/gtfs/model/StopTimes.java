package fr.diyfr.sncf.gtfs.model;

import fr.diyfr.sncf.gtfs.exception.FileFormatException;

public class StopTimes implements GTFSModel {
	private String trip_id;// contient le numéro du train
	private StopTime arrival_time;// horaire arrivée
	private StopTime departure_time;//horaires depart
	private String stop_id;// code de la gare
	private int stop_sequence;// ordre des horaires
	private String stop_headsign;//nu
	private int pickup_type;//nu
	private int drop_off_type;//nu
	private String shape_dist_traveled;//nu

	@Override
	public void fromStringArray(String[] value) throws FileFormatException {
		try {
			this.trip_id = value[0];
			this.arrival_time = new StopTime();
			arrival_time.fromString(value[1]);
			this.departure_time = new StopTime();
			this.departure_time.fromString(value[2]);
			this.stop_id = value[3];
			this.stop_sequence = Integer.parseInt(value[4]);
			this.stop_headsign = value[5].replace("\"", "");
			this.pickup_type = Integer.parseInt(value[6]);
			this.drop_off_type = Integer.parseInt(value[7]);
			this.shape_dist_traveled = value[8];
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new FileFormatException(e.getLocalizedMessage());
		} catch (NumberFormatException n) {
			throw new FileFormatException(n.getLocalizedMessage());
		}
	}

	/**
	 * @return the trip_id
	 */
	public String getTrip_id() {
		return trip_id;
	}

	/**
	 * @param trip_id
	 *            the trip_id to set
	 */
	public void setTrip_id(String trip_id) {
		this.trip_id = trip_id;
	}

	/**
	 * @return the arrival_time
	 */
	public StopTime getArrival_time() {
		return arrival_time;
	}

	/**
	 * @param arrival_time
	 *            the arrival_time to set
	 */
	public void setArrival_time(StopTime arrival_time) {
		this.arrival_time = arrival_time;
	}

	/**
	 * @return the departure_time
	 */
	public StopTime getDeparture_time() {
		return departure_time;
	}

	/**
	 * @param departure_time
	 *            the departure_time to set
	 */
	public void setDeparture_time(StopTime departure_time) {
		this.departure_time = departure_time;
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
	 * @return the stop_sequence
	 */
	public int getStop_sequence() {
		return stop_sequence;
	}

	/**
	 * @param stop_sequence
	 *            the stop_sequence to set
	 */
	public void setStop_sequence(int stop_sequence) {
		this.stop_sequence = stop_sequence;
	}

	/**
	 * @return the stop_headsign
	 */
	public String getStop_headsign() {
		return stop_headsign;
	}

	/**
	 * @param stop_headsign
	 *            the stop_headsign to set
	 */
	public void setStop_headsign(String stop_headsign) {
		this.stop_headsign = stop_headsign;
	}

	/**
	 * @return the pickup_type
	 */
	public int getPickup_type() {
		return pickup_type;
	}

	/**
	 * @param pickup_type
	 *            the pickup_type to set
	 */
	public void setPickup_type(int pickup_type) {
		this.pickup_type = pickup_type;
	}

	/**
	 * @return the drop_off_type
	 */
	public int getDrop_off_type() {
		return drop_off_type;
	}

	/**
	 * @param drop_off_type
	 *            the drop_off_type to set
	 */
	public void setDrop_off_type(int drop_off_type) {
		this.drop_off_type = drop_off_type;
	}

	/**
	 * @return the shape_dist_traveled
	 */
	public String getShape_dist_traveled() {
		return shape_dist_traveled;
	}

	/**
	 * @param shape_dist_traveled
	 *            the shape_dist_traveled to set
	 */
	public void setShape_dist_traveled(String shape_dist_traveled) {
		this.shape_dist_traveled = shape_dist_traveled;
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
		return "{" + (trip_id != null ? "\"trip_id\":\"" + trip_id + "\", " : "")
				+ (arrival_time != null ? "\"arrival_time\":" + arrival_time.toString() + ", " : "")
				+ (departure_time != null ? "\"departure_time\":" + departure_time.toString() + ", " : "") + (stop_id != null ? "\"stop_id\":\"" + stop_id + "\", " : "")
				+ "\"stop_sequence\":" + stop_sequence + ", " + (stop_headsign != null && !stop_headsign.isEmpty()? "\"stop_headsign\":\"" + stop_headsign + "\", " : "")
				+ "\"pickup_type\":" + pickup_type + ", \"drop_off_type\":" + drop_off_type + ", "
				+ (shape_dist_traveled != null && !shape_dist_traveled.isEmpty() ? "\"shape_dist_traveled\":\"" + shape_dist_traveled+"\"" : "") + "}";
	}

}