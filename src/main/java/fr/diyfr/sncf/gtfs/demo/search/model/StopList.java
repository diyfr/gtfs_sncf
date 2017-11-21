package fr.diyfr.sncf.gtfs.demo.search.model;

import java.util.LinkedList;
import java.util.List;

public class StopList {
	private List<Stop> stopList;

	public StopList() {
		this.stopList = new LinkedList<Stop>();
	}

	/**
	 * Liste de circulations
	 * 
	 * @return the trainList
	 */
	public List<Stop> getStopList() {
		return stopList;
	}

	/**
	 * Liste de circulations
	 * 
	 * @param trainList
	 *            the trainList to set
	 */
	public void setTrainList(List<Stop> trainList) {
		this.stopList = trainList;
	}

	/**
	 * Surcharge de la méthode toString Ici les éléments sont fournis au format
	 * JSON
	 * 
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{\"stopList\":");
		if (stopList != null) {
			builder.append(stopList);
		} else {
			builder.append("null");
		}
		builder.append("}");
		return builder.toString();
	}
}
