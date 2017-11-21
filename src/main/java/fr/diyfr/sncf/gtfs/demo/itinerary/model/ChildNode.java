package fr.diyfr.sncf.gtfs.demo.itinerary.model;

public class ChildNode {

	String idChild;
	Arc arcParent;
	int distanceA;
	int distanceB;

	
	public ChildNode(String id)
	{
		this.idChild =id;
	}
	
	/**
	 * @return the idParent
	 */
	public Arc getArcParent() {
		return arcParent;
	}

	/**
	 * @param idParent
	 *            the idParent to set
	 */
	public void setArcParent(Arc arcParent) {
		this.arcParent = arcParent;
	}

	/**
	 * @return the distanceA
	 */
	public int getDistanceA() {
		return distanceA;
	}

	/**
	 * @param distanceA
	 *            the distanceA to set
	 */
	public void setDistanceA(int distanceA) {
		this.distanceA = distanceA;
	}

	/**
	 * @return the distanceB
	 */
	public int getDistanceB() {
		return distanceB;
	}

	/**
	 * @param distanceB
	 *            the distanceB to set
	 */
	public void setDistanceB(int distanceB) {
		this.distanceB = distanceB;
	}

	/**
	 * @return the idChild
	 */
	public String getIdChild() {
		return idChild;
	}

	/**
	 * @param idChild
	 *            the idChild to set
	 */
	public void setIdChild(String idChild) {
		this.idChild = idChild;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj instanceof ChildNode) {
			ChildNode tmp = (ChildNode) obj;
			if (tmp.getIdChild().equals(this.idChild)) {
				result = (this.idChild.equals(tmp.getIdChild()));
			}
		}
		return result;
	}

	public void updateDist(ChildNode obj) {
		if (obj.getArcParent().getDistance()< this.arcParent.getDistance())
		{
			this.arcParent = obj.getArcParent();
			this.distanceA=obj.getDistanceA();
			this.distanceB=obj.getDistanceB();
		}
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("child[");
		if (idChild != null) {
			builder.append(idChild);
		}
		builder.append("=>");
		if (arcParent != null) {
			builder.append(arcParent);
		}
		builder.append("=(");
		builder.append(distanceA);
		builder.append(",");
		builder.append(distanceB);
		builder.append(")]");
		return builder.toString();
	}
}
