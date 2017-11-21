package fr.diyfr.sncf.gtfs.exception;

public class FileFormatException extends Exception {

	private static final long serialVersionUID = -6052303547916983958L;

	public FileFormatException(String localizedMessage) {
		super(localizedMessage);
	}

}
