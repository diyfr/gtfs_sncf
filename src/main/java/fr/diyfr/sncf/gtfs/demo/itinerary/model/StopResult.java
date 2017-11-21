package fr.diyfr.sncf.gtfs.demo.itinerary.model;

import java.util.Arrays;

public class StopResult {

	
	String codeUIC;
	String stop_id;
	String parentUIC[];
	/**
	 * @return the codeUIC
	 */
	public String getCodeUIC() {
		return codeUIC;
	}
	/**
	 * @param codeUIC the codeUIC to set
	 */
	public void setCodeUIC(String codeUIC) {
		this.codeUIC = codeUIC;
	}
	/**
	 * @return the stop_id
	 */
	public String getStop_id() {
		return stop_id;
	}
	/**
	 * @param stop_id the stop_id to set
	 */
	public void setStop_id(String stop_id) {
		this.stop_id = stop_id;
	}
	/**
	 * @return the parentUIC
	 */
	public String[] getParentUIC() {
		return parentUIC;
	}
	/**
	 * @param parentUIC the parentUIC to set
	 */
	public void setParentUIC(String[] parentUIC) {
		this.parentUIC = parentUIC;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{");
		if (codeUIC != null) {
			builder.append("codeUIC\":\"");
			builder.append(codeUIC);
			builder.append("\", ");
		}
		if (stop_id != null) {
			builder.append("stop_id\":\"");
			builder.append(stop_id);
			builder.append("\", ");
		}
		if (parentUIC != null) {
			builder.append("parentUIC\":\"");
			builder.append(Arrays.toString(parentUIC));
		}
		builder.append(" }");
		return builder.toString();
	}
}
