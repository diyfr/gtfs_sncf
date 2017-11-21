package fr.diyfr.sncf.gtfs.exception;

public class FileAccessException  extends Exception {

	private static final long serialVersionUID = -3403942448303772417L;

	public FileAccessException(String localizedMessage) {
		super(localizedMessage);
	}
}
