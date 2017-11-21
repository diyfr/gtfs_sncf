package fr.diyfr.sncf.gtfs.model;

import fr.diyfr.sncf.gtfs.exception.FileFormatException;

public final class Agency implements GTFSModel {

	private String id;

	private String name;

	private String url;

	private String timezone;

	private String lang;

	@Override
	public void fromStringArray(String[] value) throws FileFormatException {
		this.id = value[0];
		this.name = value[1].replace("\"", "");
		this.url = value[2];
		this.timezone = value[3];
		this.lang = value[4];
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	@Override
	public int getNbEntry() {

		return 5;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "{" + (id != null ? "\"id\":\"" + id + "\", " : "") + (name != null ? "\"name\":\"" + name + "\", " : "")
				+ (url != null ? "\"url\":\"" + url + "\", " : "") + (timezone != null ? "\"timezone\":\"" + timezone + "\", " : "")
				+ (lang != null ? "\"lang\":\"" + lang + "\"" : "") + "}";
	}
}
