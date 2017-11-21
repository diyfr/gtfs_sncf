package fr.diyfr.sncf.gtfs.demo.search.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Classe représentant un ensemble de circulation
 */
public class TrainList {

	private List<Train> trainList;

	public TrainList() {
		this.trainList = new LinkedList<Train>();
	}

	/**
	 * Liste de circulations
	 * 
	 * @return the trainList
	 */
	public List<Train> getTrainList() {
		return trainList;
	}

	/**
	 * Liste de circulations
	 * 
	 * @param trainList
	 *            the trainList to set
	 */
	public void setTrainList(List<Train> trainList) {
		this.trainList = trainList;
	}

	/**
	 * Surcharge de la méthode toString Ici les éléments sont fournis au format
	 * JSON
	 * 
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{\"trainList\":");
		if (trainList != null) {

			builder.append("[");
			builder.append(trainList);
			builder.append("]");
		} else {
			builder.append("null");
		}
		builder.append("}");
		return builder.toString();
	}

}
