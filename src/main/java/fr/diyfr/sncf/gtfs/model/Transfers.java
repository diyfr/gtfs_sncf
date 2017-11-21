package fr.diyfr.sncf.gtfs.model;

import fr.diyfr.sncf.gtfs.exception.FileFormatException;

public class Transfers implements GTFSModel {

	private String from_stop_id, to_stop_id, transfer_type, min_transfer_time;

	@Override
	public void fromStringArray(String[] value) throws FileFormatException {
		try {
			this.from_stop_id = value[0];
			this.to_stop_id = value[1];
			this.transfer_type = value[2];
			this.min_transfer_time = value[3];
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new FileFormatException(e.getLocalizedMessage());
		}

	}

	/**
	 * @return the from_stop_id
	 */
	public String getFrom_stop_id() {
		return from_stop_id;
	}

	/**
	 * @param from_stop_id
	 *            the from_stop_id to set
	 */
	public void setFrom_stop_id(String from_stop_id) {
		this.from_stop_id = from_stop_id;
	}

	/**
	 * @return the to_stop_id
	 */
	public String getTo_stop_id() {
		return to_stop_id;
	}

	/**
	 * @param to_stop_id
	 *            the to_stop_id to set
	 */
	public void setTo_stop_id(String to_stop_id) {
		this.to_stop_id = to_stop_id;
	}

	/**
	 * @return the transfer_type
	 */
	public String getTransfer_type() {
		return transfer_type;
	}

	/**
	 * @param transfer_type
	 *            the transfer_type to set
	 */
	public void setTransfer_type(String transfer_type) {
		this.transfer_type = transfer_type;
	}

	/**
	 * @return the min_transfer_time
	 */
	public String getMin_transfer_time() {
		return min_transfer_time;
	}

	/**
	 * @param min_transfer_time
	 *            the min_transfer_time to set
	 */
	public void setMin_transfer_time(String min_transfer_time) {
		this.min_transfer_time = min_transfer_time;
	}

	@Override
	public int getNbEntry() {
		return 4;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "\"transfers\":{" + (from_stop_id != null ? "\"from_stop_id\":\"" + from_stop_id + "\", " : "")
				+ (to_stop_id != null ? "\"to_stop_id\":\"" + to_stop_id + "\", " : "")
				+ (transfer_type != null ? "\"transfer_type\":\"" + transfer_type + "\", " : "")
				+ (min_transfer_time != null ? "\"min_transfer_time\":\"" + min_transfer_time : "") + " }";
	}

}
