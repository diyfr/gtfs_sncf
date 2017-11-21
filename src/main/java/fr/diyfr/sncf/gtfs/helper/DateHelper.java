package fr.diyfr.sncf.gtfs.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 
 * Helper de manipulation des dates
 *
 */
public class DateHelper {

	private static final String FORMAT_GTFS = "yyyyMMdd";
	private static final SimpleDateFormat spf_FORMAT_GTFS = new SimpleDateFormat(FORMAT_GTFS);

	private static final String FORMAT_SHORT_DATE = "yyyy-MM-dd";
	private static final SimpleDateFormat spf_FORMAT_SHORT_DATE = new SimpleDateFormat(FORMAT_SHORT_DATE, Locale.FRANCE);

	/**
	 * Fournit une chaine représentant une date
	 * 
	 * @param dateTime
	 * @return
	 */
	public static String getShortDateFormat(Date dateTime) {
		String result = null;
		result = spf_FORMAT_SHORT_DATE.format(dateTime);
		return result;
	}

	/**
	 * Fournit une chaine représentant une date
	 * 
	 * @param dateTime
	 * @return
	 */
	public static Date getShortDateFormatFromString(String value) {
		Date result = null;
		try {
			result = spf_FORMAT_SHORT_DATE.parse(value);
		} catch (ParseException e) {
		}
		return result;
	}
	
	/**
	 * Converti une chaine en provenance du fichier GTFS en Date
	 * 
	 * @param calendarGTFSDate
	 * @return
	 * @throws ParseException
	 */
	public static Date getDateFromGTFSCalendar(String calendarGTFSDate) throws ParseException {
		Date result = null;
		result = spf_FORMAT_GTFS.parse(calendarGTFSDate);
		return result;
	}
}
