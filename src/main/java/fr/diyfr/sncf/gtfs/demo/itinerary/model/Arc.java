package fr.diyfr.sncf.gtfs.demo.itinerary.model;

public class Arc {
	String a;
	String b;
	int distance;

	/**
	 * @return the a
	 */
	public String getA() {
		return a;
	}

	/**
	 * @param a
	 *            the a to set
	 */
	public void setA(String a) {
		this.a = a;
	}

	/**
	 * @return the b
	 */
	public String getB() {
		return b;
	}

	/**
	 * @param b
	 *            the b to set
	 */
	public void setB(String b) {
		this.b = b;
	}

	@Override
	public boolean equals(Object arg0) {
		boolean result = false;
		if (arg0 instanceof Arc) {
			Arc tmp = (Arc) arg0;
			result = (tmp.a.equals(this.a) && tmp.b.equals(this.b)) || (tmp.a.equals(this.b) && tmp.b.equals(this.a));
		}
		return result;
	}

	/**
	 * @return the distance
	 */
	public int getDistance() {
		return distance;
	}

	/**
	 * @param distance
	 *            the distance to set
	 */
	public void setDistance(int distance) {
		this.distance = distance;
	}

	public void updateDistance(Arc obj) {
		this.distance = (this.distance > obj.getDistance()) ? obj.getDistance() : this.distance;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("arc(");
		if (a != null) {
			builder.append(a);
		}
		builder.append(",");
		if (b != null) {
			builder.append(b);
		}
		builder.append(")=");
		builder.append(distance);
		builder.append("");
		return builder.toString();
	}

}
